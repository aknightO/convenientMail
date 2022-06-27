package file;
import domian.SendMailVO;
import utils.MailUtils;

import java.io.*;
import java.nio.file.Files;

/**
 * @author yue.sun
 * @date 2022-06-24
 */
public class FileSliceAndGlue {
    private static final String FILE_PART_EXT = ".parts";
    private static final Integer BUFFER_SIZE = 1024;
    /**
     * 分割文件
     * @param file          文件
     * @param filePieceSize 每个文件大小
     * @return 文件输出路径
     */
    public static String sliceAndSendMail(File file, int filePieceSize, SendMailVO sendMailVO) {
        if (!file.isFile()) {
            return null;
        }
        /**
         *  默认19M
         */
        if (filePieceSize == -1) {
            filePieceSize = 1024 * 1024 * 19;
        }
        int howManyParts = calcParts(file.length(), filePieceSize);
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            /**
             * 获取保存文件路径,不存在则创建
             */
            String[] split = file.getName().split("\\.");
            String oPath = file.getParent() + "/" + file.getName() + "_" + System.currentTimeMillis();
            if (!(new File(oPath).exists())) {
                new File(oPath).mkdirs();
            }
            /**
             * 循环读取内容,分配到不同的区间
             */
            int len = -1;
            // 计算文件的hash
            String ext = split[split.length - 1];
            // 定义每次读取的字节数
            byte[] buffer = new byte[1024];

            for (int i = 1; i <= howManyParts; i++) {
                synchronized (FileSliceAndGlue.class){
                    System.out.println("开始第" + i + "次的内容分配");
                    int total = 0;
                    // 生成的文件名
                    String fileName = oPath + "/" + file.getName() + "_" + i + "." + ext + FILE_PART_EXT;
                    FileOutputStream fos = new FileOutputStream(fileName);
                    while ((len = inputStream.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                        total += len;
                        if (total == filePieceSize) {
                            break;
                        }
                    }
                    fos.close();
                    //2.通过邮件发送出去
                   boolean sendSuccess = MailUtils.sendMail(sendMailVO,fileName);
                    System.out.println("开始第"+i+"个文件，文件名称：["+fileName+"]发送是否成功？" +
                            (sendSuccess?"   Success   ":"   Fail   "));
                }
            }
            return oPath;
        } catch (IOException e) {
            System.out.println("文件流读取失败: " + e.getMessage());
            return null;
        } finally {
            if (inputStream != null) {
                /**
                 * 关闭流
                 */
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 计算要分割多少个文件
     *
     * @param length        文件大小
     * @param filePieceSize 每个文件的大小
     * @return int 分为多少块
     */
    private static int calcParts(double length, int filePieceSize) {
        return (int) Math.ceil(length / (double) filePieceSize);
    }

    /**
     * 将分割好的文件重新链接
     *
     * @param filePath 被分割好的其中之一文件路径，默认其他块与其在同一目录下
     * @return 成功返回True，出错则返回False
     */
    public static String glue(String filePath,String newFilePath) {
        System.out.println("开始文件合并，文件地址："+filePath);
        long start = System.currentTimeMillis();
        BufferedOutputStream dest = null;
        BufferedInputStream is = null;

        File files = new File(filePath);
        File[] tempList = files.listFiles((dir, name) -> name.endsWith(FILE_PART_EXT));
        if (tempList.length == 0) {
            return null;
        }

        try {
            // 输出文件保存路径
            File outPutFile = new File(newFilePath);
            if (!outPutFile.exists()) {
                outPutFile.mkdirs();
            }
            // 获取
            int lastIndexOf = filePath.lastIndexOf("_");
            String combineFileName = newFilePath + filePath.substring(filePath.lastIndexOf("/") + 1, lastIndexOf);
            FileOutputStream fos = new FileOutputStream(combineFileName);

            for (int i = 0; i < tempList.length; i++) {
                File file = tempList[i];
                is = new BufferedInputStream(Files.newInputStream(file.toPath()));
                int count;
                byte[] dataByte = new byte[BUFFER_SIZE];
                dest = new BufferedOutputStream(fos,BUFFER_SIZE);
                while ((count = is.read(dataByte,0,BUFFER_SIZE))!=-1){
                    dest.write(dataByte,0,count);
                }
                dest.flush();
            }
            dest.close();
            fos.close();
            long end = System.currentTimeMillis();
            System.out.println("合并文件耗时："+(end-start)+"毫秒");
            return combineFileName;
        } catch (IOException e) {
            System.out.println("文件流读取失败: " + e.getMessage());
            return null;
        }
    }
}

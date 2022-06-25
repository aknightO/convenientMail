import domian.SendMailVO;
import file.DirDelete;
import file.FileSliceAndGlue;
import swing.TFream;
import utils.ZipUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class RunApplication {

    public static void main(String[] args) {
        new TFream();
    }

//    public static void main(String[] args) throws FileNotFoundException {
//          String myEmailAccount = "1412824257@qq.com";
//        // 发件人邮箱密码（授权码）
//        // 在开启SMTP服务时会获取到一个授权码，把授权码填在这里
//          String myEmailPassword = "otutlqgukwohbaci";
//
//          String otherEmail = "1412824257@qq.com";
//
//        //1.输入的文件需要先进行压缩为.zip文件
//        String filepath = "/Users/sy_mbp/Desktop/testFileMail/test.pdf";
//        String zipFilePath = filepath.substring(0, filepath.lastIndexOf(".")) + ".zip";
//        FileOutputStream zipFileOpt = new FileOutputStream(new File(zipFilePath));
//        ZipUtils.toZip(filepath, zipFileOpt);
//        System.out.println("step2:创建压缩文件地址为：" + zipFilePath);
//
//        //2.获取压缩文件地址，然后通过压缩分片文件并通过邮件发送
//        //构建发送Vo
//        SendMailVO sendMailVO = SendMailVO.buildSendMail(myEmailAccount,myEmailPassword,otherEmail);
//        String sliceFilePath = FileSliceAndGlue.sliceAndSendMail(new File(zipFilePath), -1,sendMailVO);
//        System.out.println("创建文件分片文件夹为：" + sliceFilePath);
//
//        //5.测试文件合并
//        String glueDir = "/Users/sy_mbp/Desktop/sjsj/";
//        String glueAfterAbsoluteFilePath = FileSliceAndGlue.glue(sliceFilePath, glueDir);
//        System.out.println("step5:合并后文件夹绝对路径为：" + glueAfterAbsoluteFilePath);
//
//        //6.把合并好的文件进行解压缩
//        boolean unZipIsSuccess = ZipUtils.unzip(glueAfterAbsoluteFilePath,glueDir);
//        System.out.println("step6:解压zip文件夹是否成功？" + (unZipIsSuccess ? "   Success   " : "   Fail   "));
//
//        //3.删除本地分片文件夹
//        boolean deleteFileDirIsSuccess = DirDelete.delFolder(sliceFilePath);
//        System.out.println("step3:删除本地分片文件夹是否成功？" + (deleteFileDirIsSuccess ? "   Success   " : "   Fail   "));
//        //4.删除本地压缩zip文件
//        boolean deleteZipFileIsSuccess = new File(zipFilePath).delete();
//        System.out.println("step4:删除本地zip文件是否成功？" + (deleteZipFileIsSuccess ? "   Success   " : "   Fail   "));
//    }
}

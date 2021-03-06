package swing;

import domian.SendMailVO;
import file.DirDelete;
import file.FileSliceAndGlue;
import utils.MailUtils;
import utils.ZipUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;

/**
 * 发送邮件GUI界面
 * @author yue.sun
 * @date 2022-06.26
 * **/
public class SendMailAction implements ActionListener{
    TFream tf = null;
    private static final Long fileSize19M = 1024 * 1024 * 19L;
    public SendMailAction(TFream tf){
        this.tf = tf;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            //1.点击按钮
            String sourceFilePath = this.tf.jtf1.getText();
            String selfQQAccount = this.tf.jtf2.getText();
            String authorizationCode = this.tf.jtf3.getText();
            String otherQQAccount = this.tf.jtf4.getText();
            SendMailVO sendMailVO = SendMailVO.buildSendMail(selfQQAccount, authorizationCode ,otherQQAccount);

            //1.输入的文件需要先进行压缩为.zip文件
            String filepath = sourceFilePath;
            String zipFilePath = "";
            if(!sourceFilePath.endsWith(".zip")){
                zipFilePath = filepath.substring(0, filepath.lastIndexOf(".")) + ".zip";
                FileOutputStream zipFileOpt = new FileOutputStream(new File(zipFilePath));
                ZipUtils.toZip(filepath, zipFileOpt,this.tf.jta);
                this.tf.jta.append("step2:创建压缩文件地址为：" + zipFilePath);
                System.out.println("step2:创建压缩文件地址为：" + zipFilePath);
            }else{
                zipFilePath = filepath;
            }

            //2.获取压缩文件地址，然后通过压缩分片文件并通过邮件发送
            //如果文件没有大于19M，那么将不会进行分片，只会进行压缩
            File zipFile = new File(zipFilePath);
            if(zipFile.length() >= fileSize19M){
                String sliceFilePath = FileSliceAndGlue.sliceAndSendMail(zipFile, -1,sendMailVO);
                this.tf.jta.append("创建文件分片文件夹为：" + sliceFilePath+"发送分片文件到邮箱成功！");
                System.out.println("创建文件分片文件夹为：" + sliceFilePath);

                //5.测试文件合并
//            String glueDir = "/Users/sy_mbp/Desktop/sjsj/";
//            String glueAfterAbsoluteFilePath = FileSliceAndGlue.glue(sliceFilePath, glueDir);
//            this.tf.jta.append("step5:合并后文件夹绝对路径为：" + glueAfterAbsoluteFilePath);
//            System.out.println("step5:合并后文件夹绝对路径为：" + glueAfterAbsoluteFilePath);
//
//            //6.把合并好的文件进行解压缩
//            boolean unZipIsSuccess = ZipUtils.unzip(glueAfterAbsoluteFilePath,glueDir);
//            this.tf.jta.append("step6:解压zip文件夹是否成功？" + (unZipIsSuccess ? "   Success   " : "   Fail   "));
//            System.out.println("step6:解压zip文件夹是否成功？" + (unZipIsSuccess ? "   Success   " : "   Fail   "));

                //3.删除本地分片文件夹
                boolean deleteFileDirIsSuccess = DirDelete.delFolder(sliceFilePath);
                this.tf.jta.append("step3:删除本地分片文件夹是否成功？" + (deleteFileDirIsSuccess ? "   Success   " : "   Fail   "));
                System.out.println("step3:删除本地分片文件夹是否成功？" + (deleteFileDirIsSuccess ? "   Success   " : "   Fail   "));
            }else{
                boolean sendSuccess = MailUtils.sendMail(sendMailVO,zipFilePath);
                System.out.println("文件小于19M，文件名称：["+zipFilePath+"]发送是否成功？" +
                        (sendSuccess?"   Success   ":"   Fail   "));
            }

            //如果本身就是.zip文件不需要删除
            if (filepath.endsWith(".zip")){
                return;
            }
            //4.删除本地压缩zip文件
            boolean deleteZipFileIsSuccess = new File(zipFilePath).delete();
            this.tf.jta.append("step4:删除本地zip文件是否成功？" + (deleteZipFileIsSuccess ? "   Success   " : "   Fail   "));
            System.out.println("step4:删除本地zip文件是否成功？" + (deleteZipFileIsSuccess ? "   Success   " : "   Fail   "));
        }catch (Exception exception){
            throw new RuntimeException("邮件发送失败！");
        }
    }
}

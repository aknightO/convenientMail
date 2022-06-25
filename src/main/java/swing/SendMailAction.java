package swing;

import domian.SendMailVO;
import file.DirDelete;
import file.FileSliceAndGlue;
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
            String zipFilePath = filepath.substring(0, filepath.lastIndexOf(".")) + ".zip";
            FileOutputStream zipFileOpt = new FileOutputStream(new File(zipFilePath));
            ZipUtils.toZip(filepath, zipFileOpt,this.tf.jta);
            this.tf.jta.append("step2:创建压缩文件地址为：" + zipFilePath);
            System.out.println("step2:创建压缩文件地址为：" + zipFilePath);

            //2.获取压缩文件地址，然后通过压缩分片文件并通过邮件发送
            String sliceFilePath = FileSliceAndGlue.sliceAndSendMail(new File(zipFilePath), -1,sendMailVO);
            this.tf.jta.append("创建文件分片文件夹为：" + sliceFilePath);
            System.out.println("创建文件分片文件夹为：" + sliceFilePath);

            //5.测试文件合并
            String glueDir = "/Users/sy_mbp/Desktop/sjsj/";
            String glueAfterAbsoluteFilePath = FileSliceAndGlue.glue(sliceFilePath, glueDir);
            this.tf.jta.append("step5:合并后文件夹绝对路径为：" + glueAfterAbsoluteFilePath);
            System.out.println("step5:合并后文件夹绝对路径为：" + glueAfterAbsoluteFilePath);

            //6.把合并好的文件进行解压缩
            boolean unZipIsSuccess = ZipUtils.unzip(glueAfterAbsoluteFilePath,glueDir);
            this.tf.jta.append("step6:解压zip文件夹是否成功？" + (unZipIsSuccess ? "   Success   " : "   Fail   "));
            System.out.println("step6:解压zip文件夹是否成功？" + (unZipIsSuccess ? "   Success   " : "   Fail   "));

            //3.删除本地分片文件夹
            boolean deleteFileDirIsSuccess = DirDelete.delFolder(sliceFilePath);
            this.tf.jta.append("step3:删除本地分片文件夹是否成功？" + (deleteFileDirIsSuccess ? "   Success   " : "   Fail   "));
            System.out.println("step3:删除本地分片文件夹是否成功？" + (deleteFileDirIsSuccess ? "   Success   " : "   Fail   "));

            //4.删除本地压缩zip文件
            boolean deleteZipFileIsSuccess = new File(zipFilePath).delete();
            this.tf.jta.append("step4:删除本地zip文件是否成功？" + (deleteZipFileIsSuccess ? "   Success   " : "   Fail   "));
            System.out.println("step4:删除本地zip文件是否成功？" + (deleteZipFileIsSuccess ? "   Success   " : "   Fail   "));
        }catch (Exception exception){
            throw new RuntimeException("邮件发送失败！");
        }
    }
}

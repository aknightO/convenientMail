package utils;

import com.sun.mail.util.MailSSLSocketFactory;
import domian.SendMailVO;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

/**
 * @author yue.sun
 * @date 2022-06-24
 */
public class MailUtils {
    private static String myEmailAccount = "1412824257@qq.com";
    // 发件人邮箱密码（授权码）
    // 在开启SMTP服务时会获取到一个授权码，把授权码填在这里
    private static String myEmailPassword = "otutlqgukwohbaci";

    private static String otherEmail = "1412824257@qq.com";

    /**
     * 使用QQ邮箱发送邮件
     * **/
    public  static boolean sendMail(SendMailVO sendMailVO,String fileName) {
        try{
            Properties properties = new Properties();
            //设置QQ邮件服务器
            properties.setProperty("mail.host","smtp.qq.com");
            //邮件发送协议
            properties.setProperty("mail.transport.protocol","smtp");
            //需要验证用户名密码
            properties.setProperty("mail.smtp.auth","true");

            //还要设置SSL加密，加上以下代码即可
            MailSSLSocketFactory mailSSLSocketFactory = new MailSSLSocketFactory();
            mailSSLSocketFactory.setTrustAllHosts(true);
            properties.put("mail.smtp.ssl.enable","true");
            properties.put("mail.smtp.ssl.socketFactory",mailSSLSocketFactory);

            //使用JavaMail发送邮件的5个步骤
            //1、创建定义整个应用程序所需环境信息的 Session 对象
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    //发件人用户名，授权码
                    return new PasswordAuthentication(myEmailAccount,myEmailPassword);
                }
            });
            //开启Session的debug模式，这样就可以查看程序发送Email的运行状态
            session.setDebug(true);
            //2、通过session得到transport对象
            Transport transport = session.getTransport();
            //3、使用用户名和授权码连上邮件服务器
            transport.connect("smtp.qq.com",sendMailVO.getMyEmailAccount(),sendMailVO.getMyEmailPassword());
            //4、创建邮件：写邮件
            //注意需要传递Session
            MimeMessage message = new MimeMessage(session);
            //指明邮件的发件人
            // InternetAddress 的三个参数分别为: 发件人邮箱, 显示的昵称(只用于显示, 没有特别的要求), 昵称的字符集编码
            message.setFrom(new InternetAddress(sendMailVO.getMyEmailAccount()));
            //指明邮件的收件人，现在发件人和收件人是一样的，就是自己给自己发
            message.setRecipient(Message.RecipientType.TO , new InternetAddress(sendMailVO.getOtherSendEmailAccount(),
                    "yue.sun","UTF-8"));
            //邮件标题(存在没有分割的时候)
            if (fileName.contains(".parts")){
                message.setSubject(fileName.substring(fileName.lastIndexOf("/")+1,fileName.lastIndexOf(".parts")));
            }else {
                message.setSubject(fileName);
            }
            message.setContent(fileName,"text/html;charset=UTF-8");
            // 创建附件“附件节点”
            MimeBodyPart attachment = new MimeBodyPart();
            // 读取本地文件
            DataHandler dh2 = new DataHandler(new FileDataSource(fileName));
            // 将附件数据添加到“节点”
            attachment.setDataHandler(dh2);
            // 设置附件的文件名(需要编码)
            attachment.setFileName(MimeUtility.encodeText(dh2.getName()));
            //设置整个邮件的关系(将最终的混合“节点”作为邮件的内容添加到邮件对象)
            MimeMultipart mm = new MimeMultipart();
            mm.addBodyPart(attachment);
            mm.setSubType("mixed"); // 混合关系
            message.setContent(mm);
            //保存设置
            message.saveChanges();
            //5、发送邮件
            transport.sendMessage(message,message.getAllRecipients());
            //6、关闭连接
            transport.close();
            return true;
        }catch (Exception e){
            throw new RuntimeException("发送邮件失败！"+e.getMessage());
        }
    }
}

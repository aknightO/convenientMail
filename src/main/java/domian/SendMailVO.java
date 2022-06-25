package domian;

public class SendMailVO {
    /**
     * 发件人
     * **/
    private String myEmailAccount;
    /**
     * 收件人
     * **/
    private String otherSendEmailAccount;

    /**
     * 授权码
     * 授权码获取方式：https://service.mail.qq.com/cgi-bin/help?subtype=1&&id=28&&no=1001256
     * **/
    private String myEmailPassword;

    /**
     * 创建mailVO
     * **/
    public static SendMailVO buildSendMail(String myEmailAccount,String myEmailPassword,String otherSendEmailAccount){
        SendMailVO mailVO = new SendMailVO();
        mailVO.setMyEmailAccount(myEmailAccount);
        mailVO.setMyEmailPassword(myEmailPassword);
        mailVO.setOtherSendEmailAccount(otherSendEmailAccount);
        return mailVO;
    }

    public String getMyEmailAccount() {
        return myEmailAccount;
    }

    public void setMyEmailAccount(String myEmailAccount) {
        this.myEmailAccount = myEmailAccount;
    }

    public String getOtherSendEmailAccount() {
        return otherSendEmailAccount;
    }

    public void setOtherSendEmailAccount(String otherSendEmailAccount) {
        this.otherSendEmailAccount = otherSendEmailAccount;
    }

    public String getMyEmailPassword() {
        return myEmailPassword;
    }

    public void setMyEmailPassword(String myEmailPassword) {
        this.myEmailPassword = myEmailPassword;
    }
}

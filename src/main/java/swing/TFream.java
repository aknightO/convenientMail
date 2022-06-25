package swing;

import javax.swing.*;
import java.awt.*;

/**
 * @author yue.sun
 * @date 2022-6-26
 */
public class TFream {
    public JFrame frame = null;
    public JPanel jp1 = null;
    public JTextField jtf1 = null;
    public JTextField jtf2 = null;
    public JTextField jtf3 = null;
    public JTextField jtf4 = null;
    public JLabel sourceFilePath = null;
    public JLabel selfQQAccount = null;
    public JLabel authorizationCode = null;
    public JLabel otherQQAccount = null;
    public JTextArea jta = null;
    public JScrollPane jp = null;
    public Button sendMailButton = null;
    public TFream(){
        frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setBounds(300, 300, 480, 400);
        frame.setBackground(new Color(255, 255, 255));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setTitle("ConvenientSendMail");
        //3行2列 水平间距20 垂直间距10
        jp1 = new JPanel(new GridLayout(5, 2,5,13));
        jp1.setBounds(0,0,300,300);
        //第一行
        sourceFilePath = new JLabel("AbsoluteSourceFilePath:");
        sourceFilePath.setHorizontalAlignment(SwingConstants.CENTER);
        jtf1 = new JTextField(15);

        jp1.add(sourceFilePath);
        jp1.add(jtf1);
        //第二行
        selfQQAccount = new JLabel("selfQQAccount:");
        selfQQAccount.setHorizontalAlignment(SwingConstants.CENTER);
        jtf2 = new JTextField(15);
        jp1.add(selfQQAccount);jp1.add(jtf2);
        //第三行
        authorizationCode = new JLabel("授权码:");
        authorizationCode.setHorizontalAlignment(SwingConstants.CENTER);
        jtf3 = new JTextField(15);
        jtf3.setText("");
        jp1.add(authorizationCode);jp1.add(jtf3);
        //第四行
        otherQQAccount = new JLabel("otherAccount:");
        otherQQAccount.setHorizontalAlignment(SwingConstants.CENTER);
        jtf4 = new JTextField(15);
        jtf3.setText("");
        jp1.add(otherQQAccount);
        jp1.add(jtf4);
        frame.add(jp1);
        //第五行加入滚动条
        jta=new JTextArea("Log:",6,30);
        jta.setEditable(false);
        jta.setTabSize(4);
        jta.setFont(new Font("标楷体", Font.BOLD, 10));
        // 激活自动换行功能
        jta.setLineWrap(true);
        // 激活断行不断字功能
        jta.setWrapStyleWord(true);
        jp=new JScrollPane(jta);
        frame.add(jp);

        sendMailButton = new Button("SendMail");
        //因为 addActionListener(); 需要一个 ActionListener ,所以我们需要构造一个 ActionListener
        SendMailAction myActionListener = new SendMailAction(this);
        sendMailButton.addActionListener(myActionListener);
        frame.add(sendMailButton);
        frame.setVisible(true);
    }
}

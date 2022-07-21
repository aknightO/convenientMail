package swing;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
    public JTextField msgTextArea= null;
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
//        sourceFilePath = new JLabel("AbsoluteSourceFilePath:");
//        sourceFilePath.setHorizontalAlignment(SwingConstants.CENTER);
//        jtf1 = new JTextField(15);

        // 创建文本区域, 用于显示相关信息
        jtf1  = new JTextField(15);
//        msgTextArea.setLineWrap(true);
        jp1.add(jtf1);

        JButton openBtn = new JButton("选择文件");
        openBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFileOpenDialog(jp1, jtf1);
            }
        });
        jp1.add(openBtn);

//        JButton saveBtn = new JButton("保存");
//        saveBtn.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                showFileSaveDialog(jp1, msgTextArea);
//            }
//        });
//        jp1.add(saveBtn);


//
//        jp1.add(sourceFilePath);
//        jp1.add(jtf1);
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
        jta=new JTextArea("LogAndTips:输入文件为绝对路径必须为'/'，授权码获取获取地址：https://service.mail.qq.com/cgi-bin/help?subtype=1&&id=28&&no=1001256，如果文件没有大于19M，那么将不会进行分片，只会进行压缩",6,30);
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



    /*
     * 打开文件
     */
    private static void showFileOpenDialog(Component parent, JTextField msgTextArea) {
        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();

        // 设置默认显示的文件夹为当前文件夹
        fileChooser.setCurrentDirectory(new File("."));

        // 设置文件选择的模式（只选文件、只选文件夹、文件和文件均可选）
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        // 设置是否允许多选
        fileChooser.setMultiSelectionEnabled(true);

        // 添加可用的文件过滤器（FileNameExtensionFilter 的第一个参数是描述, 后面是需要过滤的文件扩展名 可变参数）
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("zip(*.zip, *.rar)", "zip", "rar"));
        // 设置默认使用的文件过滤器
        fileChooser.setFileFilter(new FileNameExtensionFilter("image(*.jpg, *.png, *.gif)", "jpg", "png", "gif"));

        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = fileChooser.showOpenDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"确定", 则获取选择的文件路径
            File file = fileChooser.getSelectedFile();

            // 如果允许选择多个文件, 则通过下面方法获取选择的所有文件
            // File[] files = fileChooser.getSelectedFiles();

            msgTextArea.setText(file.getAbsolutePath() + "\n\n");
        }
    }

    /*
     * 选择文件保存路径
     */
    private static void showFileSaveDialog(Component parent, JTextField msgTextArea) {
        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();

        // 设置打开文件选择框后默认输入的文件名
        fileChooser.setSelectedFile(new File("测试文件.zip"));

        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = fileChooser.showSaveDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"保存", 则获取选择的保存路径
            File file = fileChooser.getSelectedFile();
            msgTextArea.setText("保存到文件: " + file.getAbsolutePath() + "\n\n");
        }
    }

}

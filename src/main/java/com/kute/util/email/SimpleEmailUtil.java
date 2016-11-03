package com.kute.util.email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by kute on 16/9/16.
 */
public class SimpleEmailUtil {


    private static String username;
    private static String password;
    private static String host;
    private static String port;

    private static  Properties props = new Properties();


    static {
        ResourceBundle rb = ResourceBundle.getBundle("mail", Locale.ENGLISH);

        //获取配置参数
        host = rb.getString("mail.smtp.host");
        port = rb.getString("mail.smtp.port");
        username = rb.getString("username");
        password = rb.getString("password");

        //设置参数
        props = new Properties();
        props.setProperty("mail.smtp.auth", rb.getString("mail.smtp.auth"));
        props.setProperty("mail.smtp.starttls.enable",rb.getString("mail.smtp.starttls.enable"));
        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.port", port);
        props.setProperty("mail.smtp.ssl.enable", "true");
        props.setProperty("mail.smtp.ssl.trust", host);
        props.setProperty("mail.smtp.ssl.checkserveridentity","false");
    }

    public static boolean sendEmail() throws Exception {

        //获取session对象
        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        //打印debug信息
        session.setDebug(true);

        //邮件内容
        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(username));

        msg.setRecipient(Message.RecipientType.TO, new InternetAddress("solo.he@mpos.net"));

        msg.setSentDate(new Date());

        msg.setSubject("hava a test");

        MimeBodyPart mbp = new MimeBodyPart();
        mbp.setContent("</br>hava a test</br>hava a test</br>", "text/html;charset=UTF-8");

        Multipart mulp = new MimeMultipart();
        mulp.addBodyPart(mbp);

        msg.setContent(mulp);

        //获取Service对象
        Transport tran = session.getTransport("smtp");

        //连接服务器
        tran.connect(host, username, password);

        //发送邮件
        tran.sendMessage(msg, msg.getAllRecipients());

        return true;
    }







    public static void main(String[] args) throws Exception {
        sendEmail();
    }
}

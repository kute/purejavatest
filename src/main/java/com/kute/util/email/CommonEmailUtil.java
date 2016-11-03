package com.kute.util.email;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by kute on 16/9/16.
 */
public class CommonEmailUtil {

    private static String username;
    private static String password;
    private static String host;

    private CommonEmailUtil() {
    }

    static {
        ResourceBundle rb = ResourceBundle.getBundle("mail", Locale.ENGLISH);

        //获取配置参数
        host = rb.getString("mail.smtp.host");
        username = rb.getString("username");
        password = rb.getString("password");
    }

    /**
     *
     * @param files
     * @param subject
     * @param htmlContent
     * @param recieves
     */
    public static void sendMail(File[] files, String subject, String htmlContent, String[] recieves) {
        // SimpleEmail email = new SimpleEmail();
        MultiPartEmail email = new MultiPartEmail();
        // HtmlEmail email = new HtmlEmail();

        email.setHostName(host);
        email.setAuthentication(username, password);
        email.setCharset("UTF-8");
        try {
            email.setFrom(username);
            email.setSubject(subject);
//            email.setMsg(htmlContent);
            email.addPart(htmlContent, "text/html; charset=UTF-8");

            if (null != files && files.length > 0) {
                for (File file : files) {
                    EmailAttachment attachment = new EmailAttachment();
                    attachment.setPath(file.getPath());
                    attachment.setName(file.getName());
                    attachment.setDescription(subject);
                    attachment.setDisposition(EmailAttachment.ATTACHMENT);
                    email.attach(attachment);
                }
            }
            if (null != recieves && recieves.length > 0) {
                for (String to : recieves) {
                    email.addTo(to);
                }
                email.send();
            }
        } catch (EmailException e) {
            e.printStackTrace();
        }

    }
}

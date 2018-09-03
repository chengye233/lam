package utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 发送邮件给指定邮箱(qq)
 */
public class MailUtils
{
    public static void sendMail(String email, String subject, String emailMsg)
    {
        //1.创建一个程序与邮件服务器会话的Session
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "SMTP");
        properties.setProperty("mail.host", "smtp.163.com");
        properties.setProperty("mail.smtp.auth", "true");  //指定验证为true

        //创建验证器
        Authenticator authenticator = new Authenticator()
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication("13516250492", "wangyi163");
            }
        };

        Session session = Session.getInstance(properties, authenticator);

        //2.创建一个Message，相当于邮件内容
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress("13516250492@163.com"));  //设置发送者
            message.addRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress("13516250492@163.com"),
                    new InternetAddress(email)});//设置发送方式和接收者
//            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));  //设置发送方式和接收者
            message.setSubject(subject);  //设置主题
            message.setContent(emailMsg, "text/html;charset=UTF-8");  //设置内容
            //3.创建 Transport用于将邮件发送
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}

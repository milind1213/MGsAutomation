package com.mgs.Utils;

import com.mgs.CommonConstants;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;
import static com.mgs.Utils.FileUtil.getProperty;

public class EmailReportingUtils {
    public static void main(String[] args) {
        String[] to = {"mgs.milind@gmail.com", "gmilind13@gmail.com"};
        String from = "milind.ghoongade@gmail.com";
        String subject = "Subject : Test Automation Execution Report";
        String text =
                "Hi Team,\n\n" +
                "Please find the attached Automation Execution Report. Review the results and provide any feedback.\n\n\n" +
                "Thanks and Regards,\n" +
                "Milind Ghongade\n" +
                "QA Engineer";
        File file = new File("C:\\Users\\Lenovo\\IdeaProjects\\Reports\\Automation_Report_084602.html");
        boolean b = sendEmailWithAttachment(to, from, subject, text, file);
        if (b) {
            System.out.println("Email Sent Successfully");
        } else {
            System.out.println("Failed to Sending the Email");
        }
    }

    public static boolean sendEmailWithAttachment(String[] to, String from, String subject, String text, File file) {
        boolean flag = false;
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication( getProperty(CommonConstants.COMMON, CommonConstants.GMAIL_USERNAME),
                        getProperty(CommonConstants.COMMON, CommonConstants.GMAIL_PASSWORD));
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setSubject(subject);

            for (String recipient : to) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            }

            MimeBodyPart part1 = new MimeBodyPart();  // Create the message body
            part1.setText(text);

            MimeBodyPart part2 = new MimeBodyPart();  // Create the attachment part
            part2.attachFile(file);

            MimeMultipart mimeMultipart = new MimeMultipart();  // Create multipart for both body and attachment
            mimeMultipart.addBodyPart(part1);
            mimeMultipart.addBodyPart(part2);

            message.setContent(mimeMultipart);  // Set the content to the message
            Transport.send(message);  // Send the message
            flag = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static boolean sendEmail(String[] to, String from, String subject, String text) {
        boolean flag = false;
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.host", "smtp.gmail.com");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication( getProperty(CommonConstants.COMMON, CommonConstants.GMAIL_USERNAME),
                        getProperty(CommonConstants.COMMON, CommonConstants.GMAIL_PASSWORD));
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setSubject(subject);
            message.setText(text);
            for (String recipient : to) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            }
            Transport.send(message);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

}

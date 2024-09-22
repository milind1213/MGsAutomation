package com.mgs.Utils;

import com.mgs.CommonConstants;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Properties;

import static com.mgs.CommonConstants.CURRENT_DIRECTORY;
import static com.mgs.Utils.FileUtil.getProperty;

public class EmailReportingUtil {
    public void sendExecutionReport() {
        String[] to = getProperty(CommonConstants.COMMON, CommonConstants.GMAIL_TO).split(",");
        String from = getProperty(CommonConstants.COMMON, CommonConstants.GMAIL_FROM);
        String subject = getProperty(CommonConstants.COMMON, CommonConstants.GMAIL_SUBJECT);
        String text = getProperty(CommonConstants.COMMON, CommonConstants.GMAIL_TEXT);

        File reportDirectory = new File(CURRENT_DIRECTORY + "\\Reports");
        File latestReportFile = null;

        for (int attempt = 1; attempt <=5; attempt++) {
            latestReportFile = getLatestFileFromDir(reportDirectory);
            if (latestReportFile != null) {
                System.out.println("Latest Report File: " + latestReportFile.getAbsolutePath());
                break;         // Exit the loop if the file is found
            } else {
                try {
                    Thread.sleep(2000);// Wait for the defined period before retrying
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        if (latestReportFile != null) {
            boolean emailSent = sendEmailWithAttachment(to, from, subject, text, latestReportFile);
            if (emailSent) {
                System.out.println("Extent Report has been successfully sent with the attachment to: " + Arrays.toString(to));
            } else {
                System.out.println("Failed to send the Extent Report email.");
            }
        } else {
            System.out.println("Failed to locate the most recent Extent Report.");
        }
    }
    public static File getLatestFileFromDir(File dir) {
        File[] files = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".html"));
        if (files != null && files.length > 0) {
            Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
            return files[0]; // Return the most recent file
        }
        return null;
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

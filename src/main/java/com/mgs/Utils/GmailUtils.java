package com.mgs.Utils;

import jakarta.mail.*;
import jakarta.mail.internet.MimeBodyPart;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class GmailUtils {
    // This code is designed for reading and processing emails from a Gmail account using the IMAP protocol.
    public static void fetchEmails(String username, String password, String emailFolder, String subject) throws Exception {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imap");
        props.setProperty("mail.imaps.partialfetch", "false");
        props.put("mail.imap.ssl.enable", "true");
        props.put("mail.mime.base64.ignoreerrors", "true");

        Session session = Session.getInstance(props, null);
        Store store = session.getStore("imap");
        store.connect("imap.gmail.com", 993, username, password);

        Folder folder = store.getFolder(emailFolder);
        folder.open(Folder.READ_WRITE);

        System.out.println("Total Messages:" + folder.getMessageCount());
        System.out.println("Unread Messages:" + folder.getUnreadMessageCount());

        List<Message> messages = Arrays.asList(folder.getMessages());
        int messagesSize = messages.size();
        for (int i = messagesSize - 1; i >= messagesSize - 10; i--) {
            if (messages.get(i).getSubject().contains(subject)) {

                System.out.println("***************************************************");
                System.out.println("MESSAGE : \n");

                System.out.println("Subject: " + messages.get(i).getSubject());
                System.out.println("From: " + messages.get(i).getFrom()[0]);
                System.out.println("To: " + messages.get(i).getAllRecipients()[0]);
                System.out.println("Date: " + messages.get(i).getReceivedDate());
                System.out.println("Size: " + messages.get(i).getSize());
                System.out.println("Flags: " + messages.get(i).getFlags());
                System.out.println("ContentType: " + messages.get(i).getContentType());
                System.out.println("Body: \n" + getEmailBody(messages.get(i)));
                System.out.println("Has Attachments: " + hasAttachments(messages.get(i)));

            }
        }
    }

    public static boolean hasAttachments(Message email) throws Exception {
        String contentType = email.getContentType();
        System.out.println(contentType);

        if (contentType.toLowerCase().contains("multipart/mixed")) {
            Multipart multiPart = (Multipart) email.getContent();

            for (int i = 0; i < multiPart.getCount(); i++) {
                MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(i);
                if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                    System.out.println("Attached filename is:" + part.getFileName());

                    MimeBodyPart mimeBodyPart = (MimeBodyPart) part;
                    String fileName = mimeBodyPart.getFileName();

                    String destFilePath = System.getProperty("user.dir") + "\\Resources\\";

                    File fileToSave = new File(fileName);
                    mimeBodyPart.saveFile(destFilePath + fileToSave);

                    // PDFUtil pdfUtil = new PDFUtil();
                    // String pdfContent = pdfUtil.getText(destFilePath + fileToSave);

                    System.out.println("******---------------********");
                    System.out.println("\n");
                    System.out.println("Started reading the pdfContent of the attachment:==");

                    // System.out.println(pdfContent);

                    System.out.println("\n");
                    System.out.println("******---------------********");

                    Path fileToDeletePath = Paths.get(destFilePath + fileToSave);
                    Files.delete(fileToDeletePath);
                }
            }

            return true;
        }
        return false;
    }

    public static String getEmailBody(Message email) throws IOException, MessagingException {
        String line, emailContentEncoded;
        StringBuffer bufferEmailContentEncoded = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(email.getInputStream()));
        while ((line = reader.readLine()) != null) {
            bufferEmailContentEncoded.append(line);
        }

        System.out.println("**************************************************");
        System.out.println(bufferEmailContentEncoded);
        System.out.println("**************************************************");

        emailContentEncoded = bufferEmailContentEncoded.toString();
        System.out.println("emailContentEncoded " + emailContentEncoded);
        FileUtil.writeToFile(String.valueOf((new File(System.getProperty("user.dir") + "/TestData/email.html"))), emailContentEncoded, false);
        FileReader htmlReader = new FileReader(System.getProperty("user.dir") + "/TestData/email.html");
        System.out.println(HtmlParser.extractText(htmlReader));

        return emailContentEncoded;
    }
}

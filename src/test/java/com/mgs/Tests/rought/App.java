package com.mgs.Tests.rought;

import com.mgs.Utils.EmailReportingUtils;

public class App {
    public static void main(String[] args) {

        EmailReportingUtils gEmailSender = new EmailReportingUtils();

        // List of recipients
        String[] to = {"mgs.milind@gmail.com", "gmilind13@gmail.com"};
        String from = "milind.ghoongade@gmail.com";
        String subject = "Second: Sending email using GMail";
        String text = "This is a example email sent using gmail and java program without less secure app";

        // Send email
        boolean b = gEmailSender.sendEmail(to, from, subject, text);
        if (b) {
            System.out.println("Email is sent successfully");
        } else {
            System.out.println("There is a problem in sending the email");
        }

    }
}

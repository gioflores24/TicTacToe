/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cssquad.commons;

import java.util.Properties;
import java.util.Random;
import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Giovanni
 */
public abstract class Email {

    
    public static String sendPassword(String sendTo, String emailBody) {

        //final String trimmedEmail = sendTo.substring(0, sendTo.indexOf('@'));
        String sentFrom = "compsciclu@gmail.com";

        // -- assumes you're sender
        String host = "smtp.gmail.com";
        String port = "587";
        
       

        Properties properties = System.getProperties();

        // setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", host);

        try {
            Session session = Session.getDefaultInstance(properties, new Authenticator(){
                @Override
                protected PasswordAuthentication getPasswordAuthentication()
                {
                    return new PasswordAuthentication("gdr943", "Tiki Taka");
                }
            });
           
            MimeMessage mimeMsg = new MimeMessage(session);

            // "from" part of header field 
            mimeMsg.setFrom(new InternetAddress(sentFrom));

            // "to" part of header field 
            mimeMsg.addRecipient(RecipientType.TO, new InternetAddress(sendTo));

            mimeMsg.setSubject("TicTacToe : Forgot Password");
            String randomPwd = randomPassword();
            mimeMsg.setText(emailBody + randomPwd);

            Transport.send(mimeMsg);
            System.out.println("Sent email successfully");
            return randomPwd;
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public static String randomPassword() {

        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 8) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            System.out.println(index);
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cssquad.server;

import com.cssquad.commons.ResponseCode;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Giovanni
 */
public class Validation {

    private static String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static Pattern emailPattern = Pattern.compile(emailRegex);
//Minimum eight characters, at least one letter and one number:
    private static String passwordRegex = "^(?=.*[0-9])(?=.*[A-Za-z])(?=\\S+$).{8,}$";
    

    private static Pattern passwordPattern = Pattern.compile(passwordRegex);

    public static boolean validPassword(String password) {
        Matcher matcher = passwordPattern.matcher(password);
        return matcher.find();
    }

    public static boolean validEmailAddress(String emailAddress) {
        Matcher matcher = emailPattern.matcher(emailAddress);
        return matcher.find();
    }

    public static void main(String[] args) {
        String email = "giovanniflores@callutheran.edu";
        String password = "TikiTaka11!";

        if (validEmailAddress(email)) {
            System.out.println("Valid email");
        } else {
            System.out.println("Not valid email");
        }

        if (validPassword(password)) {
            System.out.println("valid password");
        } else {
            System.out.println("not valid password");
        }
    }

}

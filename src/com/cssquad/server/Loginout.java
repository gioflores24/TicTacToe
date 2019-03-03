/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cssquad.server;

import com.cssquad.commons.Email;

/**
 * Handles what happens when the user clicks "Log in"
 * @author Giovanni
 */
public class Loginout {

    public boolean failed = false;
    static int loginFailure = 0;

    public boolean canLogin(String uname, String pwd) {

        if (Main.database.checkRegistered(uname)) {
            if (Main.database.passwordMatches(uname, pwd)) {

                return true;
            }
        }
        loginFailure++;
        if (loginFailure == 5) {
            if (Main.database.checkRegistered(uname)) {
                String email = Main.database.getEmail(uname);
                System.out.println(loginFailure);
                String newPassword = Email.sendPassword(email, "If you're receiving this email, it's because you've been unsuccessfully trying to login"
                        + "You can login using the following temporary password: ");
                Main.database.changePassword(uname, newPassword);
            }
            loginFailure = 0;
            failed = true;
            return false;
        }
        failed = false;
        return false;

    }

}

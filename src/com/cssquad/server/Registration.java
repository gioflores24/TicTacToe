/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cssquad.server;

/**
 *
 * @author Giovanni
 */
public class Registration {

    public boolean isRegistered(String uname) {

        boolean registered = Main.database.checkRegistered(uname);

        return registered;
    }

    public void register(String uname, String pwd, String emailAddress) {

        Main.database.register(uname, pwd, emailAddress);
      
        
    }
}

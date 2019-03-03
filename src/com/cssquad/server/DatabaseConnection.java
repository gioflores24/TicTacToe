/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cssquad.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Giovanni
 */
public class DatabaseConnection {
    // -- objects to be used for database access

    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rset = null;

    // -- connect to the world database
    // -- this is the connector to the database, default port is 3306
//    private String url = "jdbc:mysql://localhost:3306/world";
    private String url = "jdbc:mysql://localhost:3300/?user=root";

    // -- this is the username/password, created during installation and in MySQL Workbench
    //    When you add a user make sure you give them the appropriate Administrative Roles
    //    (DBA sets all which works fine)
    private String user = "root";
    private String password = "TikiTaka11!";

    public DatabaseConnection() {

        System.out.println("Connected database");

        try {
            // -- make the connection to the database
            conn = DriverManager.getConnection(url, user, password);

            // -- These will be used to send queries to the database
            stmt = conn.createStatement();
            rset = stmt.executeQuery("SELECT VERSION()");

            if (rset.next()) {
                System.out.println(rset.getString(1));
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean checkRegistered(String uname) {
        try {
            String command = "SELECT * FROM users.user where username=\"" + uname + "\"";

            rset = stmt.executeQuery(command);
            while (rset.next()) {
                String gottenUsername = rset.getString("username");
                if (uname.equals(gottenUsername)) {
                    return true;
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void register(String u, String p, String e) {
        try {
            String idCommand = "SELECT COUNT(*) FROM users.user;";
            String id = "";
            rset = stmt.executeQuery(idCommand);
            if (rset.next()) {
                id = rset.getString(1);
            }
            String login_attempts = "0";
            int win = 0, loss = 0, tie = 0;

            //String command = "INSERT INTO users.user VALUES('" + id + "','" + u + "','" + e + "','" + p  + "'," + login_attempts + ");";
            String command = "INSERT INTO users.user VALUES ('"
                    + id + "','" + u + "','" + e + "','" + p + "','" + login_attempts + "','" + win + "','" + loss + "'," + tie + ");";

            stmt.executeUpdate(command);

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.WARNING, null, ex);
        }
    }

    public void setData(String username, String score) {
        try {
            String command = "UPDATE users.user SET " + score + " = " + score + " + 1" +  " WHERE username=\"" + username + "\"";
            stmt.executeUpdate(command);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.WARNING, null, ex);

        }
    }

    public String getData(String uname) {

        try {
            String win = "", loss = "", tie = "";
            String command = "SELECT * FROM users.user WHERE username=\"" + uname + "\"";
            rset = stmt.executeQuery(command);
            while (rset.next()) {
                win = rset.getString("win");
                loss = rset.getString("loss");
                tie = rset.getString("tie");
            }
            System.out.println("Win amount: " + win);
            return win + "," + loss + "," + tie;

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.WARNING, null, ex);
        }
        return "";
    }

    public boolean passwordMatches(String uname, String pwd) {
        try {
            String command = "SELECT * FROM users.user WHERE username=\"" + uname + "\"";

            rset = stmt.executeQuery(command);
            while (rset.next()) {
                String gottenPassword = rset.getString("password");
                if (gottenPassword.equals(pwd)) {
                    return true;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.WARNING, null, ex);
        }
        return false;
    }

    public void forgotPassword(String email, String randomPassword) {

        try {
            String username = "";
            String command = "SELECT username FROM users.user WHERE email=\"" + email + "\"";
            rset = stmt.executeQuery(command);
            while (rset.next()) {
                username = rset.getString("username");
            }
            command = "UPDATE users.user SET password=\"" + randomPassword + "\"" + " WHERE username=\"" + username + "\"";
            stmt.executeUpdate(command);

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.WARNING, null, ex);
        }
    }

    public void changePassword(String uname, String newPassword) {
        try {
            String command = "UPDATE users.user SET password=\"" + newPassword + "\"" + " WHERE username=\"" + uname + "\"";
            stmt.executeUpdate(command);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.WARNING, null, ex);
        }
    }

    public String getEmail(String uname) {
        String email = "";
        try {

            String command = "SELECT * FROM users.user WHERE username=\"" + uname + "\"";
            rset = stmt.executeQuery(command);
            while (rset.next()) {
                email = rset.getString("email");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.WARNING, null, ex);
        }
        return email;
    }
}

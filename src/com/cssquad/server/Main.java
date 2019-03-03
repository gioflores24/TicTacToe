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
public class Main {

    static DatabaseConnection database;
    static Server server;

    public static void main(String[] args) {

        database = new DatabaseConnection();
        server = new Server();

    }
}

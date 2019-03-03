package com.cssquad.server;

import com.cssquad.commons.ResponseCode;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ConnectionThread extends Thread {

    private boolean go;
    private String name;
    private int id;

    // -- the main server (port listener) will supply the socket
    //    the thread (this class) will provide the I/O streams
    //    BufferedReader is used because it handles String objects
    //    whereas DataInputStream does not (primitive types only)
    private BufferedReader datain;
    private DataOutputStream dataout;

    // -- this is a reference to the "parent" Server object
    //    it will be set at time of construction
    private Server server;

    public ConnectionThread(int id, Socket socket, Server server) {
        this.server = server;
        this.id = id;
        this.name = Integer.toString(id);
        go = true;

        // -- create the stream I/O objects on top of the socket
        try {
            datain = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            dataout = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    public String toString() {
        return name;
    }

    public String getname() {
        return name;
    }

    public void run() {
        // -- server thread runs until the client terminates the connection
        while (go) {
            try {
                // -- always receives a String object with a newline (\n)
                //    on the end due to how BufferedReader readLine() works.
                //    The client adds it to the user's string but the BufferedReader
                //    readLine() call strips it off
                String[] txt = datain.readLine().split(",");
                System.out.println("SERVER receive: " + txt[0]);
                // -- if it is not the termination message, send it back adding the
                //    required (by readLine) "\n"

                // -- if the disconnect string is received then 
                //    close the socket, remove this thread object from the
                //    server's active client thread list, and terminate the thread
                if (txt[0].equals("disconnect")) {
                    datain.close();
                    server.removeID(id);
                    go = false;

                } // -- easter egg ;)
                else if (txt[0].equals("hello")) {

                    dataout.writeBytes("world!" + "\n");
                    dataout.flush();

                } else if (txt[0].equals("register")) {

                    Registration rg = new Registration();

                    if (rg.isRegistered(txt[1])) {
                        dataout.writeBytes(ResponseCode.REGISTERED + "\n");

                    } else {
                        rg.register(txt[1], txt[2], txt[3]);
                        dataout.writeBytes(ResponseCode.NOT_REGISTERED + "\n");
                    }
                    dataout.flush();

                } else if (txt[0].equals("login")) {
                    Loginout loginHandler = new Loginout();
                    if (loginHandler.canLogin(txt[1], txt[2])) {
                        dataout.writeBytes(ResponseCode.CAN_LOGIN + "\n");

                    } else if (loginHandler.failed) {
                        System.out.println("Failed login");
                        dataout.writeBytes(ResponseCode.FAILED_LOGIN + "\n");
                    } else {
                        dataout.writeBytes(ResponseCode.CANNOT_LOGIN + "\n");
                    }
                    dataout.flush();

                } else if (txt[0].equals("getData")) {
                    String winLossTie = Main.database.getData(txt[1]); // gets the win/loss/tie display for that username

                    dataout.writeBytes(winLossTie + "\n");
                    dataout.flush();

                } else if (txt[0].equals("forgotPassword")) {
                    Main.database.forgotPassword(txt[1], txt[2]);
                    dataout.writeBytes(ResponseCode.PASSWORD_CHANGED + "\n");
                    dataout.flush();
                } else if (txt[0].equals("changePassword")) {
                    Main.database.changePassword(txt[1], txt[2]);
                    dataout.writeBytes(ResponseCode.PASSWORD_CHANGED + "\n");
                    dataout.flush();
                } else if (txt[0].equals("setData")) {
                    Main.database.setData(txt[1],txt[2]);
                    dataout.writeBytes(ResponseCode.UPDATED_SCORE + "\n");
                    dataout.flush();
                } else {
                    System.out.println("unrecognized command >>" + txt[0] + "<<");
                    dataout.writeBytes(txt[0] + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
                go = false;
            }

        }
    }
}

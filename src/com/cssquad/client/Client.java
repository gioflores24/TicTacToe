package com.cssquad.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private static final long serialVersionUID = 1L;

    // -- port and host name of server
    private static final int PORT = 8000; // changed from 3030

    /*
	 * From ipconfig:
	 * 
	 * Wireless LAN adapter Wireless Network Connection:
	 * 
	 * Connection-specific DNS Suffix . : clunet.edu Link-local IPv6 Address . . . .
	 * . : fe80::1083:3e22:f5a1:a3ec%11 IPv4 Address. . . . . . . . . . . :
	 * 199.107.222.115 <=======This address works Subnet Mask . . . . . . . . . . .
	 * : 255.255.240.0 Default Gateway . . . . . . . . . : 199.107.210.2
     */
    private static final String HOST = "localhost";// "199.107.222.115";//"localhost";//"127.0.0.1";
    // -- the actual host IP address of the machine can
    // be found using ipconfig from a command console
    // private final String HOST = "192.168.20.4";

    // -- socket variable for peer to peer communication
    private Socket socket;

    // -- stream variables for peer to peer communication
    // to be opened on top of the socket
    private BufferedReader datain;
    private DataOutputStream dataout;

    public Client() {
        System.out.println("new client");
        try {
            // -- construct the peer to peer socket
            socket = new Socket(HOST, PORT);
            // -- wrap the socket in stream I/O objects
            setDatain(new BufferedReader(new InputStreamReader(socket.getInputStream())));
            dataout = new DataOutputStream(socket.getOutputStream());
        } catch (UnknownHostException e) {
            System.out.println("Host " + HOST + " at port " + PORT + " is unavailable.");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Unable to create I/O streams.");
            //System.exit(1);
        }

    }

    public String sendString(String _msg) {
        String rtnmsg = "";

        try {
            // -- the server only receives String objects that are
            // terminated with a newline \n"
            // -- send the String making sure to flush the buffer
            dataout.writeBytes(_msg + "\n");
            dataout.flush();

            // -- receive the response from the server
            // The do/while makes this a blocking read. Normally BufferedReader.readLine()
            // is non-blocking.
            // That is, if there is no String to read, it will read "". Doing it this way
            // does not allow
            // that to occur. We must get a response from the server. Time out could be
            // implemented with
            // a counter.
            rtnmsg = "";
            do {
                rtnmsg = datain.readLine();
               
                //rtnmsg = "world"; // -- this ensures server returns world when the client presses the button in
                // the gui.
            } while (rtnmsg.equals(""));

        } catch (IOException e) {
            // -- replaced the stack trace so as to better handle the exception.
            // -- this could be improved to instead create a label that displays this in our
            // client gui instance.

            // gui.createLabel("Unable to reach server");
            System.out.println("\n\nUnable to reach server. Please check your connection.");
            System.exit(1);
        }

        return rtnmsg;

    }

    

    public void disconnect() {
        String text = "disconnect";
        try {
            // -- the server only receives String objects that are
            // terminated with a newline "\n"

            // -- send a special message to let the server know
            // that this client is shutting down
            text += "\n";
            dataout.writeBytes(text);
            dataout.flush();

            // -- close the peer to peer socket
            socket.close();
        } catch (IOException e1) {
            e1.printStackTrace();

            System.exit(1);
        }

    }

    public BufferedReader getDatain() {
        return datain;
    }

    public void setDatain(BufferedReader datain) {
        this.datain = datain;
    }

}

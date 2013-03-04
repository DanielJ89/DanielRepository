/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ourownhttpserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Daniel Jensen
 */
public class HTTPClient {
        
    /**
     * @param args the command line arguments
     */
    public static void main(final String[] args) throws IOException {

        String host;
        String resource;
        int firstNumber = 2228;
        int secondNumber = 432;
        
        if (args.length == 2) {
            host = args[0];
            resource = args[1];

        } else {
        System.out.println("Calculating / from localhost");
        host = "localhost";
//        resource = "/";

        final int HTTP_PORT = 2222;
        Socket cSocket = new Socket(host, HTTP_PORT);

        InputStream instream = cSocket.getInputStream();
        OutputStream outstream = cSocket.getOutputStream();

        Scanner in = new Scanner(instream);
        PrintWriter out = new PrintWriter(outstream);
  
        String intToSend = "ADD " + firstNumber + " " + secondNumber + "\n";   
        System.out.println(intToSend);
        out.print(intToSend); 
        out.flush();
               
//        String command = "GET " + resource + " HTTP/1.1\n"
//                + "Host: " + host + "\n\n";
//        out.print(command);
//        
//        
//        out.flush();

        while (in.hasNext()) {
            String input = " " + in.nextLine();
            System.out.println(in.next());
        }
        
        cSocket.close();
    }
}
}
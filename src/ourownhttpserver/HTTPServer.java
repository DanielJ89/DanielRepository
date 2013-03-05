package ourownhttpserver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HTTPServer{

    public static final String ROOT_CATALOG = "./src/data";
    public static int port = 8080;

    /**
     *
     * @param args
     * @throws IOException the socket "sSocket" is the server socket
     */
    public static void main(final String[] args) throws IOException {

        final ServerSocket sSocket = new ServerSocket(port);
         
        
        ExecutorService pool = Executors.newCachedThreadPool();
        

        while (true) {
             final Socket clientSocket = sSocket.accept();
             Runnable Runner = new Runner(clientSocket);
             pool.execute(Runner);
////           handleClient(sSocket);
        }
//      pool.shutdown();
    }
}
   

       
//        final InputStream iStream = clientSocket.getInputStream();
//        final OutputStream oSteam = clientSocket.getOutputStream();
//
//        final PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
//        final Scanner fromClient = new Scanner(clientSocket.getInputStream());
//
//
//        final String method = fromClient.next();
//        final String url = fromClient.next();
//        final String version = fromClient.next();
//
//        try {
//            final FileInputStream fis = new FileInputStream(ROOT_CATALOG + url);
//            toClient.println("HTTP/1.0 200 FINE");
//            toClient.println();
//            toClient.flush();
//        } catch (FileNotFoundException ex) {
//            toClient.println("HTTP/1.0 404 Not found: /doesNotExist.html");
//            toClient.println();
//            toClient.flush();
//        }
//
//        clientSocket.close();
//    }

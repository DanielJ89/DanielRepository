package ourownhttpserver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.MimetypesFileTypeMap;
import org.omg.SendingContext.RunTime;

/**
 *
 * @author Daniel Jensen
 */
public class Runner implements Runnable {

    public static final Logger ADVANCEDLOGGER = Logger.getLogger("Advanced");
    public static final Logger SIMPLELOGGER = Logger.getLogger("Simple");
    public static final String ROOT_CATALOG = "./src/data";
    public static int port = 8080;
    final private Socket clientSocket;

    /**
     * ROOT_CATALOG is the place of our file port is the port number the server
     * listens
     */
    public Runner(final Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    /**
     * Creates in/output streams
     * Creates printwriter and scanner tools
     * Scanner scans input from client
     * Printer prints output to client (ServerTest) 
     * 
     */
    @Override
    public void run() {
        try {
            final FileInputStream fis2 = new FileInputStream("./src/data/mime.types.txt");
            final InputStream iStream = clientSocket.getInputStream();
            final OutputStream oSteam = clientSocket.getOutputStream();
            final PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
            final Scanner fromClient = new Scanner(clientSocket.getInputStream());

            final MimetypesFileTypeMap mTFTMap = new MimetypesFileTypeMap(fis2);

            final String method = fromClient.next();
            final String url = fromClient.next();
            String version = fromClient.next();
           
            ADVANCEDLOGGER.log(Level.INFO, "A Client has made a request");
            SIMPLELOGGER.log(Level.INFO, "A Client has made a request");
            try {
                if (!"HTTP/1.0".equals(version)) {
                    throw new IllegalArgumentException();
                }
                final FileInputStream fis = new FileInputStream(ROOT_CATALOG + url);
                toClient.print("HTTP/1.0 200 FINE\r\n");
                toClient.print("Content-Type: " + mTFTMap.getContentType(url));
                SIMPLELOGGER.log(Level.INFO, mTFTMap.getContentType(url));
                // her skal content type være
                toClient.print("\r\n");
                toClient.flush();
                ADVANCEDLOGGER.log(Level.INFO, "The Client has recived its information");
                SIMPLELOGGER.log(Level.INFO, "The Client has recived its information");
            } catch (FileNotFoundException ex) {
                toClient.print("HTTP/1.0 404 Not found: /doesNotExist.html\r\n");
                toClient.print("\r\n");
                toClient.flush();
                ADVANCEDLOGGER.log(Level.SEVERE, "The request caused a FileNotFoundException");
                SIMPLELOGGER.log(Level.INFO, "The request caused a FileNotFoundException");
            } catch (IllegalArgumentException ex) {
                toClient.print("HTTP/1.0 400 Illegal request\r\n");
                toClient.print("\r\n");
                toClient.flush();
                ADVANCEDLOGGER.log(Level.SEVERE, "Log test");
                SIMPLELOGGER.log(Level.INFO, "Log test");
            }

            clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(Runner.class.getName()).log(Level.SEVERE, "An IOExeption has occured", ex);
        }
       
    }

    private static void copy(final InputStream input, final OutputStream output) throws IOException {
        final byte[] buffer = new byte[1024];
        while (true) {
            int bytesRead = input.read(buffer);
            if (bytesRead == -1) {
                break;
            }
            output.write(buffer, 0, bytesRead);
        }
    }
    
}
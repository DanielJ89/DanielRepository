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
    
    @Override
    public void run() {
        try {
            final InputStream iStream = clientSocket.getInputStream();
            final OutputStream oSteam = clientSocket.getOutputStream();

            final PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
            final Scanner fromClient = new Scanner(clientSocket.getInputStream());


            final String method = fromClient.next();
            final String url = fromClient.next();
            final String version = fromClient.next();
            ADVANCEDLOGGER.log(Level.INFO, "A Client has made a request");
            SIMPLELOGGER.log(Level.INFO, "A Client has made a request");
            try {
                final FileInputStream fis = new FileInputStream(ROOT_CATALOG + url);
                toClient.println("HTTP/1.0 200 FINE");
                toClient.println();
                toClient.flush();
                ADVANCEDLOGGER.log(Level.INFO, "The Client has recived its information");
                SIMPLELOGGER.log(Level.INFO, "The Client has recived its information");
            } catch (FileNotFoundException ex) {
                toClient.println("HTTP/1.0 404 Not found: /doesNotExist.html");
                toClient.println();
                toClient.flush();
                ADVANCEDLOGGER.log(Level.SEVERE, "The request caused a FileNotFoundException");
                SIMPLELOGGER.log(Level.INFO, "The request caused a FileNotFoundException");
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

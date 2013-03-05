package ourownhttpserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniel Jensen
 */
public class Reciver implements Runnable {

    public static final Logger ADVANCEDLOGGER = Logger.getLogger("Advanced");
    public static final Logger SIMPLELOGGER = Logger.getLogger("Simple");
    public static final String ROOT_CATALOG = "./src/data";
    public static int port = 8080;
    final private Socket clientSocket;

    /**
     * ROOT_CATALOG is the place of our file port is the port number the server
     * listens
     */
    public Reciver(final Socket clientSocket) {
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
            
            Sender sender = new Sender(toClient, url);
          clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(Reciver.class.getName()).log(Level.SEVERE, "An IOExeption has occured", ex);
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

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

/**
 *
 * @author Daniel Jensen
 */
public class HTTPServer{

    /**
     * ROOT_CATALOG should be without C: in a mac
     */
    private static final String ROOT_CATALOG = "./src/data";
    private static int port = 8080;
  

    /**
     *
     * @param args
     * @throws IOException
     */
    public static void main(final String[] args) throws IOException {

       final ServerSocket sSocket = new ServerSocket(port);

        while (true) {
            handleClient(sSocket);
        }

    }

    /**
     *
     * @param sSocket
     * @throws IOException
     */
    private static void handleClient(final ServerSocket sSocket) throws IOException {
        final Socket clientSocket = sSocket.accept();
        final InputStream iStream = clientSocket.getInputStream();
        final OutputStream oSteam = clientSocket.getOutputStream();

        final PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
        final Scanner fromClient = new Scanner(clientSocket.getInputStream());


        final String method = fromClient.next();
        final String url = fromClient.next();
        final String version = fromClient.next();
     
        try {
            final FileInputStream fis = new FileInputStream(ROOT_CATALOG + url);
            toClient.println("HTTP/1.0 200 FINE");
            toClient.println();
            toClient.flush();
        } catch (FileNotFoundException ex) {
            toClient.println("HTTP/1.0 404 Not found: /doesNotExist.html");
            toClient.println();
            toClient.flush();
        }


        clientSocket.close();
    }

    /**
     *
     * @param input
     * @param output
     * @throws IOException
     */
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
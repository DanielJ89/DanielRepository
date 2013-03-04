package ourownhttpserver;

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
public class Server {

    private static final String ROOT_CATALOG = "C:/file.html";
    static int port = 8080;
    float firstNumber;
    float secondNumber;
    float echhoLine;

    public static void main(final String[] args) throws IOException {

        ServerSocket sSocket = new ServerSocket(port);

        while (true) {
            handleClient(sSocket);
        }

    }

    private static void handleClient(ServerSocket sSocket) throws IOException {
        Socket clientSocket = sSocket.accept();
        InputStream iStream = clientSocket.getInputStream();
        OutputStream oSteam = clientSocket.getOutputStream();

        PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
        Scanner fromClient = new Scanner(clientSocket.getInputStream());

        while (fromClient.hasNextLine()) {

            String method = fromClient.next();
            float firstNumber = fromClient.nextFloat();
            float secondNumber = fromClient.nextFloat();
            float echhoLine = firstNumber / secondNumber;
            toClient.println(echhoLine);
            toClient.flush();

        }
        clientSocket.close();
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

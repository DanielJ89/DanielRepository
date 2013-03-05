package ourownhttpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
         
        
        final ExecutorService pool = Executors.newCachedThreadPool();
        

        while (true) {
             final Socket clientSocket = sSocket.accept();
             final Runnable Runner = new Runner(clientSocket);
             pool.execute(Runner);
////           handleClient(sSocket);
        }
//      pool.shutdown();
    }
}

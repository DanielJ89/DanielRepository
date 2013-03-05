package ourownhttpserver;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.XMLFormatter;

public class HTTPServer {

    public static final String ROOT_CATALOG = "./src/data";
    public static int port = 8080;
    public static final Logger ADVANCEDLOGGER = Logger.getLogger("Advanced");
    public static final Logger SIMPLELOGGER = Logger.getLogger("Simple");
    /**
     *
     * @param args
     * @throws IOException the socket "sSocket" is the server socket
     */
    public static void main(final String[] args) throws IOException {
        FileHandler fHandler = new FileHandler("./src/data/advancedlog.txt");
        FileHandler fHandler2 = new FileHandler("./src/data/simplelog2.txt");
        ADVANCEDLOGGER.addHandler(fHandler);
        SIMPLELOGGER.addHandler(fHandler2);
        fHandler2.setFormatter(new SimpleFormatter());
        final ServerSocket sSocket = new ServerSocket(port);
        final ExecutorService pool = Executors.newCachedThreadPool();

        ADVANCEDLOGGER.log(Level.INFO, "The server has started");
        SIMPLELOGGER.log(Level.INFO, "The server has started");
        while (true) {
            final Socket clientSocket = sSocket.accept();
            final Runnable Runner = new Runner(clientSocket);
            pool.execute(Runner);
////           handleClient(sSocket);
            ADVANCEDLOGGER.log(Level.INFO, "new thread has been created");
            SIMPLELOGGER.log(Level.INFO, "new thread has been created");
        }

//      pool.shutdown();
    }
}

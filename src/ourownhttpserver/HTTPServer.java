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
import org.omg.SendingContext.RunTime;

/**
 *
 * @author Daniel Jensen
 */
public class HTTPServer implements Runnable{
    /**
     * ROOT_CATALOG is the place of our file
     * port is the port number the server listens
     */
    @Override
    public void run() {
        final Runnable Runner = new Runner();
        Thread thread = new Thread(HTTPserver);
        Thread thread1 = new Thread(HTTPserver);

        thread.start();
        thread1.start();
    }
}

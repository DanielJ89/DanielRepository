/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ourownhttpserver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;

/**
 *
 * @author stefandanielsen
 */
public class Sender extends Reciver {
    
    public void sender(PrintWriter toClient, String url ) {
    
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

           }
    
}

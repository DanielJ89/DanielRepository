package ourownhttpserver;

import java.io.*;
import java.net.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
/**
 *
 * @author andersb
 */
public class ServerTest {

  private static final String CRLF = "\r\n";
  private static final String Host = "localhost";

  /**
   * test the response of what happens if the file does exist
   * @throws IOException 
   */
  @Test
  public void testResponseOK() throws IOException {
    final Socket client = new Socket(Host, HTTPServer.port);

    final OutputStream output = client.getOutputStream();
    output.write(("GET /File.html HTTP/1.0" + CRLF + CRLF).getBytes());
    output.flush();

    final BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
    final String statusLine = input.readLine();
    assertEquals("HTTP/1.0 200 FINE", statusLine);
    client.close();
  }
 /**
  * test the response of what happens if the file does not exist
  * @throws IOException 
  */
 @Test
  public void testResponseNotOK() throws IOException {
    final Socket client = new Socket(Host, HTTPServer.port);

    final OutputStream output = client.getOutputStream();
    output.write(("GET /doesNotExist.html HTTP/1.0" + CRLF + CRLF).getBytes());
    output.flush();

    final BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
    final String statusLine = input.readLine();
    assertEquals("HTTP/1.0 404 Not found: /doesNotExist.html", statusLine);
    client.close();
  }
/**
 * 
 * @throws IOException 
 * Should get a illigalargument exeption from runner which causes this test to pass .
 * But it just keeps looping and never terminates.
 */
    @Test
    public void testIllegalProtocol() throws IOException {
    final Socket client = new Socket(Host, HTTPServer.port);

    final OutputStream output = client.getOutputStream();
    output.write(("GET /doesNotExist.html" + CRLF + CRLF).getBytes());
    output.flush();

    final BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
    final String statusLine = input.readLine();
    assertEquals("HTTP/1.0 400 Illegal request", statusLine);
    client.close();
  }

  //@Test
  public void testMissingProtocol() throws IOException {
    final Socket client = new Socket(Host, HTTPServer.port);

    final OutputStream output = client.getOutputStream();
    output.write(("GET /doesNotExist.html HTTP 1.0" + CRLF + CRLF).getBytes());
    output.flush();

    final BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
    final String statusLine = input.readLine();
    assertEquals("HTTP/1.0 400 Illegal protocol: HTTP 1.0", statusLine);
    client.close();
  }

 // @Test
  public void testNotImplemented() throws IOException {
    final Socket client = new Socket(Host, HTTPServer.port);

    final OutputStream output = client.getOutputStream();
    output.write(("PUT /doesNotExist.html HTTP/1.0" + CRLF + CRLF).getBytes());
    output.flush();

    final BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
    final String statusLine = input.readLine();
    assertEquals("HTTP/1.0 501 Not implemented: PUT", statusLine);
    client.close();
  }
}

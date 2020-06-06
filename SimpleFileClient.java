/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package src;

/**
 *
 * @author sai
 */
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class SimpleFileClient {

  public  static int SOCKET_PORT = 13267;      // you may change this
  public  static String SERVER = "127.0.0.1";  // localhost
  public  static String  FILE_TO_RECEIVED = "c:/temp/source-downloaded.pdf";  // you may change this, I give a
                                                           // different name because i don't want to
                                                            // overwrite the one used by server...

  public final static int FILE_SIZE = 6022386; // file size temporary hard coded
                                               // should bigger than the file to be downloaded

  public static void recieveFile () throws IOException {
      
    int bytesRead;
    int current = 0;
    FileOutputStream fos = null;
    BufferedOutputStream bos = null;
    Socket sock = null;
    try {
      sock = new Socket(SERVER, SOCKET_PORT);
      System.out.println("Connecting...");

      // receive file
      byte [] mybytearray  = new byte [FILE_SIZE];
      InputStream is = sock.getInputStream();
      fos = new FileOutputStream(FILE_TO_RECEIVED);
      bos = new BufferedOutputStream(fos);
      bytesRead = is.read(mybytearray,0,mybytearray.length);
      current = bytesRead;
      
      do {
         bytesRead =
            is.read(mybytearray, current, (mybytearray.length-current));
         if(bytesRead >= 0) current += bytesRead;
      } while(bytesRead > -1);

      bos.write(mybytearray, 0 , current);
      bos.flush();
      bos.close();
      fos.close();
      System.out.println("File " + FILE_TO_RECEIVED
          + " downloaded (" + current + " bytes read)");
      sock.close();
    }
    finally {
      if (fos != null) fos.close();
      if (bos != null) bos.close();
      if (sock != null) sock.close();
      
    }
  }
 
  public static void sendString(String server1,int port,String message)
  {
      try{
     Socket socket1;
    
    String str = "initialize";

    socket1 = new Socket(server1, port);

    BufferedReader br = new BufferedReader(new InputStreamReader(socket1.getInputStream()));

    PrintWriter pw = new PrintWriter(socket1.getOutputStream(), true);

    pw.println(message);

    str = br.readLine();
    System.out.println(str);
     

    br.close();
    pw.close();
    socket1.close();    
    }catch(Exception ex){ex.printStackTrace();}
  }
  
      
  public static void recieveData(int port,String enc_key)
  {
    int bytesRead;
    int current = 0;
    FileOutputStream fos = null;
    BufferedOutputStream bos = null;
      try{
   
    ServerSocket servSocket = new ServerSocket(port);
    System.out.println("Waiting for a connection on " + port);

    Socket sock = servSocket.accept();
     byte [] mybytearray  = new byte [FILE_SIZE];
     PrintWriter pw = new PrintWriter(sock.getOutputStream(), true);
      pw.println(enc_key);
      InputStream is = sock.getInputStream();
      fos = new FileOutputStream(FILE_TO_RECEIVED);
      bos = new BufferedOutputStream(fos);
      bytesRead = is.read(mybytearray,0,mybytearray.length);
      current = bytesRead;
      do {
         bytesRead =
            is.read(mybytearray, current, (mybytearray.length-current));
         if(bytesRead >= 0) current += bytesRead;
      } while(bytesRead > -1);
      bos.write(mybytearray, 0 , current);
      pw.close();
      bos.close();
      fos.close();
      sock.close();
        servSocket.close();
 
      }
      catch(Exception ex)
      {
          ex.printStackTrace();
      }
  }
  
  public static void sendString1(String server1,int port,String message)
  {
      try{
    ServerSocket servSocket = new ServerSocket(port);
    System.out.println("Waiting for a connection on " + port);
    Socket sock = servSocket.accept();
    PrintWriter pw = new PrintWriter(sock.getOutputStream(), true);
    pw.println(message);
    pw.close();
    sock.close();    
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
  }
  
}
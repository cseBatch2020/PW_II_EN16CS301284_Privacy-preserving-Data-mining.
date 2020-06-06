/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package distributed_treeclient;

/**
 *
 * @author sai
 */
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleFileServer {

    public static int SOCKET_PORT = 13267;  // you may change this
    public static String FILE_TO_SEND;  // you may change this

    public static void SendFile() throws IOException {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        ServerSocket servsock = null;
        Socket sock = null;
        try {
            servsock = new ServerSocket(SOCKET_PORT);
            System.out.println("Waiting...");
            try {
                sock = servsock.accept();
                System.out.println("Accepted connection : " + sock);
                // send file
                File myFile = new File(FILE_TO_SEND);
                byte[] mybytearray = new byte[(int) myFile.length()];
                fis = new FileInputStream(myFile);
                bis = new BufferedInputStream(fis);
                bis.read(mybytearray, 0, mybytearray.length);
                os = sock.getOutputStream();
                System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
                os.write(mybytearray, 0, mybytearray.length);
                os.flush();
                os.close();
                sock.close();

            } finally {
                if (bis != null) {
                    bis.close();
                }
                if (os != null) {
                    os.close();
                }
                if (sock != null) {
                    sock.close();
                }
            }
        } finally {
            if (servsock != null) {
                servsock.close();
            }
        }
    }

    public static String RecieveData(int port) {
        String str = "";
        try {
            ServerSocket servSocket = new ServerSocket(port);
            System.out.println("Waiting for a connection on " + port);

            Socket fromClientSocket = servSocket.accept();
            PrintWriter pw = new PrintWriter(fromClientSocket.getOutputStream(), true);
            BufferedReader br = new BufferedReader(new InputStreamReader(fromClientSocket.getInputStream()));

            str = br.readLine();
            System.out.println("The Assigned KEY : " + str);
            pw.write("done");
            pw.close();
            br.close();
            fromClientSocket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
       
        return str;
    }

    public static String receiveKey(String server, int port) {
        String dat = "";
        try {
            Socket socket1;
            socket1 = new Socket(server, port);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
            dat = br.readLine();
            br.close();
            socket1.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dat;
    }
    public static String sendFile1(String server,int port)
    {
         String key="";
         FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        Socket socket1;
         try {
                socket1 = new Socket(server,port);
                BufferedReader br = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
                key = br.readLine();
                File myFile = new File(FILE_TO_SEND);
                byte[] mybytearray = new byte[(int) myFile.length()];
                fis = new FileInputStream(myFile);
                bis = new BufferedInputStream(fis);
                bis.read(mybytearray, 0, mybytearray.length);
                os = socket1.getOutputStream();
                System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
                os.write(mybytearray, 0, mybytearray.length);
                os.close();
                //Read Response From Server
                br.close();
                socket1.close();
        }
           catch (Exception ex) {
            ex.printStackTrace();
        }
         return key;
    }
   
}

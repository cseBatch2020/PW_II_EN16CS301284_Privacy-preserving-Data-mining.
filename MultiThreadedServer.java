/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

/**
 *
 *
 */
//Example 26
import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

/*
 * A chat server that delivers public and private messages.
 */
public class MultiThreadedServer {

    // The server socket.
    private static ServerSocket serverSocket = null;
    // The client socket.
    private static Socket clientSocket = null;
    public static int PortNo = 2222;
    // This chat server can accept up to maxClientsCount clients' connections.
    private static final int maxClientsCount = 10;
    private static final clientThread[] threads = new clientThread[maxClientsCount];

    public static void main(String args[]) {

        // The default port number.
        //int portNumber = 2222;
        //if (args.length < 1) {
        System.out
                .println("Usage: java MultiThreadChatServer <portNumber>\n"
                        + "Now using port number=" + PortNo);
        // } else {
        //  PortNo = Integer.valueOf(args[0]).intValue();
        // }
        /*
     * Open a server socket on the portNumber (default 2222). Note that we can
     * not choose a port less than 1023 if we are not privileged users (root).
         */
        try {
            serverSocket = new ServerSocket(PortNo);
        } catch (IOException e) {
            System.out.println(e);
        }

        /*
     * Create a client socket for each connection and pass it to a new client
     * thread.
         */
        while (true) {
            try {
                clientSocket = serverSocket.accept();
                int i = 0;

                for (i = 0; i < maxClientsCount; i++) {
                    if (threads[i] == null) {
                        (threads[i] = new clientThread(clientSocket, threads)).start();
                        break;
                    }
                }

                if (i == maxClientsCount) {
                    PrintStream os = new PrintStream(clientSocket.getOutputStream());
                    os.println("Server too busy. Try later.");
                    os.close();
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}

/*
 * The chat client thread. This client thread opens the input and the output
 * streams for a particular client, ask the client's name, informs all the
 * clients connected to the server about the fact that a new client has joined
 * the chat room, and as long as it receive data, echos that data back to all
 * other clients. When a client leaves the chat room this thread informs also
 * all the clients about that and terminates.
 */
class clientThread extends Thread {

    private DataInputStream is = null;
    private PrintStream os = null;
    private Socket clientSocket = null;
    private final clientThread[] threads;
    private int maxClientsCount;

    public clientThread(Socket clientSocket, clientThread[] threads) {
        this.clientSocket = clientSocket;
        this.threads = threads;
        maxClientsCount = threads.length;
    }

    public void run() {
        int maxClientsCount = this.maxClientsCount;
        clientThread[] threads = this.threads;

        try {
            /*
       * Create input and output streams for this client.
             */
            is = new DataInputStream(clientSocket.getInputStream());
            os = new PrintStream(clientSocket.getOutputStream());
            //os.println("Enter your name.");
            String name = is.readLine().trim();
            String opt = name.substring(0, 4);
//            if (opt.contains("CLAS")) {
//                os.println(getClassify(name.substring(4)));
//            } else if (opt.contains("TREE")) {
            os.println(getData(name.substring(4)));
//            } else if (opt.contains("ATTR")) {
//                os.println(getAttributes(name.substring(4)));
//            } else if (opt.contains("ALLA")) {
//                os.println(getALLAttributes(name.substring(4)));
//            } else if (opt.contains("ALLK")) {
//                os.println(getAttributes(name.substring(4)));
//            }
            String s = null;
            os.println(s);

            // os.println("Hello " + name
            //   + " to our chat room.\nTo leave enter /quit in a new line");
//      for (int i = 0; i < maxClientsCount; i++) {
//        if (threads[i] != null && threads[i] != this) {
//          threads[i].os.println("*** A new user " + name
//              + " entered the chat room !!! ***");
//        }
//      }
//      while (true) {
//        String line = is.readLine();
//        if (line.startsWith("/quit")) {
//          break;
//        }
//	
//        for (int i = 0; i < maxClientsCount; i++) {
//          if (threads[i] != null) {
//            threads[i].os.println("<" + name + "> " + line);
//          }
//        }
//	
//      }
//	
//      for (int i = 0; i < maxClientsCount; i++) {
//        if (threads[i] != null && threads[i] != this) {
//          threads[i].os.println("*** The user " + name
//              + " is leaving the chat room !!! ***");
//        }
//      }
//	
//      os.println("*** Bye " + name + " ***");
            /*
       * Clean up. Set the current thread variable to null so that a new client
       * could be accepted by the server.
             */
            for (int i = 0; i < maxClientsCount; i++) {
                if (threads[i] == this) {
                    threads[i] = null;
                }
            }

            /*
       * Close the output stream, close the input stream, close the socket.
             */
            is.close();
            os.close();
            clientSocket.close();
        } catch (IOException e) {
        }

    }

//    public static String getClassify(String s) {
//        String clas = "MONIKA ";
//        String values = s.substring(0, s.indexOf("|"));
//        s = hexToString(s.substring(s.indexOf("|") + 1));
//        int key = Integer.parseInt(s.substring(s.lastIndexOf("@") + 1));
//        int Startcol = Integer.parseInt(s.substring(0, s.indexOf("#")));
//        int Endcol = Integer.parseInt(s.substring(s.lastIndexOf("#") + 1, s.indexOf("@")));
//        if (Startcol < 0) {
//            Startcol = 0;
//        }
//        Endcol = Startcol + Endcol;
//
//        int i = 0;
//        int k = 0;
//        String[] testToken = values.split(",");
//        String testInstance = "";
//        while (i < MainMenu.MyTable.getColumnCount()) {
//            if (i >= Startcol && i < Endcol && k < testToken.length) {
//                testInstance = testInstance + "," + testToken[k];
//                k++;
//            } else {
//                testInstance = testInstance + ", ";
//            }
//            i++;
//        }
//        i = 0;
//        k = 0;
//        testInstance = testInstance.substring(1);
//        String[] inst = testInstance.split(",");
//
//        clas = getPredict(inst, Startcol, Endcol);
//        return clas;
//    }

    public static String getData(String s) {
//        StringBuilder sbulid = new StringBuilder();

//        ArrayList<String> list = src.MainMenu.all_data.get(s);
//        for (int i = 0; i < list.size(); i++) {
//            sbulid.append(list.get(i) + "\n");
//        }
        return gui.AssociationRules.data.toString();
    }

//    public static String getALLAttributes(String s) {
//        String ret = "";
//        int i = 0;
//        while (i < MainMenu.MyTable.getColumnCount()) {
//
//            ret = ret + "," + MainMenu.MyTable.getColumnName(i);
//
//            i++;
//        }
//        return ret.substring(1);
//    }
//
//    public static String getAttributes(String s) {
//        String ret = "";
//        s = hexToString(s);
//        int key = Integer.parseInt(s.substring(s.lastIndexOf("@") + 1));
//        int Startcol = Integer.parseInt(s.substring(0, s.indexOf("#")));
//        int Endcol = Integer.parseInt(s.substring(s.lastIndexOf("#") + 1, s.indexOf("@")));
//        //Endcol=Endcol;
//        //Startcol=Startcol;
//        if (Startcol < 0) {
//            Startcol = 0;
//        }
//        Endcol = Endcol + Startcol;
//        System.out.println(s);
//        System.out.println("key : " + key);
//        System.out.println("StartCol : " + Startcol);
//        System.out.println("EndCol : " + Endcol);
//        int i = 0;
//        while (i < MainMenu.MyTable.getColumnCount()) {
//            if (i >= Startcol && i <= Endcol) {
//                ret = ret + "," + MainMenu.MyTable.getColumnName(i);
//            }
//            i++;
//        }
//        return ret.substring(1);
//    }
//
//    public static String getPredict(String[] inst, int startcol, int endcol) {
//        TreeMap<Integer, Integer> tm = new TreeMap<Integer, Integer>();
//        String clas = "";
//        int i = 0;
//        // if(startcol>0){startcol++;}
//        while (i < MainMenu.MyTable.getRowCount()) {
//
//            int j = startcol;
//            Integer d = 0;
//            while (j <= endcol) {               //while (j<MainMenu.MyTable.getColumnCount()) {               
//                if (inst[j] == MainMenu.MyTable.getValueAt(i, j) || inst[j].equals(MainMenu.MyTable.getValueAt(i, j))) {
//                    d++;
//                }
//                j++;
//            }
//            if (d == (endcol - startcol)) {
//                return "" + MainMenu.MyTable.getValueAt(i, MainMenu.MyTable.getColumnCount() - 1);
//            }
//            tm.put(d, i);
//            i++;
//        }
//        int c = tm.lastEntry().getValue();
//        clas = "" + MainMenu.MyTable.getValueAt(c, MainMenu.MyTable.getColumnCount() - 1);
//        return clas;
//    }
//
//    public static String hexToString(String txtInHex) {
//        byte[] txtInByte = new byte[txtInHex.length() / 2];
//        int j = 0;
//        for (int i = 0; i < txtInHex.length(); i += 2) {
//            txtInByte[j++] = Byte.parseByte(txtInHex.substring(i, i + 2), 16);
//        }
//        return new String(txtInByte);
//    }
}

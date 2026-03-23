import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(4000);
            System.out.println("Server is waiting for client...");

            Socket sc = serverSocket.accept();
            System.out.println("Client connected");

            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(sc.getInputStream()));
            PrintWriter out = new PrintWriter(sc.getOutputStream(), true);

            // Thread for receiving messages from client
            Thread receiveThread = new Thread(() -> {
                try {
                    String msg;
                    while ((msg = in.readLine()) != null) {
                        System.out.println("Client: " + msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            // Thread for sending messages to client
            Thread sendThread = new Thread(() -> {
                try {
                    String msg;
                    while ((msg = keyboard.readLine()) != null) {
                        out.println(msg);
                        if (msg.equalsIgnoreCase("exit")) {
                            sc.close();
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            //recieved thread start
            receiveThread.start();
            sendThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
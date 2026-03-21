import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            Socket sc = new Socket("localhost", 4000);
            System.out.println("Connected to server");

            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(sc.getInputStream()));
            PrintWriter out = new PrintWriter(sc.getOutputStream(), true);

            // Thread for receiving messages from server
            Thread receiveThread = new Thread(() -> {
                try {
                    String msg;
                    while ((msg = in.readLine()) != null) {
                        System.out.println("Server: " + msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            // Thread for sending messages to server
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
//threads are started to allow simultaneous sending and receiving of messages
            receiveThread.start();
            sendThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
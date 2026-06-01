package socketapp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            System.out.println("Server running on port 1234...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket.getInetAddress());

                // Handle each client in a new thread
                new Thread(new ClientHandler(socket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

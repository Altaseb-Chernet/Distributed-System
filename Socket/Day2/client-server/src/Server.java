import java.io.*;
import java.net.*;
// Server class
public class Server {
   public static void main(String[] args) {
       ServerSocket serverSocket = null;
       try {
           // Start server on port 1234
           serverSocket = new ServerSocket(1234);
           System.out.println("Server is listening on port 1234...");
           while (true) {
               // Accept incoming client connection
               Socket clientSocket = serverSocket.accept();
               System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());
               // Create a new thread to handle the client
               ClientHandler clientHandler = new ClientHandler(clientSocket);
               new Thread(clientHandler).start();
           }
       } catch (IOException e) {
           e.printStackTrace();
       } finally {
           try {
               if (serverSocket != null) serverSocket.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
   }
}

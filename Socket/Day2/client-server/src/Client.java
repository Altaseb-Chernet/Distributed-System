import java.io.*;
import java.net.*;
import java.util.Scanner;
// Client class
public class Client {
   public static void main(String[] args) {
       try (Socket socket = new Socket("localhost", 1234);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in)) {
           String userInput;
           while (true) {
               System.out.print("Enter message: ");
               userInput = scanner.nextLine();
               if ("exit".equalsIgnoreCase(userInput)) break;
               out.println(userInput); // Send message to server
               System.out.println("Server replied: " + in.readLine()); // Read response from server
           }
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
}
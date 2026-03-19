package Database;

import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) {

        try {

            Socket socket = new Socket("localhost", 4000);

            BufferedReader keyboard =
                    new BufferedReader(new InputStreamReader(System.in));

            BufferedReader in =
                    new BufferedReader(new InputStreamReader(socket.getInputStream()));

            PrintWriter out =
                    new PrintWriter(socket.getOutputStream(), true);

            System.out.println("===== MENU =====");
            System.out.println("1. Customers name starts with 'A'");
            System.out.println("2. Customers from Germany");
            System.out.println("3. Employees from London");
            System.out.println("4. Products with price > 50");
            System.out.println("5. Orders for customer ALFKI");
            System.out.println("6. Suppliers from USA");
            System.out.println("7. List Categories");
            System.out.println("8. List Shippers");

            System.out.print("Enter choice (number only): ");

            String choice = keyboard.readLine();

            // 🚫 Prevent user from typing text
            if (!choice.matches("[1-8]")) {
                System.out.println("Invalid input! Please enter number between 1 and 8.");
                socket.close();
                return;
            }

            out.println(choice);

            String response;

            System.out.println("\n--- RESULTS ---");

            while ((response = in.readLine()) != null) {
                System.out.println(response);
            }

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
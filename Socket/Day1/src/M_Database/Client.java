package M_Database;

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

            System.out.println("===== STUDENT MENU =====");
            System.out.println("1. All Students");
            System.out.println("2. Female Students");
            System.out.println("3. Male Students");
            System.out.println("4. Students Age > 21");
            System.out.println("5. Female Students Age > 21");
            System.out.println("6. Software Engineering Students");
            System.out.println("7. Names Start With 'A'");
            System.out.println("8. Sort Students by Age");

            System.out.print("Enter choice (1-8): ");

            String choice = keyboard.readLine();

            // 🚫 Only numbers allowed
            if (!choice.matches("[1-8]")) {
                System.out.println("Invalid input!");
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
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

            System.out.println("Choose operation:");
            System.out.println("1. NAME");
            System.out.println("2. COMPANY");

            String choice = keyboard.readLine();

            out.println(choice);

            String response;

            while ((response = in.readLine()) != null) {
                System.out.println(response);
            }

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
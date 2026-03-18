package Database;

import java.io.*;
import java.net.*;
import java.sql.*;

public class Server {

    static final String URL = "jdbc:mysql://localhost:3306/northwind";
    static final String USER = "root";
    static final String PASS = "";

    public static void main(String[] args) {

        try {

            ServerSocket serverSocket = new ServerSocket(4000);
            System.out.println("Server waiting for client...");

            Socket socket = serverSocket.accept();
            System.out.println("Client connected");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);

            String request = in.readLine();

            // connect to database
            Connection conn = DriverManager.getConnection(URL, USER, PASS);

            Statement stmt = conn.createStatement();

            ResultSet rs;

            if (request.equalsIgnoreCase("NAME")) {

                rs = stmt.executeQuery(
                        "SELECT ContactName FROM Customers");

                while (rs.next()) {
                    out.println(rs.getString("ContactName"));
                }

            } else if (request.equalsIgnoreCase("COMPANY")) {

                rs = stmt.executeQuery(
                        "SELECT CompanyName FROM Customers");

                while (rs.next()) {
                    out.println(rs.getString("CompanyName"));
                }

            } else {
                out.println("Invalid request");
            }

            conn.close();
            socket.close();
            serverSocket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
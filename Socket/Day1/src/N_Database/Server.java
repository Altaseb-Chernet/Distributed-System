package N_Database;

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

            Connection conn = DriverManager.getConnection(URL, USER, PASS);

            PreparedStatement pstmt = null;
            ResultSet rs;

            // 🎯 MENU-BASED REQUEST HANDLING
            switch (request) {

                case "1":
                    // Customers: Name starts with 'A'
                    pstmt = conn.prepareStatement(
                            "SELECT ContactName FROM Customers WHERE ContactName LIKE 'A%'");
                    break;

                case "2":
                    // Customers from Germany
                    pstmt = conn.prepareStatement(
                            "SELECT ContactName, Country FROM Customers WHERE Country = 'Germany'");
                    break;

                case "3":
                    // Employees from London
                    pstmt = conn.prepareStatement(
                            "SELECT FirstName, LastName, City FROM Employees WHERE City = 'London'");
                    break;

                case "4":
                    // Products with price > 50
                    pstmt = conn.prepareStatement(
                            "SELECT ProductName, UnitPrice FROM Products WHERE UnitPrice > 50");
                    break;

                case "5":
                    // Orders with CustomerID (example: ALFKI)
                    pstmt = conn.prepareStatement(
                            "SELECT OrderID, CustomerID FROM Orders WHERE CustomerID = 'ALFKI'");
                    break;

                case "6":
                    // Suppliers from USA
                    pstmt = conn.prepareStatement(
                            "SELECT CompanyName, Country FROM Suppliers WHERE Country = 'USA'");
                    break;

                case "7":
                    // Categories list
                    pstmt = conn.prepareStatement(
                            "SELECT CategoryName FROM Categories");
                    break;

                case "8":
                    // Shippers list
                    pstmt = conn.prepareStatement(
                            "SELECT CompanyName FROM Shippers");
                    break;
                // defualt is excuted if the case not implement
                default:
                    out.println("Invalid choice");
                    socket.close();
                    return;
            }

            rs = pstmt.executeQuery();

            // Send results
            ResultSetMetaData meta = rs.getMetaData();
            int columns = meta.getColumnCount();

            while (rs.next()) {
                String row = "";

                for (int i = 1; i <= columns; i++) {
                    row += rs.getString(i) + " | ";
                }

                out.println(row);
            }

            conn.close();
            socket.close();
            serverSocket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
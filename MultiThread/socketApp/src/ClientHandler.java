import java.io.*;
import java.net.Socket;
import java.sql.*;

public class ClientHandler implements Runnable {

    private Socket socket;

    // 🔹 CHANGE these if needed
    private static final String URL = "jdbc:mysql://localhost:3306/chatdb";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try (
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(
                socket.getOutputStream(), true);
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)
        ) {

            out.println("Connected to server.");
            out.println("Options:");
            out.println("1 - Show all users");
            out.println("exit - Quit");

            String input;

            while ((input = in.readLine()) != null) {

                if (input.equals("1")) {

                    String query = "SELECT * FROM users";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    out.println("---- Users ----");

                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String name = rs.getString("name");
                        out.println(id + " - " + name);
                    }

                    out.println("----------------");

                    rs.close();
                    stmt.close();

                } else if (input.equalsIgnoreCase("exit")) {
                    out.println("Disconnected.");
                    break;

                } else {
                    out.println("Invalid option.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
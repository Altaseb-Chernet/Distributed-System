package chat.client;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Client application for connecting to the Multi-threaded Socket Chat Server.
 */
public class Client {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 1234;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter serverOutput = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            logMessage("Connected to server at " + SERVER_HOST + ":" + SERVER_PORT);

            Thread receiverThread = new Thread(() -> listenToServer(serverInput));
            receiverThread.setName("ServerListener");
            receiverThread.setDaemon(true);
            receiverThread.start();

            sendUserInput(userInput, serverOutput);

        } catch (IOException e) {
            logError("Connection failed: " + e.getMessage());
        }
    }

    private static void listenToServer(BufferedReader serverInput) {
        try {
            String message;
            while ((message = serverInput.readLine()) != null) {
                System.out.println("[Server] " + message);
            }
            logMessage("Disconnected from server.");
        } catch (IOException e) {
            logError("Error reading from server: " + e.getMessage());
        }
    }

    private static void sendUserInput(BufferedReader userInput, PrintWriter serverOutput) throws IOException {
        String input;
        while ((input = userInput.readLine()) != null) {
            if (input.trim().isEmpty()) {
                continue;
            }
            serverOutput.println(input);
        }
    }

    private static void logMessage(String message) {
        System.out.println("[" + LocalDateTime.now().format(FORMATTER) + "] " + message);
    }

    private static void logError(String message) {
        System.err.println("[" + LocalDateTime.now().format(FORMATTER) + "] ERROR: " + message);
    }
}

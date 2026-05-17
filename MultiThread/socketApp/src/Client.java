import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Client application for connecting to the Multi-threaded Socket Chat Server.
 * Sends and receives messages from the server using separate threads for I/O operations.
 */
public class Client {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 1234;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * Connects to the server and establishes a two-way communication channel.
     * One thread listens for server messages while the main thread sends user input.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             BufferedReader serverInput = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             PrintWriter serverOutput = new PrintWriter(
                     socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(
                     new InputStreamReader(System.in))) {

            logMessage("Connected to server at " + SERVER_HOST + ":" + SERVER_PORT);

            // Start a separate thread to listen for server messages
            Thread receiverThread = new Thread(() -> listenToServer(serverInput));
            receiverThread.setName("ServerListener");
            receiverThread.setDaemon(true);
            receiverThread.start();

            // Send user input to server
            sendUserInput(userInput, serverOutput);

        } catch (IOException e) {
            logError("Connection failed: " + e.getMessage());
        }
    }

    /**
     * Listens for incoming messages from the server.
     *
     * @param serverInput the input stream from the server
     */
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

    /**
     * Sends user input to the server.
     *
     * @param userInput the input stream from the console
     * @param serverOutput the output stream to the server
     * @throws IOException if an I/O error occurs
     */
    private static void sendUserInput(BufferedReader userInput, PrintWriter serverOutput) throws IOException {
        String input;
        while ((input = userInput.readLine()) != null) {
            if (input.trim().isEmpty()) {
                continue;
            }
            serverOutput.println(input);
        }
    }

    /**
     * Logs a message with timestamp.
     *
     * @param message the message to log
     */
    private static void logMessage(String message) {
        System.out.println("[" + LocalDateTime.now().format(FORMATTER) + "] " + message);
    }

    /**
     * Logs an error message with timestamp.
     *
     * @param message the error message to log
     */
    private static void logError(String message) {
        System.err.println("[" + LocalDateTime.now().format(FORMATTER) + "] ERROR: " + message);
    }
}
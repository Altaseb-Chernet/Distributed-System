/**
 * Main application entry point for the Multi-threaded Socket Chat Application.
 * This class serves as the launcher for the server and client components.
 */
public class App {
    /**
     * Main method to launch the application.
     * Provides usage instructions for running the server or client.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Multi-threaded Socket Chat Application");
        System.out.println("========================================");
        System.out.println("\nTo start the server, run: java Server");
        System.out.println("To start the client, run: java Client");
        System.out.println("\nMake sure the server is running before starting clients.\n");
    }
}

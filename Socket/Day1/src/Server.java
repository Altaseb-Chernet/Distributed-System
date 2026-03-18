import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    public static void main(String [] args){
        try{
            ServerSocket serverSocket = new ServerSocket(4000);
            System.out.println("server is waiting client ...");

            Socket sc = serverSocket.accept();
            System.out.println("client connected");


            BufferedReader in = new BufferedReader(new InputStreamReader(sc.getInputStream()));
            PrintWriter writer = new PrintWriter(sc.getOutputStream(), true);

            
            String Message = in.readLine();
            System.out.println("Message from client : " + Message);


            writer.println("Hello Client, I am Server");
                sc.close();
                serverSocket.close();
                
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
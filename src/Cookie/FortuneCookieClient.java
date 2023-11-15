package Cookie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

//client code
public class FortuneCookieClient {
    public static void main(String[] args) {
        //checking command line argument
        if (args.length != 1) {
            System.out.println("Usage: java -cp fortunecookie.jar fc.Client <server_address>");
            return;
        }
        //assigning server address based on argument
        String serverAddress = args[0];

        //connecting client to server
        try (
            Socket socket = new Socket(serverAddress, 12345);
            InputStreamReader isr = new InputStreamReader(socket.getInputStream());
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader br =  new BufferedReader(isr);
            InputStreamReader input = new InputStreamReader(System.in);
            BufferedReader userInput = new BufferedReader(input);) {

                while (true) {
                System.out.println("Enter a command (get-cookie or close): ");
                String command = userInput.readLine();

                if ((command.equals("get-cookie")) || (command.equals("close"))) {
                    writer.println(command);
                    if (command.equals("close")) {
                        break;
                    }
                    String response = br.readLine();
                    if ((command.equals("get-cookie"))) {
                        String cookie = response.substring(10);
                        System.out.println("Fortune cookie: " + cookie);
                    } else {
                        System.out.println("Invalid command. Please type get-cookie or close");
                    }
                } 
                
            }

        } catch (IOException e) {
            e.printStackTrace();
        }       
    }
}

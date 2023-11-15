package Cookie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class FortuneCookieServer {

    public static final int default_port = 12345;

    public static void main(String[] args) {
        int port = default_port;
        String cookieFile = "";

        //handling command line argument
        switch (args.length) {
            case 1:
                cookieFile = args[0];
                break;
            case 2:
                cookieFile = args[0];
                port = Integer.parseInt(args[1]);
                break;
            default:
                System.out.println("Argument error");
                System.exit(1);
                break;
        }

        System.out.printf("Loading cookie file %s\n", cookieFile);
        Cookie cookieManager = new Cookie(cookieFile);

        //Loading server and catch error
        try (ServerSocket serSocket = new ServerSocket(port)) {
            System.out.println("Server listening to port: " + port);

            while (true) {
                Socket cliSocket = serSocket.accept();
                ClientHandler(cliSocket, cookieManager);
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }      
    }

    private static void ClientHandler(Socket cliSocket, Cookie cookieManager) {
        
        try ( 
            InputStreamReader isr = new InputStreamReader(cliSocket.getInputStream());
            OutputStream os = cliSocket.getOutputStream();
            BufferedReader br = new BufferedReader(isr); 
            PrintWriter write = new PrintWriter(os, true)) {

            String command = br.readLine();

            if (command.equals("get-cookie")) {
                String cookie = cookieManager.getRandom();
                write.println("cookie-text" + " "+ cookie);
                } else if (command.equals("close")) {
                    cliSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();;
            }


            }
}


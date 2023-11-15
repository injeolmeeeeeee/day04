package Cookie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cookie {

        private final List<String> cookies;

        public Cookie(String filePath) {
            cookies = new ArrayList<>();
            openFile(filePath);
        }

        //open file and read it into an Array List
        public void openFile(String filePath) {
           
            try(
                FileReader fr = new FileReader(filePath);
                BufferedReader br = new BufferedReader(fr)) {
                
                    String line;
                    while ((line = br.readLine()) != null) {
                        cookies.add(line.trim());
                }  
            }
            catch (IOException e) {
                e.printStackTrace();
            } //try-catch block automatically closes br
        }

        //to get random cookie
        public String getRandom() {
            if (cookies.isEmpty()) {
                return "No cookies available";
            }

            Random randomCookie = new Random();
            int i = randomCookie.nextInt(cookies.size());
            return cookies.get(i);
        }
    }
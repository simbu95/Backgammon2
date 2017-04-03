package commtest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommTest {

    public static void main(String[] args) {
        try {
            System.out.println("Hello, please type something:");  //displays string to console
            BufferedReader Read=new BufferedReader(new InputStreamReader(System.in));  //Creates a reader to read inputs
            String line=Read.readLine();  //Reads in input
            System.out.println("You wrote " + line);  // outputs what was sent
        } catch (IOException ex) {
            System.out.println(" IOException");
        }
    }
    
}

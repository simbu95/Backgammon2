package javacomm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaComm {

    
    public static void main(String[] args) {
        try {
            Process p=Runtime.getRuntime().exec("java -jar CommTest.jar");  //starts the other process
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));  // creates a communication from the other process
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));  // creates a communication to the other process
            String line=input.readLine(); //reads a line from other process
            System.out.println("Process p wrote " + line);  //outputs to console what was read
            output.write("Input Received\n"); //sends a message to other process
            output.flush();  //flushes the communication, so the message is indicated as sent
            System.out.println("Sent message: Input Received");  //writes to console what was sent
            line=input.readLine();  //reads reply
            System.out.println("Process p wrote " + line);  //outputs reply to console
            
        } catch (IOException ex) {
            System.out.println("IOException");
        }
    }
    
}

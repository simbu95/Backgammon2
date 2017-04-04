package javacomm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaComm {

    
    public static void main(String[] args) {
        GameTest2();
    }
    public static void GameTest(){
        try {
            BufferedReader Read=new BufferedReader(new FileReader("TextFile.txt"));
            Process p=Runtime.getRuntime().exec(Read.readLine());
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream())); 
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));  
            String line;
            while((line=Read.readLine())!=null){
                System.out.println("Sending: " + line);
                output.write(line +"\n"); //sends a message to other process
                output.flush();  //flushes the communication, so the message is indicated as sent
                System.out.println("Other Program sent: " + input.readLine());
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found");
        } catch (IOException ex) {
            System.out.println("IO Error");
        }
    }
    public static String RandomMoveGen(){
        Random r=new Random();
        String Builder;
        if(r.nextBoolean()){
            Builder="Y,Y,N";
        }
        else{
            Builder="N,N,Y";
        }
        int[] stones=new int[26];
        int mine=16;
        int yours=16;
        for(int i=1;i<25;i++){
            if(r.nextBoolean()){
                stones[i]=r.nextInt(mine);
                mine-=stones[i];
            }
            else{
                stones[i]=-r.nextInt(yours);
                yours+=stones[i];
            }
        }
        if(r.nextBoolean()){
            int[] switcher=new int[26];
            for(int i=0;i<26;i++){
                switcher[i]=stones[25-i];
            }
            stones=switcher;
        }
        int one;
        int two;
        int temp;
        for(int i=0;i<100;i++){
            one=r.nextInt(24)+1;
            two=r.nextInt(24)+1;
            temp=stones[one];
            stones[one]=stones[two];
            stones[two]=temp;
        }
        for(int i:stones){
            Builder+=","+i;
        }
        return Builder;
    }
    public static void ExampleOne(){
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

    private static void GameTest2() {
        try {
            BufferedReader Read=new BufferedReader(new FileReader("TextFile.txt"));
            Process p=Runtime.getRuntime().exec(Read.readLine());
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream())); 
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));  
            String line;
            int max=Integer.parseInt(Read.readLine());
            for(int i=0;i<max;i++){
                line=RandomMoveGen();
                System.out.println("Sending: " + line);
                output.write(line +"\n"); //sends a message to other process
                output.flush();  //flushes the communication, so the message is indicated as sent
                System.out.println("Other Program sent: " + input.readLine());
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found");
        } catch (IOException ex) {
            System.out.println("IO Error");
        }
    }
}

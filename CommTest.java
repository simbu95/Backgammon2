package commtest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommTest {

    public static void main(String[] args) {
        TestTwo();
    }
    
    public static void TestOne(){
        BufferedReader Read=new BufferedReader(new InputStreamReader(System.in));  //Creates a reader to read inputs
        try {
            while(true){
                String line=Read.readLine();  //Reads in input
                System.out.println("You wrote " + line);  // outputs what was sent
            }
        } catch (IOException ex) {
            System.out.println(" IOException");
        }
    }
    public static void TestTwo(){
        BufferedReader Read=new BufferedReader(new InputStreamReader(System.in));
        String[] inputs;
        String line;
        int temp;
        int count=0;
        try {
            while(true){
                count=0;
                line=Read.readLine();  //Reads in input
                inputs=line.split(",");
                for(int i=3;i<29;i++){
                    if((temp=Integer.parseInt(inputs[i]))>=0){
                        count+=temp*(29-i);
                    }
                    else{
                        count+=temp*(i-3);
                    }
                    
                }
                System.out.println(count);
                if(count>0){
                    System.out.println("N");
                }
                else{
                    System.out.println("Y");
                }
            }
        } catch (IOException ex) {
            System.out.println(" IOException");
        }
    }
}

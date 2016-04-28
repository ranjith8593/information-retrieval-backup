/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationretrieval;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ranjith Ramesh
 */
public class ReadFile {
    
    
    public void readData(String str , String file) throws FileNotFoundException, IOException{
        
   
        
        Scanner in = new Scanner(new File(file));
        
        while(in.hasNext()){
            
            String line = in.nextLine();
            
            if(line.contains(str.toLowerCase())){
                
                String [] linearray = line.split(" ");
                
                if(linearray[0].equals(str)){
                  System.out.println(line);  
                }
                
            }
        }
        
        
        
      
            
        }
        
        
       public static void main(String [] args){
         
         ReadFile rf = new ReadFile();
        try {
            rf.readData("archer","postingslist.bin");
            rf.readData("archer","dictionary.bin");
            rf.readData("blameless","postingslist.bin");
            rf.readData("blameless","dictionary.bin");
            rf.readData("study","postingslist.bin");
            rf.readData("study","dictionary.bin");
            rf.readData("nuclear","postingslist.bin");
            rf.readData("nuclear","dictionary.bin");
            rf.readData("horse","dictionary.bin");
            rf.readData("lovingkindness","dictionary.bin");
            rf.readData("Mary","dictionary.bin");
            rf.readData("dance","dictionary.bin");
        } catch (IOException ex) {
            Logger.getLogger(ReadFile.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        }
        
        
        
    }
    
     
    
    


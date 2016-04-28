
package informationretrieval;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.RandomAccess;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ranjith Ramesh
 * The program takes the input file and process it line by line.
 * It normalizes the lines by removing special characters.
 * The program then tokenizes each word and puts it in a dictionary.
 * simultaniously we also create postings list which contains the doc id and the document frequency. 
 * we write both the files as binary files in the disk. 
 */
public class InvertedFile {
    
    
    
    public TreeMap<String , TreeMap<Integer ,Integer>> tmap = new TreeMap<String , TreeMap<Integer ,Integer>>();
    public TreeMap< String , Integer> dictionary = new TreeMap<String ,Integer> ();
    
    static int sum = 0;
    
    public void indexFile(BufferedReader reader) throws IOException{
        
        String line = "";
        int docid = 0;

        
        RandomAccessFile  file =  new RandomAccessFile ("postingslistfirequery.bin","rw");
        RandomAccessFile  file2 =  new RandomAccessFile ("dictionaryfirequery.bin","rw");

        while(  (line = reader.readLine())!= null) {
            
            line = line.replaceAll("[^a-zA-Z</>=0-9]"," ").toLowerCase();
            line = line.replaceAll("[://]"," ").toLowerCase();
            
            if( line.contains("id=")){
                
                docid++;
                
            }
            
            StringTokenizer st = new StringTokenizer( line , " ");
            
            while(st.hasMoreTokens()){
                
                String tmp = st.nextToken().toLowerCase();
                
                 if(!(tmp.contains("</q>") || tmp.contains("<q") || tmp.contains("id=") )  ){
                     
                     
                     
                     if(tmap.get(tmp) != null){
                         TreeMap <Integer , Integer> templist = new TreeMap<Integer , Integer>();
                         templist = tmap.get(tmp);
                         
                         if(templist.get(docid) != null){
                             
                             int temp = templist.get(docid);
                             temp++;
                             sum++;
                             templist.put(docid,temp);
                             tmap.put(tmp,templist);
                     
                             
                         }
                         
                         else{
                             
                             templist.put(docid,1);
                             tmap.put(tmp,templist);
                             sum = sum+1;
                         }
                   }
                     
                     else{
                         TreeMap <Integer , Integer> ilist = new TreeMap<Integer , Integer>();
                         ilist.put(docid,1);
                         tmap.put(tmp,ilist);         
                     }
                     
                     
    
                 }
      
            }
      
        }
        
        
        
        
        
        //int key1 = 0;
       
        
        for( Map.Entry entry : tmap.entrySet()){
           
            String t = entry.getKey().toString()+" ";
        
            file.write(t.getBytes());
        
             TreeMap <Integer , Integer> tt = new TreeMap<Integer , Integer>();
       
             tt = ( TreeMap <Integer , Integer>)entry.getValue();
             
             dictionary.put(entry.getKey().toString(), tt.size());
             file.write( tt.toString().getBytes());
             file.write("\n".getBytes());
             
        
        }
        
       for( Map.Entry entry : dictionary.entrySet()){
             
           
            file2.write(entry.getKey().toString().getBytes());
            file2.write(" ".getBytes());
            file2.write(entry.getValue().toString().getBytes());
            file2.write("\n".getBytes());
            
        }
        
        System.out.println("The total number of documents :"+ docid);
        System.out.println("The size of the vocabulary :"+ tmap.size());
        System.out.println("Total numbr of words  :"+ sum);
        
    }
    
    public static void main(String [] args){
        
        try {
            FileInputStream file = new FileInputStream ( new File("C:\\MS\\IR\\assignment 3\\fire10.topics.en.utf8"));
            BufferedReader reader = new BufferedReader( new InputStreamReader( new DataInputStream(file)));
            InvertedFile inf = new InvertedFile();
            inf.indexFile(reader);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InvertedFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InvertedFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}

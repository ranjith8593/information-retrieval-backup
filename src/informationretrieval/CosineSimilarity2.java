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
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ranjith Ramesh
 */
public class CosineSimilarity2 {
    

public TreeMap<String , TreeMap<Integer ,Double>> tmap = new TreeMap<String , TreeMap<Integer ,Double>>();

public TreeMap<String , TreeMap<Integer ,Double>> tdfvalue = new TreeMap<String , TreeMap<Integer ,Double>>();
    public TreeMap< String , Double> idfvalue1 = new TreeMap<String ,Double> ();
    
    static int sum = 0;
    
    public void indexFile(BufferedReader reader) throws IOException{
        
        String line = "";
        int docid = 0;

        
        RandomAccessFile  file =  new RandomAccessFile ("animalcorpus.txt","rw");
        RandomAccessFile  file2 =  new RandomAccessFile ("animalquery.txt","rw");

        while(  (line = reader.readLine())!= null) {
            
            line = line.replaceAll("[^a-zA-Z</>=0-9]"," ").toLowerCase();
            line = line.replaceAll("[://]"," ").toLowerCase();
            
            if( line.contains("id=")){
                
                docid++;
                
            }
            
            StringTokenizer st = new StringTokenizer( line , " ");
            
            while(st.hasMoreTokens()){
                
                String tmp = st.nextToken().toLowerCase();
                
              //  if(tmp.length() > 5)
                     
                    //tmp = tmp.substring(0, 4);
                     
                
                 if(!(tmp.contains("</p>") || tmp.contains("<p") || tmp.contains("id=") )  ){
                     
                     
                     if(tmap.get(tmp) != null){
                         TreeMap <Integer , Double> templist = new TreeMap<Integer , Double>();
                         templist = tmap.get(tmp);
                         
                         if(templist.get(docid) != null){
                             
                             double temp = templist.get(docid);
                             temp++;
                             sum++;
                             templist.put(docid,temp);
                             tmap.put(tmp,templist);
                     
                             
                         }
                         
                         else{
                             
                             templist.put(docid,1.0);
                             tmap.put(tmp,templist);
                             sum = sum+1;
                         }
                   }
                     
                     else{
                         TreeMap <Integer , Double> ilist = new TreeMap<Integer , Double>();
                         ilist.put(docid,1.0);
                         tmap.put(tmp,ilist);         
                     }
                     
                     
    
                 }
      
            }
      
        }
        
        
        
        
        
        //int key1 = 0;
       
        //System.out.println("dgdgsfgsgsg");
        
        for( Map.Entry entry : tmap.entrySet()){
           
            String t = entry.getKey().toString();
           // System.out.println("key" + t);
            file.write(t.getBytes());
             file.write(" ".getBytes());
        
             TreeMap <Integer , Integer> tt = new TreeMap<Integer , Integer>();
             TreeMap <Integer , Double> tflist = new TreeMap<Integer , Double>();
       
             tt = ( TreeMap <Integer , Integer>)entry.getValue();
             
             for( Map.Entry ent : tt.entrySet()){
                 
                 int key = (Integer)ent.getKey();
                
                 
                 
                 double value = (Double) ent.getValue();
                 double idfval = 1.0+ (double) Math.log(value)/ Math.log(2);
                 
                 
                 tflist.put(key, idfval);
                 
             }
             
             tmap.put(t, tflist);
             
            // System.out.println("dgdgsfgsgsg");
             
             double ttsize = (double)tt.size();
             //System.out.println("before taking log "+ ttsize);
             double tfid =  Math.log(docid/ttsize)/Math.log(2);
             
             //System.out.println("term frequency : "+ tfid);
             
             idfvalue1.put(entry.getKey().toString(), tfid);
             
             
             for( Map.Entry ent : tflist.entrySet()){
                 int key = (Integer)ent.getKey();
                  String key_str = Integer.toString(key);
                 file.write(key_str.getBytes());
                  file.write("->".getBytes());
                 //System.out.println("doc id"+key);
                 double  value = (Double)ent.getValue();
                 
                 double dictvalue = idfvalue1.get(t);
                 double idfval = value * dictvalue;
                 //System.out.println("tfidf "+idfval);
                 String idfval_str = Double.toString(idfval);
                 
                 file.write(idfval_str.getBytes());
                 file.write(" ".getBytes());
                 
                 //tflist.put(key, idfval);
                 
             }
             
             //tdfvalue.put(t, tflist);
             
             //file.write( tflist.toString().getBytes());
             file.write("\n".getBytes());
             
        
        }
        
       for( Map.Entry entry : idfvalue1.entrySet()){
             
           
            file2.write(entry.getKey().toString().getBytes());
            file2.write(" ".getBytes());
            file2.write(entry.getValue().toString().getBytes());
            file2.write("\n".getBytes());
            
        }
       
       
       QueryProcessor q = new QueryProcessor();
      q.indexFile(idfvalue1 , tmap , docid);
        
        System.out.println("The total number of  documents:"+ docid);
        System.out.println("The size of the vocabulary :"+ tmap.size());
        System.out.println("Total numbr of words  :"+ sum);
        
        int mem = (int) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
        System.out.println("memory "+mem);
        
        
        
    }
    
   
    
    
    
    public static void main(String [] args){
        
        try {
            FileInputStream file = new FileInputStream ( new File("C:\\MS\\fire10.txt"));
            BufferedReader reader = new BufferedReader( new InputStreamReader( new DataInputStream(file)));
            CosineSimilarity2 inf = new CosineSimilarity2();
            inf.indexFile(reader);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InvertedFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InvertedFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }




}

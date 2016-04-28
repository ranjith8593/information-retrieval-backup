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
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 *
 * @author Ranjith Ramesh
 */
public class QueryProcessor {
    
    
    public TreeMap< String , Double> dictionary = new TreeMap<String ,Double> ();
    
     public void indexFile(TreeMap< String , Double> idfvalue1 , TreeMap<String , TreeMap<Integer ,Double>> tdfvalue , int docid) throws IOException{
         
         //System.out.println("SIZE    "+idfvalue1.size());
         
         
          FileInputStream file = new FileInputStream ( new File("C:\\MS\\IR\\assignment 3\\firequery.txt"));
            BufferedReader reader = new BufferedReader( new InputStreamReader( new DataInputStream(file)));
         String line ; 
          boolean set = true;
          int id = 0;
         while(  (line = reader.readLine())!= null) {
          
              line = line.replaceAll("[^a-zA-Z</>=0-9]"," ").toLowerCase();
              StringTokenizer st = new StringTokenizer( line , " ");
              while(st.hasMoreTokens()){
                  String tmp = st.nextToken().toLowerCase();
                  
                  // if(tmp.length() > 5)
                     
                    //tmp = tmp.substring(0, 4);
                  
                  System.out.println(tmp);
                  if((  tmp.contains("id=") )  ){
                       set = true;
                       id++;
                  }
                  
                  if((tmp.contains("</q>"))){
                      
                      set = false;
                  }
                  
                  
                  
                  if(!(tmp.contains("</q>") || tmp.contains("<q") || tmp.contains("id=") )  ){
                      
                     
                      
                      
                        if(dictionary.containsKey(tmp)){
                          
                          double value = dictionary.get(tmp);
                          dictionary.put(tmp, value++);
                          
                      }
                        else
                      dictionary.put(tmp , 1.0);  
                          
                          
                  }
                      
                  else if ( tmp.contains("</q>")){
                          
                             callRank(dictionary , idfvalue1 , tdfvalue ,docid,id );

                          
                      }
        
            }
              
              
             
         }
         
         
          
          
     }
     
     
     public int callRank(TreeMap< String , Double> dictionary , TreeMap< String , Double> idfvalue1 , TreeMap<String , TreeMap<Integer ,Double>> tdfvalue ,int docid , int id) throws IOException{
         for( Map.Entry e : dictionary.entrySet()){
                              
                              String Key = (String) e.getKey();
                              
                              //System.out.println("KEY VALUE"+Key);
                              double val = (double)e.getValue();
                              val = 1 + Math.log(val)/ Math.log(2.0);
                              
                              double idfval =  idfvalue1.get(Key);
                              
                              val = val*idfval;
                              
                              //System.out.println(val);
                              dictionary.put(Key, val);
                              
                              
                          }
          
          RankDocuments r = new RankDocuments();
          r.rankDocuments(tdfvalue, dictionary, docid,id);
        
         
         return 1 ;
     }
    
}

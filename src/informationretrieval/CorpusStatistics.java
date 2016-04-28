
package informationretrieval;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Ranjith Ramesh
 */
public class CorpusStatistics  {

    /**
     * @param reader
     * @param out
     * @throws java.io.IOException
     * This function normalizes the input text and stores it in the output folder. It removes the special character like 
     * . ? [] {} / etc
     * It also counts the collection frequency and document frequency.
     * It also calculates the top 100 , 500, 1000 frequent words.
     * It also displays the top 30 frequent words.
     * The function also displays the unique words and the number of unique words and the percentage of unique words 
     * 
     */
    
    public void normalize( BufferedReader reader , PrintWriter out) throws IOException{
        
        String string;
        String [] words;
        String word;
        String para= "";
        String key100 = "" ;
        String key500 = "" ;
        String key1000 = "" ;
        int paracount=0;
        int wordcount=0;
        int count = 0;
        int count30 = 0;
        int uniquecount = 0 ;
        HashMap<String,Integer> wordMap  = new HashMap<String,Integer>();
        HashMap<String , List> wordPara = new HashMap<String,List>();
        HashMap<String , Integer> wordParaCount = new HashMap<String,Integer>();
        
       
        while((string = reader.readLine())!= null){
            string = Normalizer.normalize(string, Normalizer.Form.NFD);
            string = string.replaceAll("[^a-zA-Z</>=0-9 ]"," ").toLowerCase();
            
            if(string.contains("id=")){
                paracount++;  
                para = string;
            }
    
            StringTokenizer st =    new StringTokenizer(string , " ");

            while(st.hasMoreTokens()){
                
                String tmp = st.nextToken().toLowerCase();
                if(!(tmp.contains("</p>") || tmp.contains("<p") || tmp.contains("id=") )  ){


                    if(!wordPara.containsKey(tmp)){
                        List <String> list = new ArrayList<String>();
                        list.add(para);
                        wordPara.put(tmp,list);
                    }

                    else{
                        List <String> listval = new ArrayList<String>();
                        listval = wordPara.get(tmp);
                        if(!listval.contains(para)){
                            listval.add(para);
                            wordPara.put(tmp,listval);
                            
                        }
                        
                    }

                    if(wordMap.containsKey(tmp)){

                        wordMap.put(tmp,wordMap.get(tmp)+1);
                    }

                    else{
                        wordMap.put(tmp,1);
                    }

                }                      
            }              
   
            out.println(string);
        }
        
        TreeMap<String, Integer> sortedMap = SortByValue((HashMap<String, Integer>) wordMap);
        
         
       System.out.println("----------------------------------------------------Collection Frequency-------------------------------------------------");
        
        for( Map.Entry entry : sortedMap.entrySet()){
            
            count++;
            
            if(count == 100){
               
                key100 = entry.getKey().toString();
                //System.out.println("100 freq val"+entry.getKey() + ", " + entry.getValue());
            }
            
            else if (count == 500){
                key500 = entry.getKey().toString();
                //System.out.println("500 freq val"+entry.getKey() + ", " + entry.getValue());
            }
            
            else if (count == 1000){
                key1000 = entry.getKey().toString();
                //System.out.println("1000 freq val"+entry.getKey() + ", " + entry.getValue());
            }
            
            System.out.println(entry.getKey() + ":" + entry.getValue());
            
            wordcount += (Integer)entry.getValue();
            
            if((Integer)entry.getValue() == 1){
                
                uniquecount++;
                System.out.println("unique word");
                System.out.println(entry.getKey());
                
            }
            
        }
        
        System.out.println("----------------------------------------------------Collection Frequency-------------------------------------------------");
        System.out.println("100 freq val: "+ key100);
        System.out.println("500 freq val: "+key500);
        System.out.println("1000 freq val: "+key1000);
    
        for( Map.Entry entry : wordPara.entrySet()){

            List <String> templist = (List<String>)entry.getValue();

            wordParaCount.put(entry.getKey().toString(),templist.size());
        }
        
        TreeMap<String, Integer> sortedMap2 = SortByValue((HashMap<String, Integer>) wordParaCount);
        System.out.println("----------------------------------------------------Document Frequency-------------------------------------------------");
        for( Map.Entry entry : wordPara.entrySet()){

             System.out.println(entry.getKey() + ":"+entry.getValue());
             List <String> templist = (List<String>)entry.getValue();
             System.out.println("frequency"+templist.size());
        }
        System.out.println("----------------------------------------------------Document Frequency-------------------------------------------------"); 
        
        System.out.println("----------------------------------------------------Top 30-------------------------------------------------");
        for( Map.Entry entry : sortedMap.entrySet()){
             if(count30 > 30){
                 break;
             }
             System.out.println(entry.getKey() + ", Collection Frequency " + entry.getValue());
             System.out.println(" Document Frequency " + wordParaCount.get(entry.getKey().toString()));
             count30++;
        }
        
        System.out.println("----------------------------------------------------Top 30-------------------------------------------------");
        
        
        
        
          
        System.out.println("Total number of documents : "+paracount);
        System.out.println("Total number of words : "+wordcount);
        System.out.println("Ttal number of unique words  : "+uniquecount);
        double percentage = (((double)uniquecount/(double)wordcount)*100);
        System.out.println("percentage of unique word:"+percentage);
        
            
   
    }
    
    
    public static TreeMap<String, Integer> SortByValue (HashMap<String, Integer> map) {
        ValueComparator vc =  new ValueComparator(map);
        TreeMap<String,Integer> sortedMap = new TreeMap<String,Integer>(vc);
        sortedMap.putAll(map);
        return sortedMap;
    }
    
    
    
    public static void main(String[] args) throws IOException {
        
        
        
        try {
            // TODO code application logic here
           
            FileInputStream fs = new FileInputStream("C:\\MS\\fire10.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(new DataInputStream(fs)));
            PrintWriter out = new PrintWriter("C:\\MS\\IR\\mod 1\\output\\output.txt");
            
            //FileInputStream fs2 = new FileInputStream("C:\\MS\\IR\\mod 1\\assignment 1 io\\lesmis.txt");
            //BufferedReader reader2 = new BufferedReader(new InputStreamReader(new DataInputStream(fs2)));
            //PrintWriter out2 = new PrintWriter("C:\\MS\\IR\\mod 1\\output\\lesmis.txt");
            
            CorpusStatistics cs = new CorpusStatistics();
            //cs.normalize(reader,out);
            cs.normalize(reader,out);
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CorpusStatistics.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

class ValueComparator implements Comparator<String> {
 
    Map<String, Integer> map;
 
    public ValueComparator(Map<String, Integer> base) {
        this.map = base;
    }
 
    public int compare(String a, String b) {
        if (map.get(a) >= map.get(b)) {
            return -1;
        } else {
            return 1;
        } 
    }
}

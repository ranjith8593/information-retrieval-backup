/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationretrieval;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Ranjith Ramesh
 */
public class RankDocuments {
    
    
    
    public void rankDocuments(TreeMap<String , TreeMap<Integer ,Double>> tdfvalue , TreeMap< String , Double> dictionary , int maxarr,int qid ) throws FileNotFoundException, IOException{
        
        
         TreeMap <Integer , Double> rank = new  TreeMap <Integer , Double>();
         TreeMap <Integer , Double> sortedrank = new TreeMap <Integer , Double>();
         TreeMap<Integer , Double> dotproductlist = new TreeMap <Integer , Double>();
         TreeMap<Integer , Double> maglist1 = new TreeMap <Integer , Double>();
         TreeMap<Integer , Double> maglist2 = new TreeMap <Integer , Double>();
         RandomAccessFile  file =  new RandomAccessFile ("ramesh-a.txt","rw");
        
        for( Map.Entry ent : dictionary.entrySet()){
            
            String key = ent.getKey().toString();
            //System.out.println("key " + key);
            double qvalue = (double)ent.getValue();
           // System.out.println("qvalue value"+qvalue);
            TreeMap<Integer ,Double> documents = tdfvalue.get(key);
             //System.out.println("document size" + documents.size());
            for( Map.Entry en : documents.entrySet()){
                
                int docid = (int)en.getKey();
                
                //System.out.println("docid"+docid);
                double docvalue = (double)en.getValue();
                
                
                
                //System.out.println("doc value :"+docvalue);
                //System.out.println("qvalue:"+qvalue);
                
                
                //System.out.println("doc value"+docvalue);
                if(qvalue != 0.0 || docvalue != 0.0){
                    
                    double dotproduct = (docvalue)*(qvalue);
                    
                    
                    if(dotproductlist.get(docid) != null){
                        
                        double temp = dotproductlist.get(docid);
                        temp = temp + dotproduct;
                         dotproductlist.put(docid, temp);
                    }
                    
                    
                    else
                    dotproductlist.put(docid, dotproduct);
                    
                    
                    
                    double mag1 = Math.pow(docvalue, 2);
                    
                    if(maglist1.get(docid) != null){
                        
                        double temp = maglist1.get(docid);
                        temp = temp + mag1;
                         maglist1.put(docid, temp);
                    }
                    
                    
                    else
                    maglist1.put(docid, mag1);
                    
                    
                    double mag2 = Math.pow(qvalue, 2);
                    
                    if(maglist2.get(docid) != null){
                        
                        double temp = maglist2.get(docid);
                        temp = temp + mag1;
                         maglist2.put(docid, temp);
                    }
                    
                    
                    else
                    maglist2.put(docid, mag2);
                    
                    
                    //mag1 = Math.sqrt(mag1);
                   // mag2 = Math.sqrt(mag2);
                 
                //double docrankval = dotproduct/(mag1 * mag2);
                //System.out.println("docrankval :"+docrankval);
               /* if( docrankval < 1.0 ){
                    
                      //System.out.println("doc id score "+docid);
                  //System.out.println("cosine score "+docrankval);
                   //System.out.println("doc value"+docvalue);
                   // System.out.println("qvalue value"+qvalue);
                  
                }*/
               
                /*if(docrankval < 1.0){
                  if(rank.get(docid) != null){
                 double temp = rank.get(docid);
                temp += docrankval;
                rank.put(docid, temp);   
                }
                
                else{
                     rank.put(docid,docrankval ); 
                }  
                    
                }*/
                
                
                }
                
                
                
            }
            
            
            
            
            
            
            
        }
        
        
        for(Map.Entry e : dotproductlist.entrySet()){
                int docidvalue = (int) e.getKey();
                
                //System.out.println("key"+":"+docidvalue);
                double dotproduct = (double)e.getValue();
                double mag1 = (double)maglist1.get(docidvalue);
                double mag2 = (double)maglist2.get(docidvalue);
                double cosine;
                mag1 = Math.sqrt(mag1);
                mag2 = Math.sqrt(mag2);
                
                if(mag1 != 0.0 | mag2 != 0.0 ){
                   
                    
                    cosine = dotproduct/(mag1 * mag2 );
                    //System.out.println("docidvalue"+docidvalue);
                    
                    if(rank.get(docidvalue) == null){
                        if(cosine < 1.0)
                        rank.put(docidvalue, cosine);
                    }
                    
                }
                 
                
            }
            
            
           sortedrank = sortMapByValue(rank);
         int i = 0;
            for( Map.Entry e : sortedrank.entrySet()){
                
               
                  file.write(e.getKey().toString().getBytes());
                file.write(" q id=76 ".getBytes());
                file.write(e.getValue().toString().getBytes());
                file.write("Ranjith Ramesh".getBytes());
                 file.write(" \n ".getBytes());
                 
                   
               
               
              
                
                
                //System.out.println(" key "+e.getKey() + " value : "+ e.getValue().toString());
            }
        
        
        
        
    }
    
    
    
    public TreeMap <Integer , Double>  sortMapByValue( TreeMap <Integer , Double> rank){
        
        
         TreeMap <Integer , Double> sortedrank = new TreeMap(new ValueComparator1(rank));
         
        sortedrank.putAll(rank);
        return sortedrank;
        
    }

    
}


class ValueComparator1 implements Comparator{
    Map map;
 
	public ValueComparator1(Map map) {
		this.map = map;
	}
    public int compare(Object keyA, Object keyB) {
		Comparable valueA = (Comparable) map.get(keyA);
		Comparable valueB = (Comparable) map.get(keyB);
		return valueB.compareTo(valueA);
	}
}
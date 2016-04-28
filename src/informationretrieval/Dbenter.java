/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationretrieval;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ranjith Ramesh
 */
public class Dbenter {
    
    static int top10 = 0;
     public static void main(String[] args) throws IOException {
         
        try {
            String query = "";
            int stowordsquery = 0 ;
            int lowercase = 0 ;
            int uppercase = 0 ;
            int total = 0; 
            int mixed = 0 ;
            int words = 0 ; 
            int charsize = 0;
            String querytrim = "";
            
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ir", "root","root");
            String querydb = "";
            querydb = "INSERT into ir.weblogs VALUES(?,?,?,?)";
            PreparedStatement ps;
            ps = conn.prepareStatement(querydb);
            
            BufferedReader TSVFile = new BufferedReader(new FileReader("C:\\MS\\IR\\proj 5\\io.tsv"));
            String dataRow = "";
            while ((dataRow = TSVFile.readLine().toString())!= null){
                
                total ++;
                String [] result = dataRow.split("\t");
                if(result.length == 4 && result[3].length() < 500){
                    
                     
                ps.setString(1,result[0]);
                ps.setString(2,result[1]);
                ps.setString(3,result[2]);
                ps.setString(4,result[3]);
                
                ps.execute();

                    
                }

                

                
                
                
            }
            
        
        
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Dbenter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Dbenter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Dbenter.class.getName()).log(Level.SEVERE, null, ex);
        }
       
         
         
     }
    
}

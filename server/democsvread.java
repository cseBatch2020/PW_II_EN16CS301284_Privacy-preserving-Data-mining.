/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 *
 * @author maxtech
 */
public class democsvread {
    public static void main(String[] args) {
        getdata();
    }
    public static void getdata(){
        StringBuffer data=new StringBuffer();
        try {
            String line="";
            File f=new File("dataset.csv");
            FileReader fr=new FileReader(f);
            BufferedReader br=new BufferedReader(fr);
            while ((line=br.readLine()) != null) {                
               String[] linedata=line.split(",");
                for (int i = 0; i < linedata.length; i++) {
                    String linedata1 = linedata[i].substring(1, linedata[i].length()-1);
                    if(i > 7 || i==3){
                       
                    data.append(linedata1+",");
                        
                    }
                    else if(i==linedata.length-1){
                    data.append(linedata1);
                    }
                }
                data.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
           PrintWriter pw=new PrintWriter("medicaldata12.csv");
           pw.print(data.toString());
           pw.close();
        } catch (Exception e) {
        }
       
    
    }
}

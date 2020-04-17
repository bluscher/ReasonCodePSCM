/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.reasoncode;

import com.experian.eda.component.decisionagent.LookUpManager;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import com.opencsv.CSVWriter;
import java.io.FileInputStream;
import java.nio.file.Path;

/**
 *
 * @author e10934a
 */
public class ArchivoTest {
   
     String rcodesPath = "";    
     String SIGNATURE_NAME = "";
     String resultPath = "";
    
    
    
    public ArchivoTest(String input, String firma, Path output ){
       
        this.rcodesPath = input;
 
        if (output.toFile().isDirectory()) {
            File folderOutput = new File(output.toFile().toString()+"/output");
            folderOutput.mkdir();  
            resultPath = output.toString()+ "/output/reasonCSV.csv";
        }else 
            System.exit(1);
               
        this.SIGNATURE_NAME = firma;
    }
  
    public void extraerReasonCode() throws IOException{
        File file = new File("/test/Decision Reason Codes.ser");
        File folder = new File("C:\\test\\");
        File[] listOfFiles = folder.listFiles(); 
        FileInputStream fic = null;
        fic = new FileInputStream(file);
        
        
        String context = this.getFileName(file.getName());
        

        LookUpManager lookUpMngr = LookUpManager.getInstance(context, SIGNATURE_NAME);
        Map<String, Object> map = lookUpMngr.getAllReasonCodeDescs();

    }//end clase ExtraerReason
    
    
    //eliminio extension del nombre
    public String getFileName(String ser){
        return ser.substring(0, ser.lastIndexOf('.'));
    }

   
    // genero el archivo.csv exista o no, y se agregan los reasones code
    private void setArchCSV(Map<String, Object> map){
       
        boolean  existeArch = new File(this.resultPath).exists();
        if(existeArch){
        File reasonCSV = new File(resultPath);
        reasonCSV.delete();
        }
        
        try {
              CSVWriter csvOutput = new CSVWriter(new FileWriter(resultPath,true),',');
          
              for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                String value =  (entry.getValue()).toString();
                csvOutput.writeNext(new String[]{key, value});
             }     
              csvOutput.close();
        } catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
       
    }
}

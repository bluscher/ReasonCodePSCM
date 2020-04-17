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
import java.nio.file.Path;

/**
 *
 * @author e10934a
 */
public class Archivo {
   
     String rcodesPath = "";    
     String SIGNATURE_NAME = "";
     String resultPath = "";
   
    //constructor con output en carpeta del sistema
    /*public Archivo(String path, String firma){
        this.rcodesPath = path;
        this.SIGNATURE_NAME = firma;
        this.resultPath =  "./output/reasonCSV.csv";
     
        //creo carpeta output
        File folderOutput = new File("output");
        folderOutput.mkdir();    
    }*/
    
     //constructor
    public Archivo(Path input, String firma){
        if(!input.toFile().isDirectory()) {
            System.out.println("No existe la carpeta");
            System.exit(1);
        }
        else
           {
            this.rcodesPath = input.toString() + "/Input";
            File folderOutput = new File(input.toFile().toString()+"/Output");
            folderOutput.mkdir();  
            resultPath = input.toString()+ "/output/reasonCSV-" + firma + ".csv";
           }
        this.SIGNATURE_NAME = firma;
    }
  
    public void extraerReasonCode(){
        
        File folder = new File(rcodesPath);
        File[] listOfFiles = folder.listFiles(); 
        String file = "";
        int cantidadDeElementos = 0;
       // int codigo = 0;
        if (listOfFiles.length != 0) { 
         for (int i = 0; i < listOfFiles.length; i++){
             if (listOfFiles[i].isFile()&& esSER(listOfFiles[i])){
                 file = listOfFiles[i].getName();
                 String reasonCodeName = this.getFileName(file);
              try {
                  //
                //api experian para recuperar reason code si el firma corresponde al archivo.ser
                   LookUpManager lookUpMngr = LookUpManager.getInstance(reasonCodeName, SIGNATURE_NAME);
                   Map<String, Object> map = lookUpMngr.getAllReasonCodeDescs();
                   cantidadDeElementos = cantidadDeElementos + map.size();
                   setArchCSV(map);
                   System.out.println("-TERMINO CON EXITO-");
                   System.exit(0);
                
                } catch (Exception e) {
                    System.out.println("no abre.ser/no corresponde Signature con Reason Code");
                    System.out.println(e.toString());
                    System.exit(1);
                }  
          }
       }// end for
        }else{
             System.out.println("no hay archivos de reasoncode");   
             System.exit(1);
        }
    }//end clase ExtraerReason
    
    
    //eliminio extension del nombre
    public String getFileName(String ser){
        return ser.substring(0, ser.lastIndexOf('.'));
    }
    
    
    //check si el archivo es de extension .ser
    public boolean esSER(File f){
        char fileSeparator = '.';
        String file = f.getName();
        int dot = file.lastIndexOf(fileSeparator);
        String ext = file.substring(dot + 1);
        return ext.equals("ser");
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

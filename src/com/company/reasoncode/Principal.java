/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.reasoncode;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;


/**
 *
 * @author e10934a
 */
public class Principal {

    
    
    public static void main(String[] args) throws IOException {
      
     /*   
      String rutaCarpeta = args[0];
      String signature = args[1];
      String rutaSalida = args[2];
     */
     
  
     //*********************test local******************
     // Archivo arch = new Archivo("./input", "ClevCard");
      
      //************************************************
      
      FileSystem sistemaFicheros = FileSystems.getDefault();
      if(args.length<=0){
          System.err.println("faltan parametros");
          System.exit(1);
       }else{
            Path rutaFichero = sistemaFicheros.getPath("./");
            
            String firma = args[0];
 
            Archivo arch = new Archivo(rutaFichero, firma);

            arch.extraerReasonCode();
      }
     
    }
    
   
}

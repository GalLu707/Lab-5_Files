/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab.pkg5_file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author USER
 */
public class ManejodeArchivos {
    private File rutaActual;
    
    public ManejodeArchivos(String rutaInicial){
        this.rutaActual = new File(rutaInicial);
    }
    
    public void cd(File nuevaRuta){
        this.rutaActual = nuevaRuta;
    }
    
    public boolean mkdir(String nombre){
        if(nombre == null || nombre.trim().isEmpty()){
            System.out.println("Error: Nombre no valido");
            return false;
        }
        
        File target = new File(rutaActual, nombre);
        
        if(target.exists()){
            System.out.println("Error: Un archivo o directorio ya existe"+target.getPath());
            return false;
        }
        
        if(target.mkdirs()){
            System.out.println("Directorio creado exitosamente"+target.getPath());
            return true;
        } else{
            System.out.println("Error: No se pudo crear el directorio");
            return false;
        }
    }
    
    public boolean mfile(String nombre)throws IOException{
        if(nombre == null || nombre.trim().isEmpty()){
            System.out.println("Error: Nombre no valido");
            return false;
        }
        
        File target = new File(rutaActual, nombre);
        
        if(target.exists()){
            System.out.println("Error: Un archivo o directorio ya existe"+target.getPath());
            return false;
        }
        
        if(target.createNewFile()){
            System.out.println("Archivo creado exitosamente: "+target.getPath());
            return true;
        } else{
            System.out.println("Error: No se pudo crear el archivo");
            return false;
        }
    }
    
    public boolean rm(String nombre){
        if(nombre == null || nombre.trim().isEmpty()){
            System.out.println("Error: Nombre no valido");
            return false;
        }
        
        File target = new File(rutaActual, nombre);
        
        if(!target.exists()){
            System.out.println("Error: El archivo o folder no existe "+target.getPath());
            return false;
        }
        
        if(target.delete()){
            System.out.println("Archivo o directorio elimiando exitosamente: "+target.getPath());
            return true;
        } else{
            System.out.println("Error: No se pudo eliminar el archivo o directorio");
            return false;
        }
    }
}

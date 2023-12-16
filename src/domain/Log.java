package domain;

import java.util.logging.Logger;
import java.io.Serializable;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.util.logging.Level;

/**
 * 
 */ 
public class Log implements Serializable{
    public static String nombre="Log file";
    
    
    public static void record(Exception e){
        try{
            Logger logger=Logger.getLogger(nombre);
            logger.setUseParentHandlers(false);
            FileHandler file=new FileHandler(nombre+".log",true);
            file.setFormatter(new SimpleFormatter());
            logger.addHandler(file);
            logger.log(Level.SEVERE,e.toString(),e);
            file.close();
            System.out.println("¡Ha ocurrido un error inesperado! Se ha registrado el error en el archivo de registro.");
        }catch (Exception oe){
            oe.printStackTrace();
            System.exit(0);
        }
    }
}
  

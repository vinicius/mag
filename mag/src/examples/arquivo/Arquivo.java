package examples.arquivo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import core.application.*;

public class Arquivo extends MagApplication {
   
   public void run() {
     
      try{
      BufferedReader starterFile= new BufferedReader(new FileReader(baseDir.getCanonicalPath() + "/arquivo.txt")); // abre o arquivo gerente.dat
			
     String  line = starterFile.readLine(); // ler a linha
 


      while(line != null && line.length()>0){
         System.out.println(line);
         line = starterFile.readLine(); // ler a linha 
    
      }

     starterFile.close();

    }catch(FileNotFoundException fnfe){
       System.err.println("Não encontrei o arquivo: arquivo.txt");
   }catch(IOException ioe){
     System.err.println("Erro de entrada/saida do arquivo: arquivo.txt");
    }

}

}

package examples.multmat;

import java.util.Random;

import core.application.*;

public class MultMat extends MagApplication {

	/**
	 * Ordem das Matrizes
	 */
	private int ordem;	

	private float [][] mata, matb, matc;	
	
	public MultMat(String[] args){
		this.ordem = Integer.parseInt(args[0]);
		
		System.out.println(ordem);
		
		mata = new float[ordem][ordem];
		matb = new float[ordem][ordem];
		matc = new float[ordem][ordem];
	}
	
	
	public void run() {
		
		Random random = new Random( System.currentTimeMillis() ); 
		
		for (int i = 0; i < ordem; i++) {
			for (int j = 0; j < ordem; j++) {
				mata[i][j] = matb[j][i] = random.nextFloat();
				//System.out.print(mata[i][j] + " ");
		      }
		      //System.out.println();
		  }
		  //System.out.println();	
		  	
		  for (int i = 0; i < ordem; i++) {
		      for (int j = 0; j < ordem; j++) {
		    	  //System.out.print(matb[i][j] + " ");
		      }
		  //System.out.println();
		      	
		  }

		  for (int i = 0; i < ordem; i++) {
		     for (int j = 0; j < ordem; j++) {		    	 
		    	 for (int k = 0; k < ordem; k++) {
		    		 matc[i][j] += mata[i][k] * matb[k][j];
		    	 }
		     }	 	
		  }	
		  //System.out.println();	
		  for (int i = 0; i < ordem; i++) {
		      for (int j = 0; j < ordem; j++) {
		    	  //System.out.print(matc[i][j] +" ");
		    	  
		      }
		      //System.out.println();		      
		  }
		  System.out.println("multiplication done");
		  
	}
	

}

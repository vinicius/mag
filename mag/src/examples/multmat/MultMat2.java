package examples.multmat;

import java.util.Random;


public class MultMat2 {
	
	private int ordem;	

	private float [][] mata, matb, matc;
	
	
	Random random = new Random( System.currentTimeMillis() );
	
	public MultMat2(int ordem) {
		
		this.ordem = ordem; 
		
		mata = new float[ordem][ordem];
		matb = new float[ordem][ordem];
		matc = new float[ordem][ordem];
	}

	public void calcula() {
		for (int i = 0; i < ordem; i++) {
			for (int j = 0; j < ordem; j++) {
				mata[i][j] = matb[j][i] = random.nextFloat();
				System.out.print(mata[i][j]+" ");
		      }
		      System.out.println();
		  }
		  System.out.println();	
		  	
		  for (int i = 0; i < ordem; i++) {
		      for (int j = 0; j < ordem; j++) {
		    	  System.out.print(matb[i][j]+ " ");
		      }
		  System.out.println();
		      	
		  }

		  for (int i = 0; i < ordem; i++) {
		     for (int j = 0; j < ordem; j++) {		    	 
		    	 for (int k = 0; k < ordem; k++) {
		    		 matc[i][j] += mata[i][k] * matb[k][j];
		    		 //System.out.println(matc[i][j]);
		    	 }
		     }	 	
		  }	
		  System.out.println();	
		  for (int i = 0; i < ordem; i++) {
		      for (int j = 0; j < ordem; j++) {
		    	  System.out.print(matc[i][j] +" ");
		      }
		      System.out.println();		      
		  }
		  
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		int ordem = Integer.parseInt(args[0]);
		
		MultMat2 app = new MultMat2(ordem);
		app.calcula();

	}

}

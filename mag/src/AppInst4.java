
import core.application.*;

public class AppInst4 extends MagApplication {
    private String something;
    private int maxLength;
    
    public AppInst4 (String args[]) {
	something = args[0];
    }
   
    public void putStuff() {
            if(something.length() > maxLength)
	        something = "fim";
	    else {
                something = something + ", " + maxLength;
	    }
    }

    public void run() {
        System.out.println(":: Instrumented Application ::");
        System.out.println("Inicio: " + System.currentTimeMillis());
	maxLength = 200000;
	while(!something.equals("fim")) {
            putStuff();
	}
        System.out.println("Termino: " + System.currentTimeMillis());
    }
}

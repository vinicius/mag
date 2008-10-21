
import core.application.*;

public class AppInst2 extends MagApplication {
    private String something;
    private int maxLength;
    
    public AppInst2 (String args[]) {
	something = args[0];
    }
   
    public void putStuff(String s) {
            if(something.length() > maxLength)
	        something = "fim";
	    else {
                something = something + ", " + s;
	    }
    }

    public void run() {
        System.out.println(":: Instrumented Application ::");
        System.out.println("Inicio: " + System.currentTimeMillis());
	String stuff = "stuff";
	maxLength = 200000;
	while(!something.equals("fim")) {
            putStuff(stuff);
	}
        System.out.println("Termino: " + System.currentTimeMillis());
    }
}

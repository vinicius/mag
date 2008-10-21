
import core.application.*;

public class AppInst3 extends MagApplication {
    private String something;
    private int maxLength;
    
    public AppInst3 (String args[]) {
	something = args[0];
    }
   
    public void putStuff(String s, int length) {
            if(something.length() > length)
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
            putStuff(stuff, maxLength);
	}
        System.out.println("Termino: " + System.currentTimeMillis());
    }
}

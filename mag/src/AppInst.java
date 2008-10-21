
import core.application.*;

public class AppInst extends MagApplication {
    private String something;
    
    public AppInst (String args[]) {
	something = args[0];
    }
   
    public void putStuff(String s) {
            if(something.length() > 200000)
	        something = "fim";
	    else {
                something = something + ", " + s;
	    }
    }

    public void run() {
        System.out.println(":: Instrumented Application ::");
        System.out.println("Inicio: " + System.currentTimeMillis());
	String stuff = "stuff";
	while(!something.equals("fim")) {
            putStuff(stuff);
	}
        System.out.println("Termino: " + System.currentTimeMillis());
    }
}

import java.util.Date;
import core.application.*;

public class FiboPVT extends MagApplication {
    private int value;
    private int i;
    private int j;
    private long ini;
    private long fim;
    private long te;
    private int[] fib;
    
    public FiboPVT (String[] args) {
	value = Integer.parseInt(args[0]);
    }
 
    private int fibo () {
        return fib[1] + fib[0];
    }
   
    private void iterate() {
	    fib = new int[value];
	    execute();
    }

    public void run() {
        ini = System.currentTimeMillis();
        System.out.println(":: Múltiplos fibonaccis até o " + value + " elemento");
        System.out.println("   Inicio em " + new Date(ini));
	j = 0;
	while(j < value) {
            System.out.print(".");
	    iterate();
	    iterate();
	    iterate();
	    iterate();
	    iterate();
	    iterate();
	    iterate();
	    j++;
	}
        System.out.println(" .");
	fim = System.currentTimeMillis();
        System.out.println("   Final em " + new Date(fim));
	te = fim - ini;
        System.out.println("   Tempo de execução:" + te);
    }

    public void execute() {
        fib[0] = 0;
        fib[1] = 1;
        i = 0;
        
        for(i = 2; i < value; i++) {
            fib[2] = fibo();
	    fib[0] = fib[1];
            fib[1] = fib[2];
        }
    }
}

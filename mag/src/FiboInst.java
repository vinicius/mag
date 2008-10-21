import java.util.Date;
import core.application.*;

public class FiboInst extends MagApplication {
    private int value;
    
    public FiboInst (String[] args) {
	value = Integer.parseInt(args[0]);
    }
 
    private int fibo (int[] f, int i) {
        if(f[i-1] + f[i-2] > 1134903170)
            return f[i-1] + f[i-2];
	else 
	    return 1;
    }
   
    private void iterate() {
	    int[] fib;
	    fib = new int[value];
	    execute(fib);
    }

    public void run() {
        long ini = System.currentTimeMillis();
        System.out.println(":: Múltiplos fibonaccis até o " + value + " elemento");
        System.out.println("   Inicio em " + new Date(ini));
	int j = 0;
	while(j < value) {
            System.out.print(".");
	    iterate();
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
	long fim = System.currentTimeMillis();
        System.out.println("   Final em " + new Date(fim));
	long te = fim - ini;
        System.out.println("   Tempo de execução:" + te);
    }

    public void execute(int[] fib) {
        fib[0] = 0;
        fib[1] = 1;
        int i = 0;
        
        for(i = 2; i < value; i++) {
            fib[2] = fibo(fib, 2);
	    fib[0] = fib[1];
            fib[1] = fib[2];
        }
    }
}

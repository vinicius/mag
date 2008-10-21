package examples.counter;

import core.application.*;

public class Counter extends MagApplication {
    private int id = 0;
    
    private int telOp(int a) {
	a = a + 1;

	return a;
    }

    public Counter(String args[]) {
	id = Integer.parseInt (args[0]);
    }
    
    public void run() {
	System.out.println("Started the Counter.run()");
	
	int teller = 0;

	while (teller < 100) {
	    teller = telOp(teller);
	    System.out.println(id + "-" + teller);
	    
	    try {
		Thread.currentThread().sleep(1000);
	    } catch (Exception e) {}
	}
    }
}

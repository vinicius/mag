package testIntegrade;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ExperimentThread extends Thread {

    int processNumber;
    int[] failureTimes;
    ConcurrentLinkedQueue<Integer> processQueue;
    
    ExperimentThread (int processNumber, int[] failureTimes, ConcurrentLinkedQueue<Integer> processQueue) {
        this.processNumber = processNumber;
        this.failureTimes = failureTimes;
        this.processQueue = processQueue;         
        //System.out.println("FirstTime " + failureTimes[0]);
    }
           
    public void run() {
        try {
            for (int f=0; f<failureTimes.length; f++) {
                Thread.sleep(failureTimes[f]*1000);
                //System.out.println("Woke up " + processNumber);
                processQueue.add(processNumber);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

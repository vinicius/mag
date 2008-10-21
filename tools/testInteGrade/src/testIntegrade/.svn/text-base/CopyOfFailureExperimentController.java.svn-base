package testIntegrade;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CopyOfFailureExperimentController extends Thread {
    
    Vector<int[]> failureTimes;
    DataInputStream inStream;
    int nProcesses;
    Vector<String> hostNames;
    ConcurrentLinkedQueue<Integer> processQueue;
    int execNumber;
    
    private boolean experimentFinished = false;
          
    private static final String experDir = "/home/rcamargo/randDist/data/";
    private static final int nFailures = 100;        
    
    public void setExperimentFinished() {
        experimentFinished = true;
    }
    
    public void run() {
        final int minFailureInterval = 5;
        processQueue = new ConcurrentLinkedQueue<Integer>();
        try {
            // Launch the experiment threads 
            for (int e=0; e<nProcesses; e++) {
                ExperimentThread exp = new ExperimentThread(e, failureTimes.get(e), processQueue);
                exp.start();
            }
            // Kills the selected processes
            while (true) {
                Thread.sleep(minFailureInterval*1000);
                if (experimentFinished == true) return;
                Integer processNumber = processQueue.poll();
                if (processNumber != null && hostNames.size() == nProcesses) {
                    String host = hostNames.get(processNumber.intValue());
                    Date currentTime = new Date();
                    System.out.println(host +": " + currentTime);
                    Runtime runTime = Runtime.getRuntime();                 
                    runTime.exec("killall -9 " + (execNumber*1000 + processNumber));
                    //runTime.exec("ssh " + host + " killall -9 integrade");                   
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    void addHost(int nodeId, String hostName) {
        hostNames.set(nodeId, hostName);
        System.out.println("size="+hostNames.size());
    }
    
    void readHostNames(String hostFiles) {        
        //hostNames = new Vector<String>();
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(hostFiles));
            String line = fileReader.readLine();
            for (int l = 0; line != null && l < nFailures; l++) {
                hostNames.add(l, line);
                line = fileReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }        

    }
    
    void prepareExperiment(int experNumber, int nProcesses, String lambda) {
        
        this.nProcesses = nProcesses;
        this.execNumber = experNumber;
        
        hostNames = new Vector<String>(nProcesses);
        hostNames.setSize(nProcesses);
        failureTimes = new Vector<int[]>(nProcesses);       
        for (int i=0; i<nProcesses; i++) {
            String fileName =  experDir + "randDist" + lambda + "_" + ((experNumber%1000)*nProcesses+i) + ".data";
            try {
                BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
                failureTimes.add(i, new int[nFailures]);
                String line = fileReader.readLine();
                for (int l = 0; line != null && l < nFailures; l++) {
                    failureTimes.get(i)[l] = Integer.parseInt(line);
                    line = fileReader.readLine();                    
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }        
    }

}

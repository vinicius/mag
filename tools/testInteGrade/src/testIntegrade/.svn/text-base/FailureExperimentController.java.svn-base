package testIntegrade;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FailureExperimentController extends Thread {
    
    Vector<int[]> failureTimes;
    DataInputStream inStream;
    int nProcesses;
    int nProcessesToKill;
    
    Vector<String []> hostNames;
    ConcurrentLinkedQueue<Integer> processQueue;
    int execNumber;
    java.util.Random random = new java.util.Random(System.currentTimeMillis());
    
    private boolean experimentFinished = false;
          
    private static final String experDir = "../inputData/failureDist/";
    private static final int nFailures = 100;        
    
    public void setExperimentFinished() {
        experimentFinished = true;
    }
    
    /**
     * Periodically kills a process of a remote machine
     */    
    public void run() {
        final int minFailureInterval = 6;
        processQueue = new ConcurrentLinkedQueue<Integer>();
        try {
            // Launch the experiment threads 
            //for (int e=0; e<nProcesses; e++) { // comentado por stanley
            for(int e=0 ; e< 1; e++){ // adicionado por Stanley 
                ExperimentThread exp = new ExperimentThread(e, failureTimes.get(e), processQueue);
                exp.start();
            }
            // Kills the selected processes
            while (true) {
                //Thread.sleep(minFailureInterval*1000);
            	            	            	
                if (experimentFinished == true) return;
                Integer processNumber = processQueue.poll();
                
                if (processNumber != null && processNumber >= nProcessesToKill ) 
                	continue;
                
                if (processNumber != null && hostNames.size() == nProcesses) {

                    int sorteado = random.nextInt(nProcesses);
                    System.out.println("Sorteado=" + sorteado+ " nProcesses="+nProcesses);
                    //String[] nodeData = hostNames.get(processNumber.intValue());
                    String[] nodeData = hostNames.get(sorteado);
                    if (nodeData == null) {
                        System.out.println("Skipping " + execNumber + ":" + processNumber);
                        continue;
                    }
                    String host  = nodeData[0];
                    String appId = nodeData[1];
                    Date currentTime = new Date();
                    processNumber = 0 ; // added by stanley  
                    System.out.println(host +": " + currentTime + " --> " + execNumber + ":" + processNumber);
                    Runtime runTime = Runtime.getRuntime();   

                    System.out.println ("calling: ssh " + host + " ps aux | grep :" + execNumber + ":" + processNumber);
                    
                                        
                    Process ps = runTime.exec("ssh " + host + " ps aux | grep :" + execNumber + ":" + processNumber);
                    BufferedReader in = new BufferedReader(new InputStreamReader(ps.getInputStream()));                    
                    ps.waitFor();                    
                    
                    String line = in.readLine();
                   
                    String pid = line.split("\\s+")[1]; // '/s' represents whitespace characters
                 runTime.exec("ssh " + host + " kill -9 " + pid);

                    System.out.println ("calling: ssh " + host + " kill -9 " + pid);

                }
                else
                    Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    void addHost(int nodeId, String hostName, String appId) {
        hostNames.set(nodeId, new String[] {hostName, appId});
    }
    
    void prepareExperiment(int execNumber, int nProcesses_, String lambda, int nProcessesToKill_) {
        
        //this.nProcesses = nProcesses_;
        this.nProcesses=1;
        //this.nProcessesToKill = nProcessesToKill_;
        this.nProcessesToKill=1;
        this.execNumber = execNumber;
        
        hostNames = new Vector<String []>(nProcesses);
        hostNames.setSize(nProcesses);
        failureTimes = new Vector<int[]>(nProcesses);       
        for (int i=0; i<nProcesses; i++) {
            String fileName =  experDir + "randDist" + lambda + ".data";
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

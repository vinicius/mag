package testIntegrade;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Vector;

import org.omg.CORBA.ORB;

import resourceProviders.Lrm;
import resourceProviders.LrmHelper;

import clusterManagement.ApplicationRepository;
import clusterManagement.Grm;
import dataTypes.ExecutionRequestId;
import dataTypes.FileStruct;
import dataTypes.RequestAcceptanceInformation;

/**
 * @author rcamargo
 */

public class TestCoordinator{

    private TestSuite currentTestSuite;
    private RemoteExecutionInformation remoteExecutionInformation;
    private FailureExperimentController failureController = null;
	
    static final String outputDirectory = "../executionOutput";
    static final boolean useFailures = true;
    
    private ORB orb;
    private Grm grm;
    private ApplicationRepository appRepos;
    
    private String appReposIor;
    private String grmIor;
    private String asctIor;
    private long inicio;
    private long fim;  
    private int counter = 0;  
    private HashMap<String, AcceptedExecutionInformation> acceptedExecutionInformationMap;
        
    TestCoordinator(ORB orb, Grm grm, ApplicationRepository appRepos) {
        this.orb = orb;
        this.grm = grm;
        this.appRepos = appRepos;
    
        this.grmIor = orb.object_to_string(grm);        
        this.appReposIor = orb.object_to_string(appRepos);
        this.acceptedExecutionInformationMap = new HashMap<String, AcceptedExecutionInformation>();
        
        // Deletes old output files
        File outputDirectoryFile = new File(outputDirectory);        
        deleteExecutionOutput(outputDirectoryFile);
        outputDirectoryFile.mkdir();
        
        try {
        	(new File(outputDirectory)).mkdir();
			Runtime.getRuntime().exec("rm " + outputDirectory + "/* -rf");			
		} catch (IOException e) { e.printStackTrace(); }
    }
    
    private boolean deleteExecutionOutput(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteExecutionOutput(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }     
        return dir.delete();
    }
    
    public void setAsctIor(String asctIor) {
        this.asctIor = asctIor;        
        this.remoteExecutionInformation = new RemoteExecutionInformation(this.asctIor, grmIor, appReposIor);
    }
        
	public void configureFailureExperiments () {
	    	   
	}

    public void performTest(TestSuite testSuite) {
    	    	
    	RemoteExecutionParameters executionParameters;
    	this.currentTestSuite = testSuite;    	    	
    	
    	while ( (executionParameters = testSuite.getNextRemoteExecution()) != null ) {
    		
    		executionParameters.executionLock.lock();
    		
    		System.out.println("Submitting request " + executionParameters.executionId + ".");
    		
    		remoteExecutionInformation.prepareRemoteExecution(executionParameters);    		
    		
            // Initializes the execSpecs map entry for this application
    		String requestId = remoteExecutionInformation.processExecutionInformationList[0].executionRequestId.requestId; 
            synchronized(acceptedExecutionInformationMap){
                acceptedExecutionInformationMap.put( requestId, remoteExecutionInformation.acceptedExecutionInformation );
            }

            if (useFailures) {
            	String lambda = "40";
            	int nProcesses = remoteExecutionInformation.processExecutionInformationList.length;            	
            	
            	if (failureController != null)
            		failureController.setExperimentFinished();                	    
            	failureController = new FailureExperimentController();
            	failureController.prepareExperiment(Integer.parseInt(requestId), nProcesses, lambda, 1);
            }
            inicio = System.currentTimeMillis();
            grm.requestRemoteExecution(remoteExecutionInformation.applicationExecutionInformation, 
            						   remoteExecutionInformation.processExecutionInformationList);

            if (useFailures)
            	failureController.start();

    		try { executionParameters.executionCondition.await(); }
    		catch (InterruptedException e) {}
			
    		executionParameters.executionLock.unlock();
    	}    		
    	
    	System.out.println("Experiments finished.");
    }

    /**
     * @param executionRequestId
     * @return
     */
    public String[] getAppInputFiles(ExecutionRequestId executionRequestId) {

        AcceptedExecutionInformation execSpecs;
        execSpecs = (AcceptedExecutionInformation)acceptedExecutionInformationMap.get(executionRequestId.requestId);
        if (execSpecs == null) return null;
     
        String[] inputFiles = execSpecs.inputFiles[Integer.parseInt(executionRequestId.processId)];
        return inputFiles;
    }

    /**
     * @param executionRequestId
     */
    public void appFinished(ExecutionRequestId executionRequestId) {
        fim = System.currentTimeMillis();
        try{
          FileOutputStream fos = new FileOutputStream(new File("/home/pos/vinicius/tempoDeExecucao.txt"),true);
          fos.write((String.valueOf(fim-inicio)+"\n").getBytes());
          fos.close();
        }catch(Exception e ){
          System.out.println("ERRO: " + e.getMessage());
        }


        AcceptedExecutionInformation execSpecs = null;
        synchronized(acceptedExecutionInformationMap){
            execSpecs = (AcceptedExecutionInformation)acceptedExecutionInformationMap.get(executionRequestId.requestId);
        }
        
        execSpecs.finishedNodes++;
        if (execSpecs.finishedNodes == execSpecs.lrmIorList.length) {
            System.out.println("Our request id: " + executionRequestId.requestId + " has FINISHED succesfully.");
            Vector<FileStruct[]> applicationReturnedFiles = collectResults(executionRequestId);
            
            RemoteExecutionParameters executionParameters = currentTestSuite.getLastExecutionParameters(); 

            try {
          	executionParameters.executionLock.lock();
            currentTestSuite.analyseRemoteExecutionResults(applicationReturnedFiles);
    		executionParameters.executionCondition.signal(); 
    		executionParameters.executionLock.unlock();
            }
            catch (Exception e) {
            	e.printStackTrace();
            }

        }
       
    }

    private Vector<FileStruct[]> collectResults(ExecutionRequestId executionRequestId){

    	String executionOutputDirectory = outputDirectory + "/" + executionRequestId.requestId;
        if(! (new File(executionOutputDirectory)).mkdir()){
          System.out.println("Directory creation failed: " + executionOutputDirectory);
        }
        
        AcceptedExecutionInformation execSpecs;
        synchronized(acceptedExecutionInformationMap){
          execSpecs = (AcceptedExecutionInformation) acceptedExecutionInformationMap.get(executionRequestId.requestId);
        }    
        System.out.println("Requesting results from " + execSpecs.lrmIorList.length + " nodes");
        
        int nProcesses = execSpecs.lrmIorList.length;
        
        Vector<FileStruct[]> applicationReturnedFiles = new Vector<FileStruct[]>(nProcesses); 
        
        for(int i = 0; i < nProcesses; i++){

          String subpath = outputDirectory + "/" + (executionRequestId.requestId) + "/" + i;
          if(! (new File(subpath)).mkdir()){
            System.out.println("Directory creation failed: " + subpath);
          }
          Lrm lrm = LrmHelper.narrow( orb.string_to_object(execSpecs.lrmIorList[i]) );
          
          FileStruct [] returnFiles = lrm.requestOutputFiles(execSpecs.nodeExecutionIdList[i]);
          applicationReturnedFiles.add(returnFiles);
          for(int j = 0; j < returnFiles.length; j++){
            try{
              FileOutputStream fos =
               new FileOutputStream(subpath + "/" + returnFiles[j].fileName);
              fos.write(returnFiles[j].file);
              fos.close();
            }
            catch(IOException ioe){
              System.err.println("collectResults-->> Write failed");
            }
          }//for          
          
        }//for
 
        System.out.println("Results collected sucesfully.");
        
        return applicationReturnedFiles;
        	
    }//method

    /**
     * @param offerSpecs
     */
    public void acceptedExecutionRequest(RequestAcceptanceInformation offerSpecs) {
        
    	String hostName = null;
    	
        //failure
        Runtime runTime = Runtime.getRuntime();
        //System.out.println(offerSpecs.lrmIor);
        try {
            Process dior = runTime.exec("/home/pub/JacORB/bin/dior " + offerSpecs.lrmIor);
            BufferedReader in = new BufferedReader(new InputStreamReader(dior.getInputStream()));
            
            dior.waitFor();

            String line = in.readLine();
            int index = line.indexOf("Host"); 
            while ( index < 0 ) {
                line = in.readLine();
                index = line.indexOf("Host");             
            }
            String[] hosts = line.split(":");
            hostName = hosts[hosts.length-1];            
            //hostName = "orlandia";
            
            //System.out.println(Integer.parseInt(offerSpecs.executionRequestId.processId) + ": " + hostName + " appId:" + offerSpecs.executionId);
            if (failureController != null)
                //failureController.addHost(Integer.parseInt(offerSpecs.executionRequestId.processId), hostName, offerSpecs.executionId);    
                                 
             if(counter <1){
                      if (counter==0) hostName = "monet"; 
                      if (counter==1) hostName = "renoir";
                      if (counter==2) hostName = "guaguin";
                      if (counter==3) hostName = "picasso";
                               
                 System.out.println("Computador "+ counter +" " + hostName + " aceitou execuçao");   
                failureController.addHost(counter, hostName, offerSpecs.executionId);
                counter++;
             }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }                   
        
        synchronized(acceptedExecutionInformationMap){
            AcceptedExecutionInformation executionInformation = 
            	acceptedExecutionInformationMap.get(offerSpecs.executionRequestId.requestId);
            int nodeId = Integer.parseInt(offerSpecs.executionRequestId.processId);
            executionInformation.nodeExecutionIdList[nodeId] = offerSpecs.executionId;
            executionInformation.lrmIorList[nodeId] = offerSpecs.lrmIor;
            executionInformation.processHostList[nodeId] = hostName;
            executionInformation.acceptedExecutions++;
            if (executionInformation.acceptedExecutions == executionInformation.nodeExecutionIdList.length) {            	
                System.out.println("Our request id: " + offerSpecs.executionRequestId.requestId + " was ACCEPTED.");
                currentTestSuite.setExecutionStarted(executionInformation);
            }
        }
    }

    /**
     * @param executionRequestId
     */
    public void refusedExecutionRequest(ExecutionRequestId executionRequestId) {
        System.out.println("The esecution request was REFUSED :-(");
        
    }

}

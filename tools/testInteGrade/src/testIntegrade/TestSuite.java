package testIntegrade;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import clusterManagement.ApplicationRepository;

import dataTypes.ApplicationType;
import dataTypes.BinaryDescription;
import dataTypes.FileStruct;

class RemoteExecutionParameters {
	int executionId;
	ApplicationType appType;
	int nTasks;
	String appName;
	String appArgs;
	String[] inputFiles;
	String[] outputFiles;
	
	Lock executionLock = new ReentrantLock();
	Condition executionCondition = executionLock.newCondition();
}

public abstract class TestSuite {

	int numberOfExecutions = 1;
	int firstExecutionId   = 0;
	int nextExecutionId    = 0;

	RemoteExecutionParameters lastExecutionParameters;
	ApplicationRepository appRepos;
	
	TestSuite (int firstExecutionId, int numberOfExecutions, ApplicationRepository appRepos) {
		this.numberOfExecutions = numberOfExecutions;
		this.firstExecutionId   = firstExecutionId;
		this.nextExecutionId    = firstExecutionId;
		this.lastExecutionParameters = null;
		this.appRepos = appRepos;
	}
	
	public RemoteExecutionParameters getLastExecutionParameters() {
		return lastExecutionParameters;
	}
	
	/** Gets the parameters for the next execution */
	public abstract RemoteExecutionParameters getNextRemoteExecution();  
	
	/** Analyze the retuned results */
	public abstract void analyseRemoteExecutionResults (Vector<FileStruct[]> applicationReturnedFiles);
	
	/** Used to perform operations after the application has started */
	public abstract void setExecutionStarted (AcceptedExecutionInformation executionInformation);

	
}

class TestSuiteMatrixSimple extends TestSuite {
	
	BinaryDescription binaryDescription;
	
	TestSuiteMatrixSimple (int firstExecutionId, int numberOfExecutions, ApplicationRepository appRepos) {
		super(firstExecutionId, numberOfExecutions, appRepos);
		
		// Registers an application in the repository
		binaryDescription = new BinaryDescription();
		//binaryDescription.applicationName = "matrix";
                binaryDescription.applicationName = "bootstr";
		binaryDescription.basePath = "bootstrBase";
		binaryDescription.binaryName = "Linux_i686";
		binaryDescription.description = "";
		
		registerAtApplicationRepository(appRepos);
		
	}

	private void registerAtApplicationRepository(ApplicationRepository appRepos) {
		
		//int fileSize = 15975;// checkpointing
                //int fileSize = 16137;// bootstr-ckp1
		int fileSize = 11613;
        	//int fileSize = 11703;// multmat instrumentada
	      // int fileSize = 8744;// multmat n~~ao instrumentada 
		byte[] binaryCode = new byte[fileSize];
		FileInputStream inputFile;
		try {
			//inputFile = new FileInputStream("../../../examples/bsp/matrix/matrix_ckp");
			//inputFile = new FileInputStream("/home/pos/rcamargo/knapsac/knapsac");
                        inputFile = new FileInputStream("../../../examples/regular/Bootstr/bootstr");
                //       inputFile = new FileInputStream("../../../examples/regular/MultMatrix/multmat2");
                //     inputFile = new FileInputStream("../../../examples/regular/MultMatrix/multmat");
			int bytesRead = inputFile.read(binaryCode);		
			System.out.println(bytesRead);
			
			if (bytesRead != fileSize) 
				System.out.println("Error reading data from matrix file");
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {appRepos.deleteApplicationBinary(binaryDescription.basePath, binaryDescription.applicationName, binaryDescription.binaryName);} 
		catch (Exception e) {e.printStackTrace();}		
		//try {appRepos.removeDirectory(binaryDescription.basePath);}
		//catch (Exception e) {e.printStackTrace();}
		try {appRepos.unregisterApplication(binaryDescription.basePath, binaryDescription.applicationName);} 
		catch (Exception e) {e.printStackTrace();}		
		
		try {appRepos.registerApplication(binaryDescription.basePath, binaryDescription.applicationName);} 
		catch (Exception e) {e.printStackTrace();}		
		try {appRepos.uploadApplicationBinary(binaryDescription, binaryCode);}
		catch (Exception e) {e.printStackTrace();}
	}
	
	public RemoteExecutionParameters getNextRemoteExecution() {
		
		if (nextExecutionId - firstExecutionId >= numberOfExecutions)
			return null;
				
		RemoteExecutionParameters executionParameters = new RemoteExecutionParameters();        
		executionParameters.executionId = nextExecutionId;
		executionParameters.appType     = ApplicationType.regular;
		executionParameters.nTasks      = 4;
		executionParameters.appName     = binaryDescription.applicationName;
		//executionParameters.appArgs     = "";
		//executionParameters.appArgs     = "1700"; // matriz
		executionParameters.appArgs     = "60000"; // bootstr
		//executionParameters.inputFiles  = new String[]{"/home/pos/rcamargo/knapsac/ArquivoW.txt", "/home/pos/rcamargo/knapsac/ArquivoC.txt"};
		executionParameters.inputFiles  = new String[0];
        executionParameters.outputFiles = new String[]{"stdout","stderr"};
        
        this.lastExecutionParameters = executionParameters;
        this.nextExecutionId += 1;
        
        return executionParameters;
	}

	public void analyseRemoteExecutionResults(Vector<FileStruct[]> applicationReturnedFiles) {
		
		for (FileStruct[] processOutputFiles : applicationReturnedFiles) {
			
			boolean outputFilesCorrect = false;
			
			if (processOutputFiles[0].fileName.compareTo("stdout") == 0) {
				BufferedReader fileStream = 
					new BufferedReader( new InputStreamReader( new ByteArrayInputStream(processOutputFiles[0].file)));
			
				String line = null;				
				try {
					while ( (line = fileStream.readLine()) != null) 
						if (line.contains("processor") && line.contains("time:"))
							outputFilesCorrect = true;				
				}
				catch (IOException e) { e.printStackTrace(); }								
			}
			
			if (outputFilesCorrect == false) {
				System.out.println("[ERROR] Application results are incorrect! applicationId:" + lastExecutionParameters.executionId);
				return;
			}
		}				
	}

	/**
	 * Kills the application after a given time to test the reinitialization process
	 */
	public void setExecutionStarted(AcceptedExecutionInformation executionInformation) {

		if (true) return;
		
		String hostName = executionInformation.processHostList[0];
		String executionId = executionInformation.nodeExecutionIdList[0];
		                                                              
		try { Thread.sleep(10*1000); } 
		catch (InterruptedException e) {}
		
		try { Runtime.getRuntime().exec("ssh " + hostName + " killall -9 " + executionId); } 
		catch (IOException e) {
			System.out.println("Could not kill remote process " + executionId + " at " + hostName);
		}

	}
}

//public void performRestartTests() {
//
//    restartTest = true;        
//   
//    System.out.println("Preparing request " + lastExecutionId);
//
////    if (mainRequestId == 999)
////    	mainRequestId++;
////    else if (mainRequestId/7000 == 0)
////    	mainRequestId += 1000;
////    else
////    	mainRequestId = 1000 + (mainRequestId%1000) + 1;        	
////    
////    if (mainRequestId%1000 > 7) {
////        System.out.println("Finished Experiments :-)");
////        return;
////    }
//
//    lastExecutionId++;        
//    if (lastExecutionId%1000 > 0) {
//        System.out.println("Finished Experiments :-)");
//        return;
//    }
//
//    String lambda;
//    //if (mainRequestId%2 == 0) lambda = "600";
//    //else lambda = "1800";
//    lambda = "20";
//    
//    if (failureController != null) {
//        failureController.setExperimentFinished();            
//    }
//    failureController = new FailureExperimentController();
//    failureController.prepareExperiment(lastExecutionId, 4, lambda);
//    //failureController.readHostNames("/home/rcamargo/hostNames");
//    
//    String[] outputFiles = {"stdout","stderr"};
//    String appArgs = null;
//
//    appArgs = "4 100 100 1";
//    
////    if (mainRequestId%4 == 0)      appArgs = "9 5400   1 1";
////    else if (mainRequestId%4 == 1) appArgs = "9 2700   8 1";
////    else if (mainRequestId%4 == 2) appArgs = "9 1350  32 1";             
////    else if (mainRequestId%4 == 3) appArgs = "9  675 128 1"; // 64 -> 128
//    
//    String appName = "matrixCkp";
//    String[] inputFiles = new String[0];
//    
//    prepareRemoteExecution(ApplicationType.bsp, 4, appName, appArgs, inputFiles, outputFiles);
//    
//    System.out.println("Request " + lastExecutionId + " submitted. " + appArgs);
//    
//    //performRestartTests();
//
//    //failureController.start();
//}        

//
//public void performSimpleTests() {
//
//    if (false) {
//        lastExecutionId++;              
//        String[] outputFiles = {"stdout","stderr"};
//        String appArgs = "/ -al -rt";
//        String appName = "ls";
//        String[] inputFiles = new String[0];
//        prepareRemoteExecution(ApplicationType.regular, 1, appName, appArgs, inputFiles, outputFiles);        
//        System.out.println("Request " + lastExecutionId + " submitted.");
//    }
//    
//    if (true) {
//        lastExecutionId++;
//        String[] outputFiles = {"stdout","stderr"};
//        String appArgs = "4 100 100";
//        String appName = "matrix";
//        String[] inputFiles = new String[0];
//        prepareRemoteExecution(ApplicationType.bsp, 4, appName, appArgs, inputFiles, outputFiles);
//        System.out.println("Request " + lastExecutionId + " submitted.");
//    }       
//}        

package testIntegrade;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import testIntegrade.AcceptedExecutionInformation;
import dataTypes.ApplicationExecutionInformation;
import dataTypes.ApplicationType;
import dataTypes.ExecutionRequestId;
import dataTypes.ProcessExecutionInformation;

public class RemoteExecutionInformation {

	private String asctIor, grmIor, appReposIor;	
	ApplicationExecutionInformation applicationExecutionInformation;
	ProcessExecutionInformation[] processExecutionInformationList;
	AcceptedExecutionInformation acceptedExecutionInformation;
	
	RemoteExecutionInformation (String asctIor, String grmIor, String appReposIor) {
		this.asctIor = asctIor;
		this.grmIor  = grmIor;
		this.appReposIor = appReposIor;
	}
	
    public int prepareRemoteExecution(RemoteExecutionParameters executionParameters) {
        
    	assert(asctIor != null && grmIor != null && appReposIor != null);
    	    	
        applicationExecutionInformation = new ApplicationExecutionInformation();
        applicationExecutionInformation.requestingAsctIor = asctIor;
        applicationExecutionInformation.originalGrmIor = grmIor;
        applicationExecutionInformation.previousGrmIor = "";
        
        applicationExecutionInformation.applicationType = executionParameters.appType;
        //applicationExecutionInformation.applicationId   = executionParameters.appName + "_appDir/Linux_i686_binaryFile";
        applicationExecutionInformation.applicationRepositoryIor = appReposIor;
        
        applicationExecutionInformation.applicationName = "bootstr";
        applicationExecutionInformation.basePath = "bootstrBase";
        
        applicationExecutionInformation.applicationConstraints = "";
        applicationExecutionInformation.applicationPreferences = "";
        applicationExecutionInformation.numberOfReplicas       = 0;
        applicationExecutionInformation.forceDifferentNodes = false;
        applicationExecutionInformation.availableBinaries = new String[1];
        applicationExecutionInformation.availableBinaries[0] = "Linux_i686";
        
        if (executionParameters.appType == ApplicationType.regular)     
            processExecutionInformationList = getRegularSpecs(executionParameters);
        else if (executionParameters.appType == ApplicationType.bsp)
            processExecutionInformationList = getBspSpecs(executionParameters);
                
        return 0;
    }
    
    private ProcessExecutionInformation[] getRegularSpecs(RemoteExecutionParameters executionParameters) {

        processExecutionInformationList = new ProcessExecutionInformation[1];
        processExecutionInformationList[0] = new ProcessExecutionInformation();
        processExecutionInformationList[0].executionRequestId = new ExecutionRequestId();
        processExecutionInformationList[0].executionRequestId.requestId = String.valueOf(executionParameters.executionId);
        processExecutionInformationList[0].executionRequestId.processId = String.valueOf(0);
        processExecutionInformationList[0].processArguments = executionParameters.appArgs;
        processExecutionInformationList[0].outputFileNames = executionParameters.outputFiles;

        acceptedExecutionInformation = new AcceptedExecutionInformation();
        acceptedExecutionInformation.nodeExecutionIdList = new String[1];
        acceptedExecutionInformation.processHostList = new String[1];
        acceptedExecutionInformation.lrmIorList = new String[1];
        acceptedExecutionInformation.inputFiles = new String[1][]; 
        acceptedExecutionInformation.inputFiles[0] = executionParameters.inputFiles;

        return processExecutionInformationList;
    }

    private ProcessExecutionInformation[] getBspSpecs(RemoteExecutionParameters executionParameters) {

        String tmpBspDescriptorDir = 
        	"bspConfs" + System.getProperty("file.separator") + String.valueOf(executionParameters.executionId);

        File bspConfs = new File("bspConfs");
        bspConfs.mkdir();
        
        File tmpBspBir = new File(tmpBspDescriptorDir);
        if(! tmpBspBir.exists())
            if( ! tmpBspBir.mkdir())
                System.err.println("Cannot create dir");

        String[] bspDescriptorPath = new String[executionParameters.nTasks];
        for(int i = 0; i < executionParameters.nTasks; i++) {

            String bspDescriptorDir = tmpBspDescriptorDir + System.getProperty("file.separator") + i;              
            bspDescriptorPath[i] = bspDescriptorDir + System.getProperty("file.separator") + "bspExecution.conf";

            // writes bsp.conf data
            try{
                File bspBir = new File(bspDescriptorDir);
                if(! bspBir.exists())
                    if( ! bspBir.mkdir())
                        System.err.println("Cannot create dir");

                PrintWriter ps = new PrintWriter( new FileOutputStream(new File(bspDescriptorPath[i])));

                ps.println("applicationId " + executionParameters.executionId);
                ps.println("processId " + i);
                //ps.println("asctIor " + asctIor);
                ps.println("numExecs " + executionParameters.nTasks);
                ps.close();
            }
            catch(FileNotFoundException fnfe){
                fnfe.printStackTrace();
                System.exit(-1);
            }
          }
          
        processExecutionInformationList = new ProcessExecutionInformation[executionParameters.nTasks];
        for(int i = 0; i < processExecutionInformationList.length; i++)
            processExecutionInformationList[i] = new ProcessExecutionInformation();

        acceptedExecutionInformation  = new AcceptedExecutionInformation();
        acceptedExecutionInformation.nodeExecutionIdList = new String[executionParameters.nTasks];
        acceptedExecutionInformation.processHostList = new String[executionParameters.nTasks];
        acceptedExecutionInformation.lrmIorList = new String[executionParameters.nTasks];
        acceptedExecutionInformation.inputFiles = new String[executionParameters.nTasks][]; 
        acceptedExecutionInformation.inputFiles = new String[executionParameters.nTasks][executionParameters.inputFiles.length+1];

        for(int i = 0; i < processExecutionInformationList.length; i++){
            processExecutionInformationList[i].executionRequestId = new ExecutionRequestId();
            processExecutionInformationList[i].executionRequestId.requestId = String.valueOf(executionParameters.executionId);
            processExecutionInformationList[i].executionRequestId.processId = String.valueOf(i);
            processExecutionInformationList[i].processArguments = executionParameters.appArgs;
            processExecutionInformationList[i].outputFileNames = executionParameters.outputFiles;
            for (int j=0; j < executionParameters.inputFiles.length; j++)
                acceptedExecutionInformation.inputFiles[i][j] = executionParameters.inputFiles[j];           
            acceptedExecutionInformation.inputFiles[i][executionParameters.inputFiles.length] = "./" + bspDescriptorPath[i]; 
        }

        return processExecutionInformationList;
    }
    
//    private ProcessExecutionInformation[] getParametricSpecs(int nTasks, String appArgs, String[] outputFiles) {    
//        else if(eap.isParametric()){
//          commonSpecs.appType = ApplicationType.parametric;
//          distinctSpecs = new ProcessExecutionInformation[eap.getCopies().length];
//          for(int i = 0; i < distinctSpecs.length; i++)
//            distinctSpecs[i] = new ProcessExecutionInformation();
//
//          for(int i = 0; i < eap.getCopies().length; i++){
//            distinctSpecs[i].executionRequestId = new ExecutionRequestId();
//            distinctSpecs[i].executionRequestId.applicationId = String.valueOf(mainRequestId);
//            distinctSpecs[i].executionRequestId.processId = String.valueOf(i);
//            distinctSpecs[i].applicationArgs = eap.getCopies()[i].args();
//            distinctSpecs[i].outputFiles = eap.getCopies()[i].outputFiles();
//            RequestedExecHolder reqExecHolder =
//                     new RequestedExecHolder(eap.getCopies()[i].inputFiles());
//            execHolder.addSubRequest(String.valueOf(i), reqExecHolder);
//          }
//        }
//        return null;
//    }

}

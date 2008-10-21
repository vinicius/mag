 package core.ft.erm.behaviours;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import core.ft.erm.ErmAgent;
import core.ft.erm.IOCommonUtils;
import jade.core.behaviours.OneShotBehaviour;
import core.wrappers.WrapperFactory;
import dataTypes.ApplicationExecutionInformation;
import dataTypes.ProcessExecutionInformation;
import dataTypes.ExecutionRequestId;
import dataTypes.FileStruct;



/**
 * @author diego
 *
 */
public class ExecuteApplicationWithReplicasBehaviour extends OneShotBehaviour {
	
	private ErmAgent ermAgent = null;
	private ApplicationExecutionInformation applicationExecutionInformation;		
	private ArrayList<ProcessExecutionInformation[]> processExecutionInformationList;
	private String[] lrmIors;	
	
	public ExecuteApplicationWithReplicasBehaviour(ErmAgent ermAgent,
												ApplicationExecutionInformation applicationExecutionInformation,
												ArrayList processExecutionInformationList,
												String[] lrmIors ) {
		super(ermAgent);
		
		this.ermAgent = ermAgent;
		
		this.applicationExecutionInformation = applicationExecutionInformation;
		this.processExecutionInformationList = processExecutionInformationList;
		this.lrmIors = lrmIors;			
		
	}
	
	public void action() {
		
		int currentExecution = 0;
		int currentExecutionReplicas = 0;
		int i = 0;
		int processExecutionInformationListSize = processExecutionInformationList.size();
		int quant = 0;
		int lrmPosition = 0;
		
		if(processExecutionInformationListSize >0){	
			
			
			ProcessExecutionInformation[] p = processExecutionInformationList.get(0);
			ProcessExecutionInformation[] q = processExecutionInformationList.get(0);
			quant = p.length;
			
			ermAgent.setAgentHandlerIorExecuting((ArrayList<String[]>[]) new ArrayList[quant]);
			
			ermAgent.setIsReplicaExecutionFinished( new Boolean[quant] );			
			ermAgent.setIsReplicaExecutionAccepted( new Boolean[quant] );
			
			String ids[] = p[0].executionRequestId.requestId.split(":");
			String ids1[] = q[0].executionRequestId.requestId.split(":");
			
			for(int j=0; j < ids.length; j++ ){
				System.out.println("ExecutionRequestID "+ ids[j] );
			}
			
			for(int j=0; j < ids1.length; j++ ){
				System.out.println("ExecutionRequestID "+ ids1[j] );
			}			
			
			ermAgent.setRequestIdNormal( normalizeRequestId(p[0].executionRequestId.requestId) );		
			
			
			for (i = 0; i < quant; i++) {
				
				ermAgent.getAgentHandlerIorExecuting()[i] = new ArrayList<String[]>();
				
				ermAgent.putInIsReplicaExecutionAccepted(i,new Boolean(false));
				ermAgent.putInIsReplicaExecutionFinished(i,new Boolean(false));
			
			
			FileStruct fileStructs[] = requestInputFiles(ids[2], p[i].executionRequestId.processId);
			
			// Armazena os arquivos de entrada da requisição de execução
			ermAgent.saveInputFileStructs(fileStructs, ids[2]+":"+p[i].executionRequestId.processId);
			
			}
			
			while (currentExecution < quant) {
				currentExecutionReplicas = 0;
				while(currentExecutionReplicas < processExecutionInformationListSize && currentExecutionReplicas<  ermAgent.getLrmIorsList().size()){
					p = processExecutionInformationList.get(currentExecutionReplicas);
					//try {
						
						core.wrappers.LrmWrapper lrm = WrapperFactory.getInstance().createLrmWrapper( (String) ermAgent.getLrmIorsList().get(lrmPosition));						
				
						lrm.requestExecution(applicationExecutionInformation,p[currentExecution]);
						

						
						currentExecutionReplicas++;
						lrmPosition++;
						lrmPosition = lrmPosition %  ermAgent.getLrmIorsList().size();
						
						
						
					/*} catch (org.omg.CORBA.TRANSIENT transientException) {
						System.err.println("Erm:: Unable to reach LRM. Removing it from database");
						lrmIorsList.remove(lrmPosition);
					}*/
				}
				currentExecution++;
			}
//			ermAgent.runGeneratorThread();
			
			
		}
		
		//core.wrappers.LrmWrapper lrm = WrapperFactory.getInstance().createLrmWrapper();
		
		
	}
	
	private FileStruct[] requestInputFiles(String requestId, String processId) {
		core.wrappers.AsctWrapper asct = WrapperFactory.getInstance().createAsctWrapper(ermAgent.getAsctIor());
		//FileStruct iFiles[] = asct.requestInputFiles(magAgent.getAppMainRequestId(), magAgent.getAppNodeRequestId(), "./" + magAgent.getAppExecutionId());
		
		ExecutionRequestId executionRequestId = new ExecutionRequestId();		
		executionRequestId.requestId = requestId;
		executionRequestId.processId = processId;
		FileStruct iFiles[] = asct.returnInputFiles(executionRequestId);

/*		for (int i = 0; i < iFiles.length; i++) {
			InputFile file = new InputFile();
			
			file.setFileName (iFiles[i].fileName);
			file.setContent (iFiles[i].file);
			
			ermAgent.getInputFiles().add(file);
		}*/
		
		return iFiles;
	}

	private String normalizeRequestId(String id){
		  
		 int p = id.lastIndexOf(":");
		 return id.substring(0,p);
		  
	}
	
	
	
	


}

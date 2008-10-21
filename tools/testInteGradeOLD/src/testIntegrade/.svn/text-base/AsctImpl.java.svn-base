package testIntegrade;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import dataTypes.BspProcessZeroInformation;
import dataTypes.ExecutionRequestId;
import dataTypes.FileStruct;
import dataTypes.RequestAcceptanceInformation;

import tools.AsctPOA;

/**
    AsctImpl - Implements the ASCT interface
    
    @author Andrei Goldchleger
    @date February/2004
    
    @modified Raphael Y. de Camargo
    @date May/2005
 */
public class AsctImpl extends AsctPOA {

    //Fields---------------------------------------------------------------
    private TestCoordinator coordinator;

    //Constructor----------------------------------------------------------
    public AsctImpl(TestCoordinator coord){
        coordinator = coord;
    }

    //---------------------------------------------------------------------
    // /**
    //  *
    //  * Receive confirmation from a remote LRM that accepted our
    //  * execution request
    //  *
    //  * Analog to 2k's "ACK"
    //  *
    //  * @param offSpecs - Offer information, see(@see types::offSpecs)
    //  */

    public void setExecutionAccepted(RequestAcceptanceInformation offSpecs){
              
        coordinator.acceptedExecutionRequest(offSpecs);
    }


    //---------------------------------------------------------------------
    // /**
    //  * Receive notice that one of our queries was rejected
    //  * by all other LRMs (Though we can retry again if we
    //  * so wish)
    //  *
    //  * Analog to 2k's "NACK"
    //  *
    //  * @param requestId - Id of the refused request
    //  */
    public void setExecutionRefused(ExecutionRequestId asctRequestId){

      System.out.println("Our request represented by main id: " +
                         asctRequestId.requestId +
                         " and NODE id: " +
                         asctRequestId.processId +
                         " was REFUSED");
      coordinator.refusedExecutionRequest(asctRequestId);
    }

    //---------------------------------------------------------------------
    // /**
    // *  Receive request for files needed to allow the remote execution of
    // *  a request made by this ASCT
    // *
    // *  @param paths -  the file path in the host where this ASCT is running
    // *  @return all files needed by the remote execution
    // */
    public FileStruct [] getInputFiles(ExecutionRequestId asctRequestId){
    	
    	//System.out.println("Requesting input files");
    	
    	String [] filePaths = coordinator.getAppInputFiles(asctRequestId);
    	FileStruct [] inputFiles = new FileStruct[filePaths.length];
    	for(int i = 0; i < inputFiles.length; i++){
    		inputFiles[i] = new FileStruct();
    		try{
    			File file = new File(filePaths[i]);
    			inputFiles[i].fileName = file.getName();
    			FileInputStream fis = new FileInputStream(file);
    			byte [] serializedFile = new byte[(int)file.length()];//FIXME: check if cast is acceptable
    			fis.read(serializedFile);
    			fis.close();
    			inputFiles[i].file = serializedFile;
    		}
    		catch(FileNotFoundException fnfe){
    			System.err.println("AsctImpl::getInputFiles-->>Someone asked for an inexistent file");
    			System.err.println(filePaths[i]);
    			/** @todo: Signal error condition to caller(Raise CORBA exception) */
    			System.exit(-1);
    			return null;
    		}
    		catch(IOException ioe){
    			System.err.println("AsctImpl::requestInputFiles-->>IOException");
    			/** @todo: Signal error condition to caller(Raise CORBA exception) */
    			System.exit(-1);
    			return null;
    		}
    	}//for
    	
    	//System.out.println("Retunrning input files");
    	
    	return inputFiles;
    }//method

    //--------------------------------------------------------------------
    //FIXME: Comment me
    public void setExecutionFinished(ExecutionRequestId asctRequestId){ 
        coordinator.appFinished(asctRequestId); 
    }


    //--------------------------------------------------------------------
    // Empty implementation
    public BspProcessZeroInformation registerBspNode(String appMainRequestId, String bspProxyIor){
        System.out.println("AsctImpl.registerBspNode --> ERROR: This method should not be called!!!");
        return null;
    }

}//class

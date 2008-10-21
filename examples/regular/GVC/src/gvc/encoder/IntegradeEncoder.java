package gvc.encoder;

import gvc.util.RequestStatusList;
import gvc.video.*;

import java.io.File;
import java.io.IOException;

import asct.shared.ExecutionRequestStatus;
import asct.shared.ParametricCopyHolder;
import asct.ui.ASCTController;
import clusterManagement.ApplicationNotFoundException;
import clusterManagement.DirectoryNotFoundException;
import clusterManagement.InvalidPathNameException;
import clusterManagement.SecurityException;
import dataTypes.ApplicationType;

public class IntegradeEncoder{
	private RequestStatusList requestList;
	private ASCTController asct;
	private static int idRequest=0;
	private String basePath;
	private String applicationName;
	private String applicationContraints;
	private String applicationPreferences;
	private ApplicationType applicationType;
	private String binaryNames;
	private String[] inputFiles;
	private String[] outputFiles;
	private ParametricCopyHolder[] parametricCopies;
	private int numberOfTasks;
	private boolean forceDifferentMachines;	
	
	/**
	 * 
	 */
	public IntegradeEncoder() {
		requestList = RequestStatusList.getInstance();
		asct = ASCTController.getInstance();		
		
		basePath = "/";
		applicationName = "ffmpeg2"; 
		applicationContraints = "Linux_i686";
		applicationPreferences = "";
		applicationType = ApplicationType.parametric;
		binaryNames = "";
		inputFiles = new String[0];
		outputFiles = new String[0];
		forceDifferentMachines = false;
	}
	
	/* (non-Javadoc)
	 * @see gvc.encoder.IEncoder#encode(java.io.File, java.io.File, java.lang.String)
	 */
	public void encode(VideoFile videoFile, File target, 
			String convertionParameters, String format, int numberOfNodes)
	throws IOException, FfmpegExecutionException {
		
		if(!videoFile.getOriginal().getFile().exists())
			throw new IOException("Input file does not exist.");
		if(!videoFile.getOriginal().getFile().canRead())
			throw new IOException("Cannot read input file.");
		if(videoFile.getOriginal().getFile().getAbsolutePath() == target.getAbsolutePath())
			throw new IOException("The input and output file should be different.");

		String applicationArguments =
			" " + convertionParameters +
			" "	+ videoFile.getOriginal().getFile().getAbsolutePath() +
			" " + target.getAbsolutePath();
		numberOfTasks = numberOfNodes+1;
		
		ExecutionRequestStatus requestStatus = executeFfmpegIntegrade(applicationArguments);
		
		requestList.addRequest(requestStatus, videoFile.getOriginal().getFile(), 
				target, format, convertionParameters, videoFile,numberOfTasks);
	}
	
	/**
	 * 
	 * @param applicationArguments
	 * @throws FfmpegExecutionException
	 */
	public ExecutionRequestStatus executeFfmpegIntegrade(String applicationArguments) 
	throws FfmpegExecutionException {
		try {
			
			parametricCopies = new ParametricCopyHolder[numberOfTasks]; 
			
			idRequest ++;
			
			for (int i=0; i< numberOfTasks; i++)
				parametricCopies[i]= new ParametricCopyHolder(i, applicationArguments + " " + i + " " + (numberOfTasks-1) + " " + idRequest,inputFiles,outputFiles);
			
			return asct.executeApplication(
						basePath, applicationName,
						applicationArguments, applicationContraints,
						applicationPreferences, applicationType,
						binaryNames, inputFiles,
						outputFiles, parametricCopies,
						numberOfTasks, forceDifferentMachines);
			
		} catch (ApplicationNotFoundException e) {
			e.printStackTrace();
			throw new FfmpegExecutionException(
			"Could not convert input file - Application Not Found .");
		} catch (DirectoryNotFoundException e) {
			e.printStackTrace();
			throw new FfmpegExecutionException(
			"Could not convert input file - Directory Not Found.");
		} catch (InvalidPathNameException e) {
			e.printStackTrace();
			throw new FfmpegExecutionException(
			"Could not convert input file - Invalid Path Name.");
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new FfmpegExecutionException(
			"Could not convert input file - Security Exception.");
		}
	}
	
}

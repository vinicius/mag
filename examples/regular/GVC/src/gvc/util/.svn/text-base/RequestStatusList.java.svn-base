package gvc.util;

import gvc.video.*;
import gvc.video.format.MPlayerFormatIdentifier;

import java.util.*;
import java.io.*;

import dataTypes.ExecutionRequestId;
import asct.shared.*;
import asct.ui.ASCTController;
/**
 * @author helves
 *
 */
public class RequestStatusList implements IExecutionListener {	
	private static RequestStatusList instance;
	private ConfigurationManager config;
	private Map<String, ExecutionInformation> requestList;
	private File fileList;
	
	private RequestStatusList() {
		config = ConfigurationManager.getInstance();
		fileList = config.getIntegradeRequestList();
		try {
			requestList = (Map<String, ExecutionInformation>) SerializableUtil.readObject(fileList);
		} catch (IOException e) {
			System.out.println(
			"Lista de requisicoes do integrade não encontrada, criando uma nova.");
			requestList = new HashMap<String, ExecutionInformation>();
			try {
				SerializableUtil.writeObject(requestList, fileList);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static RequestStatusList getInstance() {
		if(instance ==  null) {
			instance = new RequestStatusList();
			ASCTController asct = ASCTController.getInstance();
			asct.registerExecutionStateListener(instance);
		}
		return instance;
	}
	
	public synchronized void updateStatus(ExecutionRequestId executionRequest, 
			ApplicationState applicationState) {
		
		if (requestList.containsKey(executionRequest.requestId)) {			
			ExecutionInformation requestUpdated = 
				(ExecutionInformation) requestList.get(executionRequest.requestId);
			
			
			
			
			if (ApplicationState.FINISHED == applicationState){
				requestUpdated.addCompletedTasks();
				System.out.println(
						"Uma tarefa da requisicao " + executionRequest.requestId
					  + " finalizou. ");
			}
		
			
			if (requestUpdated.getNumberOfTasks() == requestUpdated.getNumberOfCompletedTasks()) {
				requestUpdated.getRequest().setApplicationState(applicationState);
				saveRequestList(requestList);
				System.out.println(
						"Alterado o status da requisicao " + executionRequest.requestId
					  + " para " + applicationState.toString());
			}
			
			if(requestUpdated.getRequest().getApplicationState().equals(ApplicationState.FINISHED)) {
				Video outputVideo = new Video(requestUpdated.getTarget());
				
				MPlayerFormatIdentifier.getInstance().identifyFormat(outputVideo);
				VideoFileList videoFilesList = VideoFileList.getInstance();
				videoFilesList.addVideoToVideoFile(requestUpdated.getVideoFile(), outputVideo);
			}
		}
	}
	
	/**
	 * @param requestStatus The request the will put in the hash map.
	 */
	public synchronized void addRequest(
			ExecutionRequestStatus requestStatus,
			File source,
			File target,
			String format,
			String convertion_parameter,
			VideoFile videoFile,
			int numberOfTasks) {
		
		if (!requestList.containsKey(requestStatus.getRequestId())) {
			ExecutionInformation execInfo = new ExecutionInformation();
			execInfo.setRequest(requestStatus);
			execInfo.setSource(source);
			execInfo.setTarget(target);
			execInfo.setFormat(format);
			execInfo.setVideoFile(videoFile);
			execInfo.setConvertion_parameter(convertion_parameter);
			execInfo.setNumberOfTasks(numberOfTasks);
			
			requestList.put(requestStatus.getRequestId(),execInfo);
		}
		saveRequestList(requestList);
	}
	
	private void saveRequestList(Map<String, ExecutionInformation> requestList) {
		try {
			SerializableUtil.writeObject(requestList, fileList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param requestStatus The request the will put in the hash map.
	 */
	public Map<String, ExecutionInformation> getRequestList() {
		return requestList;
	}
	
}

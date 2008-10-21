package gvc.bean;

import gvc.util.Comparator;
import gvc.util.ExecutionInformation;
import gvc.util.RequestStatusList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author Helves
 *
 */
public class RequestListBean {	
	private int amountOfRequest = 0;
	
	/**
	 * 
	 */
	public RequestListBean() {
	
	}
	
	/**
	 * @return
	 */
	public List<ExecutionInformation> getRequestList() {
		List<ExecutionInformation> requestList = new ArrayList<ExecutionInformation>();
		RequestStatusList requestStatusList = RequestStatusList.getInstance();
		
		Map requestMap = requestStatusList.getRequestList();

		Set set= requestMap.keySet();
	    Iterator iter = set.iterator();
	    
	     amountOfRequest =0;
	     while(iter.hasNext()){
	    	 requestList.add((ExecutionInformation) requestMap.get(iter.next()));
	    	 amountOfRequest++;
	     } 
	     
	    Collections.sort(requestList, new Comparator());
		
	    System.out.println("Qtde de elementos =" + amountOfRequest);
		return requestList;
	}	
	
	/**
	 * @return
	 */
	public int getAmountOfRequests() {
		this.getRequestList();
		return amountOfRequest;
	}
	
}

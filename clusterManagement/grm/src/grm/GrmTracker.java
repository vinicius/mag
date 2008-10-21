package grm;

import org.omg.CORBA.ORB;

import java.util.ArrayList;

import clusterManagement.Grm;
import clusterManagement.GrmHelper;

public class GrmTracker {
	private ORB orb;
	private String parentGrmId;
	private String originatingGrmId;

	private boolean parentGrmIdReturned;
	
	private ArrayList<String> untraversedGrmIds = new ArrayList<String>();
	
	public GrmTracker(ORB orb, String originatingGrmId, String parentGrmId) {
		this.orb = orb;

		this.parentGrmId = parentGrmId;
		this.originatingGrmId = originatingGrmId;

		this.parentGrmIdReturned = false;
	}
	
	public void insertUntraversedGrmId(String childGrmId) {
		untraversedGrmIds.add(childGrmId);
	}
	
	public String getNextGrmId() {
		String nextGrmId;
		
		while(untraversedGrmIds.size() > 0) {
			nextGrmId = untraversedGrmIds.remove(0);
				
		 	if(!nextGrmId.equals(originatingGrmId)) {
				System.out.println("Returning a child GRM " + nextGrmId + " as next forwarder.");
				
		 		return nextGrmId;
		 	}
		}
		
		if(!parentGrmIdReturned) {
			parentGrmIdReturned = true;

			System.out.println("Returning the parent GRM " + parentGrmId + " as next forwarder.");
			
			return parentGrmId;
		}
		
		System.out.println("There are no more GRM forwarders left.");
		
		return null;
	}
	
	public Grm getNextGrm() {
		String nextGrmId;
		
		if((nextGrmId = getNextGrmId()) != null) {
			return GrmHelper.narrow(orb.string_to_object(nextGrmId));
		}
		
		return null;
	}
}
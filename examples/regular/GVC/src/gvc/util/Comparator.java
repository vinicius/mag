package gvc.util;

public class Comparator implements java.util.Comparator{

	public Comparator() {
		
	}
	
	public int compare(Object a, Object b) {
		
        ExecutionInformation execA = (ExecutionInformation) a;
        ExecutionInformation execB = (ExecutionInformation) b;
        
        Integer requestIdA = new Integer(execA.getRequest().getRequestId());
        Integer requestIdB = new Integer(execB.getRequest().getRequestId());
        
        if ( requestIdA.intValue()< requestIdB.intValue() ) {
            return -1;
        } else if (requestIdA.intValue() > requestIdB.intValue()) {
            return 1;
        } else {
            return 0;
        }
    }
}

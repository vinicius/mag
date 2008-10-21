package asct.shared;

/**
 * An abstraction to the state of an application.
 * @author randrade
 *
 */
public class ApplicationState {
	private String state;
	public static ApplicationState EXECUTING = new ApplicationState("EXECUTING");
	// TODO - Terminated means Refused ??
	public static ApplicationState TERMINATED = new ApplicationState("TERMINATED");
	public static ApplicationState FINISHED = new ApplicationState("FINISHED");
	public static ApplicationState REFUSED = new ApplicationState("REFUSED");
	
	private ApplicationState(){
	}
	
	private ApplicationState(String state){
		this.state = state;
	}
	/**
	 * Compares this object with another of the same kind
	 * @param o
	 * @return true if both objects are equal, false otherwise.
	 */
	public boolean equals(ApplicationState o){
		if(this.state.equals(o.toString())) return true;
		return false;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return this.state;
	}
}

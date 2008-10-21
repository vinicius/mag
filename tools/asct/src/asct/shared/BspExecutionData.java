package asct.shared;

/**
 * A class structure that represents all the data needed to submit an
 * bsp application execution.
 * 
 * @author randrade and vidal
 * 
 */
public class BspExecutionData extends ExecutionRequestData {
	private int numberOfTasks;
	
	public BspExecutionData(){
		super();
		setNumberOfTasks(-1);
	}

	/**
	 * @return Returns the numberOfTasks.
	 */
	public int getNumberOfTasks() {
		return numberOfTasks;
	}

	/**
	 * Sets the numberOfTasks
	 * @param numberOfTasks The numberOfTasks to set.
	 */
	public void setNumberOfTasks(int bspApplicationTaskNumber) {
		this.numberOfTasks = bspApplicationTaskNumber;
	}
	
}

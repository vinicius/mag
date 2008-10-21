/**
 * 
 */
package asct.shared;

/**
 * A class structure that represents all the data needed to submit an
 * parametric application execution.
 * 
 * @author randrade and vidal
 * 
 */
public class ParametricExecutiondata extends ExecutionRequestData {
	private int numberOfCopies;
	/**
	 * 
	 */
	public ParametricExecutiondata() {
		super();
		numberOfCopies = -1;
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return Returns the numberOfCopies.
	 */
	public int getNumberOfCopies() {
		return numberOfCopies;
	}
	/**
	 * Sets the number of copies
	 * @param numberOfCopies The numberOfCopies to set.
	 */
	public void setNumberOfCopies(int numberOfCopies) {
		this.numberOfCopies = numberOfCopies;
	}

}

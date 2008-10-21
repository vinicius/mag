/**
 * 
 */
package asct.shared;

import java.util.List;

/**
 * A class structure that represents all the data needed to submit an
 * application execution.
 * 
 * @author randrade and vidal
 * 
 */
public class ExecutionRequestData {
	/** */
	private AbstractGridApplication application;

	/** */
	private String constraints;
	
	/** */
	private String preferences;
	
	/** */
	private String arguments;

	/** */
	private String[] inputFiles;
	
	/** */
	private String[] outputFileNames;

	/** */
	private boolean forceDifferentMachines;

	/** used in parametric executions. */
	private int numberOfCopies;

	/** used in parametric executions. */
	private ParametricCopyHolder[] parametricCopies;

	/** used in bsp executions. */
	private int numberOfTasks;
	
	/**
	 * Constructor.
	 * */
	public ExecutionRequestData() {

	}

	/**
	 * @return Returns the application.
	 */
	public AbstractGridApplication getApplication() {
		return application;
	}

	/**
	 * @param application The application to set.
	 */
	public void setApplication(AbstractGridApplication application) {
		this.application = application;
	}

	/**
	 * @return Returns the arguments.
	 */
	public String getArguments() {
		return arguments;
	}

	/**
	 * @param arguments The arguments to set.
	 */
	public void setArguments(String arguments) {
		this.arguments = arguments;
	}

	/**
	 * @return Returns the constraints.
	 */
	public String getConstraints() {
		return constraints;
	}

	/**
	 * @param constraints The constraints to set.
	 */
	public void setConstraints(String constraints) {
		this.constraints = constraints;
	}

	/**
	 * @return Returns the inputFiles.
	 */
	public String[] getInputFiles() {
		return inputFiles;
	}

	/**
	 * @param inputFiles The inputFiles to set.
	 */
	public void setInputFiles(String[] inputFiles) {
		this.inputFiles = inputFiles;
	}

	/**
	 * @return Returns the outputFiles.
	 */
	public String[] getOutputFileNames() {
		return outputFileNames;
	}

	/**
	 * @param outputFiles The outputFiles to set.
	 */
	public void setOutputFileNames(String[] outputFileNames) {
		this.outputFileNames = outputFileNames;
	}

	/**
	 * @return Returns the preferences.
	 */
	public String getPreferences() {
		return preferences;
	}

	/**
	 * @param preferences The preferences to set.
	 */
	public void setPreferences(String preferences) {
		this.preferences = preferences;
	}

	public boolean isForcedOnDifferentNodes() {
		return this.forceDifferentMachines;
	}

	/**
	 * @return Returns the numberOfCopies.
	 */
	public int getNumberOfCopies() {
		return numberOfCopies;
	}

	/**
	 * @param numberOfCopies The numberOfCopies to set.
	 */
	public void setNumberOfCopies(int numberOfCopies) {
		this.numberOfCopies = numberOfCopies;
	}

	/**
	 * @return Returns the numberOfTasks.
	 */
	public int getNumberOfTasks() {
		return numberOfTasks;
	}

	/**
	 * @return Returns the parametricCopies.
	 */
	public ParametricCopyHolder[] getParametricCopies() {
		return parametricCopies;
	}

	public boolean isForceDifferentMachines() {
		return forceDifferentMachines;
	}

	public void setForceDifferentMachines(boolean forceDifferentMachines) {
		this.forceDifferentMachines = forceDifferentMachines;
	}

	public void setNumberOfTasks(int numberOfTasks) {
		this.numberOfTasks = numberOfTasks;
	}

	public void setParametricCopies(ParametricCopyHolder[] parametricCopies) {
		this.parametricCopies = parametricCopies;
	}
	
	
}

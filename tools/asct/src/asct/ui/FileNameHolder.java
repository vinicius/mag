package asct.ui;

import java.io.File;

//Class----------------------------------------------------------------------
/**
 * FileNameHolder - Holds an Application's name and path.
 * 
 * @author Andrei Goldchleger
 * @date February/2004
 * 
 */
class FileNameHolder implements TooltipAble {

	// Fields------------------------------------------------------------------
	private String appPath_;

	/** < Local path to the registered application */

	// Costructor--------------------------------------------------------------
	/**
	 * Creates a new FileNameHolder.
	 * 
	 * @param appPath -
	 *            Local path to the registered application
	 */
	FileNameHolder(String appPath) {

		appPath_ = appPath;

	}

	// ------------------------------------------------------------------------
	/**
	 * Overrides java.lang.Object method. Used to determine what is displayed by
	 * the JList that holds this object.
	 */
	public String toString() {
		return appName();
	}

	public String appName() {
		return (new File(appPath_)).getName();
	}

	public String appPath() {
		return appPath_;
	}

	public String getToolTipText() {
		return appPath_;
	}

}// class

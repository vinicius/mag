package asct.ui;

import java.awt.Point;

import javax.swing.JDialog;
import javax.swing.JPanel;

public class RequestExecutionDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	// private JPanel executeApplicationPanel = null;
	protected RequestExecutionPanel executeApplicationPanel = null;

	private ExecutingApplicationsPanel callerPanel;

	/**
	 * This is the default constructor
	 */
	private RequestExecutionDialog() {
		super();
		initialize();
	}

	public RequestExecutionDialog(String applicationPath, String applicationId, String binaries,
			ExecutingApplicationsPanel caller) {
		super();
		this.setTitle("Executeapplication");
		executeApplicationPanel = new RequestExecutionPanel(applicationPath,
				applicationId, binaries);
		this.add(executeApplicationPanel);
		this.callerPanel = caller;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setContentPane(getJContentPane());
		this.setLocation(new Point(250, 250));
		this.pack();
		this.setVisible(true);
	}

	/**
	 * This method initializes executeApplicationPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		return this.executeApplicationPanel;
	}

	/**
	 * Gets the panel that initialized this dialog
	 * 
	 * @return Returns the panel who initialized this dialog
	 */
	public ExecutingApplicationsPanel getCallerPanel() {
		return callerPanel;
	}

	/**
	 * Sets the panel that initialized this dialog
	 * 
	 * @param callerPanel
	 *            The callerPanel to set.
	 */
	public void setCallerPanel(ExecutingApplicationsPanel callerPanel) {
		this.callerPanel = callerPanel;
	}

}

package asct.ui;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JCheckBox;

/**
 * BspPanel - Provides a means for user input for a BSP execution request.
 * 
 * @author Andrei Goldchleger
 * @date February/2004
 */
class BspPanel extends JPanel {

	// Fields--------------------------------------------------------------------------
	private MultiOptionHolder options;

	private InputFilesPanel inputFiles;

	/** < Nedded files for the execution */
	private OutputFilesPanel outputFiles;

	private JCheckBox forceDifferentMachinesCheck;

	// --------------------------------------------------------------------------------
	BspPanel() {

		options = new MultiOptionHolder();
		options.addOption("Number of tasks: ");
		options.addOption("Arguments: ");
		JPanel topTopPanel = new JPanel();
		forceDifferentMachinesCheck = new JCheckBox(
				"Force Copies to execute on different Nodes");
		topTopPanel.add(forceDifferentMachinesCheck);
		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.add(options, BorderLayout.NORTH);
		topPanel.add(topTopPanel, BorderLayout.SOUTH);

		inputFiles = new InputFilesPanel();
		outputFiles = new OutputFilesPanel();
		JPanel bottomPanel = new JPanel(new GridLayout(0, 2));
		bottomPanel.add(inputFiles);
		bottomPanel.add(outputFiles);
		this.setLayout(new BorderLayout());
		this.add(topPanel, BorderLayout.NORTH);
		this.add(bottomPanel, BorderLayout.CENTER);

	}

	// Getters-------------------------------------------------------------------------
	public int taskNum() {
		try {
			return Integer.parseInt(options.getValue("Number of tasks: "));
		} catch (NumberFormatException nfe) {
			return 0;
		}
	}

	public String appArgs() {
		return options.getValue("Arguments: ");
	}

	public String[] inputFiles() {
		return inputFiles.getFilenames();
	}

	public String[] outputFiles() {
		return outputFiles.getFilenames();
	}

	// Setters-------------------------------------------------------------------------
	public void appArgs(String appArgs) {
		options.setValue("Arguments: ", appArgs);
	}

	public void taskNum(String taskNum) {
		options.setValue("Number of tasks: ", taskNum);
	}

	public void inputFiles(String[] inputFilesArray) {
		inputFiles.setFilenames(inputFilesArray);
	}

	public void outputFiles(String[] outputFilesArray) {
		outputFiles.setFilenames(outputFilesArray);
	}

	// -----------------------------------------------------------------------
	public boolean shouldForceDifferentMachines() {
		return forceDifferentMachinesCheck.isSelected();
	}

	// Methods-------------------------------------------------------------------------
	public void reset() {
		options.setValue("Arguments: ", "");
		options.setValue("Number of tasks: ", "");
		inputFiles.reset();
		outputFiles.reset();
		forceDifferentMachinesCheck.setSelected(false);

	}

	public void addInputFile(String filename) {
		inputFiles.addInputFile(filename);
	}

	public void addOutputFile(String filename) {
		outputFiles.addOutputFile(filename);
	}

}

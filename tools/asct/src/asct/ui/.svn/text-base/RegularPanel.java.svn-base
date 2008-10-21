package asct.ui;

import javax.swing.JPanel;
import java.awt.BorderLayout;

// import javax.swing.Box;
// import javax.swing.BoxLayout;

import java.awt.GridLayout;

/**
 * RegularPanel - Provides a means for user input for a regular execution
 * request.
 * 
 * @author Andrei Goldchleger
 * @date February/2004
 */
class RegularPanel extends JPanel {

	// Fields--------------------------------------------------------------------------
	private MultiOptionHolder options;

	private InputFilesPanel inputFiles;

	/** < Nedded files for the execution */
	private OutputFilesPanel outputFiles;

	// Constructor---------------------------------------------------------------------
	RegularPanel() {

		options = new MultiOptionHolder();
		options.addOption("Arguments: ");
		inputFiles = new InputFilesPanel();
		outputFiles = new OutputFilesPanel();
		JPanel bottomPanel = new JPanel(new GridLayout(0, 2));
		bottomPanel.add(inputFiles);
		bottomPanel.add(outputFiles);
		this.setLayout(new BorderLayout());
		this.add(options, BorderLayout.NORTH);
		this.add(bottomPanel, BorderLayout.CENTER);

	}

	// Getters-------------------------------------------------------------------------
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

	public void inputFiles(String[] inputFilesArray) {
		inputFiles.setFilenames(inputFilesArray);
	}

	public void outputFiles(String[] outputFilesArray) {
		outputFiles.setFilenames(outputFilesArray);
	}

	// Methods-------------------------------------------------------------------------
	public void reset() {
		options.setValue("Arguments: ", "");
		inputFiles.reset();
		outputFiles.reset();
	}

	public void addInputFile(String filename) {
		inputFiles.addInputFile(filename);
	}

	public void addOutputFile(String filename) {
		outputFiles.addOutputFile(filename);
	}

}

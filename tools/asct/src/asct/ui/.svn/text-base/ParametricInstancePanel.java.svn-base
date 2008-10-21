package asct.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.io.File;
import java.io.IOException;

/**
 * ParametricInstancePanel - Allows the user to add or edit an instance of a
 * parametric execution.
 * 
 * @author Andrei Goldchleger
 * @date February/2004
 */
class ParametricInstancePanel extends JPanel {

	private OptionHolder appArgs;

	private InputFilesPanel inputFiles_;

	private OutputFilesPanel outputFiles_;

	private JButton addButton;

	private JButton cancelButton;

	// Constructor-------------------------------------------------------
	ParametricInstancePanel() {

		appArgs = new OptionHolder("Arguments:   ");
		inputFiles_ = new InputFilesPanel();
		outputFiles_ = new OutputFilesPanel();

		JPanel bottonPanel = new JPanel(new GridLayout(0, 2));
		addButton = new JButton("Ok");
		cancelButton = new JButton("Cancel");
		bottonPanel.add(addButton);
		bottonPanel.add(cancelButton);

		JPanel middlePanel = new JPanel(new GridLayout(0, 2));
		middlePanel.add(inputFiles_);
		middlePanel.add(outputFiles_);

		// final assembly
		this.setLayout(new BorderLayout());
		this.add(appArgs, BorderLayout.NORTH);
		this.add(middlePanel, BorderLayout.CENTER);
		this.add(bottonPanel, BorderLayout.SOUTH);

	}

	// --------------------------------------------------------------------

	public JButton addButton() {
		return addButton;
	}

	public JButton cancelButton() {
		return cancelButton;
	}

	public String args() {
		return appArgs.getValue();
	}

	public String[] inputFiles() {
		return inputFiles_.getFilenames();
	}

	public String[] outputFiles() {
		return outputFiles_.getFilenames();
	}

	public void args(String args) {
		appArgs.setValue(args);
	}

	public void inputFiles(String[] inputFiles) {
		inputFiles_.setFilenames(inputFiles);
	}

	public void outputFiles(String[] outputFiles) {
		outputFiles_.setFilenames(outputFiles);
	}

}// class


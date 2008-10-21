package asct.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import asct.core.ApplicationControlFacade;

import clusterManagement.ApplicationNotFoundException;
import clusterManagement.BinaryCreationException;
import clusterManagement.DirectoryNotFoundException;
import clusterManagement.InvalidPathNameException;
import clusterManagement.SecurityException;

import dataTypes.BinaryDescription;

/*
 * Created on Sep 16, 2004
 */

/**
 * @author vidal
 */
public class UploadPanel extends JPanel implements ActionListener {
	private RepositoryPanel reposPanel;

	private String appDirName;

	private File selectedBinaryFile;

	private String platform;

	private static final String[] platformString = { "Linux_i686",
			"Linux_x86_64", "Darwin_Power Macintosh","Java_" };

	// UI components
	private JTextField binaryField;

	private JTextField platformField;

	private JButton browseButton;

	private JButton uploadButton;

	private JButton cancelButton;

	private JComboBox platformList;

	private JFrame frame;

	// ***********************************************************************************
	// initializing methods
	// ***********************************************************************************
	/**
	 * constructor
	 * 
	 * @param RepositoryPanel
	 *            reposPanel
	 * @param Asct
	 *            asct
	 * @param String
	 *            appDirName
	 */
	public UploadPanel(RepositoryPanel reposPanel, String appDirName) {
		this.reposPanel = reposPanel;
		this.appDirName = appDirName;

		// Keep references to the next few borders,
		// for use in titles and compound borders.
		Border blackline, raisedetched, loweredetched, raisedbevel, loweredbevel, empty, redline, blueline;
		blackline = BorderFactory.createLineBorder(Color.black);
		raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		raisedbevel = BorderFactory.createRaisedBevelBorder();
		loweredbevel = BorderFactory.createLoweredBevelBorder();
		empty = BorderFactory.createEmptyBorder();
		redline = BorderFactory.createLineBorder(Color.red);
		blueline = BorderFactory.createLineBorder(Color.BLUE);
		// Titled borders
		TitledBorder titledBorder;

		selectedBinaryFile = null;
		setOpaque(true); // content panes must be opaque
		setPreferredSize(new Dimension(550, 165));
		setLayout(new BorderLayout());

		// top panel specification
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		binaryField = new JTextField(40);
		binaryField.addActionListener(this);
		binaryField.setBackground(Color.WHITE);
		binaryField.setEditable(false);
		browseButton = new JButton("Browse");
		browseButton.addActionListener(this);
		topPanel.add(binaryField, BorderLayout.WEST);
		topPanel.add(browseButton, BorderLayout.EAST);
		String title = (appDirName.substring(appDirName
				.lastIndexOf(File.separator) + 1, appDirName.length() - 1))
				.toUpperCase();
		title = "Choose a " + title + " Binary File";
		titledBorder = BorderFactory.createTitledBorder(blueline, title);
		topPanel.setBorder(titledBorder);
		add(topPanel, BorderLayout.NORTH);

		// platformPanel specification
		JPanel platformPanel = new JPanel();
		platformPanel.setBackground(Color.LIGHT_GRAY);

		platformField = new JTextField(platformString[0]);
		platformField.setEditable(false);
		platformList = new JComboBox(platformString);
		platformList.setSelectedIndex(0);
		platformList.addActionListener(this);
		platformPanel.add(platformList);
		titledBorder = BorderFactory.createTitledBorder(redline, "Platform");
		platformPanel.setBorder(titledBorder);
		add(platformPanel, BorderLayout.CENTER);

		// bottom panel specification
		JPanel bottomPanel = new JPanel();
		uploadButton = new JButton("Upload");
		uploadButton.setBorder(blueline);
		uploadButton.setEnabled(false);
		uploadButton.addActionListener(this);
		bottomPanel.add(uploadButton);
		cancelButton = new JButton("Cancel");
		cancelButton.setBorder(blueline);
		cancelButton.addActionListener(this);
		bottomPanel.add(cancelButton);
		add(bottomPanel, BorderLayout.SOUTH);

	}// UploadPanel constructor

	// ***********************************************************************************
	// auxiliary methods
	// ***********************************************************************************
	public JButton submitButton() {
		return uploadButton;
	}

	public JButton cancelButton() {
		return cancelButton;
	}

	/**
	 * @author vidal
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == platformList)
			platformField.setText(platformString[platformList
					.getSelectedIndex()]);
		else if (e.getSource() == browseButton)
			selectBinary();
	}

	/**
	 * @author vidal
	 */
	void selectBinary() {
		JFileChooser fileChooser;
		fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select a Binary Application");
		fileChooser.setApproveButtonText("Upload");
		fileChooser.setVisible(true);
		int returnVal = fileChooser.showOpenDialog(this);
		if (returnVal == JFileChooser.CANCEL_OPTION) {
			uploadButton.setEnabled(false);
			fileChooser.setVisible(false);
		} else if (returnVal == JFileChooser.APPROVE_OPTION) {
			selectedBinaryFile = fileChooser.getSelectedFile();
			
		    binaryField.setText(selectedBinaryFile.getAbsolutePath());

			System.out.println("DIEGO binaryField = " + selectedBinaryFile.getName());
			uploadButton.setEnabled(true);
		}
	}// selectBinary method

	void uploadBinary() {
		uploadButton.setEnabled(false);
		int lastIndexOfBar = appDirName.lastIndexOf("/");
		try {
			ASCTController.getInstance().uploadBinary(binaryField.getText(),appDirName.substring(0,lastIndexOfBar),appDirName.substring(lastIndexOfBar+1,appDirName.length()),platformField.getText());
			reposPanel.insertBinaryNode(appDirName + "/" + platformField.getText() ,platformField.getText());
			binaryField.setText("");
		} catch (BinaryCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ApplicationNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DirectoryNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidPathNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}// getBinary method

}// class UploadPanel


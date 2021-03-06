package asct.ui;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JList;
import javax.swing.border.TitledBorder;
import javax.swing.JDialog;

import java.io.File;
import java.io.IOException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;

import java.awt.Container;
import javax.swing.JFrame;

import asct.shared.ParametricCopyHolder;

import java.awt.Point;

//Class----------------------------------------------------------------------
/**
 * ParametricPanel - Allows a user to specify the parameters of each copy of a
 * parametric application.
 * 
 * @author Andrei Goldchleger
 * @date February/2004
 */
class ParametricPanel extends JPanel {

	// Fields------------------------------------------------------------------
	private JButton addButton;

	private JButton removeButton;

	private JButton editButton;

	private JCheckBox forceDifferentMachinesCheck;
	
	// Vinicius
	private OptionHolder taskNum;

	private JScrollPane copiesScrollPane;

	private DefaultListModel copiesListModel;

	private JList copiesList;

	private int lastCopyId;

	// Costructor--------------------------------------------------------------
	/**
	 * Creates a new ParametricPanel
	 * 
	 */
	ParametricPanel() {

		lastCopyId = 1;

		JPanel topTopPanel = new JPanel();
		forceDifferentMachinesCheck = new JCheckBox(
				"Force Copies to execute on different Nodes");
		topTopPanel.add(forceDifferentMachinesCheck);
		
		// Vinicius {
		taskNum = new OptionHolder("Number of replicas for each task: ");
		taskNum.setValue("1");
		topTopPanel.add(taskNum);
		// } Vinicius

		// Top Panel(Add/Remove/Edit)
		JPanel bottomTopPanel = new JPanel();
		bottomTopPanel.add(new JLabel("Application copies: "));
		addButton = new JButton("Add");
		removeButton = new JButton("Remove");
		editButton = new JButton("Edit");
		bottomTopPanel.add(addButton);
		bottomTopPanel.add(removeButton);
		bottomTopPanel.add(editButton);

		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.add(topTopPanel, BorderLayout.NORTH);
		topPanel.add(bottomTopPanel, BorderLayout.SOUTH);

		// Setting button listeners
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addCopy();
			}
		});

		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeCopy();
			}
		});

		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editCopy();
			}
		});

		removeButton.setEnabled(false);
		editButton.setEnabled(false);
		// Botton Panel
		this.copiesScrollPane = new JScrollPane();
		this.copiesListModel = new DefaultListModel();
		this.copiesList = new JList(copiesListModel);
		copiesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		copiesScrollPane.getViewport().add(copiesList, null);

		// Final assembly
		this.setLayout(new BorderLayout());
		this.add(topPanel, BorderLayout.NORTH);
		this.add(copiesScrollPane, BorderLayout.CENTER);

	}

	// Methods----------------------------------------------------------------
	/**
	 * Called when a user adds a new instance of the application to be executed.
	 * Pops a ParametricInstancePanel.
	 */
	private void addCopy() {

		final JDialog addCopyDialog;
		Container rootContainer = this.getTopLevelAncestor();
		if (rootContainer instanceof JDialog)
			addCopyDialog = new JDialog((JDialog) rootContainer, "Add Copy",
					true);
		else
			// Assuming it is a JFrame
			addCopyDialog = new JDialog((JFrame) rootContainer, "Add Copy",
					true);
		final ParametricInstancePanel aepp = new ParametricInstancePanel();
		aepp.addButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addCopy(aepp);
				addCopyDialog.dispose();
			}
		});
		aepp.cancelButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addCopyDialog.dispose();
			}
		});
		addCopyDialog.setContentPane(aepp);
		addCopyDialog.setLocation(new Point(
				this.getTopLevelAncestor().getX() + 50, this
						.getTopLevelAncestor().getX() + 50));
		addCopyDialog.pack();
		addCopyDialog.setVisible(true);

	}

	// -----------------------------------------------------------------------
	/**
	 * Allows a user to modify an instance of the application to be executed.
	 * Pops a ParametricInstancePanel.
	 */
	private void editCopy() {

		if (copiesList.getSelectedIndex() < 0)
			return;
		final JDialog editCopyDialog;
		Container rootContainer = this.getTopLevelAncestor();
//		if (rootContainer.getClass() == JDialog.class)
		if (rootContainer instanceof JDialog)
			editCopyDialog = new JDialog((JDialog) rootContainer, "Edit Copy",
					true);
		else
			// Assuming it is a JFrame
			editCopyDialog = new JDialog((JFrame) rootContainer, "Edit Copy",
					true);

		ParametricCopyHolder copyHolder = (ParametricCopyHolder) copiesListModel
				.elementAt(copiesList.getSelectedIndex());

		final ParametricInstancePanel aepp = new ParametricInstancePanel();
		aepp.args(copyHolder.getArguments());
		aepp.inputFiles(copyHolder.getInputFiles());
		aepp.outputFiles(copyHolder.getOutputFiles());
		aepp.addButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editCopy(aepp);
				editCopyDialog.dispose();
			}
		});
		aepp.cancelButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editCopyDialog.dispose();
			}
		});
		editCopyDialog.setContentPane(aepp);
		editCopyDialog.setLocation(new Point(
				this.getTopLevelAncestor().getX() + 50, this
						.getTopLevelAncestor().getX() + 50));
		editCopyDialog.pack();
		editCopyDialog.setVisible(true);

	}

	// -----------------------------------------------------------------------
	/**
	 * Called when a user adds a new instance of the application to be executed.
	 */
	private void addCopy(ParametricInstancePanel aepp) {

		ParametricCopyHolder copyHolder = new ParametricCopyHolder(
				lastCopyId++, aepp.args(), aepp.inputFiles(), aepp
						.outputFiles());
		copiesListModel.addElement(copyHolder);
		removeButton.setEnabled(true);
		editButton.setEnabled(true);
	}

	// -----------------------------------------------------------------------
	/**
	 * Allows a user to modify an instance of the application to be executed.
	 */
	private void editCopy(ParametricInstancePanel aepp) {

		ParametricCopyHolder copyHolder = (ParametricCopyHolder) copiesListModel
				.elementAt(copiesList.getSelectedIndex());
		copyHolder.setArguments(aepp.args());
		copyHolder.setInputFiles(aepp.inputFiles());
		copyHolder.setOutputFiles(aepp.outputFiles());

	}

	// -----------------------------------------------------------------------
	/**
	 * Removes an instance of the parametric application.
	 */
	private void removeCopy() {

		if (copiesList.getSelectedIndex() >= 0) {
			copiesListModel.remove(copiesList.getSelectedIndex());
			if (copiesListModel.size() == 0) {
				removeButton.setEnabled(false);
				editButton.setEnabled(false);
			}
			renumberCopies();
		}
	}

	// -----------------------------------------------------------------------
	/**
	 * Returns an array containing the instances of a Parametric Application
	 */
	public ParametricCopyHolder[] getCopies() {

		ParametricCopyHolder[] copies = new ParametricCopyHolder[copiesListModel
				.size()];
		copiesListModel.copyInto(copies);
		return copies;

	}

	// -----------------------------------------------------------------------
	public void addCopy(ParametricCopyHolder copyHolder) {
		copiesListModel.addElement(copyHolder);
		renumberCopies();
		removeButton.setEnabled(true);
		editButton.setEnabled(true);
	}

	// -----------------------------------------------------------------------
	public void reset() {
		copiesListModel.removeAllElements();
		removeButton.setEnabled(false);
		editButton.setEnabled(false);
		forceDifferentMachinesCheck.setSelected(false);
		// Vinicius
		taskNum.setValue("1");
	}

	// -----------------------------------------------------------------------
	public boolean shouldForceDifferentMachines() {
		return forceDifferentMachinesCheck.isSelected();
	}
	
	// Vinicius {
	public int taskNum() {
		return Integer.parseInt(taskNum.getValue());
	}

	// } Vinicius
	
	// -----------------------------------------------------------------------
	/**
	 * Only to ensure that all copies are numbered 1....n. For Visualization
	 * purposes only.
	 */
	private void renumberCopies() {
		for (int i = 0; i < copiesListModel.size(); i++)
			((ParametricCopyHolder) copiesListModel.get(i)).setCopyId(i + 1);
		lastCopyId = copiesListModel.size() + 1;

	}

}// class


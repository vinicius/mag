package asct.ui;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JLabel;

import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JList;
import javax.swing.border.TitledBorder;
import javax.swing.JOptionPane;

import java.io.File;
import java.io.IOException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;

//Class----------------------------------------------------------------------

/**
 * InputFilesPanel - A Panel that stores a file list, allowing users to specify
 * any files needed by his application.
 * 
 * @author Andrei Goldchleger
 * @date February/2004
 * 
 */
class InputFilesPanel extends JPanel {

	// Fields------------------------------------------------------------------
	private JButton addButton;

	private JButton removeButton;

	private JScrollPane fileScrollPane;

	private DefaultListModel fileListModel;

	private ItemTooltippedList fileList;

	private String lastSelectedDir;

	// Costructor--------------------------------------------------------------
	InputFilesPanel() {

		this.setBorder(new TitledBorder("Input Files"));
		lastSelectedDir = ".";
		// Top Panel(Add/Remove)
		JPanel topPanel = new JPanel();
		// topPanel.add(new JLabel("Input Files "));
		addButton = new JButton("Add");
		removeButton = new JButton("Remove");
		topPanel.add(addButton);
		topPanel.add(removeButton);

		// Setting button listeners
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addFile();
			}
		});

		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeFile();
			}
		});

		removeButton.setEnabled(false);
		// Botton Panel
		this.fileScrollPane = new JScrollPane();
		this.fileListModel = new DefaultListModel();
		this.fileList = new ItemTooltippedList(fileListModel);
		fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fileScrollPane.getViewport().add(fileList, null);

		// Final assembly
		this.setLayout(new BorderLayout());
		this.add(topPanel, BorderLayout.NORTH);
		this.add(fileScrollPane, BorderLayout.CENTER);

	}

	// Methods----------------------------------------------------------------
	private void addFile() {

		JFileChooser fileChooser = new JFileChooser(lastSelectedDir);
		fileChooser.setDialogTitle("Add Input File");
		fileChooser.setApproveButtonText("Add");
		fileChooser.setMultiSelectionEnabled(true);
		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			try {
				File[] selectedFiles = fileChooser.getSelectedFiles();
				lastSelectedDir = selectedFiles[0].getPath();
				FileNameHolder[] nameHolder = new FileNameHolder[fileListModel
						.size()];
				fileListModel.copyInto(nameHolder);
				for (int i = 0; i < selectedFiles.length; i++) {
					int j = 0;
					if ((!selectedFiles[i].isFile())
							|| (!selectedFiles[i].isFile())) {
						Object[] options = new Object[] { "Dismiss" };
						JOptionPane.showOptionDialog(this, "File Not Found: \n"
								+ fileChooser.getSelectedFile().getPath()
								+ "\n", "File Not Found",
								JOptionPane.OK_OPTION,
								JOptionPane.ERROR_MESSAGE, null,// default icon
								options, options[0]);
						return;
					}
					for (; j < nameHolder.length; j++)
						if (selectedFiles[i].getCanonicalPath().equals(
								nameHolder[j].appPath()))
							break;
					if (j == nameHolder.length)
						// if(! fileListModel.contains(new
						// FileNameHolder(selectedFiles[i].getCanonicalPath())))
						fileListModel.addElement(new FileNameHolder(
								selectedFiles[i].getCanonicalPath()));
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
				System.exit(-1);
			}
			removeButton.setEnabled(true);
		}

	}

	// -----------------------------------------------------------------------
	private void removeFile() {

		if (fileList.getSelectedIndex() >= 0) {
			fileListModel.remove(fileList.getSelectedIndex());
			if (fileListModel.size() == 0)
				removeButton.setEnabled(false);

		}

	}

	// -----------------------------------------------------------------------
	public String[] getFilenames() {

		FileNameHolder[] nameHolder = new FileNameHolder[fileListModel.size()];
		fileListModel.copyInto(nameHolder);
		String[] names = new String[fileListModel.size()];
		for (int i = 0; i < names.length; i++)
			names[i] = nameHolder[i].appPath();
		return names;

	}

	// -----------------------------------------------------------------------
	public void setFilenames(String[] filenames) {
		fileListModel.removeAllElements();
		for (int i = 0; i < filenames.length; i++)
			fileListModel.addElement(new FileNameHolder(filenames[i]));
		if (filenames.length > 0)
			removeButton.setEnabled(true);

	}

	// -----------------------------------------------------------------------
	public void addInputFile(String filename) {
		fileListModel.addElement(new FileNameHolder(filename));
	}

	// -----------------------------------------------------------------------
	public void reset() {
		fileListModel.removeAllElements();
		removeButton.setEnabled(false);
	}

}// class


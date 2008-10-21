package asct.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import dataTypes.*;

import asct.core.ApplicationControlFacade;
import asct.ui.ExecutingApplicationsPanel;

//----------------------------------------------------------------------------
/**
 * Asct - One GUI implementation of the ASCT in Java/Swing.
 * 
 * @author Andrei Goldchleger
 * @date February/2004
 * 
 */
class Asct extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;

	private ExecutingApplicationsPanel executingApplicationsPanel;

	private RepositoryPanel repositoryPanel;

	private JPanel outterRepositoryPanelLayout;

	private TitledBorder title;

	Asct() {

		super("InteGrade ASCT");
		cleanUpUnusedFiles();

		UIManager.put("Label.font", new Font("SansSerif", Font.BOLD, 14));
		UIManager.put("Button.font", new Font("SansSerif", Font.BOLD, 14));
		UIManager.put("RadioButton.font", new Font("SansSerif", Font.BOLD, 14));
		UIManager.put("CheckBox.font", new Font("SansSerif", Font.BOLD, 14));
		UIManager
				.put("TitledBorder.font", new Font("SansSerif", Font.BOLD, 14));
		UIManager.put("List.font", new Font("SansSerif", Font.BOLD, 14));
		UIManager.put("ComboBox.font", new Font("SansSerif", Font.BOLD, 14));
		UIManager.put("ToolTip.font", new Font("SansSerif", Font.BOLD, 14));
		UIManager.put("MenuItem.font", new Font("SansSerif", Font.BOLD, 14));
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		executingApplicationsPanel = new ExecutingApplicationsPanel(
				"Requested Executions");
		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setBackground(new Color(0x000021));
		// for the new tree structure release
		ImageIcon img = new ImageIcon("images/ASCT.gif");
		JLabel imgLabel = new JLabel(img);
		topPanel.add(imgLabel, BorderLayout.NORTH);
		JMenuBar menuBar = new JMenuBar();
		toolbar.add(menuBar);
		setJMenuBar(menuBar);
		// Create a menu labeled Help
		JMenu help = new JMenu("Help");
		JMenuItem item;
		// Have it call doAboutCommand when selected
		help.add(item = new JMenuItem("About"));
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doAboutCommand();
			}
		});
		menuBar.add(help);

		// outterRepositoryPanelLayout initialization
		outterRepositoryPanelLayout = new JPanel(new BorderLayout());
		title = BorderFactory.createTitledBorder("Application Repository");
		outterRepositoryPanelLayout.setBorder(title);
		// repository panel initialization

		repositoryPanel = new RepositoryPanel();
		outterRepositoryPanelLayout.add(repositoryPanel, BorderLayout.CENTER);

		// bottom panel initialization
		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.setPreferredSize(new Dimension(600, 500));
		bottomPanel.add(outterRepositoryPanelLayout, BorderLayout.NORTH);
		bottomPanel.add(executingApplicationsPanel, BorderLayout.CENTER);

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(topPanel, BorderLayout.NORTH);
		this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		this.setIconImage(img.getImage());
		this.setLocation(new Point(400, 150));
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		pack();
		this.setVisible(true);

		ASCTController.getInstance().registerExecutionStateListener(executingApplicationsPanel);

	}// Asct constructor method

	// ------------------------------------------------------------------
	void doAboutCommand() {
		final JDialog dialog = new JDialog(this, "About...", true);
		dialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dialog.dispose();
			}
		});

		JLabel lab1 = new JLabel("AsctGui Version 2.0 by Andrei Goldchlerger, José Braga",
				JLabel.CENTER);
		dialog.getContentPane().add(lab1, BorderLayout.NORTH);
		JLabel lab2 = new JLabel("Alexandre Vidal, Eduardo Guerra, Raphael Camargo e Ricardo Andrade",
				JLabel.CENTER);
		dialog.getContentPane().add(lab2, BorderLayout.CENTER);

		JButton butt = new JButton("Close");
		dialog.getContentPane().add(butt, BorderLayout.SOUTH);
		butt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
				dialog.setResizable(false);
				dialog.dispose();
			}
		});
		dialog.setSize(530, 100);
		dialog.setVisible(true);
	}// doAboutCommand() method

	// -------------------------------------------------------------------------------
	private void removeFile(File file) {
		if (file.isFile()) {
			System.out.println("Deleting: " + file.getAbsolutePath());
			file.delete();
		} else if (file.isDirectory()) {
			File[] dirContents = file.listFiles();
			for (int i = 0; i < dirContents.length; i++)
				removeFile(dirContents[i]);
			System.out.println("Deleting: " + file.getAbsolutePath());
			file.delete();
		}
	}

	// -------------------------------------------------------------------------------
	private void cleanUpUnusedFiles() {

		for (int i = 1;; i++) {
			File dir = new File(String.valueOf(i));
			if (dir.exists() && dir.isDirectory())
				removeFile(dir);
			else
				break;
		}

		File bspConfs = new File("bspConfs");
		removeFile(bspConfs);
	}

	// -------------------------------------------------------------------------------
	public static void main(String[] args) {

		Asct asct = new Asct();
		new Thread(asct).start();
	}

	public void run() {

	}

	/**
	 * @return Returns the executingApplicationsPanel.
	 */
	public ExecutingApplicationsPanel getExecutingApplicationsPanel() {
		return executingApplicationsPanel;
	}

}// class Asct

// =================================================================================

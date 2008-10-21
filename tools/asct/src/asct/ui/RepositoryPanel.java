package asct.ui;

import asct.core.ApplicationControlFacade;
import clusterManagement.*;
import clusterManagement.SecurityException;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Stack;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import dataTypes.*;


/*
 * Created on Sep 16, 2004
 */

/**
 * @author vidal
 */
public class RepositoryPanel extends JPanel implements ActionListener {

	private DefaultMutableTreeNode rootNode;

	private DefaultMutableTreeNode lastSelectedNode;

	private final RepositoryTree treeView; // provides a view of the data

	private DefaultTreeModel treeModel; //

	private JFrame frame;

	// ***********************************************************************************
	// initializing methods
	// ***********************************************************************************
	/**
	 * constructor
	 * 
	 * @param String
	 *            path
	 * @param Asct
	 *            asct
	 */
	public RepositoryPanel() {
		Icon[] icons = new Icon[6];
		icons[0] = createImageIcon("images/root.gif");
		icons[1] = createImageIcon("images/appDir.gif");
		icons[2] = createImageIcon("images/appDescFile.gif");
		icons[3] = createImageIcon("images/commonDir.gif");
		icons[4] = createImageIcon("images/commonFile.gif");
		icons[5] = createImageIcon("images/binaryFile.gif");

		rootNode = createNode("/", kindOfItens.rootDirectory, true); // creates
																		// the
																		// root
																		// node

		lastSelectedNode = rootNode;

		treeView = new RepositoryTree(rootNode, this); // creates a JT0ree
		treeView.setCellRenderer(new RepositoryRenderer(icons));

		buildRepositoryTree(rootNode); // creates all nodes recursively

		treeModel = new DefaultTreeModel(rootNode, true);
		treeModel.addTreeModelListener(new RepositoryTreeModelListener());

		treeView.setModel(treeModel);
		treeView.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		treeView.setRootVisible(true);
		treeView.setEditable(false);
		treeView.addMouseListener(new RepositoryTreeMouseAdapter(treeView,rootNode));
		treeView.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeView
						.getLastSelectedPathComponent();
				if (node == null)
					return;
				lastSelectedNode = node;
			}
		});

		// Enable tool tips.
		ToolTipManager.sharedInstance().registerComponent(treeView);

		JScrollPane treeScroll = new JScrollPane(treeView);
		treeScroll.setPreferredSize(new Dimension(200, 200));

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setLeftComponent(treeScroll);

		this.setLayout(new BorderLayout());
		this.add(splitPane, BorderLayout.CENTER);
	}// RepositoryPanel constructor

	/**
	 * @author vidal
	 * @param DefaultMutableTreeNode
	 *            rootNode creates the nodes present in the root directory
	 *            adapted from 'FileViewPanel.createTree' method in
	 *            FileViewPanel.java file
	 */
	private void buildRepositoryTree(DefaultMutableTreeNode parentNode) {
		DefaultMutableTreeNode idxNode;
		boolean allowsChildren;
		TreeNamePathHolder nodeDesc = (TreeNamePathHolder) parentNode
				.getUserObject();

		if (nodeDesc.getKind().equals(kindOfItens.applicationDirectory)
				|| nodeDesc.getKind().equals(kindOfItens.commonDirectory)
				|| nodeDesc.getKind().equals(kindOfItens.rootDirectory)) {
			ContentDescription contents[] = null;
			try {
				if (parentNode.toString().equals("/"))
					contents = ASCTController.getInstance().listRootDirectoryContents();
				else{
					Enumeration ancestorsOfParent = parentNode.pathFromAncestorEnumeration(rootNode);
					String nodePath = "";
					while(ancestorsOfParent.hasMoreElements()){
						DefaultMutableTreeNode ancestor = (DefaultMutableTreeNode) ancestorsOfParent.nextElement();
						nodePath += "/" + ancestor.toString();
					}
					contents = ASCTController.getInstance().listDirectoryContents(nodePath);
				} 

			} catch (Exception e) {
				System.out.println(e.toString());
			}
			
			for (int i = 0; i < contents.length; i++) {
				allowsChildren = contents[i].kind
						.equals(kindOfItens.applicationDirectory)
						|| contents[i].kind.equals(kindOfItens.commonDirectory)
						|| contents[i].kind.equals(kindOfItens.rootDirectory);
				contents[i].fileName = "/" + contents[i].fileName;
				idxNode = createNode(contents[i].fileName, contents[i].kind,
						allowsChildren);
				parentNode.add(idxNode);
				buildRepositoryTree(idxNode);
			}

		}
	}// rebuildRepositoryTree method

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path) {
		File imageFile = new File(path);
		if (imageFile.isFile() && imageFile.exists()) {
			return new ImageIcon(path);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}// createImageIcon method

	// ***********************************************************************************
	// auxiliary methods
	// ***********************************************************************************

	public JFrame getFrame() {
		return frame;
	}

	/**
	 * @author vidal
	 * @param String
	 *            nodeName
	 * @param String
	 *            kind - kind of this node
	 * @param boolean
	 *            allowsChildren
	 */
	public DefaultMutableTreeNode createNode(String nodeName, kindOfItens kind,
			boolean allowsChildren) {
		String platform = "";
		String fileName = nodeName.substring(nodeName.lastIndexOf("/")+1);
		String filePath = nodeName;
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(
				new TreeNamePathHolder(filePath, fileName, kind, platform));
		node.setAllowsChildren(allowsChildren);
		return node;
	}// createNode method

	/**
	 * @author vidal
	 * @param DefaultMutableTreeNode
	 *            childNode
	 * @param DefaultMutableTreeNode
	 *            parentNode
	 */
	public void insertNode(DefaultMutableTreeNode childNode,
			DefaultMutableTreeNode parentNode) {
		treeModel.insertNodeInto(childNode, parentNode, parentNode
				.getChildCount());
		treeModel.nodeChanged(childNode);
	}

	/**
	 * @author vidal
	 */
	public void removeNode() {
		treeModel.removeNodeFromParent(lastSelectedNode);
	}

	// ***********************************************************************************
	// action executing methods
	// ***********************************************************************************
	/**
	 * @author vidal
	 * @param String
	 *            parent called from RepositoryTreeMouseAdapter.
	 *            processMouseEvent method
	 */
	void createDirectory(String parent) {
		String dirName = "";
		dirName = (String) JOptionPane.showInputDialog(frame,
				"Enter the name of the new Directory", "Create Directory",
				JOptionPane.PLAIN_MESSAGE, null, null, "");
		if (dirName != null && dirName.length() != 0) {
			kindOfItens kind = kindOfItens.commonDirectory;
			String dir = new String(parent + "/" + dirName);
			// updates the application repository
			try {
				ASCTController.getInstance().createDirectory(dir);
			} catch (DirectoryCreationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidPathNameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// creates a common directory node
			boolean allowsChildren = true;
			DefaultMutableTreeNode commonDirNode = createNode(dir, kind,
					allowsChildren);
			// adds this common directory node at the tree
			DefaultMutableTreeNode parentNode = null;
			TreePath parentPath = treeView.getSelectionPath();
			if (parentPath == null) {
				parentNode = rootNode; // if there's no selection. Default
				// to the root node.
			} else {
				parentNode = (DefaultMutableTreeNode) (parentPath
						.getLastPathComponent());
			}
			insertNode(commonDirNode, parentNode);
		}
	}// createDirectory method

	/**
	 * @author vidal
	 * @param String
	 *            parentName
	 * @param String
	 *            parentKind called from RepositoryTreeMouseAdapter.
	 *            processMouseEvent method
	 */
	void registerApplication(String parentName, String parentKind) {
		String appName = "";
		appName = (String) JOptionPane.showInputDialog(frame,
				"Enter the name of the new Application",
				"Register an Application", JOptionPane.PLAIN_MESSAGE, null,
				null, "");
		if (appName != null && appName.length() != 0) {
			String appDirName = parentName + "/" + appName;
			String appDescName = "/AppDescription_appDescFile";
			// updates the application repository
			try {
				ASCTController.getInstance().registerApplication(parentName, appName);
				//creates an appDir node
				boolean allowsChildren = true;
				DefaultMutableTreeNode dirNode = createNode(appDirName,
						kindOfItens.applicationDirectory, allowsChildren);
				// adds this appDir node at the tree
				DefaultMutableTreeNode parentNode = null;
				TreePath parentPath = treeView.getSelectionPath();
				if (parentPath == null) {
					// if there's no selection. Default to the root node.
					parentNode = rootNode;
				} else {
					parentNode = (DefaultMutableTreeNode) (parentPath
							.getLastPathComponent());
				}
				insertNode(dirNode, parentNode);
				// creates an appDesc node
				allowsChildren = false;
				DefaultMutableTreeNode appDescNode = createNode(appDescName,
						kindOfItens.applicationDescriptionFile, allowsChildren);
				// adds this appDesc node at the tree
				insertNode(appDescNode, dirNode);
			} catch (ApplicationRegistrationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DirectoryCreationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidPathNameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}// endif
	}// registerApplication method

	/**
	 * @author vidal
	 * @param String
	 *            appDirName called from RepositoryTreeMouseAdapter.
	 *            processMouseEvent method
	 */
	void showUploadBinaryDialog(String appDirName) {
		// asct.showUploadPanel(this, appDirName);
		final UploadPanel upPanel = new UploadPanel(this, appDirName);
		final JDialog uploadDialog = new JDialog();
		uploadDialog.setTitle("Upload Binary");
		uploadDialog.setModal(true);
		upPanel.cancelButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				uploadDialog.dispose();
			}
		});
		upPanel.submitButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				upPanel.uploadBinary();
				uploadDialog.dispose();
			}
		});
		uploadDialog.setContentPane(upPanel);
		uploadDialog.setLocation(new Point(250, 250));
		uploadDialog.pack();
		uploadDialog.setVisible(true);
	}

	/**
	 * @author vidal
	 * @param BinaryDescription
	 *            binDesc called from UploadPanel. getBinary method
	 */
	void insertBinaryNode(String binaryRemotePath,String platform) {
		String binaryNodeName = binaryRemotePath;
		boolean allowsChildren = false;
		DefaultMutableTreeNode binaryNode = createNode(binaryNodeName,
				kindOfItens.binaryFile, allowsChildren);
		TreeNamePathHolder descNode = (TreeNamePathHolder) binaryNode
				.getUserObject();
		descNode.setPlatform(platform);
		// adds this binary node at the tree
		TreePath parentPath = treeView.getSelectionPath();
		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) (parentPath
				.getLastPathComponent());
		insertNode(binaryNode, parentNode);
	}// insertBinaryNode method

	/**
	 * @author vidal
	 * @param String
	 *            parentName called from RepositoryTreeMouseAdapter.
	 *            processMouseEvent method
	 */
	void unregisterApplication(String appDirName) {
		int returnVal = JOptionPane
				.showConfirmDialog(frame,
						"Do you really want unregister this Application ?",
						"Unregister this Application",
						JOptionPane.OK_CANCEL_OPTION);
		if (returnVal == JOptionPane.OK_OPTION) {
			// updates the application repository
			int lastBarIndex = appDirName.lastIndexOf("/");
			try {
				ASCTController.getInstance().unregisterApplication(appDirName.substring(0,lastBarIndex),appDirName.substring(lastBarIndex+1,appDirName.length()));
				// updates the tree view
				removeNode();
			} catch (ApplicationNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DirectoryNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DirectoryNotEmptyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidPathNameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}// unregisterApplication method

	/**
	 * @author vidal
	 * @param removableDir
	 *            called from RepositoryTreeMouseAdapter. processMouseEvent
	 *            method
	 */
	void removeDirectory(String directoryPath) {
		int returnVal = JOptionPane.showConfirmDialog(frame,
				"Do you really want remove this Directory ?",
				"Remove Directory", JOptionPane.OK_CANCEL_OPTION);
		if (returnVal == JOptionPane.OK_OPTION) {
			// removes this directory from the application repository
			try{
				ASCTController.getInstance().removeDirectory(directoryPath);
				// 	updates the tree view
				removeNode();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}// removeDirectory method

	/**
	 * @author vidal
	 * @param String
	 *            absolutePath
	 * @param String
	 *            platform called from RepositoryTreeMouseAdapter.
	 *            processMouseEvent method
	 */
	void deleteBinary(String absolutePath) {
		int returnVal = JOptionPane.showConfirmDialog(frame,
				"Do you really want delete this Binary file ?",
				"Delete Binary", JOptionPane.OK_CANCEL_OPTION);

		if (returnVal == JOptionPane.OK_OPTION) {
			// deletes this binary file from the repository
			int lastIndexOfBar = absolutePath.lastIndexOf("/");
			int prevLastIndexOfBar = absolutePath.substring(0,lastIndexOfBar).lastIndexOf("/");
			
			try {
				ASCTController.getInstance().deleteBinary(absolutePath.substring(0,prevLastIndexOfBar),
							absolutePath.substring(prevLastIndexOfBar+1,lastIndexOfBar),
							absolutePath.substring(lastIndexOfBar+1));
				// updates the tree view
				removeNode();
			} catch (ApplicationNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DirectoryNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BinaryNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidPathNameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}// deleteBinary method

	/**
	 * @author vidal
	 * @param String
	 *            absolutePath called from RepositoryTreeMouseAdapter.
	 *            processMouseEvent method
	 */
	void executeApplication(String absolutePath, String binaries) {
		String applicationBasePath=absolutePath.substring(0, absolutePath
				.lastIndexOf("/" )+ 1);
		String applicationName = absolutePath.substring(absolutePath
				.lastIndexOf("/") + 1);
		Container rootContainer = getTopLevelAncestor();
		if (rootContainer instanceof Asct) {
			RequestExecutionDialog executeDialog = new RequestExecutionDialog(
					applicationName, applicationBasePath, binaries, ((Asct) rootContainer)
							.getExecutingApplicationsPanel());
			executeDialog.setVisible(true);
		}
	}

	/**
	 * @author vidal
	 * @param String
	 *            absolutePath called from RepositoryTreeMouseAdapter.
	 *            processMouseEvent method
	 */
	void executeBinary(String absolutePath, String platform) {
		String absoluteBinarynPath=absolutePath.substring(0, absolutePath
				.lastIndexOf("/") + 1 );
		String absoluteApplicationPath = absoluteBinarynPath.substring(0,absoluteBinarynPath.lastIndexOf("/"));
		String applicationBasePath = absoluteApplicationPath.substring(0,absoluteApplicationPath.lastIndexOf("/")+1);
		String binaryName = absolutePath.substring(absolutePath
				.lastIndexOf("/") + 1);
		String applicationName = absoluteApplicationPath.substring(absoluteApplicationPath.lastIndexOf("/") + 1);
		Container rootContainer = getTopLevelAncestor();
		if (rootContainer instanceof Asct) {
			RequestExecutionDialog executeDialog = new RequestExecutionDialog(
					applicationName, applicationBasePath, binaryName, ((Asct) rootContainer)
							.getExecutingApplicationsPanel());
			executeDialog.setVisible(true);
		}
	}

	/**
	 * @author vidal
	 * @param appDescName
	 *            called from RepositoryTreeMouseAdapter. processMouseEvent
	 *            method
	 */
	void editFile(String appDescName) {
		JOptionPane
				.showMessageDialog(frame, "under construction. Be patient !");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		JComboBox cb = (JComboBox) e.getSource();
		String osName = (String) cb.getSelectedItem();
	}
}// class RepositoryPanel

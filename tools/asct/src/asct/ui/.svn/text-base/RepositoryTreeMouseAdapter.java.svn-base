package asct.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import dataTypes.kindOfItens;

class RepositoryTreeMouseAdapter extends MouseAdapter {
	private RepositoryTree treeView;
	private DefaultMutableTreeNode treeRoot;

	/**
	 * @author Andrei constructor
	 */
	RepositoryTreeMouseAdapter(RepositoryTree aTreeView, DefaultMutableTreeNode atreeRoot) {
		treeView = aTreeView;
		treeRoot = atreeRoot;
	}

	/**
	 * @author vidal
	 * @param abspath
	 */
	private String getmyPath(Object[] abspath) {
		int i = 0;
		String path = abspath[i].toString();
		if (abspath.length == 1)
			return path;
		for (i = 1; i < abspath.length - 1; i++) {
			path = path + "/" + abspath[i].toString();
		}
		return path + "/" + abspath[i].toString();
	}// getmyPath method

	/**
	 * @author vidal
	 * @param abspath
	 */
	private String getmyParent(Object[] abspath) {
		int i = 0;
		String path = abspath[i].toString();
		for (i = 1; i < abspath.length - 1; i++) {
			path = path + "/" + abspath[i].toString();
		}
		return path + "/";
	}// getmyParent method

	/**
	 * @author vidal
	 * @param MouseEvent
	 *            e
	 */
	private void processMouseEvent(MouseEvent e) {
		if (e.getComponent().getClass() == RepositoryTree.class) {
			TreePath selPath = treeView.getPathForLocation(e.getX(), e.getY());
			if (selPath != null) {
				if (selPath.getLastPathComponent().getClass() == DefaultMutableTreeNode.class) {
					treeView.setSelectionPath(selPath); // it sets the clicked
                                       					// node as selected
					final TreeNamePathHolder selectedPathHolder = ((TreeNamePathHolder) ((DefaultMutableTreeNode) selPath
							.getLastPathComponent()).getUserObject());

					if (selectedPathHolder.isRoot()) {
						final String absolutePath = selectedPathHolder
								.getFilePath();

						JMenuItem registerAppItem = new JMenuItem(
								"Register Application");
						registerAppItem
								.setToolTipText("Register a new application under this selected directory");
						registerAppItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								treeView.getMyPanel().registerApplication(
										absolutePath, "_root");
							}
						});
						JMenuItem createDirItem = new JMenuItem(
								"Create Directory");
						createDirItem
								.setToolTipText("Create a new directory under this selected directory");
						createDirItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								treeView.getMyPanel().createDirectory(
										absolutePath);
							}
						});

						JPopupMenu popup = new JPopupMenu();
						popup.add(registerAppItem);
						popup.add(createDirItem);
						popup.setInvoker(e.getComponent());
						popup.show(e.getComponent(), e.getX(), e.getY());
					} else if (selectedPathHolder.isApp()) {
						final String absolutePath = selectedPathHolder
								.getFilePath();
						treeView.expandPath(selPath);
						JMenuItem executeAppItem = new JMenuItem(
								"Execute Application");
						executeAppItem
								.setToolTipText("Execute  any possible application binary");
						Enumeration allNodes = treeRoot.breadthFirstEnumeration();
						DefaultMutableTreeNode node = (DefaultMutableTreeNode)allNodes.nextElement();
						while (allNodes.hasMoreElements() && !((TreeNamePathHolder)node.getUserObject()).getFilePath().equals(absolutePath)){
							node = (DefaultMutableTreeNode) allNodes.nextElement();
						}
						final String binaries = getApplicationBinaries(node);
						executeAppItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								treeView.getMyPanel().executeApplication(absolutePath, binaries);
							}
						});
						JMenuItem uploadBinaryItem = new JMenuItem(
								"Upload Binary");
						uploadBinaryItem
								.setToolTipText("Upload a binary code for this application");
						uploadBinaryItem
								.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										treeView.getMyPanel()
												.showUploadBinaryDialog(
														absolutePath);
									}
								});
						JMenuItem registerAppItem = new JMenuItem(
								"Register Application");
						registerAppItem
								.setToolTipText("Register a new application at the Application Repository");
						registerAppItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								treeView.getMyPanel().registerApplication(
										absolutePath, "_appDir");
							}
						});
						JMenuItem createDirItem = new JMenuItem(
								"Create Directory");
						createDirItem
								.setToolTipText("Create a new directory under this selected directory");
						createDirItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								treeView.getMyPanel().createDirectory(
										absolutePath);
							}
						});
						JMenuItem unregisterAppItem = new JMenuItem(
								"Unregister Application");
						unregisterAppItem
								.setToolTipText("Unregister an application at the Application Repository");
						unregisterAppItem
								.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										treeView.getMyPanel()
												.unregisterApplication(
														absolutePath);
									}
								});
						JPopupMenu popup = new JPopupMenu();
						popup.add(uploadBinaryItem);
						popup.add(executeAppItem);
						popup.add(registerAppItem);
						popup.add(createDirItem);
						popup.add(unregisterAppItem);
						popup.setInvoker(e.getComponent());
						popup.show(e.getComponent(), e.getX(), e.getY());
					} else if (selectedPathHolder.isCommonDir()) {
						final String absolutePath = selectedPathHolder
								.getFilePath();

						JMenuItem registerAppItem = new JMenuItem(
								"Register Application");
						registerAppItem
								.setToolTipText("Register a new application at the Application Repository");
						registerAppItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								treeView.getMyPanel().registerApplication(
										absolutePath, "_commonDir");
							}
						});
						JMenuItem createDirItem = new JMenuItem(
								"Create Directory");
						createDirItem
								.setToolTipText("Create a directory under the current directory");
						createDirItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								treeView.getMyPanel().createDirectory(
										absolutePath);
							}
						});
						JMenuItem removeDirItem = new JMenuItem(
								"Remove this Directory");
						removeDirItem
								.setToolTipText("Remove this selected directory");
						removeDirItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								treeView.getMyPanel().removeDirectory(
										absolutePath);
							}
						});
						JPopupMenu popup = new JPopupMenu();
						popup.add(registerAppItem);
						popup.add(createDirItem);
						popup.add(removeDirItem);
						popup.setInvoker(e.getComponent());
						popup.show(e.getComponent(), e.getX(), e.getY());
					} else if (selectedPathHolder.isBinary()) {
						final String absolutePath = selectedPathHolder
								.getFilePath();

						JMenuItem executeBinaryItem = new JMenuItem("Execute");
						executeBinaryItem
								.setToolTipText("Execute this selected application binary");
						executeBinaryItem
								.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										treeView.getMyPanel().executeBinary(
												absolutePath,
												selectedPathHolder
														.getPlatform());
									}
								});
						JMenuItem deleteBinaryItem = new JMenuItem("Delete");
						deleteBinaryItem
								.setToolTipText("Delete this selected application binary");
						deleteBinaryItem
								.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										treeView.getMyPanel().deleteBinary(
												absolutePath);
									}
								});
						JPopupMenu popup = new JPopupMenu();
						popup.add(executeBinaryItem);
						popup.add(deleteBinaryItem);
						popup.setInvoker(e.getComponent());
						popup.show(e.getComponent(), e.getX(), e.getY());
					}
				}
			}
		}
	}// processMouseEvent method

	private String getApplicationBinaries(DefaultMutableTreeNode node) {
		String binariesList =  new String();
		for(int i=0; i< node.getChildCount();++i){
			if(((TreeNamePathHolder)((DefaultMutableTreeNode) node.getChildAt(i)).getUserObject()).isBinary()){
				binariesList += ((TreeNamePathHolder)((DefaultMutableTreeNode) node.getChildAt(i)).getUserObject()).getFileName() + " ";
			}
		}
		return binariesList;
	}

	public void mousePressed(MouseEvent e) {
		processMouseEvent(e);
	}

	public void mouseClicked(MouseEvent e) {
		processMouseEvent(e);
	}

	public void mouseReleased(MouseEvent e) {
		processMouseEvent(e);
	}

}// class RepositoryTreeMouseAdapter

package asct.ui;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

class RepositoryTree extends JTree {

	private RepositoryPanel myPanel;

	/**
	 * @author vidal
	 */
	public RepositoryTree(DefaultMutableTreeNode rootNode,
			RepositoryPanel myPanel) {
		super(rootNode, true);
		this.myPanel = myPanel;
	}

	public RepositoryPanel getMyPanel() {
		return myPanel;
	}

}// class RepositoryTree

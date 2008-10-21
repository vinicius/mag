package asct.ui;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;

class RepositoryTreeModelListener implements TreeModelListener {
	public void treeNodesChanged(TreeModelEvent e) {
		DefaultMutableTreeNode node;
		node = (DefaultMutableTreeNode) (e.getTreePath().getLastPathComponent());
		/*
		 * If the event lists children, then the changed node is the child of
		 * the node we've already gotten. Otherwise, the changed node and the
		 * specified node are the same.
		 */
		try {
			int index = e.getChildIndices()[0];
			node = (DefaultMutableTreeNode) (node.getChildAt(index));
		} catch (NullPointerException exc) {
		}
	}// treeNodesChanged method

	public void treeNodesInserted(TreeModelEvent e) {
		DefaultMutableTreeNode node;
		node = (DefaultMutableTreeNode) (e.getTreePath().getLastPathComponent());
		/*
		 * If the event lists children, then the changed node is the child of
		 * the node we've already gotten. Otherwise, the changed node and the
		 * specified node are the same.
		 */
		try {
			int index = e.getChildIndices()[0];
			node = (DefaultMutableTreeNode) (node.getChildAt(index));
		} catch (NullPointerException exc) {
		}
	}// treeNodesInserted method

	public void treeNodesRemoved(TreeModelEvent e) {
	}

	public void treeStructureChanged(TreeModelEvent e) {
	}
} // class RepositoryTreeModelListener

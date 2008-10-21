package asct.ui;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

class RepositoryRenderer extends DefaultTreeCellRenderer {
	private Icon rootDirIcon;

	private Icon appDirIcon;

	private Icon appDescIcon;

	private Icon commonDirIcon;

	private Icon commonFileIcon;

	private Icon binaryIcon;

	public RepositoryRenderer(Icon[] images) {
		super();
		rootDirIcon = images[0];
		appDirIcon = images[1];
		appDescIcon = images[2];
		commonDirIcon = images[3];
		commonFileIcon = images[4];
		binaryIcon = images[5];
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {

		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
				row, hasFocus);
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		if (isBinaryNode(node)) {
			setIcon(binaryIcon);
		} else if (isRootNode(node)) {
			setIcon(rootDirIcon);
		} else if (isAppDirNode(node)) {
			setIcon(appDirIcon);
		} else if (isAppDescNode(node)) {
			setIcon(appDescIcon);
		} else if (isCommonDirNode(node)) {
			setIcon(commonDirIcon);
		} else {
			setIcon(commonFileIcon);
		}
		return this;
	}// getTreeCellRendererComponent method

	protected boolean isCommonDirNode(DefaultMutableTreeNode node) {
		if (node.getUserObject().getClass() == TreeNamePathHolder.class) {
			TreeNamePathHolder pathHolder = (TreeNamePathHolder) (node
					.getUserObject());
			return (pathHolder.isCommonDir());
		}
		return false;
	}// isCommonDirNode method

	// protected boolean isCommonFileNode(DefaultMutableTreeNode node) {
	// if (node.getUserObject().getClass() == TreeNamePathHolder.class) {
	// TreeNamePathHolder pathHolder = (TreeNamePathHolder) (node
	// .getUserObject());
	// return (pathHolder.isCommonFile());
	// }
	// return false;
	// }// isCommonFileNode method

	protected boolean isBinaryNode(DefaultMutableTreeNode node) {
		if (node.getUserObject().getClass() == TreeNamePathHolder.class) {
			TreeNamePathHolder pathHolder = (TreeNamePathHolder) (node
					.getUserObject());
			return (pathHolder.isBinary());
		}
		return false;
	}// isBinaryNode method

	protected boolean isRootNode(DefaultMutableTreeNode node) {
		if (node.getUserObject().getClass() == TreeNamePathHolder.class) {
			TreeNamePathHolder pathHolder = (TreeNamePathHolder) (node
					.getUserObject());
			return (pathHolder.isRoot());
		}
		return false;
	}// isRootNode method

	protected boolean isAppDescNode(DefaultMutableTreeNode node) {
		if (node.getUserObject().getClass() == TreeNamePathHolder.class) {
			TreeNamePathHolder pathHolder = (TreeNamePathHolder) (node
					.getUserObject());
			return (pathHolder.isAppDesc());
		}
		return false;
	}// isAppDescNode method

	protected boolean isAppDirNode(DefaultMutableTreeNode node) {
		if (node.getUserObject().getClass() == TreeNamePathHolder.class) {
			TreeNamePathHolder pathHolder = (TreeNamePathHolder) (node
					.getUserObject());
			return (pathHolder.isApp());
		}
		return false;
	}// isAppDirNode method

}// class MyRenderer

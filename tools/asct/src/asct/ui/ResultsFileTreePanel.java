package asct.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 * The panel that will show the results file tree of an application
 * @author Andrei
 * @author Ricardo Andrade
 */

  class ResultsFileTreePanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private DefaultMutableTreeNode rootNode;
    private final JTree treeView;
    private ResultsDisplayPanel displayPanel;
    
    //-------------------------------------------------------------------------
    /**
     * Constructs a ResultsFileTreePanel with the path to the application results
     * @param path to the application results
     */
    public ResultsFileTreePanel(String path){

      displayPanel = new ResultsDisplayPanel();
      rootNode = createTree(new File(path));
      treeView = new JTree(rootNode);
      treeView.getSelectionModel().
       setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
      treeView.setRootVisible(false);

      treeView.addTreeSelectionListener(
        new TreeSelectionListener(){
          public void valueChanged(TreeSelectionEvent e) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                           treeView.getLastSelectedPathComponent();
            if(node == null)
              return;
            if(node.isLeaf())
             if(node.getUserObject().getClass() == NodeContents.class)
              displayPanel.loadText(((NodeContents) node.getUserObject()).path());
          }
        });

      treeView.addMouseListener (new ResultsTreeMouseAdapter(treeView));

      JScrollPane treeScroll = new JScrollPane(treeView);
      treeScroll.setPreferredSize(new Dimension(200,0));

      JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
      splitPane.setLeftComponent(treeScroll);
      splitPane.setRightComponent(displayPanel);

      this.setLayout(new BorderLayout());
      this.add(splitPane, BorderLayout.CENTER);

    }

    //-------------------------------------------------------------------------
    private DefaultMutableTreeNode createTree(File rootFile){

      //Base
      if(rootFile.isFile()){
        return new DefaultMutableTreeNode(
                 new NodeContents(rootFile.getName(), rootFile.getPath()));
      }
      //Recursion
      else{
        File [] rootContents = rootFile.listFiles();
        DefaultMutableTreeNode rootNode  = null;
        if(rootContents.length == 0)
          rootNode =
           new DefaultMutableTreeNode("Node "
                                      + (Integer.parseInt(rootFile.getName()) + 1)
                                      + ": No Output Files");
        else
          rootNode =
           new DefaultMutableTreeNode("Node " + (Integer.parseInt(rootFile.getName()) + 1));
        for(int i = 0; i < rootContents.length; i++)
          if(rootContents[i].isFile() || rootContents[i].isDirectory())
            rootNode.add(createTree(rootContents[i]));
          return rootNode;
      }

    }
  }//class

  //=========================================================================
  /**
   * This class represents the contents of a node in the tree
   * located inside the ResultFileTreePanel
   * 
   * @author Andrei 
   * @author Ricardo Andrade
   */
  class NodeContents{

    private String id_;
    private String path_;

    NodeContents(String id, String path){
      id_ = id;
      path_ = path;
    }

    public String path(){ return path_; }
    public String toString(){ return id_; }

  }//class

  //=========================================================================
  /**
   * A mouse adapter to the tree located inside the ResultFileTreePanel
   * 
   * @author Andrei 
   * @author Ricardo Andrade
   */
  class ResultsTreeMouseAdapter extends MouseAdapter{

    private JTree treeView;

    ResultsTreeMouseAdapter(JTree aTreeView){ treeView = aTreeView; }

    //-----------------------------------------------------------------------
    public void mousePressed (MouseEvent e) {
      processMouseEvent(e);
    }

    //-----------------------------------------------------------------------
    public void mouseReleased (MouseEvent e) {
        processMouseEvent(e);

      }

    //-----------------------------------------------------------------------
    private void processMouseEvent(MouseEvent e){
       if (e.isPopupTrigger())
       if(e.getComponent().getClass() == JTree.class){
           final TreePath selPath = treeView.getPathForLocation(e.getX(), e.getY());
           if(selPath != null)
             if(selPath.getLastPathComponent().getClass() == DefaultMutableTreeNode.class)
               if(((DefaultMutableTreeNode)selPath.getLastPathComponent()).isLeaf()){
                 JPopupMenu popup = new JPopupMenu ();
                 JMenuItem saveItem = new JMenuItem ("Save");
                 JMenuItem deleteItem = new JMenuItem ("Delete");
                 saveItem.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e){
                     DefaultMutableTreeNode selectedNode =
                       (DefaultMutableTreeNode) selPath.getLastPathComponent();
                      saveFile(((NodeContents)selectedNode.getUserObject()).path());
                    }
                 });
                 deleteItem.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e){
                      DefaultMutableTreeNode selectedNode =
                       (DefaultMutableTreeNode) selPath.getLastPathComponent();
                      deleteFile(selectedNode);
                    }
                 });
                 popup.add (saveItem);
                 popup.add (deleteItem);
                 popup.setInvoker (e.getComponent());
                 popup.show (e.getComponent(), e.getX(), e.getY());
               }
        }
    }//Method


    //-----------------------------------------------------------------------
    private void saveFile(String srcPath){
      JFileChooser fileChooser = new JFileChooser (".");
      fileChooser.setDialogTitle("Save File");
      fileChooser.setApproveButtonText("Save");
      if (fileChooser.showOpenDialog(treeView.getTopLevelAncestor()) == JFileChooser.APPROVE_OPTION){
        try {
          FileChannel srcChannel = new FileInputStream(srcPath).getChannel();
          FileChannel dstChannel = new FileOutputStream(fileChooser.getSelectedFile()).getChannel();
          dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
          srcChannel.close();
          dstChannel.close();
        }
        catch (IOException e) {
          System.out.println("FileTreePanel::Error Saving file");
          e.printStackTrace();
        }
      }
    }

    //-----------------------------------------------------------------------
    private void deleteFile(DefaultMutableTreeNode delNode){
      Object [] options = new Object [] {"Delete",  "Cancel"};
      int selection = JOptionPane.showOptionDialog(treeView.getTopLevelAncestor(),
                                   "Do you really want to delete the file: \n" +
                                   ((NodeContents)delNode.getUserObject()).toString() + " ?\n",
                                   "Confirm Deletion",
                                   JOptionPane.YES_NO_OPTION,
                                   JOptionPane.WARNING_MESSAGE,
                                   null,//default icon
                                   options,
                                   options[1]);
      if(selection == JOptionPane.YES_OPTION){
        File delFile = new File(((NodeContents)delNode.getUserObject()).path());
        delFile.delete();
        if(delNode.getParent().getChildCount() == 1)
          ((DefaultTreeModel)treeView.getModel())
              .removeNodeFromParent((DefaultMutableTreeNode) delNode.getParent());
        ((DefaultTreeModel)treeView.getModel()).removeNodeFromParent(delNode);
        //FIXME: NEED to recurse if depth > 2
      }
    }//method

  }//class



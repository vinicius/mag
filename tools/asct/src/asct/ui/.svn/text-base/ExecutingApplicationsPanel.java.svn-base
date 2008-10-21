package asct.ui;

import javax.swing.JLabel;
import javax.swing.ListCellRenderer;
import javax.swing.DefaultListCellRenderer;

//For menus
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import javax.swing.JDialog;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.ListModel;
import javax.swing.JList;

import dataTypes.ExecutionRequestId;

import asct.core.ApplicationControlFacade;
import asct.shared.ApplicationState;
import asct.shared.ExecutionRequestStatus;
import asct.shared.IExecutionListener;

//
import java.awt.Color;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;

class ExecutingApplicationsPanel extends ListPanel implements
		IExecutionListener {

	private static final long serialVersionUID = 1900468874804010744L;

	ExecutingApplicationsPanel(String listName) {
		super(listName);
		addListMouseListener(new ExecutingAppsMouseAdapter());
	}

	// -----------------------------------------------------------------------
	public void removeItem(Object obj) {
		synchronized (applicationsListModel) {
			applicationsListModel.removeElement(obj);
		}
	}

	public void updateStatus(ExecutionRequestId requestId, ApplicationState applicationState) {		
		int requestCont = applicationsListModel.getSize();
		for (int i=0; i < requestCont; ++i){
			ExecutionRequestStatus requestExamined = (ExecutionRequestStatus) applicationsListModel.elementAt(i);
			if (requestExamined.getRequestId().equals(requestId.requestId)){
				requestExamined.setApplicationState(applicationState);
//				 TODO Change the color of the item in the list to reflect the state change
				System.out.println("Updating status of " + requestId.requestId + " to " + applicationState.toString());
			}
		}
	}
}// class

// =========================================================================
class ExecutingAppsMouseAdapter extends MouseAdapter {

	ExecutingAppsMouseAdapter() {
	}

	// -----------------------------------------------------------------------
	public void mousePressed(MouseEvent e) {
		processMouseEvent(e);
	}

	// -----------------------------------------------------------------------
	public void mouseReleased(MouseEvent e) {
		processMouseEvent(e);

	}

	// -----------------------------------------------------------------------
	private void processMouseEvent(MouseEvent e) {
		if (!e.isPopupTrigger())
			return;
		final JList execList = (JList) e.getComponent();
		final ListModel listModel = execList.getModel();
		if (listModel.getSize() == 0)
			return;
		final ExecutionRequestStatus selectedExecution = (ExecutionRequestStatus) listModel
				.getElementAt(execList.locationToIndex(e.getPoint()));
		execList.setSelectedIndex(execList.locationToIndex(e.getPoint()));
		if (selectedExecution == null)
			return;

		JPopupMenu popup = new JPopupMenu();
		if (selectedExecution.getApplicationState().equals(
				ApplicationState.TERMINATED)
				|| selectedExecution.getApplicationState().equals(
						ApplicationState.FINISHED)) {
			if (selectedExecution.getApplicationState().equals(
					ApplicationState.FINISHED)) {
				JMenuItem viewItem = new JMenuItem("View Results");
				viewItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ASCTController.getInstance().getApplicationResults(((ExecutionRequestStatus)selectedExecution).getRequestId());
						ResultsFileTreePanel viewPanel = new ResultsFileTreePanel(ASCTController.getOutputDirectory()+((ExecutionRequestStatus)selectedExecution).getRequestId());
						final JDialog viewDialog = new JDialog(new Frame(),
                                 	"View Results: " +  selectedExecution.getApplicationName(), true);
                                 	viewDialog.setLocation(new Point(250, 250));
                                 	viewDialog.setContentPane(viewPanel);
                                 	viewDialog.pack();
                                 	viewDialog.setVisible(true);
					}
				});
				popup.add(viewItem);
			}
			JMenuItem removeItem = new JMenuItem("Remove Application");
			removeItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
				}
			});
			popup.add(removeItem);

		} else if (selectedExecution.getApplicationState().equals(
				ApplicationState.EXECUTING)) {
			JMenuItem killAppItem = new JMenuItem("Kill Application");
			killAppItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//TODO: Correct this!
					//asctFacade.killApplication();
				ASCTController.getInstance().killApplication(selectedExecution.getRequestId());

				}
			});

			popup.add(killAppItem);
		}
		popup.setInvoker(e.getComponent());
		popup.show(e.getComponent(), e.getX(), e.getY());
	}// Method
}// class


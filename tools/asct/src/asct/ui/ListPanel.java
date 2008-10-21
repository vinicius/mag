package asct.ui;

import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import java.awt.event.MouseAdapter;

//Class----------------------------------------------------------------------

/**
 * RegisteredApplicationsPanel - Holds names of the applications registered in
 * the Application Repository.
 * 
 * @author Andrei Goldchleger
 * @date February/2004
 * 
 */
class ListPanel extends JPanel {

	// Fields------------------------------------------------------------------
	private JScrollPane applicationsScrollPane;

	protected DefaultListModel applicationsListModel;

	protected ItemTooltippedList applicationsList;

	// Costructor--------------------------------------------------------------
	ListPanel(String listName) {

		// Botton Panel
		this.applicationsScrollPane = new JScrollPane();
		this.applicationsListModel = new DefaultListModel();
		this.applicationsList = new ItemTooltippedList(applicationsListModel);
		applicationsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		applicationsScrollPane.getViewport().add(applicationsList, null);

		// Final assembly
		this.setLayout(new BorderLayout());
		// this.add(topPanel, BorderLayout.NORTH);
		this.add(applicationsScrollPane, BorderLayout.CENTER);
		this.setBorder(new TitledBorder(listName));

	}

	// -------------------------------------------------------------------------
	public void addItem(Object item) {
		synchronized (applicationsListModel) {
			applicationsListModel.addElement(item);
		}
	}

	// -------------------------------------------------------------------------
	public Object getSelectedItem() {

		if (applicationsList.getSelectedIndex() >= 0) {
			synchronized (applicationsListModel) {
				return applicationsListModel.get(applicationsList
						.getSelectedIndex());
			}
		}
		return null;
	}

	// -------------------------------------------------------------------------
	public void addListMouseListener(MouseAdapter adapter) {
		applicationsList.addMouseListener(adapter);
	}

}// class


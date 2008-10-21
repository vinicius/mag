package asct.ui;

import javax.swing.JList;
import javax.swing.ListModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

//=============================================================================
class ItemTooltippedList extends JList {


	ItemTooltippedList(ListModel listModel) {
		super(listModel);
		setToolTipText("");


	}

	// -------------------------------------------------------------------------
	public String getToolTipText(MouseEvent e) {

		if (locationToIndex(e.getPoint()) > -1) {
			//ListModel lm = (ListModel) getModel();
			return new String("Right click for options");
			/*((TooltipAble) lm
					.getElementAt(locationToIndex(e.getPoint())))
					.getToolTipText();*/
		} else
			return null;
	}

}// class


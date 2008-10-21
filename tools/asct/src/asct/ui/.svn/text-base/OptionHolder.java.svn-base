package asct.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

//Class----------------------------------------------------------------------
/**
 * OptionHolder - Small aPanel that holds a JLabel and a JTextField.
 * 
 * @author Andrei Goldchleger
 * @date February/2004
 * 
 */
class OptionHolder extends JPanel {

	// Fields------------------------------------------------------------------
	private JTextField option;

	/** < The option to be captured by this OptinHolder */

	// Costructor--------------------------------------------------------------
	/**
	 * Creates a new OptionHoder object.
	 * 
	 * @param optionName -
	 *            The name of the option being created
	 */
	OptionHolder(String optionName) {

		JPanel intraPanel = new JPanel(new BorderLayout());
		JLabel optionLabel = new JLabel(optionName);
		intraPanel.add(optionLabel, BorderLayout.WEST);
		option = new JTextField();
		intraPanel.add(option, BorderLayout.CENTER);
		this.setLayout(new BorderLayout());
		this.add(intraPanel, BorderLayout.NORTH);

	}

	// Methods-----------------------------------------------------------------
	/**
	 * Returns the option Value.
	 */
	public String getValue() {
		return option.getText();
	}

	/**
	 * Sets the option Value.
	 * 
	 * @param value --
	 *            New value for the option holder
	 */
	public void setValue(String value) {
		option.setText(value);
	}

}// class

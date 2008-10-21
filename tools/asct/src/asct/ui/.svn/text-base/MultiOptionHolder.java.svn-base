package asct.ui;

import java.awt.GridLayout;
import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.util.HashMap;

//Class----------------------------------------------------------------------
/**
 * OptionHolder - Small aPanel that holds a JLabel and a JTextField.
 * 
 * @author Andrei Goldchleger
 * @date February/2004
 * 
 */
class MultiOptionHolder extends JPanel {

	// Fields------------------------------------------------------------------

	private HashMap options;

	private JPanel labels;

	private JPanel inputFields;

	// Costructor--------------------------------------------------------------
	/**
	 * Creates a new OptionHoder object.
	 * 
	 * 
	 */
	MultiOptionHolder() {

		labels = new JPanel(new GridLayout(0, 1));
		inputFields = new JPanel(new GridLayout(0, 1));
		options = new HashMap();

		setLayout(new BorderLayout());
		this.add(labels, BorderLayout.WEST);
		this.add(inputFields, BorderLayout.CENTER);

	}

	// -----------------------------------------------------------------------
	public void addOption(String name) {

		JTextField newOpt = new JTextField();
		options.put(name, newOpt);
		labels.add(new JLabel(name));
		inputFields.add(newOpt);

	}

	// -----------------------------------------------------------------------
	public void addOptionAt(String name, int index) {

		JTextField tmpOption = (JTextField) options.get(name);
		if (tmpOption == null) {
			JTextField newOpt = new JTextField();
			options.put(name, newOpt);
			labels.add(new JLabel(name));
			inputFields.add(newOpt, index);
			validate();
		}

	}

	// Methods-----------------------------------------------------------------
	/**
	 * Returns the option Value.
	 * 
	 * @param optionName --
	 *            The name of the option to be returned
	 */
	public String getValue(String optionName) {

		JTextField tmpOption = (JTextField) options.get(optionName);
		if (tmpOption != null)
			return tmpOption.getText();
		else
			return "";

	}

	/**
	 * Sets the option Value.
	 * 
	 * @param optionName --
	 *            The name of the option to be set
	 * @param value --
	 *            New value for the option holder
	 */
	public void setValue(String optionName, String value) {

		JTextField tmpOption = (JTextField) options.get(optionName);
		if (tmpOption != null)
			tmpOption.setText(value);

	}

	public void toggleField(String optionName, boolean value) {
		JTextField tmpOption = (JTextField) options.get(optionName);
		if (tmpOption != null)
			tmpOption.setEnabled(value);
	}

}// class

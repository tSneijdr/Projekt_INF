package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ModePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4426179000569155819L;

	public ModePanel() {
		JComboBox<String> selection = new JComboBox<String>();
		JLabel lbl = new JLabel(" Standard ");
		JButton btn = new JButton("Change");

		for (Mode m : Mode.values()) {
			selection.addItem(m.name());
		}
		selection.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String text = (String) selection.getSelectedItem();
				text = Mode.valueOf(text).getDescription();
				text = " " + text + " ";

				lbl.setText(text);
			}

		});

		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}

		});

		this.add(selection);
		this.add(lbl);
		this.add(btn);
	}

}

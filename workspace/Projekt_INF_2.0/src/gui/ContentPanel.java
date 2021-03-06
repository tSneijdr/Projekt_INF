package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import core.Mode;

/**
 * Enthält den eigentlichen Inhalt des Fenster sowie eine Combobox zum auswählen
 * der Art des Inhalts
 * 
 * @author tobi
 *
 */
public class ContentPanel extends JPanel {
	private static final long serialVersionUID = -4426179000569155819L;
	private final MainFrame PARENT;
	private JPanel content;

	/**
	 * Konstruktor, erhält als Parameter nur einen Verweis auf sein
	 * Parent-Element
	 * 
	 * @param parent
	 */
	public ContentPanel(MainFrame parent) {
		this.PARENT = parent;

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
				Mode m = Mode.valueOf(text);
				text = m.getDescription();
				text = " " + text + " ";

				lbl.setText(text);

				content = PARENT.getContent(m);
			}

		});

		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}

		});
		this.content = PARENT.getContent(Mode.values()[0]);

		this.setLayout(new BorderLayout());

		this.add(this.content, BorderLayout.CENTER);
		{

			JPanel subpanel = new JPanel();

			subpanel.setLayout(new GridLayout(1, 0));
			subpanel.add(selection);
			subpanel.add(lbl);
			subpanel.add(btn);

			this.add(subpanel, BorderLayout.SOUTH);
		}
	}
}

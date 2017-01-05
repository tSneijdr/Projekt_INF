package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SelectionPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1394306799106528139L;

	private final HashSet<RecordDisplaySettings> ALL_SETTINGS = new HashSet<RecordDisplaySettings>();

	public SelectionPanel() {
		this.setLayout(new GridLayout(0, 1));
		this.setMaximumSize(new Dimension(10, 10));

	}

	public void addRecordDisplaySettings(RecordDisplaySettings s) {
		// allRecords.add(s);
		if (!ALL_SETTINGS.contains(s)) {
			ALL_SETTINGS.add(s);
			this.add(new Row(s));
		}
	}

	private class Row extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = -5526203096078064341L;

		private final RecordDisplaySettings SET;
		private final JCheckBox box;

		public Row(RecordDisplaySettings set) {
			this.SET = set;

			box = new JCheckBox("Test");
			box.setSelected(SET.active);
			box.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					SET.active = box.isSelected();
				}

			});

			this.add(new JCheckBox());

			JLabel lbl = new JLabel(SET.RECORD.TITLE);
			this.add(lbl);

			JButton btn = new JButton();
			btn.setBackground(SET.color);
			btn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					JFrame frame = new JFrame();
					frame.setAlwaysOnTop(true);
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

					JPanel panelColor = new JPanel();

					JColorChooser cc = new JColorChooser();
					panelColor.add(cc);

					JButton btnColor = new JButton("Change");
					btnColor.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							SET.color = cc.getColor();
							btn.setBackground(SET.color);
							frame.dispose();
						}

					});
					panelColor.add(btnColor);

					frame.add(panelColor);
					frame.pack();
					frame.setVisible(true);
				}

			});

			this.add(btn);
		}
	}
}

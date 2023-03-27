package corp.dbapp.client.gui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JTable;

@SuppressWarnings("serial")
public abstract class Screen extends JPanel {

	public Screen() {
		setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		setLayout(new BorderLayout());
	}

}

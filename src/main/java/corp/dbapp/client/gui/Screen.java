package corp.dbapp.client.gui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JTable;

@SuppressWarnings("serial")
public abstract class Screen extends JPanel {

	protected Component top = new JPanel();
	protected Component bottom = new JPanel();
	protected Component left = new JPanel();
	protected Component right = new JPanel();
	protected Component center = new JPanel();
	
	public Screen() {
		setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		setLayout(new BorderLayout());
		
		top.setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		bottom.setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		left.setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		right.setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		center.setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
	}
	
	public void init() {
		add(top, BorderLayout.NORTH);
		add(bottom, BorderLayout.SOUTH);
		add(left, BorderLayout.WEST);
		add(right, BorderLayout.EAST);
		add(center, BorderLayout.CENTER);
	}
	
}

package corp.dbapp.client.gui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class Screen extends JPanel {

	private JPanel top = new JPanel();
	private JPanel bottom = new JPanel();
	private JPanel left = new JPanel();
	private JPanel right = new JPanel();
	private JPanel center = new JPanel();

	public Screen() {
		setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		setLayout(new BorderLayout());

		top.setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		bottom.setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		left.setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		right.setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		center.setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);

		add(top, BorderLayout.NORTH);
		add(bottom, BorderLayout.SOUTH);
		add(left, BorderLayout.WEST);
		add(right, BorderLayout.EAST);
		add(center, BorderLayout.CENTER);
	}

	public void addTopPanel(Component top) {
		this.top.add(top);
	}

	public void addBottomPanel(Component bottom) {
		this.bottom.add(bottom);
	}

	public void addLeftPanel(Component left) {
		this.left.add(left);
	}

	public void addRightPanel(Component right) {
		this.right.add(right);
	}

	public void addCenterPanel(Component center) {
		this.center.add(center);
	}

}

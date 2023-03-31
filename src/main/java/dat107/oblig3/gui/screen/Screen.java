package dat107.oblig3.gui.screen;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import dat107.oblig3.gui.UITheme;

@SuppressWarnings("serial")
public abstract class Screen extends JPanel implements AutoCloseable {

	protected JPanel top = new JPanel();
	protected JPanel bottom = new JPanel();
	protected JPanel left = new JPanel();
	protected JPanel right = new JPanel();
	protected JPanel center = new JPanel();

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
		for (Component comp : this.top.getComponents()) {
			this.top.remove(comp);
		}
		
		this.top.add(top);
		this.top.revalidate();
	}

	public void addBottomPanel(Component bottom) {
		for (Component comp : this.bottom.getComponents()) {
			this.bottom.remove(comp);
		}
		
		this.bottom.add(bottom);
		this.bottom.revalidate();
	}

	public void addLeftPanel(Component left) {
		for (Component comp : this.left.getComponents()) {
			this.left.remove(comp);
		}
		
		this.left.add(left);
		this.left.revalidate();
	}

	public void addRightPanel(Component right) {
		for (Component comp : this.right.getComponents()) {
			this.right.remove(comp);
		}
		
		this.right.add(right);
		this.right.revalidate();
	}

	public void addCenterPanel(Component center) {
		for (Component comp : this.center.getComponents()) {
			this.center.remove(comp);
		}
		
		this.center.add(center);
		this.center.revalidate();
	}

	public abstract void display();
	
	public void loadAndDisplay() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				display();
			}
		});
	}
	
	public void close() {
		// TODO
	}
	
}

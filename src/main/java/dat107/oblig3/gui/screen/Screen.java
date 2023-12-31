package dat107.oblig3.gui.screen;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.WindowEvent;

import javax.swing.JPanel;
import javax.swing.Scrollable;
import javax.swing.SwingUtilities;

import dat107.oblig3.gui.UITheme;

/**
 * Abstract screen class, contains no methods related to jpa.
 */
@SuppressWarnings("serial")
public abstract class Screen extends JPanel implements Scrollable, AutoCloseable {

	protected BorderLayout layout;
	
	protected JPanel top;
	protected JPanel bottom;
	protected JPanel left;
	protected JPanel right;
	protected Component center;

	public Screen() {
		this.layout = new BorderLayout();
		this.top = new JPanel();
		this.bottom = new JPanel();
		this.left = new JPanel();
		this.right = new JPanel();
		this.center = new JPanel();

		configureScreen();
		configureComponents();
		addComponents();
	}
	
	private void configureScreen() {
		setLayout(layout);
		setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		
		layout.setVgap(5);
		layout.setHgap(20);
	}
	
	private void configureComponents() {
		top.setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		bottom.setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		left.setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		right.setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		center.setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
	}

	private void addComponents() {
		add(top, BorderLayout.NORTH);
		add(bottom, BorderLayout.SOUTH);
		add(left, BorderLayout.WEST);
		add(right, BorderLayout.EAST);
		add(center, BorderLayout.CENTER);
	}
	
	public abstract void display();
	
	public void safeDisplay() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				display();
			}
		});
	}
	
	public abstract void refresh();
	
	@Override
	public void close() {	
		this.dispatchEvent(new WindowEvent(getWindow(), WindowEvent.WINDOW_CLOSING));
	}
	
	public Window getWindow() {
		Container parent = getParent();
		
		while(parent != null) {
			if(parent instanceof Window) {
				return (Window) parent;
			}
			
			parent = parent.getParent();
		}
		
		return null;
	}
	
	public void setTopPanel(Component top) {
		replacePanelContent(this.top, top);
	}

	public void setBottomPanel(Component bottom) {
		replacePanelContent(this.bottom, bottom);
	}

	public void setLeftPanel(Component left) {
		replacePanelContent(this.left, left);
	}

	public void setRightPanel(Component right) {
		replacePanelContent(this.right, right);
	}

	public void setCenterPanel(Component center) {
		this.remove(this.center);
		this.add(center);
		
		this.center = center;
	}
	
	private void replacePanelContent(JPanel parent, Component newContent) {
		for (Component comp : parent.getComponents()) {
			parent.remove(comp);
		}
		
		if(newContent != null) {
			parent.add(newContent);
		}
		
		validate();
	}

	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return getPreferredSize();
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
		return 5;
	}

	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
		return 5;
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		return true;
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		return false;
	}

}

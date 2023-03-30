package no.hvl.dat107.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;

import no.hvl.dat107.gui.screen.DepartmentsScreen;
import no.hvl.dat107.gui.screen.EmployeesScreen;
import no.hvl.dat107.gui.screen.Screen;

@SuppressWarnings("serial")
public class AppWindow extends JFrame implements AutoCloseable{

	public final ToolBar toolbar;
	public final NavigationPanel navigation;
	public final JPanel screenPanel;
	
	private final CardLayout screenCards;
	private Screen currentScreen;

	private Map<String, Screen> screens = new LinkedHashMap<>() {{
			put("Employees", new EmployeesScreen());
			put("Departments", new DepartmentsScreen());
		}};

	public AppWindow() {
		setBackground(UITheme.ROOT_BACKGROUND_COLOR);
		setSize(1200, 750);
		
		this.toolbar = new ToolBar();
		
		this.screenCards = new CardLayout();
		this.screenPanel = new JPanel(screenCards) {{
			setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
			setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		}};

		this.navigation = new NavigationPanel(this);

		setJMenuBar(toolbar);
		getContentPane().add(navigation, BorderLayout.WEST);
		getContentPane().add(screenPanel, BorderLayout.CENTER);
		
		addScreens();
	}

	public void addScreens() {
		screens.forEach((name, screen) -> {
			navigation.addNavigationButton(name);
			screenPanel.add(screen, name);
		});
	}

	public void changeScreen(String name)  {
		if(currentScreen != null) {
			try {
				currentScreen.cache();
			} catch (Exception e) {
				handleScreenChangeException(e);
			}
		}
		
		try {
			currentScreen = screens.get(name);
			currentScreen.loadAndDisplay();
			
			screenCards.show(this, name);
		} catch (Exception e) {
			handleScreenChangeException(e);
		}
	}
		
	private void handleScreenChangeException(Exception e) {
		try {
			currentScreen.close();
		} catch(Exception ignored) {
		}
		
		String errorMessage = "Unexpected error occured when changing screen.";
		System.err.println(errorMessage + "\n" + e.getMessage());	
		showErrorDialogAndClose(errorMessage);
	}
	
	public void showErrorDialogAndClose(String errorMessage) {
		int refreshOrExit = JOptionPane.showConfirmDialog(this, 
				errorMessage, "dbApp Error", 
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
		
		close();
	}

	@Override
	public void close() {
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}

}

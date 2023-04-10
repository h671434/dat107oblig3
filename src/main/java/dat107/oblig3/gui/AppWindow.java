package dat107.oblig3.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dat107.oblig3.gui.screen.DepartmentsScreen;
import dat107.oblig3.gui.screen.EmployeesScreen;
import dat107.oblig3.gui.screen.ProjectsScreen;
import dat107.oblig3.gui.screen.Screen;

@SuppressWarnings("serial")
public class AppWindow extends JFrame implements AutoCloseable {

	public final ToolBar toolbar;
	public final NavigationSideBar navigation;
	public final JPanel screenPanel;
	
	private final JScrollPane screenScrollPane;
	private final CardLayout screenCards;
	private Screen currentScreen;

	private Map<String, Screen> screens = new LinkedHashMap<>() {{
			put("Employees", new EmployeesScreen());
			put("Departments", new DepartmentsScreen());
			put("Projects", new ProjectsScreen());
	}};

	public AppWindow() {
		setBackground(UITheme.LIGHT_ACCENT_COLOR);
		setSize(1400, 900);
		
		this.toolbar = new ToolBar();
		this.screenCards = new CardLayout();
		this.screenPanel = new JPanel(screenCards);
		this.screenScrollPane = new JScrollPane(screenPanel);
		this.navigation = new NavigationSideBar(this);
		
		setJMenuBar(toolbar);
		getContentPane().add(navigation, BorderLayout.WEST);
		getContentPane().add(screenScrollPane, BorderLayout.CENTER);
		
		addScreens();
		
		screens.get("Employees").display();
	}

	public void addScreens() {
		screens.forEach((name, screen) -> {
			navigation.addNavigationButton(name);
			screenPanel.add(screen, name);
		});
	}

	public void changeScreen(String name)  {		
		try {
			currentScreen = screens.get(name);
			currentScreen.safeDisplay();
			
			screenCards.show(screenPanel, name);
			
			screenScrollPane.revalidate();
			
		} catch (Exception e) {
			handleChangeScreenException(e);
		}
	}
		
	private void handleChangeScreenException(Exception e) {
		try {
			currentScreen.close();
		} catch(Exception ignored) {}
		
		e.printStackTrace();
		showErrorDialogAndClose("Unexpected error occured when changing screen.");
	}
	
	public void showErrorDialogAndClose(String errorMessage) {
		JOptionPane.showConfirmDialog(this, 
				errorMessage, "dbApp Error", 
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
		
		close();
	}
	
	@Override
	public void close() {
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	public void refresh() {
		validate();
	}

}

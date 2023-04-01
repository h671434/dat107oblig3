package dat107.oblig3.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.WindowEvent;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dat107.oblig3.gui.screen.DepartmentsScreen;
import dat107.oblig3.gui.screen.EmployeesScreen;
import dat107.oblig3.gui.screen.ProjectsScreen;
import dat107.oblig3.gui.screen.Screen;
import dat107.oblig3.gui.widget.NavigationPanel;
import dat107.oblig3.gui.widget.ToolBar;

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
			put("Projects", new ProjectsScreen());
		}};

	public AppWindow() {
		setBackground(UITheme.ROOT_BACKGROUND_COLOR);
		setSize(1200, 750);
		
		this.toolbar = new ToolBar();
		this.screenCards = new CardLayout();
		this.screenPanel = new JPanel(screenCards);
		this.navigation = new NavigationPanel(this);

		setJMenuBar(toolbar);
		getContentPane().add(navigation, BorderLayout.WEST);
		getContentPane().add(screenPanel, BorderLayout.CENTER);
		
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
			
		} catch (Exception e) {
			handleChangeScreenException(e);
		}
	}
		
	private void handleChangeScreenException(Exception e) {
		try {
			currentScreen.close();
		} catch(Exception ignored) {
		}
		
		String errorMessage = "Unexpected error occured when changing screen.";
		System.err.println(errorMessage + "\n" + e.getMessage());	
		showErrorDialogAndClose(errorMessage);
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

}

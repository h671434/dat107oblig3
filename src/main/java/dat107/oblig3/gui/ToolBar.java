package dat107.oblig3.gui;

import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class ToolBar extends JMenuBar {

	private final JMenu fileMenu;
	private final JMenu optionsMenu;
	private final JMenu helpMenu;
	
	public ToolBar() {
		this.fileMenu = createMenu("File");
		this.optionsMenu = createMenu("Options");
		this.helpMenu = createMenu("Help");

		configureToolBar();
		configureMenus();
		addComponents();
	}
	
	private void configureToolBar() {
		setBackground(UITheme.ALTERNATIVE_BACKGROUND_COLOR);
		setBorder(BorderFactory.createEmptyBorder(0, 0, 1, 0));
	}

	private JMenu createMenu(String text) {
		JMenu menu = new JMenu(text);
		
		menu.setBackground(UITheme.ALTERNATIVE_BACKGROUND_COLOR);
		menu.setForeground(UITheme.DEFAULT_TEXT_COLOR);
		menu.setBorder(BorderFactory.createEmptyBorder());
		menu.getPopupMenu().setBorder(null);
		
		return menu;
	}
	
	private void configureMenus() {
		configureFileMenu();
		configureOptionsMenu();
		configureHelpMenu();
	}
	
	private void configureOptionsMenu() {
		optionsMenu.add(createMenuItem(
				"No options", 
				e -> System.out.println("Nope")));
	}
	
	private void configureFileMenu() {
		fileMenu.add(createMenuItem(
				"What would you wanna do with a file anyway?", 
				e -> System.out.println("Nope")));
	}
	
	private void configureHelpMenu() {
		helpMenu.add(createMenuItem(
				"No help for you", 
				e -> System.out.println("Nope")));
	}

	private JMenuItem createMenuItem(String text, ActionListener method) {
		JMenuItem menuItem = new JMenuItem(text);
		
		menuItem.addActionListener(method);
		menuItem.setBackground(UITheme.ALTERNATIVE_BACKGROUND_COLOR);
		menuItem.setForeground(UITheme.DEFAULT_TEXT_COLOR);
		menuItem.setBorder(BorderFactory.createEmptyBorder());

		return menuItem;
	}
	
	private void addComponents() {
		add(fileMenu);
		add(optionsMenu);
		add(helpMenu);
	}
	
}
package dat107.oblig3.gui;

import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class ToolBar extends JMenuBar {

	public ToolBar() {
		setBackground(UITheme.ALTERNATIVE_BACKGROUND_COLOR);
		setBorder(BorderFactory.createEmptyBorder(0, 0, 1, 0));

		JMenu fileMenu = createMenu("File");
		fileMenu.add(createMenuItem(
				"What would you wanna do with a file anyway?", 
				e -> System.out.println("Nope")));

		JMenu optionsMenu = createMenu("Options");
		optionsMenu.add(createMenuItem(
				"No options", 
				e -> System.out.println("Nope")));

		JMenu helpMenu = createMenu("Help");
		helpMenu.add(createMenuItem(
				"No help for you", 
				e -> System.out.println("Nope")));

		add(fileMenu);
		add(optionsMenu);
		add(helpMenu);
	}

	private JMenu createMenu(String text) {
		JMenu menu = new JMenu(text);
		
		menu.setBackground(UITheme.ALTERNATIVE_BACKGROUND_COLOR);
		menu.setForeground(UITheme.TOOLBAR_TEXT_COLOR);
		menu.setBorder(BorderFactory.createEmptyBorder());
		menu.getPopupMenu().setBorder(null);
		
		return menu;
	}

	private JMenuItem createMenuItem(String text, ActionListener method) {
		JMenuItem menuItem = new JMenuItem(text);
		
		menuItem.addActionListener(method);
		menuItem.setBackground(UITheme.ALTERNATIVE_BACKGROUND_COLOR);
		menuItem.setForeground(UITheme.TOOLBAR_TEXT_COLOR);
		menuItem.setBorder(BorderFactory.createEmptyBorder());

		return menuItem;
	}
}
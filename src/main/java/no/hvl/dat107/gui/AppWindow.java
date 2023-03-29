package no.hvl.dat107.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

@SuppressWarnings("serial")
public class AppWindow extends JFrame {

	public final AppToolBar toolbar = new AppToolBar();
	public final AppNavigationPanel navigation = new AppNavigationPanel();
	public final AppContentPanel content = new AppContentPanel();

	private Map<String, Screen> screens = new LinkedHashMap<>() {
		{
			put("Employees", new EmployeesScreen());
			put("Departments", new DepartmentsScreen());
		}
	};

	public AppWindow() {
		setBackground(UITheme.ROOT_BACKGROUND_COLOR);
		setSize(1200, 750);

		navigation.setPreferredSize(new Dimension(getWidth() / 6, getHeight()));

		setJMenuBar(toolbar);
		getContentPane().add(navigation, BorderLayout.WEST);
		getContentPane().add(content, BorderLayout.CENTER);
		
		addContentScreens();
	}

	public void addContentScreens() {
		screens.forEach((name, screen) -> {
			navigation.addNavigationButton(name);
			content.addScreen(screen, name);
		});
	}

	private class AppToolBar extends JMenuBar {

		public AppToolBar() {
			setBackground(UITheme.TOOLBAR_BACKGROUND_COLOR);
			setBorder(BorderFactory.createEmptyBorder(0, 0, 1, 0));

			JMenu fileMenu = createMenu("File");
			fileMenu.add(createMenuItem("Save As...", e -> System.out.println("Nope")));

			JMenu optionsMenu = createMenu("Options");
			optionsMenu.add(createMenuItem("No options", e -> System.out.println("Nope")));

			JMenu helpMenu = createMenu("Help");
			helpMenu.add(createMenuItem("No help for you", e -> System.out.println("Nope")));

			add(fileMenu);
			add(optionsMenu);
			add(helpMenu);
		}

		private JMenu createMenu(String text) {
			JMenu menu = new JMenu(text);
			menu.setBackground(UITheme.TOOLBAR_BACKGROUND_COLOR);
			menu.setForeground(UITheme.TOOLBAR_TEXT_COLOR);
			menu.setBorder(BorderFactory.createEmptyBorder());

			return menu;
		}

		private JMenuItem createMenuItem(String text, ActionListener method) {
			JMenuItem menuItem = new JMenuItem(text);
			menuItem.addActionListener(method);
			menuItem.setBackground(UITheme.TOOLBAR_BACKGROUND_COLOR);
			menuItem.setForeground(UITheme.TOOLBAR_TEXT_COLOR);
			menuItem.setBorder(BorderFactory.createEmptyBorder());

			return menuItem;
		}
	}

	private final class AppNavigationPanel extends JPanel {
		                
		private GridBagConstraints nextButtonPos = new GridBagConstraints() {
			{
				fill = GridBagConstraints.HORIZONTAL;
				weightx = 1;
				weighty = 0;
				anchor = GridBagConstraints.PAGE_START;
				gridx = 0;
				gridy = 0;
			}
		};
		
		public AppNavigationPanel() {
			setLayout(new GridBagLayout());
			setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
			setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
			
			JPanel emptyArea = new JPanel();
			emptyArea.setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
			
			GridBagConstraints fillUnderButtons = new GridBagConstraints() {
				{
					fill = GridBagConstraints.BOTH;
					weighty = 1;
					gridy = 100;
				}	
			};
			
			add(emptyArea, fillUnderButtons);
		}

		public void addNavigationButton(String screenName) {
			JButton button = new JButton(screenName);
			button.addActionListener(e -> content.changeScreen(screenName));
			button.setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
			button.setForeground(UITheme.DEFAULT_TEXT_COLOR);
			button.setAlignmentX(CENTER_ALIGNMENT);
			button.setBorder(BorderFactory.createEmptyBorder());
			button.setPreferredSize(new Dimension(navigation.getWidth(), 40));
			button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			
			add(button, nextButtonPos);
			nextButtonPos.gridy++;
		}
	}

	private final class AppContentPanel extends JPanel {

		private final CardLayout layout = new CardLayout();

		public AppContentPanel() {
			setLayout(layout);
			setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
			setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		}

		public void addScreen(Screen screen, String name) {
			this.add(screen, name);
		}

		public void changeScreen(String name) {
			layout.show(this, name);
		}
	}

}
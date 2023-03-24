package corp.dbapp.client.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

@SuppressWarnings("serial")
public class AppWindow extends JFrame {

	public enum Screens {
		EMPLOYEES, DEPARTMENTS, PROJECTS
	}
	
	public final AppMenuBar menu;
	public final AppNavigationPanel navigation;
	public final AppContentPanel content;
	
	public AppWindow() {
		super("dbapp");
		this.menu = new AppMenuBar();
		this.navigation = new AppNavigationPanel(this);
		this.content = new AppContentPanel(this);
		
		setSize(1200, 800);
		
		GridBagLayout layout = new GridBagLayout(); 
        setLayout(layout);  
        
        GridBagConstraints navc = new GridBagConstraints();  
        navc.fill = GridBagConstraints.VERTICAL;  
        navc.gridx = 0;  
        navc.weightx = 0.1;
        navc.gridy = 0;  
        navc.gridwidth = 1;
        navc.anchor = GridBagConstraints.NORTHWEST;
        GridBagConstraints conc = new GridBagConstraints();  
        conc.fill = GridBagConstraints.VERTICAL;  
        conc.weightx = 0.5;
        conc.gridx = 1;  
        conc.gridy = 0;  
        conc.gridwidth = 2; 
        navc.anchor = GridBagConstraints.CENTER;
		
		setJMenuBar(menu);
		add(navigation, navc);
		add(content, conc);
	}
	
	public class AppMenuBar extends JMenuBar {
		
		public AppMenuBar() {
			setBackground(Color.DARK_GRAY);
			
			JMenu fileMenu = new JMenu("File");
			fileMenu.setForeground(Color.WHITE);
			fileMenu.add(new JMenuItem(new AbstractAction("Save As...") {
				@Override
				public void actionPerformed(ActionEvent ae) {
					onSaveAs();
				}
			}));
			
			add(fileMenu);
		}
		
		private void onSaveAs() {
			// TODO
		}
		
	}
	
	public class AppNavigationPanel extends JPanel {
		
		private AppWindow parent;
		
		public AppNavigationPanel(AppWindow parent) {
			this.parent = parent;
			
			setSize(parent.getWidth() / 3, parent.getHeight() - parent.menu.getHeight());
			setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
			
			add(new JButton(new AbstractAction("Employees") {
				@Override
				public void actionPerformed(ActionEvent ae) {
					content.setScreen(Screens.EMPLOYEES);
				}
			}));
		}

	}
	
	public class AppContentPanel extends JPanel {
		
		private AppWindow parent;
		private CardLayout layout;
		
		public AppContentPanel(AppWindow parent) {
			this.parent = parent;
			this.layout = new CardLayout();

			setLayout(layout);
			add(new Label("Content"));
			
			layout.addLayoutComponent(
					new EmployeesScreen(), 
					Screens.EMPLOYEES.toString());
		}
		
		public void setScreen(Screens screen) {
			layout.show(this, screen.toString());
		}
		
	}
}

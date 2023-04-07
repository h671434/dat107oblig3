package dat107.oblig3.gui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

@SuppressWarnings("serial")
public class NavigationSideBar extends JPanel {
    
	private final AppWindow window;
	
	private JButton selectedButton;
	
	private GridBagConstraints nextButtonPos = new GridBagConstraints() {{
			fill = GridBagConstraints.HORIZONTAL;
			weightx = 1;
			weighty = 0;
			anchor = GridBagConstraints.PAGE_START;
			gridx = 0;
			gridy = 0;
	}};
	
	public NavigationSideBar(AppWindow window) {
		this.window = window;
		
		setLayout(new GridBagLayout());
		setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		setPreferredSize(new Dimension(window.getWidth() / 6, getHeight()));
		
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
		
		button.addActionListener(e -> window.changeScreen(screenName));
		button.addActionListener(e -> setSelectedButton(button));
		button.setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		button.setForeground(UITheme.DEFAULT_TEXT_COLOR);
		button.setAlignmentX(CENTER_ALIGNMENT);
		button.setBorder(BorderFactory.createEmptyBorder());
		button.setPreferredSize(new Dimension(getWidth(), 40));
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button.setFocusPainted(false);
		
		add(button, nextButtonPos);
		nextButtonPos.gridy++;
		
		if(selectedButton == null) {
			setSelectedButton(button);
		}
	}
	
	private void setSelectedButton(JButton button) {
		if(selectedButton != null) {
			selectedButton.setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		}
		
		selectedButton = button;
		
		selectedButton.setBackground(UITheme.ALTERNATIVE_BACKGROUND_COLOR);
	}
}

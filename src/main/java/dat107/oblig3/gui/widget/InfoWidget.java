package dat107.oblig3.gui.widget;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dat107.oblig3.gui.UITheme;

@SuppressWarnings("serial")
public class InfoWidget extends JPanel {

	protected JLabel titleLabel = new JLabel("");
	protected JPanel fieldPanel = new JPanel(new GridBagLayout());
	protected JPanel buttonPanel = new JPanel(new GridLayout(0, 1));
	
	protected GridBagConstraints nextLabelPos = new GridBagConstraints() {{
		gridx = 0;
		gridy = 0;
		ipadx = 5;
		ipady = 5;
		anchor = GridBagConstraints.LINE_END;
		insets = new Insets(2, 2, 2, 2);
	}};
	protected GridBagConstraints nextFieldPos = new GridBagConstraints() {{
		gridx = 1;
		gridy = 0;
		ipadx = 5;
		ipady = 5;
		insets = new Insets(2, 2, 2, 2);
	}};
	
	public InfoWidget() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBackground(UITheme.ALTERNATIVE_BACKGROUND_COLOR);
		setBorder(BorderFactory.createLineBorder(UITheme.LIGHT_ACCENT_COLOR));
		
		titleLabel.setForeground(UITheme.DEFAULT_TEXT_COLOR);
		titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
		fieldPanel.setBackground(UITheme.ALTERNATIVE_BACKGROUND_COLOR);
		
		buttonPanel.setBackground(UITheme.ALTERNATIVE_BACKGROUND_COLOR);
		buttonPanel.setLayout(new GridLayout(0, 1));
		
		add(titleLabel);
		add(fieldPanel);
		add(buttonPanel);
	}
	
	public InfoWidget(String title) {
		this();
		titleLabel.setText(title);
	}
	
	public void setTitle(String title) {
		titleLabel.setText(title);
	}
	
	public JLabel addLabeledField(String name, Component field) {
		JLabel label = new JLabel(name, JLabel.TRAILING);
		label.setForeground(UITheme.DEFAULT_TEXT_COLOR);
		label.setLabelFor(field);
		
		JPanel outerComponentPanel = new JPanel();
		outerComponentPanel.setBackground(UITheme.ALTERNATIVE_BACKGROUND_COLOR);
		outerComponentPanel.add(field);
		
		fieldPanel.add(label, nextLabelPos);
		fieldPanel.add(outerComponentPanel, nextFieldPos);
		
		nextLabelPos.gridy++;
		nextFieldPos.gridy++;
		
		return label;
	}
	
	public void addLabeledField(String name, Component field, String toolTip) {
		addLabeledField(name, field).setToolTipText(toolTip);
	}
	
	public void addFullWidthField(Component field) {
		GridBagConstraints fullWidth = new GridBagConstraints() {{
			fill = GridBagConstraints.HORIZONTAL;
			gridy = nextLabelPos.gridy;
		}};
		
		fieldPanel.add(field, fullWidth);
		
		nextLabelPos.gridy++;
		nextFieldPos.gridy++;
	}
	
	public void removeAllFields() {
		fieldPanel.removeAll();
	}
	
	public static JButton createWidgetButton(String text, ActionListener onPress) {
		JButton button = new JButton(text);
		
		button.setBackground(UITheme.ALTERNATIVE_BACKGROUND_COLOR);
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button.setForeground(UITheme.DEFAULT_TEXT_COLOR);
		button.setBorder(BorderFactory.createLineBorder(UITheme.DEFAULT_BACKGROUND_COLOR));
		button.setPreferredSize(new Dimension(100, 40));
		button.setFocusPainted(false);
		button.addActionListener(onPress);
		
		return button;
	}
	
	public void setButtons(JButton... buttons) {
		removeAllButtons();
		
		if(buttons.length % 2 == 0) {
			buttonPanel.setLayout(new GridLayout(0, 2));
		} else {
			buttonPanel.setLayout(new GridLayout(0, 1));
		}
		
		for (JButton b : buttons) {	
			buttonPanel.add(b);
		}
	}
	
	public void removeAllButtons() {
		buttonPanel.removeAll();
	}
	
}

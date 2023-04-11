package dat107.oblig3.gui.widget;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.Scrollable;

import dat107.oblig3.gui.UITheme;

/**
 * Widget preset class to easily build new widgets
 */
@SuppressWarnings("serial")
public class Widget extends JPanel {

	protected JLabel titleLabel;
	protected JPanel fieldPanel;
	protected JPanel buttonPanel;
	
	protected GridBagConstraints nextLabelPos;
	protected GridBagConstraints nextFieldPos;
	
	public Widget(String title) {
		this.titleLabel = new JLabel(title);
		this.fieldPanel = new JPanel(new GridBagLayout());
		this.buttonPanel = new JPanel(new GridBagLayout());
		this.nextLabelPos = new GridBagConstraints() {{
			gridx = 0;
			gridy = 0;
			ipadx = 5;
			ipady = 5;
			anchor = GridBagConstraints.LINE_END;
			insets = new Insets(2, 2, 2, 2);
		}};
		this.nextFieldPos = new GridBagConstraints() {{
			gridx = 1;
			gridy = 0;
			ipadx = 5;
			ipady = 5;
			anchor = GridBagConstraints.LINE_START;
			insets = new Insets(2, 2, 2, 2);
		}};
		
		configureWidget();
		configureComponents();
		addComponents();
	}
	
	public Widget() {
		this("");
	}
	
	private void configureWidget() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBackground(UITheme.ALTERNATIVE_BACKGROUND_COLOR);
		setBorder(BorderFactory.createLineBorder(UITheme.LIGHT_ACCENT_COLOR));
	}
	
	private void configureComponents() {
		titleLabel.setForeground(UITheme.DEFAULT_TEXT_COLOR);
		titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		titleLabel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
		
		fieldPanel.setBackground(UITheme.ALTERNATIVE_BACKGROUND_COLOR);
		
		buttonPanel.setBackground(UITheme.ALTERNATIVE_BACKGROUND_COLOR);
	}
	
	private void addComponents() {
		add(titleLabel);
		add(fieldPanel);
		add(buttonPanel);
	}
	
	public void setTitle(String title) {
		titleLabel.setText(title);
	}
	
	public JLabel addLabeledField(String name, Component field) {
		JLabel label = new JLabel(name, JLabel.TRAILING);
		label.setForeground(UITheme.DEFAULT_TEXT_COLOR);
		label.setLabelFor(field);
		
		JPanel outerComponentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
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
			gridwidth = 2;
			gridy = nextLabelPos.gridy;
		}};
		
		fieldPanel.add(field, fullWidth);
		
		nextLabelPos.gridy++;
		nextFieldPos.gridy++;
	}

	public void removeField(Component component) {
		fieldPanel.remove(component);
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
		
		GridBagConstraints buttonPos = new GridBagConstraints();
		buttonPos.gridy = 0;
		buttonPos.gridx = 0;
		buttonPos.weightx = 0.5;
		buttonPos.gridwidth = 1;
		buttonPos.fill = GridBagConstraints.HORIZONTAL;
		
		int extra = buttons.length % 2;
		
		for(int i = 0; i < buttons.length - extra; i++) {
			buttonPanel.add(buttons[i], buttonPos);
			
			if(buttonPos.gridx == 0) {
				buttonPos.gridx = 1;
			} else {
				buttonPos.gridx = 0;
				buttonPos.gridy++;
			}
		}
		
		if(extra == 1) {
			buttonPos.gridwidth = 2;
			buttonPanel.add(buttons[buttons.length - 1], buttonPos);
		}
		
	}
	
	public void removeAllButtons() {
		buttonPanel.removeAll();
	}
	
}

package no.hvl.dat107.gui.widget;

import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.Closeable;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import no.hvl.dat107.gui.DynamicVisibility;
import no.hvl.dat107.gui.UITheme;

@SuppressWarnings("serial")
public class InfoWidget extends JPanel implements DynamicVisibility {

	protected final JPanel parent;
	
	public InfoWidget(JPanel parent, String title) {
		this.parent = parent;
		
		setLayout(new GridLayout(0, 2));
		setBackground(UITheme.TOOLBAR_BACKGROUND_COLOR);
		setBorder(BorderFactory.createLineBorder(UITheme.BUTTON_BACKGROUND_COLOR));
		
		JLabel titleLabel = new JLabel(title, JLabel.CENTER);		
		titleLabel.setForeground(UITheme.DEFAULT_TEXT_COLOR);
		add(titleLabel);
		
		JButton exitButton = new JButton("X");
		exitButton.setBackground(UITheme.TOOLBAR_BACKGROUND_COLOR);
		exitButton.setForeground(UITheme.DEFAULT_TEXT_COLOR);
		exitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		exitButton.addActionListener(e -> parent.remove(this));
		
		JPanel exitButtonOuterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		exitButtonOuterPanel.setBackground(UITheme.TOOLBAR_BACKGROUND_COLOR);
		exitButtonOuterPanel.add(exitButton);
		
		add(exitButtonOuterPanel);
	}
	
	public JPanel addSection(String name) {
		JLabel label = new JLabel(name, JLabel.TRAILING);
		label.setForeground(UITheme.DEFAULT_TEXT_COLOR);
		add(label);
		
		JPanel fieldPanel = new JPanel();
		fieldPanel.setBackground(UITheme.TOOLBAR_BACKGROUND_COLOR);
		label.setLabelFor(fieldPanel);
		add(fieldPanel);
		
		return fieldPanel;
	}
	
	public JPanel[] addSplitSection(String name1, String name2) {
		JPanel doublePanel = new JPanel(new GridLayout(1, 2));
		doublePanel.setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		
		JPanel panel1 = new JPanel();
		panel1.setBackground(UITheme.TOOLBAR_BACKGROUND_COLOR);
		JPanel panel2 = new JPanel();
		panel2.setBackground(UITheme.TOOLBAR_BACKGROUND_COLOR);
		
		JLabel label1 = new JLabel(name1);
		label1.setForeground(UITheme.DEFAULT_TEXT_COLOR);
		panel1.add(label1);
		JPanel fieldPanel1 = new JPanel();
		fieldPanel1.setBackground(UITheme.TOOLBAR_BACKGROUND_COLOR);
		label1.setLabelFor(fieldPanel1);
		panel1.add(fieldPanel1);
		
		JLabel label2 = new JLabel(name2);
		label2.setForeground(UITheme.DEFAULT_TEXT_COLOR);
		panel2.add(label2);
		JPanel fieldPanel2 = new JPanel();
		fieldPanel2.setBackground(UITheme.TOOLBAR_BACKGROUND_COLOR);
		label2.setLabelFor(fieldPanel2);
		panel2.add(fieldPanel2);
		
		return new JPanel[] {fieldPanel1, fieldPanel2};
	}
	

	@Override
	public void display() {
		this.parent.add(this);
		this.parent.revalidate();
	}

	@Override
	public void cache() {
		this.parent.remove(this);
		this.revalidate();
	}
	
}

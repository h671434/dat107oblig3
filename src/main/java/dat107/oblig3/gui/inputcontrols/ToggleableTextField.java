package dat107.oblig3.gui.inputcontrols;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class ToggleableTextField extends JTextField {

	public static final Color DEFAULT_BACKGROUND = Color.DARK_GRAY;
	public static final Color DEFAULT_EDITABLE_FOREGROUND = Color.LIGHT_GRAY;
	public static final Color DEFAULT_UNEDITABLE_FOREGROUND = Color.WHITE;
	public static final Color DEFAULT_FOCUSED_FOREGROUND = Color.WHITE;
	public static final Border DEFAULT_EDITABLE_BORDER = 
			BorderFactory.createLineBorder(DEFAULT_EDITABLE_FOREGROUND, 1, true);
	public static final Border DEFAULT_UNEDITABLE_BORDER = 
			BorderFactory.createLineBorder(DEFAULT_BACKGROUND, 1, true);
	public static final Border DEFAULT_FOCUSED_BORDER =
			BorderFactory.createLineBorder(Color.WHITE, 1, true);
	
	private FocusListener focusListener = new ThemeChangeFocusListener();
	
	private Color background = DEFAULT_BACKGROUND;
	private Color editableForeground = DEFAULT_EDITABLE_FOREGROUND;
	private Color uneditableForeground = DEFAULT_UNEDITABLE_FOREGROUND;
	private Color focusedForeground = DEFAULT_FOCUSED_FOREGROUND;
	private Border editableBorder = DEFAULT_EDITABLE_BORDER;
	private Border uneditableBorder = DEFAULT_UNEDITABLE_BORDER;
	private Border focusedBorder = DEFAULT_FOCUSED_BORDER;
	
	public ToggleableTextField() {
		addFocusListener(focusListener);
		
		setCaretColor(Color.WHITE);
		setTheme();
	}
	
	
	public ToggleableTextField(int columns) {
		super(columns);
		
		addFocusListener(focusListener);
		
		setCaretColor(Color.WHITE);
		setTheme();
	}
	
	@Override
	public void setEditable(boolean editable) {
		super.setEditable(editable);
		
		setTheme();
	}
	
	private void setTheme() {
		setBackground(background);
		
		if(hasFocus()) {
			setFocusedTheme();
		} else if(isEditable()) {
			setEditableTheme();
		} else {
			setUneditableTheme();
		}
	}
	
	private void setFocusedTheme() {
		setForeground(focusedForeground);
		setBorder(focusedBorder);
	}
	
	private void setEditableTheme() {
		setForeground(editableForeground);
		setBorder(editableBorder);
	}
	
	private void setUneditableTheme() {
		setForeground(uneditableForeground);
		setBorder(uneditableBorder);
	}
	
	public class ThemeChangeFocusListener implements FocusListener {
		
		@Override
		public void focusGained(FocusEvent e) {
			setFocusedTheme();
		}
	
		@Override
		public void focusLost(FocusEvent e) {
			setTheme();
		}
		
	}
	
}

package dat107.oblig3.gui.inputcontrols;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.BiConsumer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LookAndFeel;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicComboBoxUI;

import dat107.oblig3.gui.UITheme;

/**
 * A multi-option searchbar.
 */
@SuppressWarnings("serial")
public class SearchBar extends JPanel {

	private static final Border FOCUSED_BORDER =
			BorderFactory.createLineBorder(Color.WHITE, 1, true);
	private static final Border UNFOCUSED_BORDER = 
			BorderFactory.createLineBorder(Color.DARK_GRAY, 1, true);
			
	private JLabel searchByLabel = new JLabel("Search by:");
	private JComboBox<String> searchOptions = new JComboBox<String>();
	private JTextField searchField = new JTextField(30);
	private JPanel outerSearchFieldPanel = new JPanel();
	private JButton searchButton = new JButton("Search");
	
	private BiConsumer<String, String> onSearch;

	/**
	 * Calls the given BiConsumer when the search button is pressed. 
	 * Delegating action based on the selected option is not done in this class,
	 * and should be handled by the consumer.
	 * @param onSearch consumes the selected search option and the current 
	 * search input when the search button is pressed. 
	 */
	public SearchBar(BiConsumer<String, String> onSearch) {
		this.onSearch = onSearch;
		
		setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		setLayout(new FlowLayout());
		setAlignmentX(CENTER_ALIGNMENT);

		searchField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					search();
					
					searchField.transferFocus();
				}	
			}
		});
		
		outerSearchFieldPanel.add(searchField);
		
		searchButton.addActionListener(e -> search());
		
		designComponents();

		add(searchByLabel);
		add(searchOptions);
		add(outerSearchFieldPanel);
		add(searchButton);
	}
	
	private void designComponents() {
		designComboBox();
		designSearchField();
		designSearchButton();
	}
	
	private void designComboBox() {
		searchByLabel.setForeground(UITheme.DEFAULT_TEXT_COLOR);
		
		searchOptions.setBackground(UITheme.ALTERNATIVE_BACKGROUND_COLOR);
		searchOptions.setForeground(UITheme.DEFAULT_TEXT_COLOR);
		searchOptions.setBorder(BorderFactory.createEmptyBorder());
		searchOptions.setOpaque(false);
		searchOptions.setUI(new BasicComboBoxUI() {
			@Override
			protected void installDefaults() {
				super.installDefaults();
				LookAndFeel.uninstallBorder(comboBox);
				comboBox.setBorder(BorderFactory.createLineBorder(UITheme.ALTERNATIVE_BACKGROUND_COLOR, 5));
			}

			@Override
			public void configureArrowButton() {
				super.configureArrowButton(); //Do not forget this!
				arrowButton.setBackground(UITheme.ALTERNATIVE_BACKGROUND_COLOR);
				arrowButton.setForeground(UITheme.LIGHT_ACCENT_COLOR);
				arrowButton.setBorder(BorderFactory.createLineBorder(UITheme.ALTERNATIVE_BACKGROUND_COLOR, 5));
			}
		});
	}
	
	private void designSearchField() {
		searchField.setBackground(UITheme.ALTERNATIVE_BACKGROUND_COLOR);
		searchField.setForeground(UITheme.DEFAULT_TEXT_COLOR);
		searchField.setCaretColor(UITheme.LIGHT_ACCENT_COLOR);
		searchField.setBorder(BorderFactory.createEmptyBorder());
		searchField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				searchField.setForeground(UITheme.DEFAULT_TEXT_COLOR);
				outerSearchFieldPanel.setBorder(FOCUSED_BORDER);
			}

			@Override
			public void focusLost(FocusEvent e) {
				searchField.setForeground(UITheme.LIGHT_ACCENT_COLOR);
				outerSearchFieldPanel.setBorder(UNFOCUSED_BORDER);
			}
		});
		
		outerSearchFieldPanel.setBackground(UITheme.ALTERNATIVE_BACKGROUND_COLOR);
		outerSearchFieldPanel.setBorder(UNFOCUSED_BORDER);
	}
	
	private void designSearchButton() {
		searchButton.setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		searchButton.setForeground(UITheme.DEFAULT_TEXT_COLOR);
		searchButton.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, UITheme.LIGHT_ACCENT_COLOR));
		searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		searchButton.setPreferredSize(new Dimension(85, 32));
		searchButton.setFocusPainted(false);
	}
	
	public void search() {
		onSearch.accept((String) searchOptions.getSelectedItem(), searchField.getText());
	}

	/**
	 * Adds a string to the searchOptions combobox for the user to select.
	 * The action for each option is handled outside of this class.
	 */
	public void addSearchOption(String option) {
		searchOptions.addItem(option);
	}
	
	public void removeText() {
		searchField.setText("");
	}
}

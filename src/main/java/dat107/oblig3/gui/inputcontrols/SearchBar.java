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
			
	private JLabel searchByLabel;
	private JComboBox<String> searchOptions;
	private JTextField searchField;
	private JPanel outerSearchFieldPanel;
	private JButton searchButton;
	
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
		this.searchByLabel = new JLabel("Search by:");
		this.searchOptions = new JComboBox<String>();
		this.searchField = new JTextField(30);
		this.outerSearchFieldPanel = new JPanel();
		this.searchButton = new JButton("Search");
		
		configureSearchBar();
		configureComponents();
		addComponents();
	}
	
	private void configureSearchBar() {
		setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		setLayout(new FlowLayout());
		setAlignmentX(CENTER_ALIGNMENT);
	}
	
	private void configureComponents() {
		configureComboBox();
		configureSearchField();
		configureSearchButton();
	}
	
	private void configureComboBox() {
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
				super.configureArrowButton(); 
				arrowButton.setBackground(UITheme.ALTERNATIVE_BACKGROUND_COLOR);
				arrowButton.setForeground(UITheme.LIGHT_ACCENT_COLOR);
				arrowButton.setBorder(BorderFactory.createLineBorder(UITheme.ALTERNATIVE_BACKGROUND_COLOR, 5));
			}
		});
	}
	
	private void configureSearchField() {
		searchField.setBackground(UITheme.ALTERNATIVE_BACKGROUND_COLOR);
		searchField.setForeground(UITheme.DEFAULT_TEXT_COLOR);
		searchField.setCaretColor(UITheme.LIGHT_ACCENT_COLOR);
		searchField.setBorder(BorderFactory.createEmptyBorder());
		searchField.addFocusListener(new SearchFieldListener());
		searchField.addKeyListener(new SearchFieldListener());
		
		outerSearchFieldPanel.setBackground(UITheme.ALTERNATIVE_BACKGROUND_COLOR);
		outerSearchFieldPanel.setBorder(UNFOCUSED_BORDER);
		
		outerSearchFieldPanel.add(searchField);
	}
	
	private void configureSearchButton() {
		searchButton.setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		searchButton.setForeground(UITheme.DEFAULT_TEXT_COLOR);
		searchButton.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, UITheme.LIGHT_ACCENT_COLOR));
		searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		searchButton.setPreferredSize(new Dimension(85, 32));
		searchButton.setFocusPainted(false);
		searchButton.addActionListener(e -> search());
	}
	
	private void addComponents() {
		add(searchByLabel);
		add(searchOptions);
		add(outerSearchFieldPanel);
		add(searchButton);
	}
	
	public void search() {
		onSearch.accept((String) searchOptions.getSelectedItem(), searchField.getText());
	}

	public void addSearchOption(String option) {
		searchOptions.addItem(option);
	}
	
	public void removeText() {
		searchField.setText("");
	}
	
	public class SearchFieldListener extends KeyAdapter implements FocusListener {
		
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				search();
				
				searchField.transferFocus();
			}	
		}
		
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
		
	}
	
}

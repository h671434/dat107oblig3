package dat107.oblig3.gui.widget;

import java.awt.FlowLayout;
import java.util.function.BiConsumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dat107.oblig3.gui.UITheme;

@SuppressWarnings("serial")
public class SearchBar extends JPanel {

	private JComboBox<String> searchOptions = new JComboBox<String>();
	private JTextField searchField = new JTextField(20);
	private JButton searchButton = new JButton("Search");

	public SearchBar(BiConsumer<String, String> onSearch) {
		setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		setLayout(new FlowLayout());
		setAlignmentX(CENTER_ALIGNMENT);

		searchButton.addActionListener(e -> {
			onSearch.accept((String) searchOptions.getSelectedItem(), searchField.getText());
		});

		JLabel searchByText = new JLabel("Search by:");
		searchByText.setForeground(UITheme.DEFAULT_TEXT_COLOR);

		add(searchByText);
		add(searchOptions);
		add(searchField);
		add(searchButton);
	}

	public void addSearchOption(String option) {
		searchOptions.addItem(option);
	}

}

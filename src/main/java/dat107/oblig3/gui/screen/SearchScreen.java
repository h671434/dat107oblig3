package dat107.oblig3.gui.screen;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dat107.oblig3.gui.UITheme;
import dat107.oblig3.gui.collection.EntityCollection;
import dat107.oblig3.gui.inputcontrols.SearchBar;

@SuppressWarnings("serial")
public abstract class SearchScreen<T> extends Screen {

	protected final SearchBar searchBar;
	protected final EntityCollection<T> dataset;
	protected final JScrollPane scrollPane;
	protected final JPanel buttons;
	
	private Map<String, Function<String, List<T>>> searchOpt = new HashMap<>();

	protected SearchScreen() {
		this.searchBar = new SearchBar((o, s) -> onSearch(o, s));
		this.dataset = getDatasetComponent();
		this.scrollPane = new JScrollPane(dataset.getGuiComponent());
		this.buttons = new JPanel();

		setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		setForeground(UITheme.DEFAULT_TEXT_COLOR);
		
		searchBar.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		
		scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		scrollPane.setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		scrollPane.getVerticalScrollBar().setUnitIncrement(6);
		scrollPane.setPreferredSize(new Dimension(3000, 2000));
		
		buttons.setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		buttons.setBorder(BorderFactory.createLineBorder(
				UITheme.DEFAULT_BACKGROUND_COLOR, 18));
		
		setTopPanel(searchBar);
		setCenterPanel(scrollPane);
		setBottomPanel(buttons);
	}

	protected abstract EntityCollection<T> getDatasetComponent();

	/**
	 * Called when search button is pressed. Gets a list of entities
	 * with the corresponding search method and sends it to the gui component.
	 */
	private void onSearch(String option, String search) {
		List<T> results = searchOpt.get(option).apply(search);
		
		dataset.updateContent(results);
	}

	/**
	 * Adds a new search method, that can be selected by the user.
	 */
	protected void addSearchOption(String option, Function<String, List<T>> getter) {
		searchBar.addSearchOption(option);
		searchOpt.put(option, getter);
	}

	/**
	 * Adds a method to be called when an entity is selected.
	 */
	protected void addSelectionListener(Consumer<T> onSelection) {
		dataset.addSelectionListener(onSelection);
	}

	protected void showNoResultsDialog(String message) {
		JOptionPane.showMessageDialog(this, message, "No results", 
				JOptionPane.ERROR_MESSAGE);
	}
	
	protected JButton addButton(String text, ActionListener onPress, 
			boolean onlyEnabledOnSelection) {
		JButton newButton = new JButton(text);
		
		newButton.addActionListener(onPress);
		newButton.setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		newButton.setForeground(UITheme.DEFAULT_TEXT_COLOR);
		newButton.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.WHITE));
		newButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		newButton.setPreferredSize(new Dimension(200, 40));
		newButton.setFocusPainted(false);
		
		if(onlyEnabledOnSelection) {
			newButton.setEnabled(getSelected() != null);
			addSelectionListener(selected -> {
				newButton.setEnabled(selected != null);
			});
		}
		
		buttons.add(newButton);
		
		return newButton;
	}

	protected String showEditFieldDialog(String field) {
		return JOptionPane.showInputDialog(this, "Insert new " + field);
	}

	public T getSelected() {
		return dataset.getSelected();
	}
	
	public void refresh() {
		searchBar.search();
	}
	
}

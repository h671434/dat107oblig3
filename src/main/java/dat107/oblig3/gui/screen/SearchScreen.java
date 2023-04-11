package dat107.oblig3.gui.screen;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import dat107.oblig3.gui.widget.Widget;

@SuppressWarnings("serial")
public abstract class SearchScreen<T> extends Screen {

	protected final SearchBar searchBar;
	protected final EntityCollection<T> dataset;
	protected final JScrollPane scrollPane;
	protected final JPanel widgets;
	protected final JPanel buttons;
	
	private Map<String, Function<String, List<T>>> searchOptions;
	
	private GridBagConstraints widgetPositions;

	public SearchScreen() {
		this.searchBar = new SearchBar((o, s) -> onSearch(o, s));
		this.dataset = getDatasetComponent();
		this.scrollPane = new JScrollPane(dataset.getGuiComponent());
		this.widgets = new JPanel(new GridBagLayout());
		this.buttons = new JPanel();
		this.searchOptions = new HashMap<>();
		this.widgetPositions = new GridBagConstraints() {{
			ipady = 8;
			ipadx = 16;
			insets = new Insets(8, 0, 8, 16);
			weightx = 1;
			weighty = 1;
			fill = GridBagConstraints.HORIZONTAL;
		}};
		
		configureScreen();
		configureComponents();
		addComponents();
	}
	
	private void configureScreen() {
		setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		setForeground(UITheme.DEFAULT_TEXT_COLOR);
	}
	
	private void configureComponents() {
		searchBar.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		
		scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		scrollPane.setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		scrollPane.getVerticalScrollBar().setUnitIncrement(6);
		scrollPane.setPreferredSize(this.getPreferredSize());
		
		widgets.setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		widgets.setLayout(new GridBagLayout());
		
		buttons.setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		buttons.setBorder(BorderFactory.createLineBorder(
				UITheme.DEFAULT_BACKGROUND_COLOR, 18));
	}
	
	private void addComponents() {
		setTopPanel(searchBar);
		setCenterPanel(scrollPane);
		setRightPanel(widgets);
		setBottomPanel(buttons);
	}

	protected abstract EntityCollection<T> getDatasetComponent();

	private void onSearch(String option, String search) {
		List<T> results = searchOptions.get(option).apply(search);
		
		dataset.updateContent(results);
	}

	protected void addSearchOption(String option, Function<String, List<T>> getter) {
		searchBar.addSearchOption(option);
		searchOptions.put(option, getter);
	}
	

	protected void showNoResultsDialog(String message) {
		JOptionPane.showMessageDialog(this, message, "No results", 
				JOptionPane.ERROR_MESSAGE);
	}

	protected void addSelectionListener(Consumer<T> onSelection) {
		dataset.addSelectionListener(onSelection);
	}
	
	public T getSelected() {
		return dataset.getSelected();
	}
	
	public void refresh() {
		searchBar.search();
	}
	
	protected JButton createScreenButton(String text, ActionListener onPress, 
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
		
		return newButton;
	}
	
	protected void addButton(JButton button) {
		buttons.add(button);
	}
	
	protected void showWidget(Widget widget, int row) {
		widgetPositions.gridy = row;
		widgets.add(widget, widgetPositions);
		
		validate();
	}
	
	protected void hideWidget(Widget widget) {
		widgets.remove(widget);
		
		validate();
	}
	
}

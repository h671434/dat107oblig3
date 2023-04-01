package dat107.oblig3.gui.screen;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import dat107.oblig3.gui.UITheme;
import dat107.oblig3.gui.widget.SearchBar;
import dat107.oblig3.gui.widget.entitysets.EntitySet;

@SuppressWarnings("serial")
public abstract class SearchScreen<T> extends Screen {

	protected final SearchBar searchBar;
	protected final EntitySet<T> dataset;
	protected final JScrollPane scrollPane;
	protected final JPanel buttons;
	
	private Map<String, Function<String, List<T>>> searchOpt = new HashMap<>();

	protected SearchScreen() {
		this.searchBar = new SearchBar((o, s) -> onSearch(o, s));
		this.dataset = getDatasetWidget();
		this.scrollPane = new JScrollPane(dataset.getGuiComponent());
		this.buttons = new JPanel();

		setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		setForeground(UITheme.DEFAULT_TEXT_COLOR);
		
		scrollPane.setPreferredSize(new Dimension(850, 550));
		scrollPane.getVerticalScrollBar().setUnitIncrement(6);
		buttons.setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);

		addTopPanel(searchBar);
		addCenterPanel(scrollPane);
		addBottomPanel(buttons);
	}

	protected abstract EntitySet<T> getDatasetWidget();

	private void onSearch(String option, String search) {
		Function<String, List<T>> results = searchOpt.get(option);
		dataset.updateContent(results.apply(search));
	}

	protected void addSearchOption(String option, Function<String, 
			List<T>> getter) {
		searchBar.addSearchOption(option);
		searchOpt.put(option, getter);
	}

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
		buttons.add(newButton);
		
		if(onlyEnabledOnSelection) {
			newButton.setEnabled(false);
			addSelectionListener(selected -> {
				newButton.setEnabled(selected != null);
			});
		}
		
		return newButton;
	}
	
	protected String showEditFieldDialog(String field) {
		return JOptionPane.showInputDialog(this, "Insert new " + field);
	}

	public T getSelected() {
		return dataset.getSelected();
	}
	
}

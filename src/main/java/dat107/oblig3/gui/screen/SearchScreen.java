package dat107.oblig3.gui.screen;

import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dat107.oblig3.gui.UITheme;
import dat107.oblig3.gui.widget.DataRepresentation;
import dat107.oblig3.gui.widget.SearchBar;

@SuppressWarnings("serial")
public abstract class SearchScreen<T> extends Screen {

	protected final SearchBar searchBar;
	protected final DataRepresentation<T> dataview;
	protected final JPanel buttons;
	private Map<String, Function<String, List<T>>> searchOpt = new HashMap<>();

	protected SearchScreen() {
		this.searchBar = new SearchBar((o, s) -> onSearch(o, s));
		this.dataview = getDataRepresentation();
		this.buttons = new JPanel();

		setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		setForeground(UITheme.DEFAULT_TEXT_COLOR);
		
		buttons.setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);

		addTopPanel(searchBar);
		addCenterPanel(new JScrollPane(dataview.getGuiComponent()));
		addBottomPanel(buttons);
	}

	protected abstract DataRepresentation<T> getDataRepresentation();

	private void onSearch(String option, String search) {
		Function<String, List<T>> results = searchOpt.get(option);
		dataview.updateContent(results.apply(search));
	}

	protected void addSearchOption(String option, Function<String, 
			List<T>> getter) {
		searchBar.addSearchOption(option);
		searchOpt.put(option, getter);
	}

	protected void addSelectionListener(Consumer<T> onSelection) {
		dataview.addSelectionListener(onSelection);
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
		return dataview.getSelected();
	}
	

	

}

package no.hvl.dat107.gui.screen;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import no.hvl.dat107.gui.UITheme;

@SuppressWarnings("serial")
public abstract class SearchScreen<T> extends Screen {

	protected final SearchBar searchBar;
	protected final JTable table;
	protected final DataTableModel tableModel;
	protected final JPanel buttons;
	private Map<String, Function<String, List<T>>> searchOpt = new HashMap<>();

	protected SearchScreen() {
		this.searchBar = new SearchBar((o, s) -> onSearch(o, s));
		this.tableModel = getTableModel();
		this.table = new JTable(tableModel);
		this.buttons = new JPanel();

		setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		setForeground(UITheme.DEFAULT_TEXT_COLOR);

		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setPreferredScrollableViewportSize(new Dimension(850, 550));
		
		buttons.setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);

		addTopPanel(searchBar);
		addCenterPanel(new JScrollPane(table));
		addBottomPanel(buttons);
	}

	protected abstract DataTableModel getTableModel();

	private void onSearch(String option, String search) {
		Function<String, List<T>> results = searchOpt.get(option);
		tableModel.updateContent(results.apply(search));
	}

	protected void addSearchOption(String option, Function<String, 
			List<T>> getter) {
		searchBar.addSearchOption(option);
		searchOpt.put(option, getter);
	}

	protected void addSelectionListener(Consumer<T> onSelection) {
		table.getSelectionModel().addListSelectionListener(e -> {
			onSelection.accept(getSelected());
		});
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
		return JOptionPane.showInputDialog(table, "Insert new " + field);
	}

	public T getSelected() {
		int selectedRow = table.getSelectedRow();
		
		if (selectedRow == -1) {
			return null;
		}
		
		return tableModel.content.get(selectedRow);
	}

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

	public abstract class DataTableModel extends AbstractTableModel {

		private List<T> content = new ArrayList<>();

		public T get(int index) {
			return content.get(index);
		}
		
		public void updateContent(List<T> newContent) {
			table.clearSelection();
			
			this.content = newContent;
			
			fireTableDataChanged();
		}
		
		public boolean isEmpty() {
			return (tableModel == null) || (tableModel.isEmpty());
		}
		
		@Override
		public abstract String getColumnName(int columnIndex);
		
		@Override
		public int getRowCount() {
			return content.size();
		}
				
	}

}

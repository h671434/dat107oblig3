package dat107.oblig3.gui.collection;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 * Costum gui table template.
 * @param <T> Entity-type
 */
@SuppressWarnings("serial")
public abstract class EntityTable<T> extends JTable implements EntityCollection<T> {
	
	protected final EntityTableModel model;
	protected List<T> content;

	public EntityTable() {
		this.model = getTableModel();
		this.content = new ArrayList<>();
		
		configureTable();
	}
	
	private void configureTable() {
		setModel(model);
		setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	}
	 
	protected abstract EntityTableModel getTableModel();
	
	@Override
	public T get(int index) {
		return content.get(index);
	}
	
	@Override
	public T getSelected() {
		int selectedRow = getSelectedRow();
		
		if (selectedRow == -1) {
			return null;
		}
		
		return content.get(selectedRow);
	}
	
	@Override
	public void updateContent(List<T> newContent) {
		clearSelection();
		
		this.content = newContent;
		
		model.fireTableDataChanged();
	}
	
	@Override
	public void addSelectionListener(Consumer<T> onSelection) {
		getSelectionModel().addListSelectionListener(e -> {
			onSelection.accept(getSelected());
		});
	}
	
	@Override
	public boolean isEmpty() {
		return (model == null) || (content.isEmpty());
	}
	
	@Override
	public Component getGuiComponent() {
		return this;
	}
	
	/**
	 * The tablemodel controlls what data goes in the table, and how it is 
	 * collected. Each sub-class of EntityTable has its own implementation.
	 */
	public abstract class EntityTableModel extends AbstractTableModel {
		
		@Override
		public abstract String getColumnName(int index);
		
		@Override
		public int getRowCount() {
			return content.size();
		}
		
	}
	
}

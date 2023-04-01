package dat107.oblig3.gui.widget.entitysets;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public abstract class EntityTable<T> extends JTable implements EntitySet<T> {
	
	protected List<T> content = new ArrayList<>();
	protected DataTableModel model = getTableModel();

	public EntityTable() {
		setModel(model);
		setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		setPreferredScrollableViewportSize(new Dimension(850, 550));
	}
	
	/**
	 * Get the tablemodel for the current table. The tablemodel controlls
	 * what data goes in the table, and how it is collected.
	 */
	protected abstract DataTableModel getTableModel();
	
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
	
	public abstract class DataTableModel extends AbstractTableModel {
		
		@Override
		public abstract String getColumnName(int index);
		
		@Override
		public int getRowCount() {
			return content.size();
		}
		
	}
	
}

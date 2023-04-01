package dat107.oblig3.gui.widget.entitysets;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import dat107.oblig3.gui.UITheme;

@SuppressWarnings("serial")
public abstract class EntityList<T> extends JPanel implements EntitySet<T> {
	
	protected List<Entry> entries = new ArrayList<>();
	private Entry selected = null;
	
	private List<Consumer<T>> selectionListeners = new ArrayList<>();
	
	public EntityList() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
	@Override
	public T get(int index) {
		return entries.get(index).get();
	}

	@Override
	public T getSelected() {
		if (selected == null) {
			return null;
		}
		
		return selected.get();
	}

	@Override
	public void updateContent(List<T> newContent) {
		replaceEntries(newContent);
		validate();
	}
	
	private void replaceEntries(List<T> newContent) {
		for (int i = 0; i < entries.size(); i++) {
			removeEntry(i);
		}
		
		for (int i = 0; i < newContent.size(); i++) {
			Entry entry = createEntry(newContent.get(i));
			
			addEntry(entry);
		}
	}
	
	/*
	 * Returns a ListEntry for the given entity. Used in 
	 * @see #replaceEntries(List<T>)
	 */
	protected abstract Entry createEntry(T entity);
	
	private void addEntry(Entry entry) {
		add(entry);
		entries.add(entry);
	}
	
	public void removeEntry(int index) {
		Entry toRemove = entries.get(index);
		
		remove(toRemove);
		entries.remove(index);
		
		if(toRemove == selected) {
			setSelected(null);
		}
 	}

	/**
	 * Sets selection. null to unselect.
	 */
	public void setSelected(Entry selected) {
		removeSelection();
		
		this.selected = selected;
		
		if (selected != null) {
			selected.select();
		}
		
		notifySelectionListeners();
		
		validate();
	}
	
	/**
	 * Helper method only for @see #setSelected(int). Use setSelected(null) to 
	 * remove selection as this method doesn't notify selectionListeners.
	 */
	private void removeSelection() {
		if (selected != null) {
			selected.unselect();
			selected = null;
		}
	}
	
	private void notifySelectionListeners() {
		selectionListeners.forEach(e -> e.accept(getSelected()));
	}

	@Override
	public void addSelectionListener(Consumer<T> onSelection) {
		selectionListeners.add(onSelection);
	}

	@Override
	public boolean isEmpty() {
		return (entries.isEmpty());
	}

	@Override
	public Component getGuiComponent() {
		return this;
	}
	
	public abstract class Entry extends JPanel {
		
		private static final Border BORDER_SELECTED =
				BorderFactory.createLineBorder(UITheme.TABLE_BORDER_COLOR);
		private static final Border BORDER_UNSELECTED = 
				BorderFactory.createEmptyBorder();
		
		protected T entity;
		
		/*
		 * Needs to be added to every child-component
		 */
		protected EntryClickListener clickListener;
		
		public Entry(T entity) {
			this.entity = entity;
			this.clickListener = new EntryClickListener(this);
			
			setAlignmentX(Component.CENTER_ALIGNMENT);
			
			addMouseListener(clickListener);	
		}
		
		public T get() {
			return entity;
		}
		
		public void select() {
			setBorder(BORDER_SELECTED);
			validate();
		}
		
		public void unselect() {
			setBorder(BORDER_UNSELECTED);
			validate();
		}
		
	}

	/*
	 * Used to set selection on mouseclick.
	 */
	private class EntryClickListener extends MouseAdapter {
		
		private Entry entry;
		
		public EntryClickListener(Entry entry) {
			this.entry = entry;
		}
		 
		@Override
		public void mouseClicked(MouseEvent e) {
			setSelected(entry);
		}
		
	}
	
}

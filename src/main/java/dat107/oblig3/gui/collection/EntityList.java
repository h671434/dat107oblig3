package dat107.oblig3.gui.collection;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Scrollable;
import javax.swing.border.Border;

import dat107.oblig3.gui.UITheme;

/**
 * Costum gui list template.
 * @param <T> Entity-type
 */
@SuppressWarnings("serial")
public abstract class EntityList<T> extends JPanel implements EntityCollection<T>, Scrollable {
	
	protected List<ListEntry> entries = new ArrayList<>();
	private ListEntry selected = null;
	
	private List<Consumer<T>> selectionListeners = new ArrayList<>();
	
	public EntityList() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
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
		
		getParent().getParent().validate();
	}
	
	private void replaceEntries(List<T> newContent) {
		removeAll();
		entries.clear();
		
		for (int i = 0; i < newContent.size(); i++) {
			addEntry(createEntry(newContent.get(i)));
		}
		
		if(newContent.size() == 0) {
			add(emptyListMessage());
		}
	}
	
	protected void addEntry(ListEntry entry) {
		add(entry);
		entries.add(entry);
	}
	
	protected void removeEntry(ListEntry entry) {
		remove(entry);
		entries.remove(entry);
	}
	
	/**
	 * Returns a ListEntry for the given entity. Used in 
	 * @see #replaceEntries(List<T>)
	 */
	protected abstract ListEntry createEntry(T entity);
	
	/**
	 * Returns panel thats shown when list is empty
	 */
	protected JPanel emptyListMessage() {
		JPanel panel = new JPanel(new BorderLayout());
		
		JLabel text = new JLabel("No content found");
		text.setForeground(Color.DARK_GRAY);
		
		panel.add(text);
		
		return panel;
	}

	public void setSelected(ListEntry selected) {
		clearSelection();
		
		this.selected = selected;
		
		if (selected != null) {
			selected.select();
		}
		
		notifySelectionListeners();
		
		validate();
	}
	
	@Override
	public void clearSelection() {
		if (selected != null) {
			selected.unselect();
			selected = null;
		}
		
		validate();
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
	
	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return new Dimension(850, 550);
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect, 
			int orientation, int direction) {
		return 5;
	}

	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect, 
			int orientation, int direction) {
		return 2;
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		return true;
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		return false;
	}
	
	public abstract class ListEntry extends JPanel {
		
		private static final Border BORDER_SELECTED =
				BorderFactory.createLineBorder(UITheme.TABLE_BORDER_COLOR);
		private static final Border BORDER_UNSELECTED = 
				BorderFactory.createEmptyBorder();
		
		protected T entity;
		
		/**
		 * Should to be added to every child-component
		 */
		protected MouseAdapter clickListener;
		
		protected ListEntry() {};
		
		public ListEntry(T entity) {
			this.entity = entity;
			this.clickListener = new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					setSelected(EntityList.ListEntry.this);
				}
			};
			
			addMouseListener(clickListener);
			setAlignmentX(Component.CENTER_ALIGNMENT);	
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
	
}

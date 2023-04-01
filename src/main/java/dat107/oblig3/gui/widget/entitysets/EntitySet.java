package dat107.oblig3.gui.widget.entitysets;

import java.awt.Component;
import java.util.List;
import java.util.function.Consumer;

/**
 * Interface for gui-components which represent sets of data,
 * such as tables and lists.
 */
public interface EntitySet<T> {
	
	/**
	 * Get the dataset item with the given index.
	 */
	T get(int index);
	
	/**
	 * Get the currently selected entity.
	 */
	T getSelected();
	
	/**
	 * Update the data being shown.
	 */
	void updateContent(List<T> newContent);
	
	/**
	 * Add listener to item selection.
	 */
	void addSelectionListener(Consumer<T> onSelection);
	
	/**
	 * Returns if the widget currently has any data.
	 */
	boolean isEmpty();

	/**
	 * Returns itself as a gui component.
	 */
	Component getGuiComponent();
	
}

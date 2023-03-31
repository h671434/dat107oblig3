package dat107.oblig3.gui.widget;

import java.awt.Component;
import java.util.List;
import java.util.function.Consumer;

/**
 * Interface for gui-components which represent sets of data,
 * such as tables and lists.
 */
public interface DataRepresentation<T> {

	Component getGuiComponent();
	
	T get(int index);
	
	T getSelected();
	
	void updateContent(List<T> newContent);
	
	void addSelectionListener(Consumer<T> onSelection);
	
	boolean isEmpty();
}

package no.hvl.dat107.gui;

import javax.swing.SwingUtilities;

/*
 * Class for dynamicly showing or hiding GUI-Components.
 */
public interface DynamicVisibility extends AutoCloseable {

	/*
	 * Add/show the GUI-Component from it's parent panel.
	 */
	void display() throws Exception;
	
	/**
	 * Remove/hide the GUI-Component from it's parent panel.
	 */
	void cache() throws Exception;
	
	/**
	 * Updates the component
	 */
	void revalidate();

	/**
	 * Might become useful
	 */
	@Override
	default void close() throws Exception {
		cache();
	};
	
	default void loadAndDisplay() throws Exception {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					display();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		});
	}

}

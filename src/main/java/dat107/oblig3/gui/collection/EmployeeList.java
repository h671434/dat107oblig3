package dat107.oblig3.gui.collection;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;

import dat107.oblig3.entity.Employee;
import dat107.oblig3.gui.UITheme;

@SuppressWarnings("serial")
public class EmployeeList extends EntityList<Employee> {

	@Override
	protected EntityList<Employee>.ListEntry createEntry(Employee entity) {
		return new EmployeeListEntry(entity);
	}
	
	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return new Dimension(278, getPreferredSize().height);
	}
	
	public class EmployeeListEntry extends EntityList<Employee>.ListEntry {
		
		public EmployeeListEntry(Employee entity) {
			super(entity);
			
			setLayout(new FlowLayout(FlowLayout.LEFT));
			setBackground(UITheme.ALTERNATIVE_BACKGROUND_COLOR);
			removeMouseListener(clickListener);
			
			addIdAndNameLabel();	
		}
		
		private void addIdAndNameLabel() {
			JLabel idAndName = new JLabel(entity.toString());
			
			idAndName.setForeground(UITheme.DEFAULT_TEXT_COLOR);
			
			add(idAndName);
		}
	}
}

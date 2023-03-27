package corp.dbapp.client.gui;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Label;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import corp.dbapp.data.access.EmployeeDAO;
import corp.dbapp.data.model.Employee;

@SuppressWarnings("serial")
public class EmployeesScreen extends Screen {

	private static final String[] SEARCH_OPTIONS = {"Any", "ID", "Username"};

	protected SearchBar searchBar = new SearchBar();
	protected JTable dataTable = new JTable();
	protected JPanel buttonPanel = new JPanel();
	
	private EmployeeDAO dao = new EmployeeDAO();
	
	public EmployeesScreen() {
		
		
		searchBar.setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		buttonPanel.setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
	}
	
	public class EmployeesTableModel extends AbstractTableModel {

		List<Employee> content = new ArrayList<>();
		
		public void setContent(List<Employee> content) {
			this.content = content;
			fireTableDataChanged();
		}
		
		@Override
		public int getRowCount() {
			return 3;
		}

		@Override
		public int getColumnCount() {
			return 0;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Employee e = content.get(rowIndex);
			switch(columnIndex) {
			case 1: 
				return e.getEmployeeId();
			case 2: 
				return e.getUsername();
			case 3: 
				return e.getFirstName();
			case 4: 
				return e.getLastName();
			case 5: 
				return e.getEmploymentDate();
			case 6: 
				return e.getPosition();
			case 7: 
				return e.getDepartmentId();
			}
			return null;
		}
		 
	}


	
}

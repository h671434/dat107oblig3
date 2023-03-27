package corp.dbapp.data.access;

import corp.dbapp.data.model.Department;

public class DepartmentDAO extends DAO<Department> {
	
	@Override
	protected Class<Department> getEntityClass() {
		return Department.class;
	}
	
}

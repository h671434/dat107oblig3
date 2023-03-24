package corp.dbapp.data.access;

import corp.dbapp.data.model.Department;

public class DepartmentDAO extends DataAccess<Department> {
	
	@Override
	protected Class<Department> getEntityClass() {
		return Department.class;
	}
	
}

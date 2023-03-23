package dat107.dataaccess;

import java.util.List;
import java.util.Optional;

public interface DAO <T>{

	Optional<T> get(int id);
	
	List<T> getAll();
	
	List<T> getBy(String field, Object parameter);
	
//	void save();
//	
//	void update();
//	
//	void delete();
}

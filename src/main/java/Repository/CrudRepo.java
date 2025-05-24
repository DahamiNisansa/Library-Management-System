package Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudRepo <T,ID> extends SuperRepo {
    boolean save(T entity) throws SQLException;
    boolean update(T entity) throws SQLException;
    Optional<T> search(ID id) throws SQLException;
    boolean delete(ID id) throws SQLException;
    List<T> getAll();
}

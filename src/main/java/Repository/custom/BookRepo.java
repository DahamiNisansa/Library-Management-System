package Repository.custom;

import Repository.CrudRepo;
import module.Book;
import module.Member;

import java.sql.Connection;
import java.sql.SQLException;

public interface BookRepo extends CrudRepo<Book, String> {
    boolean updateAvailabilityStatus(String bookId, String borrowed, Connection connection) throws SQLException;

}

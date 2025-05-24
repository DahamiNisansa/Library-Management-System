package Repository.custom;

import Repository.CrudRepo;
import module.Book;
import module.BorrowRecords;
import module.Fine;
import module.Member;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface BorrowRepo extends CrudRepo<Book, String> {
    Book searchBookById(String bookId) throws SQLException;

    Member searchById(String memberId) throws SQLException;

    boolean saveTheRecord(BorrowRecords borrowRecord, Connection connection) throws SQLException;

    String getLastBorrowId(Connection connection) throws SQLException;

    boolean hasUnreturnedBooks(String memberId) throws SQLException;

    List<BorrowRecords> getBorrowRecordsByMemberId(String memberId) throws SQLException;

    BorrowRecords getBorrowRecordByBookId(String selectedBookId) throws SQLException;

    boolean updateBorrowRecordAsReturned(String selectedBookId, LocalDate today, Connection connection);

    String getBorrowIdByBookId(String selectedBookId, Connection connection) throws SQLException;

   boolean saveFine(Fine fine, Connection connection) throws SQLException;
}

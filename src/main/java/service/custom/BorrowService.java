package service.custom;

import dto.BookDto;
import dto.MemberDto;
import module.Book;
import module.BorrowRecords;
import module.Member;
import service.SuperService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface BorrowService extends SuperService {
    MemberDto searchMemberById(String memberId) throws SQLException;

    BookDto searchBookById(String bookId) throws SQLException;

    boolean saveBorrowRecord(String memberId, String bookId, LocalDate borrowDate, LocalDate returnDate) throws SQLException;

    boolean hasUnreturnedBooks(String memberId) throws SQLException;

    List<BorrowRecords> getBorrowRecordsByMemberId(String memberId) throws SQLException;

    BorrowRecords getBorrowRecordByBookId(String selectedBookId) throws SQLException;

    boolean returnBookWithFine(String selectedBookId, LocalDate today, double fineAmount) throws SQLException;
}

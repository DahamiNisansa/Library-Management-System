package service.custom.impl;

import Repository.RepoFactory;
import Repository.custom.BookRepo;
import Repository.custom.BorrowRepo;
import db.DBConnection;
import dto.BookDto;
import dto.MemberDto;
import module.Book;
import module.BorrowRecords;
import module.Fine;
import module.Member;
import service.custom.BorrowService;
import util.RepoType;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BorrowServiceImpl implements BorrowService {

    BorrowRepo borrowRepo = RepoFactory.getInstance().getRepoType(RepoType.BORROW);
    BookRepo bookRepo =RepoFactory.getInstance().getRepoType(RepoType.BOOK);

    private static BorrowServiceImpl instance;

    public BorrowServiceImpl() {
    }

    public static BorrowServiceImpl getInstance() {
        return instance == null ? new BorrowServiceImpl() : instance;
    }




    @Override
    public MemberDto searchMemberById(String memberId) throws SQLException {
        Member mem = borrowRepo.searchById(memberId);
        if (mem == null) {
            return null;
        }
        return new MemberDto(mem.getMemId(), mem.getMemName(), mem.getAddress(), mem.getContactNo(), mem.getMembershipDate());
    }




    @Override
    public BookDto searchBookById(String bookId) throws SQLException {
        Book book = borrowRepo.searchBookById(bookId);
        if (book == null) {
            return null;
        }
        return new BookDto(book.getId(), book.getTitle(), book.getIsbn(),
                book.getCategory(), book.getAvailabilityStatus());
    }




    @Override
    public boolean saveBorrowRecord(String memberId, String bookId, LocalDate borrowed_date, LocalDate due_date) throws SQLException {
        //Connection connection = null;

            Connection connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false); // Start transaction

            String borrowId = generateNextBorrowId();

            BorrowRecords borrowRecord = new BorrowRecords(
                    borrowId,
                    memberId,
                    bookId,
                    borrowed_date,
                    due_date,
                    null, // return_date is null
                    false // isReturned is false by default
            );
            boolean isBorrowRecordSaved = borrowRepo.saveTheRecord(borrowRecord, connection);

            // Step 3: Update the book's availability status to 'Borrowed'
            boolean isBookAvailabilityUpdated = bookRepo.updateAvailabilityStatus(bookId, "Borrowed", connection);

            if (isBorrowRecordSaved && isBookAvailabilityUpdated) {
                connection.commit(); // Commit transaction
                return true;
            } else {
                connection.rollback(); // Rollback transaction
                return false;
            }
        } /*catch (SQLException e) {
            if (connection != null) {
                connection.rollback(); // Rollback transaction on error
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true); // Reset auto-commit
                connection.close(); // Close connection
            }
        } */



    private String generateNextBorrowId() throws SQLException {
        //Connection connection = null;
        //try {
            Connection connection = DBConnection.getInstance().getConnection();
            String lastBorrowId = borrowRepo.getLastBorrowId(connection);

            if (lastBorrowId == null) {
                return "BR001";
            } else {

                int lastNumber = Integer.parseInt(lastBorrowId.substring(2));
                int nextNumber = lastNumber + 1;


                return String.format("BR%02sd", nextNumber);
            }
         /*}finally {
            if (connection != null) {
                connection.close();
            }
        } */

    }




    @Override
    public boolean hasUnreturnedBooks(String memberId) throws SQLException {
        return borrowRepo.hasUnreturnedBooks(memberId);
    }




    @Override
    public List<BorrowRecords> getBorrowRecordsByMemberId(String memberId) throws SQLException {
        List<BorrowRecords> borrowRecordsByMemberId = borrowRepo.getBorrowRecordsByMemberId(memberId);
        List<BorrowRecords> borrowRecordsList = new ArrayList<>();

        for (BorrowRecords entity : borrowRecordsByMemberId) {
            BorrowRecords record1 = new BorrowRecords();
            record1.setBorrowId(entity.getBorrowId());
            record1.setBook_id(entity.getBook_id());
            record1.setMember_id(entity.getMember_id());
            record1.setBorrowed_date(entity.getBorrowed_date());
            record1.setDue_date(entity.getDue_date());
            record1.setReturn_date(entity.getReturn_date());

            borrowRecordsList.add(record1);
        }
        return borrowRecordsList;
    }




    @Override
    public BorrowRecords getBorrowRecordByBookId(String selectedBookId) throws SQLException {
        BorrowRecords borrowRecordByBookId = borrowRepo.getBorrowRecordByBookId(selectedBookId);

        if (borrowRecordByBookId == null) {
            return null;
        }

        BorrowRecords record2 = new BorrowRecords();
        record2.setBorrowId(borrowRecordByBookId.getBorrowId());
        record2.setBook_id(borrowRecordByBookId.getBook_id());
        record2.setMember_id(borrowRecordByBookId.getMember_id());
        record2.setBorrowed_date(borrowRecordByBookId.getBorrowed_date());
        record2.setDue_date(borrowRecordByBookId.getDue_date());

        return record2;
    }




    @Override
    public boolean returnBookWithFine(String selectedBookId, LocalDate today, double fineAmount) throws SQLException {
        //Connection connection = null;
        //try {
            Connection connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false); // Start transaction

            // Step 1: Get the BorrowID for the selected book
            String borrowId = borrowRepo.getBorrowIdByBookId(selectedBookId, connection);

            if (borrowId == null) {
                throw new SQLException("No borrow record found!");
            }

            // Step 2: Update borrow record as returned
            boolean isBorrowUpdated = borrowRepo.updateBorrowRecordAsReturned(selectedBookId, today, connection);

            // Step 3: Update book availability status to "Available"
            boolean isBookUpdated = bookRepo.updateAvailabilityStatus(selectedBookId, "Available", connection);

            // Step 4: Save fine record if applicable
            boolean isFineSaved = true;
            if (fineAmount > 0) {
                Fine fine = new Fine();
                fine.setBorrow_record_id(borrowId); // Use the fetched BorrowID
                fine.setFineAmount(BigDecimal.valueOf(fineAmount));
                fine.setPaymentDate(LocalDate.now());
                isFineSaved = borrowRepo.saveFine(fine, connection);
            }


            if (isBorrowUpdated && isBookUpdated && isFineSaved) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }
        /*} catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        } */
    }
}

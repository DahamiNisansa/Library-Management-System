package Repository.custom.impl;

import Repository.custom.BorrowRepo;
import db.DBConnection;
import module.Book;
import module.BorrowRecords;
import module.Fine;
import module.Member;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BorrowRepoImpl implements BorrowRepo {

    String allMembers = "SELECT * FROM member WHERE memId = ?";
    String allBooks = "SELECT * FROM book WHERE bookID = ? AND availabilityStatus = 'Available'";

    @Override
    public boolean save(Book entity) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Book entity) throws SQLException {
        return false;
    }

    @Override
    public Optional<Book> search(String s) throws SQLException {
        return Optional.empty();
    }


    @Override
    public boolean delete(String s) throws SQLException {
        return false;
    }

    @Override
    public List<Book> getAll() {
        return List.of();
    }


    @Override
    public Book searchBookById(String bookId) throws SQLException {
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(allBooks)) {

            preparedStatement.setString(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Book(
                        resultSet.getString("bookID"),
                        resultSet.getString("title"),
                        resultSet.getString("isbn"),
                        resultSet.getString("Category"),
                        resultSet.getString("AvailabilityStatus")
                );
            }
        }
        return null;
    }




    @Override
    public Member searchById(String memberId) throws SQLException {
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(allMembers)) {

            preparedStatement.setString(1, memberId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Member(
                        resultSet.getString("memId"),
                        resultSet.getString("memName"),
                        resultSet.getString("address"),
                        resultSet.getString("contactNo"),
                        resultSet.getString("membership_Date")
                );
            }
        }
        return null;
    }




    @Override
    public boolean saveTheRecord(BorrowRecords borrowRecord, Connection connection) throws SQLException {
        String sql = "INSERT INTO borrow_records (id, book_id, member_id, borrowed_date, due_date, return_date, isReturned) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, borrowRecord.getBorrowId());
            pstm.setString(2, borrowRecord.getBook_id());
            pstm.setString(3, borrowRecord.getMember_id());
            pstm.setDate(4, Date.valueOf(borrowRecord.getBorrowed_date()));
            pstm.setDate(5, Date.valueOf(borrowRecord.getDue_date()));
            pstm.setDate(6, Date.valueOf(borrowRecord.getReturn_date()));
            pstm.setBoolean(7, borrowRecord.isReturned()); // isReturned is false by default

            return pstm.executeUpdate() > 0; // Return true if the record is saved successfully
        }
    }




    @Override
    public String getLastBorrowId(Connection connection) throws SQLException {
        String sql = "SELECT id FROM borrow_records ORDER BY id DESC LIMIT 1";

        try (PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getString("id");
            }
        }
        return null;
    }




    @Override
    public boolean hasUnreturnedBooks(String memberId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM borrow_records WHERE member_id = ? AND isReturned = 0";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setString(1, memberId);
            ResultSet resultSet = pstm.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
        return false;
    }




    @Override
    public List<BorrowRecords> getBorrowRecordsByMemberId(String memberId) throws SQLException {
        String sql = "SELECT * FROM borrow_records WHERE member_id = ? AND isReturned = 0";
        List<BorrowRecords> borrowRecords = new ArrayList<>();

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setString(1, memberId);
            ResultSet resultSet = pstm.executeQuery();

            while (resultSet.next()) {
                BorrowRecords borrowRecord = new BorrowRecords(
                        resultSet.getString("id"),
                        resultSet.getString("book_id"),
                        resultSet.getString("member_id"),
                        resultSet.getDate("borrowed_date").toLocalDate(),
                        resultSet.getDate("due_date") != null ? resultSet.getDate("due_date").toLocalDate() : null,
                        resultSet.getDate("return_date").toLocalDate(),
                        resultSet.getBoolean("isReturned")
                );
                borrowRecords.add(borrowRecord);
            }
        }
        return borrowRecords;
    }




    @Override
    public BorrowRecords getBorrowRecordByBookId(String selectedBookId) throws SQLException {
        String sql = "SELECT * FROM borrow_records WHERE book_id = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setString(1, selectedBookId);
            ResultSet resultSet = pstm.executeQuery();

            if (resultSet.next()) {
                return new BorrowRecords(
                        resultSet.getString("id"),
                        resultSet.getString("book_id"),
                        resultSet.getString("member_id"),
                        resultSet.getDate("borrowed_date").toLocalDate(),
                        resultSet.getDate("due_date") != null ? resultSet.getDate("due_date").toLocalDate() : null,
                        resultSet.getDate("return_date").toLocalDate(),
                        resultSet.getBoolean("isReturned")
                );
            }
        }
        return null; // No borrow record found
    }




    @Override
    public boolean updateBorrowRecordAsReturned(String selectedBookId, LocalDate today, Connection connection) {
        String sql = "UPDATE borrow_records SET isReturned = 1, return_date = ? WHERE book_id = ?";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setDate(1, Date.valueOf(today));
            pstm.setString(2, selectedBookId);
            return pstm.executeUpdate() > 0; // Return true if the update is successful
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    @Override
    public String getBorrowIdByBookId(String selectedBookId, Connection connection) throws SQLException {
        String sql = "SELECT BorrowID FROM borrow_records WHERE book_id = ? AND isReturned = 0";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, selectedBookId);
            ResultSet resultSet = pstm.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("id");
            }
        }
        return null;
    }




    @Override
    public boolean saveFine(Fine fine, Connection connection) throws SQLException {
        String sql = "INSERT INTO fine (borrow_record_id, fineAmount, paymentDate) VALUES (?, ?, ?)";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, fine.getBorrow_record_id());
            pstm.setBigDecimal(2, fine.getFineAmount());
            pstm.setDate(3, Date.valueOf(fine.getPaymentDate()));

            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting fine: " + e.getMessage());
            throw e;
        }
    }
}

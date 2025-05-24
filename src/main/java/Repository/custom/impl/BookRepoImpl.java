package Repository.custom.impl;

import Repository.custom.BookRepo;
import db.DBConnection;
import module.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepoImpl implements BookRepo {

    @Override
    public boolean save(Book entity) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("INSERT INTO book(bookID, title, isbn, Category, availabilityStatus)" + "VALUES (?,?,?,?,?)");

        ps.setString(1, entity.getId());
        ps.setString(2, entity.getTitle());
        ps.setString(3, entity.getIsbn());
        ps.setString(4, entity.getCategory());
        ps.setString(5, entity.getAvailabilityStatus());
        int affectedRows = ps.executeUpdate();
        return affectedRows > 0;
    }



    @Override
    public boolean update(Book entity) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("UPDATE book SET title = ? , isbn = ? , Category = ? , availabilityStatus = ? WHERE bookID = ?");

        ps.setString(1, entity.getId());
        ps.setString(2, entity.getTitle());
        ps.setString(3, entity.getIsbn());
        ps.setString(4, entity.getCategory());
        ps.setString(5, entity.getAvailabilityStatus());

        return ps.executeUpdate()>0;
    }



    @Override
    public Optional<Book> search(String bookId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM book WHERE bookID = ?");
        ps.setString(1,bookId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            String id = rs.getString(1);
            String title = rs.getString(2);
            String isbn = rs.getString(3);
            String price = rs.getString(4);
            String availabilityStatus = rs.getString(5);

            Book book = new Book(id,title,isbn,price,availabilityStatus);
            return Optional.of(book);
        }
        return Optional.empty();
    }



    @Override
    public boolean delete(String id) throws SQLException{
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("DELETE FROM book WHERE bookID = ?");

        ps.setString(1, id);
        int affectedRows = ps.executeUpdate();
        return affectedRows > 0;
    }



    @Override
    public List<Book> getAll() {
        ArrayList<Book> bookArrayList = new ArrayList<>();

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM book");

            while (resultSet.next()){
                Book book = new Book(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                );
                bookArrayList.add(book);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return bookArrayList;
    }




    @Override
    public boolean updateAvailabilityStatus(String bookId, String borrowed, Connection connection) throws SQLException {
        String sql = "UPDATE book SET availabilityStatus = ? WHERE bookID = ?";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, borrowed);
            pstm.setString(2, bookId);

            return pstm.executeUpdate() > 0; // Return true if the update is successful
        }
    }
}

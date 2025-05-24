package service.custom;

import dto.BookDto;
import dto.MemberDto;
import exceptions.MemberExceptions;
import service.SuperService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BookService extends SuperService {
    boolean addBook (BookDto book) throws SQLException;
    boolean updateBook (BookDto book) throws SQLException;
    boolean deleteMBook (String bId) throws SQLException, MemberExceptions;
    Optional<BookDto> searchBook (String bName);
    List<BookDto> getAll();
}

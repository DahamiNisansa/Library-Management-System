package service.custom.impl;

import Repository.RepoFactory;
import Repository.custom.BookRepo;
import Repository.custom.MemberRepo;
import dto.BookDto;
import dto.MemberDto;
import exceptions.MemberExceptions;
import module.Book;
import module.Member;
import service.custom.BookService;
import util.RepoType;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookServiceImpl implements BookService {

    BookRepo bookRepo = RepoFactory.getInstance().getRepoType(RepoType.BOOK);

    @Override
    public boolean addBook(BookDto book) throws SQLException {
        return bookRepo.save(convertToEntity(book));
    }


    @Override
    public boolean updateBook(BookDto book) throws SQLException {
        return bookRepo.update(convertToEntity(book));
    }


    @Override
    public boolean deleteMBook(String bId) throws SQLException, MemberExceptions {
        return bookRepo.delete(bId);
    }


    @Override
    public Optional<BookDto> searchBook(String bId) {
        try{
            Optional<Book> book = bookRepo.search(bId);

            if (book.isPresent()){
                BookDto bookDto = convertToDto(book.get());
                return Optional.of(bookDto);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }


    @Override
    public List<BookDto> getAll() {
        List<Book> books = bookRepo.getAll();
        return books.stream()
                .map(book -> new BookDto(
                        book.getId(),
                        book.getTitle(),
                        book.getIsbn(),
                        book.getCategory(),
                        book.getAvailabilityStatus()
                ))
                .collect(Collectors.toList());
    }



    private Book convertToEntity(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setTitle(bookDto.getTitle());
        book.setIsbn(bookDto.getIsbn());
        book.setCategory(bookDto.getCategory());
        book.setAvailabilityStatus(bookDto.getIsbn());

        return book;
    }

    private BookDto convertToDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setCategory(book.getCategory());
        bookDto.setAvailabilityStatus(book.getIsbn());

        return bookDto;
    }
}

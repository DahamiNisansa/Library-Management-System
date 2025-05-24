package controller.book;

import com.jfoenix.controls.JFXTextField;
import dto.BookDto;
import dto.MemberDto;
import exceptions.MemberExceptions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import service.ServiceFactory;
import service.custom.BookService;
import util.ServiceType;

import java.sql.SQLException;
import java.util.Optional;

public class BookManagementFormController {

    public JFXTextField txtBookAvailability;
    public TableColumn colAvailable;
    public TableColumn colCategory;
    public JFXTextField txtBookCategory;


    @FXML
    private TableColumn<?, ?> colBookID;

    @FXML
    private TableColumn<?, ?> colBookTitle;



    @FXML
    private TableColumn<?, ?> colISBN;





    @FXML
    private TableView<BookDto> tblBookTable;



    @FXML
    private JFXTextField txtBookID;



    @FXML
    private JFXTextField txtBookSearch;

    @FXML
    private JFXTextField txtBookTitle;

    @FXML
    private JFXTextField txtISBN;

    BookService bookService = ServiceFactory.getInstance().getBoType(ServiceType.BOOK);

    @FXML
    void btnBookClearOnAction(ActionEvent event) {
        clearFields();
    }




    @FXML
    void btnBookDeleteOnAction(ActionEvent event) {
        String bookId = txtBookID.getText();
        boolean delete = false;
        String errorMsg = "Cancelled - Not Deleted";
        Alert areUSure = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure", ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> btnType = areUSure.showAndWait();

        if(btnType.isPresent()){
            ButtonType pressedBtn = btnType.get();
            if (pressedBtn.equals(ButtonType.YES)){
                try {
                    delete = bookService.deleteMBook(bookId);
                    if (!delete) {
                        errorMsg = "Book Not Found - Check ID";
                    }
                }catch (SQLException | MemberExceptions e){
                    errorMsg = e.getMessage();
                }
            }
        }

        if (delete){
            new Alert(Alert.AlertType.INFORMATION,"Book Deleted Successfully").show();
        }else {
            new Alert(Alert.AlertType.ERROR,errorMsg).show();
        }

        loadTable();
    }




    @FXML
    void btnBookReloadOnAction(ActionEvent event) {
        loadTable();
    }


    private void loadTable() {
        colBookID.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        colBookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colISBN.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("Category"));
        colAvailable.setCellValueFactory(new PropertyValueFactory<>("availabilityStatus"));

        ObservableList<BookDto> bookObservableList = FXCollections.observableArrayList();

        //add arraylist objects into observable list from getAll() method in UserController class
        bookObservableList.addAll(bookService.getAll());

        //add observable list objects into user table
        tblBookTable.setItems(bookObservableList);

    }




    @FXML
    void btnBookSaveOnAction(ActionEvent event) throws SQLException {
        if (txtBookID.getText().isEmpty() || txtBookTitle.getText().isEmpty() || txtISBN.getText().isEmpty() ||
                txtBookCategory.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "All fields must be filled!").show();

        }else {

            BookDto newBook = new BookDto(
                    txtBookID.getText(),
                    txtBookTitle.getText(),
                    txtISBN.getText(),
                    txtBookCategory.getText(),
                    txtBookAvailability.getText()
            );

            boolean isAdded = bookService.addBook(newBook);

            if (isAdded) {
                new Alert(Alert.AlertType.INFORMATION, "Book added successfully!").show();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to add Book!").show();
            }
        }

        loadTable();
    }




    @FXML
    void btnBookSearchOnAction(ActionEvent event) {
        Optional<BookDto> searchBook = bookService.searchBook(txtBookSearch.getText());
        if (searchBook.isPresent()){
            setDataToFields(searchBook.get());
        }else {
            new Alert(Alert.AlertType.ERROR,"Member Not Found").show();
        }
    }


    public void setDataToFields(BookDto bookDto){
        txtBookID.setText(bookDto.getId());
        txtBookTitle.setText(bookDto.getTitle());
        txtISBN.setText(bookDto.getIsbn());
        txtBookCategory.setText(bookDto.getCategory());
        txtBookAvailability.setText(bookDto.getAvailabilityStatus());
    }



    private void clearFields() {
        txtBookID.clear();
        txtBookTitle.clear();
        txtISBN.clear();
        txtBookCategory.clear();
        txtBookSearch.clear();
        txtBookAvailability.clear();
    }



    @FXML
    void btnBookUpdateOnAction(ActionEvent event) {
        String bookID = txtBookID.getText();
        String title = txtBookTitle.getText();
        String isbn = txtISBN.getText();
        String category = txtBookCategory.getText();
        String available = txtBookAvailability.getText();

        BookDto bookDto = new BookDto(bookID,title,isbn,category,available);

        boolean isUpdate = false;
        String errorMsg = "Data is already added !";
        try{
            isUpdate = bookService.updateBook(bookDto);
        }catch (SQLException e){
            errorMsg = e.getMessage();
        }

        if (isUpdate){
            new Alert(Alert.AlertType.INFORMATION,"Book Updated Successfully").show();
        }else {
            new Alert(Alert.AlertType.ERROR,errorMsg).show();
        }

        loadTable();
    }

}

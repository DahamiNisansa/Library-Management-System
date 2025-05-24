package controller.Borrow;

import com.jfoenix.controls.JFXTextField;
import dto.BookDto;
import dto.MemberDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import service.ServiceFactory;
import service.custom.BorrowService;
import util.ServiceType;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class BorrowBookFormController {

    public JFXTextField txtBorrowMemId;
    @FXML
    private JFXTextField TXTBorrowMemId;

    @FXML
    private DatePicker dtpBorrowDate;

    @FXML
    private DatePicker dtpReturnDate;

    @FXML
    private JFXTextField txtBorrowBookId;

    @FXML
    private JFXTextField txtBorrowBookTitle;

    @FXML
    private JFXTextField txtBorrowContact;

    @FXML
    private JFXTextField txtBorrowIsbn;

    @FXML
    private JFXTextField txtBorrowMemName;


    BorrowService borrowService= ServiceFactory.getInstance().getBoType(ServiceType.BORROW);

    @FXML
    void btnBorrowBookOnAction(ActionEvent event) {
        if (txtBorrowMemId.getText().isEmpty() ||
                txtBorrowMemName.getText().isEmpty() ||
                txtBorrowContact.getText().isEmpty() ||
                txtBorrowBookId.getText().isEmpty() ||
                txtBorrowBookTitle.getText().isEmpty() ||
                txtBorrowIsbn.getText().isEmpty() ||
                dtpBorrowDate.getValue() == null ||
                dtpReturnDate.getValue() == null) {

            // Show alert if any field is empty
            new Alert(Alert.AlertType.WARNING, "Please fill in all fields!").show();
        } else {
            try {
                // Get input values
                String memberId = txtBorrowMemId.getText();
                String bookId = txtBorrowBookId.getText();
                LocalDate borrowDate = dtpBorrowDate.getValue(); // Parse borrow date
                LocalDate returnDate = dtpReturnDate.getValue(); // Get return date from DatePicker

                // Call the service layer to save the borrow record
                boolean isSaved = borrowService.saveBorrowRecord(memberId, bookId, borrowDate, returnDate);

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Book borrowed successfully!").show();
                    clearForm();
                    //loadTodayDate();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to borrow the book!").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
            }
        }
    }




    @FXML
    void btnBorrowBookSearchOnAction(ActionEvent event) {
        try {
            String bookId = txtBorrowBookId.getText().trim();

            if (bookId.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter a book ID to search.").show();
                return;
            }

            BookDto book = borrowService.searchBookById(bookId);

            if (book != null) {
                txtBorrowBookId.setText(book.getId());
                txtBorrowBookTitle.setText(book.getTitle());
                txtBorrowIsbn.setText(book.getIsbn());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "No book found with ID: " + bookId).show();
                clearForm();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred while searching for the book.").show();
        }
    }




    @FXML
    void btnBorrowClearOnAction(ActionEvent event) {
        clearForm();
    }




    @FXML
    void btnBorrowMemSearchOnAction(ActionEvent event) throws SQLException {
        // Get the member ID from the search field
        String memberId = txtBorrowMemId.getText().trim();

        if (memberId.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter a member ID to search.").show();
            return;
        }


        MemberDto member = borrowService.searchMemberById(memberId);

        if (member != null) {
            txtBorrowMemId.setText(member.getMemId());
            txtBorrowMemName.setText(member.getMemName());
            txtBorrowContact.setText(member.getContactNo());
        } else {
            new Alert(Alert.AlertType.INFORMATION, "No member found with ID: " + memberId).show();
            clearForm();
        }
    }



    public void btnBorrowReturnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Return_Book_Form.fxml"))));
        stage.show();
    }



    private void clearForm() {
        txtBorrowMemId.clear();
        txtBorrowMemName.clear();
        txtBorrowContact.clear();
        txtBorrowBookId.clear();
        txtBorrowBookTitle.clear();
        txtBorrowIsbn.clear();
    }



}

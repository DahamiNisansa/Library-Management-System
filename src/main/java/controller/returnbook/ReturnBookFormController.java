package controller.returnbook;

import com.jfoenix.controls.JFXTextField;
import dto.BorrowRecordsDto;
import dto.MemberDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import module.BorrowRecords;
import service.ServiceFactory;
import service.custom.BorrowService;
import service.custom.MemberService;
import util.ServiceType;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ReturnBookFormController {

    @FXML
    private ComboBox<?> cmbReturnSelectBook;

    @FXML
    private TableColumn<?, ?> colReturnBook;

    @FXML
    private TableColumn<?, ?> colReturnBookID;

    @FXML
    private TableColumn<?, ?> colReturnBorrowBook;

    @FXML
    private TableView<?> tblReturnBook;

    @FXML
    private JFXTextField txtReturnContact;

    @FXML
    private JFXTextField txtReturnMemId;

    @FXML
    private JFXTextField txtReturnMemName;

    @FXML
    private JFXTextField txtReturnShowFine;

    BorrowService borrowService = ServiceFactory.getInstance().getBoType(ServiceType.BORROW);
    MemberService memberService = ServiceFactory.getInstance().getBoType(ServiceType.MEMBER);

    @FXML
    void btnReturnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnReturnMemSearchOnAction(ActionEvent event) {
        String memberId = txtReturnMemId.getText();

        if (memberId.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter a Member ID!").show();
            return;
        }

        try {

            MemberDto member = memberService.searchMemberById(memberId);

            if (member == null) {
                new Alert(Alert.AlertType.WARNING, "Member not found!").show();
                txtReturnMemName.clear();
                txtReturnContact.clear();
                return;
            }

            boolean hasUnreturnedBooks = borrowService.hasUnreturnedBooks(memberId);

            if (!hasUnreturnedBooks) {
                new Alert(Alert.AlertType.WARNING, "This member has no unreturned books!").show();
                txtReturnMemName.clear();
                txtReturnContact.clear();
                return;
            }

            txtReturnMemName.setText(member.getMemName());
            txtReturnContact.setText(member.getContactNo());

            loadTable(memberId);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
        }
    }




    private void loadTable(String memberId) throws SQLException {
        // Fetch borrow records for the member
        List<BorrowRecords> borrowRecords = borrowService.getBorrowRecordsByMemberId(memberId);


        //tblReturnBook.getItems().setAll(borrowRecords);

        cmbReturnSelectBook.getItems().clear(); // Clear existing items
        for (BorrowRecords record : borrowRecords) {
            //cmbReturnSelectBook.getItems().add(record.getBookId()); // Add book IDs
        }

    }




    @FXML
    void btnReturnPayNow(ActionEvent event) {

        /*if (cmbReturnSelectBook.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please select a book to return!").show();
            return;
        }


        String selectedBookId = (String) cmbReturnSelectBook.getSelectionModel().getSelectedItem();

        try {

            BorrowRecords borrowRecord = borrowService.getBorrowRecordByBookId(selectedBookId);

            if (borrowRecord == null) {
                new Alert(Alert.AlertType.ERROR, "Borrow record not found for the selected book!").show();
                return;
            }

            /
            LocalDate returnDate = borrowService.getReturnDate();
            LocalDate today = LocalDate.now();
            double fineAmount = 0;

            if (returnDate.isBefore(today)) {
                long overdueDays = ChronoUnit.DAYS.between(returnDate, today);
                fineAmount = overdueDays * 10;
            }


            boolean isReturned = borrowService.returnBookWithFine(selectedBookId, today, fineAmount);

            if (isReturned) {
                new Alert(Alert.AlertType.INFORMATION, "Book returned successfully!").show();
                txtReturnShowFine.clear();
                loadTable(txtReturnMemId.getText()); // Reload the table
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to return the book!").show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
        }*/
    }

    @FXML
    void btnReturnShowFine(ActionEvent event) throws SQLException {
        showfine();
    }


    public void showfine() throws SQLException {
        /*if (cmbReturnSelectBook.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please select a book to return!").show();
            return;
        }

        String selectedBookId = (String) cmbReturnSelectBook.getSelectionModel().getSelectedItem();

        BorrowRecords borrowRecord = borrowService.getBorrowRecordByBookId(selectedBookId);

        if (borrowRecord == null) {
            new Alert(Alert.AlertType.ERROR, "Borrow record not found for the selected book!").show();
            return;
        }

        LocalDate returnDate = borrowService.getReturnDate();
        LocalDate today = LocalDate.now();
        double fineAmount = 0;

        if (returnDate.isBefore(today)) {
            long overdueDays = ChronoUnit.DAYS.between(returnDate, today);
            fineAmount = overdueDays * 10;
            txtReturnShowFine.setText(String.valueOf(fineAmount));
        } */
    }

}

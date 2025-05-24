package controller.user;

import com.jfoenix.controls.JFXTextField;
import dto.MemberDto;
import exceptions.MemberExceptions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import module.Member;
import service.ServiceFactory;
import service.custom.MemberService;
import util.ServiceType;

import java.sql.SQLException;
import java.util.Optional;

public class UserFormController {

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContactNo;

    @FXML
    private TableColumn<?, ?> colMembershipDate;

    @FXML
    private TableColumn<?, ?> colUserID;

    @FXML
    private TableColumn<?, ?> colUserName;

    @FXML
    private TableView<MemberDto> tblUserTable;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtContactNo;

    @FXML
    private JFXTextField txtMembershipDate;

    @FXML
    private JFXTextField txtUserID;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    private JFXTextField txtUserSearch;


    MemberService memberService = ServiceFactory.getInstance().getBoType(ServiceType.MEMBER);


    @FXML
    void btnUserDeleteOnAction(ActionEvent event) {
        String memId = txtUserID.getText();
        boolean delete = false;
        String errorMsg = "Cancelled - Not Deleted";
        Alert areUSure = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure", ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> btnType = areUSure.showAndWait();

        if(btnType.isPresent()){
            ButtonType pressedBtn = btnType.get();
            if (pressedBtn.equals(ButtonType.YES)){
               try {
                   delete = memberService.deleteMember(memId);
                   if (!delete) {
                       errorMsg = "User Not Found - Check ID";
                   }
               }catch (MemberExceptions | SQLException e){
                   errorMsg = e.getMessage();
               }
            }
        }

        if (delete){
            new Alert(Alert.AlertType.INFORMATION,"Member Deleted Successfully").show();
        }else {
            new Alert(Alert.AlertType.ERROR,errorMsg).show();
        }

        loadTable();
    }


    @FXML
    void btnUserSaveOnAction(ActionEvent event) throws SQLException {
        // Validate input fields
        if (txtUserID.getText().isEmpty() || txtUserName.getText().isEmpty() || txtContactNo.getText().isEmpty() ||
                txtAddress.getText().isEmpty() || txtMembershipDate.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "All fields must be filled!").show();

        }else {

            // Create member object with input values
            MemberDto newMem = new MemberDto(
                    txtUserID.getText(),
                    txtUserName.getText(),
                    txtContactNo.getText(),
                    txtAddress.getText(),
                    txtMembershipDate.getText()
            );

            // Call addUser method to save user
            boolean isAdded = memberService.addMember(newMem);

            if (isAdded) {
                new Alert(Alert.AlertType.INFORMATION, "User added successfully!").show();
                clearFields(); // Clear input fields after successful insertion
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to add user!").show();
            }
        }

        loadTable();

    }


    @FXML
    void btnUserUpdateOnAction(ActionEvent event) {
        String memberId = txtUserID.getText();
        String name = txtUserName.getText();
        String contact = txtContactNo.getText();
        String address = txtAddress.getText();
        String membershipDate = txtMembershipDate.getText();

        MemberDto memDto = new MemberDto(memberId,name,contact,address,membershipDate);

        boolean isUpdate = false;
        String errorMsg = "Data is already added !";
        try{
            isUpdate = memberService.updateMember(memDto);
        }catch (SQLException e){
            errorMsg = e.getMessage();
        }

        if (isUpdate){
            new Alert(Alert.AlertType.INFORMATION,"Member Updated Successfully").show();
        }else {
            new Alert(Alert.AlertType.ERROR,errorMsg).show();
        }

        loadTable();
    }


    @FXML
    void btnUserReloadOnAction(ActionEvent event) {
        loadTable();
    }


    private void loadTable() {
        //set object data into table columns
        colUserID.setCellValueFactory(new PropertyValueFactory<>("memId"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("memName"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colMembershipDate.setCellValueFactory(new PropertyValueFactory<>("membershipDate"));

        ObservableList<MemberDto> userObservableList = FXCollections.observableArrayList();

        //add arraylist objects into observable list from getAll() method in UserController class
        userObservableList.addAll(memberService.getAll());

        //add observable list objects into user table
        tblUserTable.setItems(userObservableList);

    }


    public void btnUserSearchOnAction(ActionEvent actionEvent) {
        Optional<MemberDto> searchMem = memberService.searchMember(txtUserSearch.getText());
        if (searchMem.isPresent()){
            setDataToFields(searchMem.get());
        }else {
            new Alert(Alert.AlertType.ERROR,"Member Not Found").show();
        }

    }


    public void setDataToFields(MemberDto memDto){
        txtUserID.setText(memDto.getMemId());
        txtUserName.setText(memDto.getMemName());
        txtAddress.setText(memDto.getAddress());
        txtContactNo.setText(memDto.getContactNo());
        txtMembershipDate.setText(memDto.getMembershipDate());
    }


    private void clearFields() {
        txtUserID.clear();
        txtUserName.clear();
        txtContactNo.clear();
        txtAddress.clear();
        txtMembershipDate.clear();
        txtUserSearch.clear();
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }
}

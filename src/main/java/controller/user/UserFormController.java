package controller.user;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import module.Users;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
    private TableView<Users> tblUserTable;

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

    @FXML
    void btnUserDeleteOnAction(ActionEvent event) {

    }



    @FXML
    void btnUserSaveOnAction(ActionEvent event) {
        // Validate input fields
        if (txtUserID.getText().isEmpty() || txtUserName.getText().isEmpty() || txtContactNo.getText().isEmpty() ||
                txtAddress.getText().isEmpty() || txtMembershipDate.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "All fields must be filled!").show();

        }else {

            // Create Users object with input values
            Users newUser = new Users(
                    Integer.parseInt(txtUserID.getText()),
                    txtUserName.getText(),
                    txtContactNo.getText(),
                    txtAddress.getText(),
                    txtMembershipDate.getText()
            );

            // Call addUser method to save user
            boolean isAdded = new UserController().addUser(newUser);

            if (isAdded) {
                new Alert(Alert.AlertType.INFORMATION, "User added successfully!").show();
                clearFields(); // Clear input fields after successful insertion
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to add user!").show();
            }
        }

    }

    private void clearFields() {
        txtUserName.clear();
        txtContactNo.clear();
        txtAddress.clear();
        txtMembershipDate.clear();
    }



    @FXML
    void btnUserUpdateOnAction(ActionEvent event) {

    }


    //Load all user data from database to user table
    @FXML
    void btnUserReloadOnAction(ActionEvent event) {
        loadTable();
    }

    private void loadTable() {
        //set object data into table columns
        colUserID.setCellValueFactory(new PropertyValueFactory<>("uid"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("uName"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colMembershipDate.setCellValueFactory(new PropertyValueFactory<>("membershipDate"));

        ObservableList<Users> userObservableList = FXCollections.observableArrayList();

        //add arraylist objects into observable list from getAll() method in UserController class
        userObservableList.addAll(new UserController().getAll());

        //add observable list objects into user table
        tblUserTable.setItems(userObservableList);

    }

    public void btnUserSearchOnAction(ActionEvent actionEvent) {
    }
}

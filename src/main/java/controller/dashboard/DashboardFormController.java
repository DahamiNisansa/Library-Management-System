package controller.dashboard;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import module.Admin;
import org.jasypt.util.text.BasicTextEncryptor;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import javafx.event.ActionEvent;

public class DashboardFormController {

    public AnchorPane LoadFormContent;

    @FXML
    void btnBooksOnAction(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/Book_Management_Form.fxml");
        assert resource != null;
        Parent load = null;

        load = FXMLLoader.load(resource);

        LoadFormContent.getChildren().clear();
        LoadFormContent.getChildren().add(load);
    }

    public void btnBorrowOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/Borrow_Book_Form.fxml");
        assert resource != null;
        Parent load = null;

        load = FXMLLoader.load(resource);

        LoadFormContent.getChildren().clear();
        LoadFormContent.getChildren().add(load);
    }

    public void btnReturnOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/Return_Book_Form.fxml");
        assert resource != null;
        Parent load = null;

        load = FXMLLoader.load(resource);

        LoadFormContent.getChildren().clear();
        LoadFormContent.getChildren().add(load);
    }


    @FXML
    void btnReportOnAction(ActionEvent event) {

    }

    @FXML
    void btnUserOnAction(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/User_Form.fxml");
        assert resource != null;
        Parent load = null;

            load = FXMLLoader.load(resource);

        LoadFormContent.getChildren().clear();
        LoadFormContent.getChildren().add(load);

    }


}

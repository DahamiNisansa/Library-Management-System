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
    void btnBooksOnAction(ActionEvent event) {

    }

    @FXML
    void btnBorrowReturnOnAction(ActionEvent event) {

    }

    @FXML
    void btnFinePaymentOnAction(ActionEvent event) {

    }

    @FXML
    void btnReportOnAction(ActionEvent event) {

    }

    @FXML
    void btnUserOnAction(ActionEvent event) {
        URL resource = this.getClass().getResource("/view/User_Form.fxml");
        assert resource != null;
        Parent load = null;
        try {
            load = FXMLLoader.load(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LoadFormContent.getChildren().clear();
        LoadFormContent.getChildren().add(load);

    }

}

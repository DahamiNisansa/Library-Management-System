package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import module.Admin;
import org.jasypt.util.text.BasicTextEncryptor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class LoginFormController {

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXTextField txtUsername;

    @FXML
    void btnLoginOnAction(ActionEvent event) throws SQLException, IOException {

        String key = "#5541Asd";
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(key);

        String sql = "SELECT * FROM admin WHERE username="+"'"+txtUsername.getText()+"'";
        Connection connection = DBConnection.getInstance().getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery(sql);

        if (resultSet.next()){
            Admin admin = new Admin(
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );

            if (basicTextEncryptor.decrypt(admin.getPassword()).equals(txtPassword.getText())){
                Stage stage = new Stage();
                stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/dashboard.fxml")))));
                stage.show();
            }else{
                new Alert(Alert.AlertType.ERROR,"Check Your Password Again !").show();
            }

        }else {
            new Alert(Alert.AlertType.ERROR,"User Not Found").show();
        }
    }

    @FXML
    void hyperRegisterOnAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Register_Form.fxml")))));
        stage.show();
    }

}

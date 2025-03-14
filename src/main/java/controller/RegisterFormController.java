package controller;

import java.lang.*;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import module.Admin;
import org.jasypt.util.text.BasicTextEncryptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterFormController {

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXPasswordField txtConfirmPassword;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXTextField txtUsername;

    @FXML
    void btnRegisterOnAction(ActionEvent event) throws SQLException {
        String key = "#5541Asd";
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(key);

        String url = "INSERT INTO admin (username, email, password) VALUES(?,?,?)";

        if (txtPassword.getText().equals(txtConfirmPassword.getText())){

            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM admin WHERE username=" + "'" + txtUsername.getText() + "'");;


                if (!resultSet.next()){
                    Admin admin = new Admin(
                            txtUsername.getText(),
                            txtEmail.getText(),
                            txtPassword.getText());

                    PreparedStatement pstm = connection.prepareStatement(url);
                    pstm.setString(1,admin.getUserName());
                    pstm.setString(2,admin.getEmail());
                    pstm.setString(3,basicTextEncryptor.encrypt(admin.getPassword()));
                    pstm.executeUpdate();

                }else{
                    new Alert(Alert.AlertType.ERROR,"Admin Already Registered !").show();
                }

        }else{
            new Alert(Alert.AlertType.ERROR,"Password Not Match. Check Your Password Again !").show();
        }


    }

    public void setTxtEmail(JFXTextField txtEmail) {
        this.txtEmail = txtEmail;
    }
}

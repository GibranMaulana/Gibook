package com.example.ui;

import com.example.App;
import com.example.data.AuthService;
import com.example.data.Session;
import com.example.model.User;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Optional;

public class LoginController {
  @FXML private TextField usernameField;
  @FXML private PasswordField passwordField;
  @FXML private Label errorLabel;

  @FXML
  public void initialize() {
    errorLabel.setVisible(false);
  }

  @FXML
private void onLogin() throws IOException {
    String user = usernameField.getText();
    String pass = passwordField.getText();

    Optional<User> opt = AuthService.getInstance().authenticate(user, pass);
    if (opt.isEmpty()) {
        errorLabel.setText("Invalid credentials");
        errorLabel.setVisible(true);
        return;
    }
    User u = opt.get();
    Session.getInstance().setCurrentUser(u);
    if ("HotelAdm".equalsIgnoreCase(u.getRole())) {
        App.setRoot("ui/admin_dashboard");
    } else {
        App.setRoot("ui/hotel_list");
    }
}

  @FXML
  private void onRegister() throws IOException {
    App.setRoot("ui/register_form");
  }
}

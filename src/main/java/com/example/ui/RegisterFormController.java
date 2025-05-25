package com.example.ui;

import com.example.data.AuthService;
import com.example.App;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;

public class RegisterFormController {
    @FXML private TextField newUserField;
    @FXML private PasswordField newPassField, confirmField;
    @FXML private ChoiceBox<String> roleChoice;
    @FXML private TextField hotelNameField;      
    @FXML private Label regErrorLabel;

    @FXML
    public void initialize() {
        regErrorLabel.setVisible(false);

        roleChoice.getItems().setAll("Customer", "HotelAdm");
        roleChoice.setValue("Customer");

        roleChoice.getSelectionModel().selectedItemProperty()
            .addListener((obs, old, val) -> {
                boolean show = "HotelAdm".equals(val);
                hotelNameField.setVisible(show);
            });
    }

    @FXML
    private void onSubmit() throws IOException {
        String username = newUserField.getText().trim();
        String password = newPassField.getText();
        String confirm  = confirmField.getText();
        String roleStr  = roleChoice.getValue();
        String hotelName = hotelNameField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            regErrorLabel.setText("Username & password required");
            regErrorLabel.setVisible(true);
            return;
        }
        if (!password.equals(confirm)) {
            regErrorLabel.setText("Passwords do not match");
            regErrorLabel.setVisible(true);
            return;
        }
        if ("HotelAdm".equals(roleStr) && hotelName.isEmpty()) {
            regErrorLabel.setText("Hotel name required");
            regErrorLabel.setVisible(true);
            return;
        }

        int nextUserId = (int)(System.currentTimeMillis() % Integer.MAX_VALUE);

        boolean ok = AuthService.getInstance()
            .register(nextUserId, username, password, roleStr, hotelName);

        if (ok) {
            App.setRoot("ui/login");
        } else {
            regErrorLabel.setText("Username already taken");
            regErrorLabel.setVisible(true);
        }
    }

    @FXML
    private void onBack() throws IOException {
        App.setRoot("ui/login");
    }
}

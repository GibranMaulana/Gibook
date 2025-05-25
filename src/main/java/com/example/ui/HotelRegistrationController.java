package com.example.ui;

import com.example.App;
import com.example.data.HotelDAO;
import com.example.data.Session;
import com.example.model.Hotel;
import com.example.model.User;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.util.LinkedList;

public class HotelRegistrationController {
    @FXML private TextField nameField;
    @FXML private TextField addrField;
    @FXML private Label errorLabel;

    @FXML
    public void initialize() {
        errorLabel.setVisible(false);

        User me = Session.getInstance().getCurrentUser();
        boolean already = HotelDAO.getInstance().getAll().stream()
            .anyMatch(h -> h.getOwnerId() == me.getUserId());

        if (already) {
        errorLabel.setText("You’ve already registered a hotel.");
        errorLabel.setVisible(true);
        nameField.setDisable(true);
        addrField.setDisable(true);
        }
    }

    @FXML
    private void onSubmit() throws IOException {
        String name = nameField.getText().trim();
        String addr = addrField.getText().trim();

        if (name.isEmpty() || addr.isEmpty()) {
            errorLabel.setText("Name and address are required");
            errorLabel.setVisible(true);
            return;
        }
        int ownerId = Session.getInstance().getCurrentUser().getUserId();
        int hotelId = (int)(System.currentTimeMillis() % Integer.MAX_VALUE);

        Hotel h = new Hotel(hotelId, ownerId,name, addr, new LinkedList<>());
        HotelDAO.getInstance().addOrUpdateHotel(h);;

        App.setRoot("ui/admin_dashboard");
    }

    @FXML
    private void onBack() throws IOException {
        App.setRoot("ui/admin_dashboard");
    }
}

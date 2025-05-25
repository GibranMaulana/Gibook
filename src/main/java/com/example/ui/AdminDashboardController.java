package com.example.ui;

import com.example.App;
import com.example.data.HotelDAO;
import com.example.data.Session;
import com.example.model.Hotel;
import com.example.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.Optional;

public class AdminDashboardController {
    @FXML private Button registerHotelBtn;
    @FXML private Button viewHotelsBtn;
    @FXML private Button logoutBtn;

    @FXML
    public void initialize() {
        User me = Session.getInstance().getCurrentUser();
        Optional<Hotel> mine = HotelDAO.getInstance()
            .getAll().stream()
            .filter(h -> h.getOwnerId() == me.getUserId())
            .findFirst();

        registerHotelBtn.setDisable(mine.isPresent());
        viewHotelsBtn   .setDisable(!mine.isPresent());
    }

    @FXML private void onRegisterHotel() throws IOException {
        App.setRoot("ui/hotel_registration_form");
    }
    @FXML private void onViewHotels() throws IOException {
        App.setRoot("ui/my_hotels_view");
    }
    @FXML private void onLogout() throws IOException {
        App.setRoot("ui/login");
    }
}

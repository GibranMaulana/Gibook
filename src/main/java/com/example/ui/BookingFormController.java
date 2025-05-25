package com.example.ui;

import com.example.App;
import com.example.data.BookingService;
import com.example.data.CustomerDAO;
import com.example.data.RoomService;
import com.example.data.Session;
import com.example.data.UndoStack;
import com.example.data.WaitlistQueue;
import com.example.model.BookingHistory;
import com.example.model.Customer;
import com.example.model.Hotel;
import com.example.model.Room;
import com.example.model.RoomType;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.IntStream;

public class BookingFormController {
    @FXML private ComboBox<Room> roomCombo;
    @FXML private TextField guestField;
    @FXML private DatePicker checkInPicker;
    @FXML private DatePicker checkOutPicker;

    @FXML
    public void initialize() {
        Room sel = Session.getInstance().getSelectedRoom();
        if (sel == null) {
            try { App.setRoot("ui/hotel_list"); }
            catch (IOException ignored) {}
            return;
        }

        roomCombo.setItems(FXCollections.observableArrayList(sel));
        roomCombo.getSelectionModel().select(sel);
        roomCombo.setDisable(true);

        checkInPicker.setValue(LocalDate.now());
        checkOutPicker.setValue(LocalDate.now().plusDays(1));
    }

    @FXML
    private void onBook() throws IOException {
        Room r      = roomCombo.getValue();
        String guest = guestField.getText().trim();
        LocalDate in   = checkInPicker.getValue();
        LocalDate out  = checkOutPicker.getValue();
        Customer me   = (Customer) Session.getInstance().getCurrentUser();
        Hotel hotel   = Session.getInstance().getSelectedHotel();
        BookingService bs = new BookingService(me);
        List<BookingHistory> conflicts = bs.findOverlaps(in, out);

        if (r == null || guest.isEmpty() || in == null || out == null || !out.isAfter(in)) {
            new Alert(Alert.AlertType.WARNING,
                "Please select a room, enter your name, and ensure check-out is after check-in."
            ).showAndWait();
            return;
        }

        boolean bad = conflicts.stream()
            .anyMatch(bh -> bh.getHotel().getHotelId() != hotel.getHotelId());
        if (bad) {
        new Alert(Alert.AlertType.WARNING,
            "You already have overlapping booking at another hotel."
        ).showAndWait();
        return;
        }

        boolean ok = RoomService.getInstance().bookRoom(r, guest, in, out);
        if (!ok) {
            boolean join = new Alert(Alert.AlertType.CONFIRMATION,
            "Sorry, that room is full.  Would you like to join the wait-list?"
            ).showAndWait()
            .filter(bt -> bt == ButtonType.OK)
            .isPresent();

            if (join) {
                Hotel   h  = Session.getInstance().getSelectedHotel();
                WaitlistQueue.getInstance().join(
                    me.getUserId(), h.getHotelId(), r.getId()
                );
                new Alert(Alert.AlertType.INFORMATION,
                    "You’ve been added to the wait-list. We’ll notify you if it frees up."
                ).showAndWait();
            }
            App.setRoot("ui/hotel_rooms");
            return;
        }

        List<RoomType> types = hotel.getRoomTypeList();
        int rtId = IntStream.range(0, types.size())
                    .filter(i -> types.get(i).getRoomList().stream()
                        .anyMatch(x -> x.getId() == r.getId()))
                    .findFirst()
                    .orElseThrow();

        int days = (int) ChronoUnit.DAYS.between(in, out);
        BookingHistory bh = me.bookingRoom(hotel, in.toString(), days, rtId, r.getId());
        CustomerDAO.getInstance().addBookingHistory(me.getUserId(), bh);
        bs.add(bh);
        me.addBookingHistory(bh);
        UndoStack.getInstance().push(bh);

        new Alert(Alert.AlertType.INFORMATION, "Room booked successfully!").showAndWait();
        
        App.setRoot("ui/hotel_list");
    }

    @FXML
    private void onBack() throws IOException {
        App.setRoot("ui/hotel_rooms");
    }

}

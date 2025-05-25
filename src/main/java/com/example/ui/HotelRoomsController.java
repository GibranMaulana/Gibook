package com.example.ui;

import com.example.App;
import com.example.data.Session;
import com.example.model.Hotel;
import com.example.model.Room;
import com.example.model.RoomType;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class HotelRoomsController {
  @FXML private Label hotelHeader;
  @FXML private TableView<Room> roomsTable;
  @FXML private TableColumn<Room,Integer> colRoomId;
  @FXML private TableColumn<Room,String>  colRoomName;
  @FXML private TableColumn<Room,Number>  colRoomPrice;
  @FXML private TableColumn<Room,Boolean> colRoomAvail;

  private ObservableList<Room> rooms;

  @FXML
  public void initialize() {
    Hotel h = Session.getInstance().getSelectedHotel();
    hotelHeader.setText("Rooms at “" + h.getHotelName() + "”");

    colRoomId   .setCellValueFactory(new PropertyValueFactory<>("id"));
    colRoomName .setCellValueFactory(new PropertyValueFactory<>("name"));
    colRoomPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    colRoomAvail.setCellValueFactory(new PropertyValueFactory<>("status"));

    rooms = FXCollections.observableArrayList();
      for (RoomType rt : h.getRoomTypeList()) {
        for (Room r : rt.getRoomList()) {
          r.setPrice(rt.getPrice());
          rooms.add(r);
        }
      }
    roomsTable.setItems(rooms);
  }

  @FXML
  private void onBookRoom() throws IOException {
    Room sel = roomsTable.getSelectionModel().getSelectedItem();
    if (sel == null) return;
    Session.getInstance().setSelectedRoom(sel);  
    App.setRoot("ui/booking_form");
  }

  @FXML
  private void onBack() throws IOException {
    App.setRoot("ui/hotel_list");
  }
}

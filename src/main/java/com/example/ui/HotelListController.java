package com.example.ui;

import com.example.App;
import com.example.data.Session;
import com.example.data.UndoStack;
import com.example.data.CustomerDAO;
import com.example.data.HotelDAO;
import com.example.data.RoomService;
import com.example.model.BookingHistory;
import com.example.model.Customer;
import com.example.model.Hotel;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class HotelListController {
  @FXML private Label welcomeLabel;
  @FXML private TextField searchField;
  @FXML private TableView<Hotel> hotelTable;
  @FXML private TableColumn<Hotel,Integer> colId, colTotalRoom;
  @FXML private TableColumn<Hotel,String>  colName, colAddress;

  @FXML private TableView<BookingHistory> bookingsTable;
  @FXML private TableColumn<BookingHistory,String> colBHotel, colBCheckIn;
  @FXML private TableColumn<BookingHistory,Integer> colBRoomId, colBNights;
  @FXML private Button undoBtn;

    private static HotelListController INSTANCE;
    public HotelListController() { INSTANCE = this; }
    public static HotelListController get() { return INSTANCE; }

  @FXML
  public void initialize() {
    String user = Session.getInstance()
                          .getCurrentUser()
                          .getUsername();
     welcomeLabel.setText("Welcome, " + user);
    var me = (com.example.model.Customer) Session.getInstance().getCurrentUser();
    
    reloadBookings();
    reloadHotels();
    updateUndoButton();
    
    ObservableList<BookingHistory> myBook = FXCollections.observableArrayList(me.getBookingHistories());

    colBHotel     .setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getHotel().getHotelName()));
    colBRoomId    .setCellValueFactory(new PropertyValueFactory<>("roomId"));
    colBCheckIn   .setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
    colBNights    .setCellValueFactory(new PropertyValueFactory<>("bookingDays"));

    bookingsTable.setItems(myBook);

    colId         .setCellValueFactory(new PropertyValueFactory<>("hotelId"));
    colName       .setCellValueFactory(new PropertyValueFactory<>("hotelName"));
    colAddress    .setCellValueFactory(new PropertyValueFactory<>("hotelAddr"));
    colTotalRoom  .setCellValueFactory(c -> 
        new ReadOnlyObjectWrapper<>(c.getValue().getTotalRoom()));

    ObservableList<Hotel> list = FXCollections.observableArrayList(
        HotelDAO.getInstance().getAllSorted()
    );
    hotelTable.setItems(list);

    searchField.textProperty().addListener((obs, old, val) -> {
      if (val.isEmpty()) {
        hotelTable.setItems(FXCollections.observableArrayList(
          HotelDAO.getInstance().getAllSorted()
        ));
      } else {
        hotelTable.setItems(FXCollections.observableArrayList(
          HotelDAO.getInstance().searchByPrefix(val)
        ));
      }
    });

  }

  @FXML
  private void onClearSearch() {
    searchField.clear();
  }

  @FXML private void onViewRooms() throws IOException {
    Hotel sel = hotelTable.getSelectionModel().getSelectedItem();
    if (sel == null) return;
    Session.getInstance().setSelectedHotel(sel);
    App.setRoot("ui/hotel_rooms");
  }

  @FXML
  private void onUndo() {
     Customer me = (Customer) Session.getInstance().getCurrentUser();
    BookingHistory last = UndoStack.getInstance().pop();
    if (last != null) {
      RoomService.getInstance().toggleRoom(last.getRoomId(), true);
      CustomerDAO.getInstance().removeBookingHistory(last);
      me.getBookingHistories().remove(last);
    }
    reloadBookings();
    reloadHotels();
    updateUndoButton();
  }

  @FXML private void onBack() throws IOException {
    App.setRoot("ui/login");
  }

  private void reloadBookings() {
    Customer me = (Customer)Session.getInstance().getCurrentUser();
    bookingsTable.setItems(
      FXCollections.observableArrayList(me.getBookingHistories())
    );
  }

  private void reloadHotels() {
    hotelTable.setItems(
      FXCollections.observableArrayList(
        HotelDAO.getInstance().getAllSorted()
      )
    );
  }

  private void updateUndoButton() {
    undoBtn.setDisable(!UndoStack.getInstance().canUndo());
  }
}

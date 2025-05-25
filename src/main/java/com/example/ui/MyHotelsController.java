package com.example.ui;

import com.example.App;
import com.example.data.HotelDAO;
import com.example.data.Session;
import com.example.model.Hotel;
import com.example.model.Room;
import com.example.model.RoomType;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Optional;
import java.util.UUID;

public class MyHotelsController {
  @FXML private Label hotelNameLabel;
  @FXML private Label hotelAddrLabel;

  @FXML private TableView<RoomType> typesTable;
  @FXML private TableColumn<RoomType, Integer> colTypeId;
  @FXML private TableColumn<RoomType, String>  colTypeName;
  @FXML private TableColumn<RoomType, Integer> colTypePrice;
  @FXML private TableColumn<RoomType, Integer> colTypeCount;
  @FXML private Button addRoomBtn;
  @FXML private Button toggleStatusBtn;


  @FXML private TableView<Room> roomsTable;
  @FXML private TableColumn<Room, Integer> colRoomId;
  @FXML private TableColumn<Room, String>  colRoomName;
  @FXML private TableColumn<Room, Boolean> colRoomStatus;

  private Hotel myHotel;
  private ObservableList<RoomType> typeList;
  private ObservableList<Room>     roomList;

  @FXML
  public void initialize() {
    int me = Session.getInstance().getCurrentUser().getUserId();
    Optional<Hotel> opt = HotelDAO.getInstance().getAll().stream()
        .filter(h -> h.getOwnerId() == me)
        .findFirst();
    
        if (opt.isEmpty()) {
      try { App.setRoot("ui/admin_dashboard"); }
      catch (IOException ignored) {}
      return;
    }

    myHotel = opt.get();

    hotelNameLabel.setText(myHotel.getHotelName());
    hotelAddrLabel.setText(myHotel.getHotelAddr());

    colTypeId   .setCellValueFactory(new PropertyValueFactory<>("id"));
    colTypeName .setCellValueFactory(new PropertyValueFactory<>("name"));
    colTypePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    colTypeCount.setCellValueFactory(c -> 
       new ReadOnlyObjectWrapper<>(c.getValue().getTotalRoom())
    );

    typeList = FXCollections.observableArrayList(myHotel.getRoomTypeList());
    typesTable.setItems(typeList);

    colRoomId    .setCellValueFactory(new PropertyValueFactory<>("id"));
    colRoomName  .setCellValueFactory(new PropertyValueFactory<>("name"));
    colRoomStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    roomsTable.setItems(FXCollections.observableArrayList());

    typesTable.getSelectionModel().selectedItemProperty()
      .addListener((obs, old, sel) -> {
        if (sel != null) {
          roomList = FXCollections.observableArrayList(sel.getRoomList());
          roomsTable.setItems(roomList);
          addRoomBtn.setDisable(false);
        } else {
          roomsTable.getItems().clear();
          addRoomBtn.setDisable(true);
        }
    });

    roomsTable.getSelectionModel().selectedItemProperty()
      .addListener((obs, oldRoom, newRoom) -> {
        addRoomBtn.setDisable(newRoom == null);
        toggleStatusBtn.setDisable(newRoom == null);
    });
  }

  @FXML
  private void onAddType() {
    TextInputDialog dlg = new TextInputDialog();
    dlg.setHeaderText("New Room Type Name:");
    Optional<String> nameOpt = dlg.showAndWait().map(String::trim);
    
    if (nameOpt.isEmpty() || nameOpt.get().isEmpty()) return;
    String typeName = nameOpt.get();

    boolean exists = typeList.stream()
        .anyMatch(rt -> rt.getName().equalsIgnoreCase(typeName));
    
    if (exists) {
      new Alert(Alert.AlertType.WARNING,
        "A type with that name already exists.").showAndWait();
      return;
    }

    TextInputDialog priceDlg = new TextInputDialog();
    priceDlg.setHeaderText("Price per night:");
    Optional<String> priceOpt = priceDlg.showAndWait();
    int price;
    
    try { price = Integer.parseInt(priceOpt.orElse("0")); }
    catch (NumberFormatException e) { return; }

    int newTypeId = Math.abs(UUID.randomUUID().hashCode());

    RoomType rt = new RoomType(newTypeId, typeName, price, new LinkedList<>());
    myHotel.getRoomTypeList().add(rt);
    typeList.add(rt);
    HotelDAO.getInstance().addOrUpdateHotel(myHotel);

    typesTable.refresh();
  }

  @FXML
  private void onAddRoom() {
    RoomType selType = typesTable.getSelectionModel().getSelectedItem();
    if (selType == null) return;

    TextInputDialog nameDlg = new TextInputDialog();
    nameDlg.setHeaderText("Room Name:");
    Optional<String> nameOpt = nameDlg.showAndWait().map(String::trim);
    
    if (nameOpt.isEmpty() || nameOpt.get().isEmpty()) return;
    
    String roomName = nameOpt.get();
    boolean exists = selType.getRoomList().stream()
                            .anyMatch(r -> r.getName()
                            .equalsIgnoreCase(roomName));
    if (exists) {
      new Alert(Alert.AlertType.WARNING,
        "A room with that name already exists in this type.").showAndWait();
      return;
    }

    int roomId = Math.abs(UUID.randomUUID().hashCode());

    Room r = new Room(roomId, roomName, true);
    selType.getRoomList().add(r);
    roomList.add(r);
    HotelDAO.getInstance().addOrUpdateHotel(myHotel);

    roomsTable.refresh();
    typesTable.refresh();   
  }

  @FXML
  private void onToggleStatus() {
    Room sel = roomsTable.getSelectionModel().getSelectedItem();
    if (sel == null) return;

    sel.setStatus(!sel.isStatus());

    HotelDAO.getInstance().addOrUpdateHotel(myHotel);

    roomsTable.refresh();
    typesTable.refresh();  
  }

  @FXML
  private void onBack() throws IOException {
    App.setRoot("ui/admin_dashboard");
  }
}

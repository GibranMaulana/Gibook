package com.example.data;

import com.example.model.Hotel;
import com.example.model.User;
import com.example.model.Room;

public class Session {
  private User    currentUser;
  private Hotel   selectedHotel; 
  private Room    selectedRoom;     
  private static final Session INSTANCE = new Session();
  private Session(){}

  public static Session getInstance(){ return INSTANCE; }

  public User getCurrentUser(){ return currentUser; }
  public void setCurrentUser(User u){ currentUser = u; }

  public Hotel getSelectedHotel(){ return selectedHotel; }
  public void setSelectedHotel(Hotel h){ selectedHotel = h; }

   public Room getSelectedRoom() {
       return selectedRoom;
   }

   public void setSelectedRoom(Room room) {
       this.selectedRoom = room;
   }
}

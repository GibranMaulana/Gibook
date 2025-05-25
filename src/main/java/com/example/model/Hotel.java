package com.example.model;

import java.util.LinkedList;

public class Hotel {
    private int hotelId;
    private int ownerId;
    private String hotelName;
    private String hotelAddr;
    private LinkedList<RoomType>roomTypeList;
    private int totalRT;
    private int totalRoom;
    
    public Hotel() { }

    public Hotel(
        int hotelId,
        int ownerId,
        String hotelName, 
        String hotelAddr, 
        LinkedList<RoomType> roomTypeList
        ) {
            this.hotelId = hotelId;
            this.ownerId = ownerId;
            this.hotelName = hotelName;
            this.hotelAddr = hotelAddr;
            this.roomTypeList = roomTypeList;
            totalRT = roomTypeList.size(); 
            
            for(int i = 0; i < totalRT; i++) {
                totalRoom = totalRoom + roomTypeList.get(i).getTotalRoom();    
            }
        }
    
    public int getHotelId() { return hotelId; }
    public int getOwnerId() { return ownerId; }
    public String getHotelName() { return hotelName; }
    public String getHotelAddr() { return hotelAddr; }
    public int getTotalRT() { return roomTypeList.size(); }
    public LinkedList<RoomType> getRoomTypeList() { return roomTypeList; }
    public int getTotalRoom() { 
        
        int newTotalRoom = 0;

        for(int i = 0; i < roomTypeList.size(); i++) {
            newTotalRoom = newTotalRoom + roomTypeList.get(i).getTotalRoom();
        }

        this.totalRoom = newTotalRoom;
        return newTotalRoom;
     }
    
    public void setHotelName(String hotelName) { this.hotelName = hotelName; }
    public void setHotelAddr(String hotelAddr) { this.hotelAddr = hotelAddr; }
    public void setOwnerId(int id) { this.ownerId = id; }

}

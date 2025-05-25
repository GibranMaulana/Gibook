package com.example.model;

import java.util.LinkedList;

public class RoomType {
    private int id;
    private String name;
    private int price;
    private int totalRoom;
    private LinkedList<Room>roomList;

    public RoomType() { 
        this.roomList = new LinkedList<>();
    }

    public RoomType(
        int id,
        String name,
        int price,
        LinkedList<Room>roomList) {
            this.id = id;
            this.name = name;
            this.price = price;
            totalRoom = roomList.size();
            this.roomList = roomList;
        }

    public int getId() { return id;}
    public String getName() { return name; }
    public int getPrice() { return price; }
    public int getTotalRoom() {
        this.totalRoom = roomList.size();
        return totalRoom;
    }
    public LinkedList<Room>getRoomList() { return roomList; }
    
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPrice(int price) { this.price = price; }
    public void setTotalRoom(int totalRoom) { this.totalRoom = totalRoom; }
    public void setRoomList(LinkedList<Room> roomList) { this.roomList = roomList; }

}

package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class Room {
    private int id;
    private String name;
    private boolean status;
    private double price;           
    
    @JsonIgnore private String hotelName;      
    @JsonIgnore private String roomTypeName;   

    
    public Room() { }
    
    public Room(int id, String name, boolean status) {
        this.id      = id;
        this.name    = name;
        this.status  = status;
    }
    
    public int getId()            { return id; }
    public String getName()       { return name; }
    public boolean isStatus()     { return status; }  
    
    public void setId(int id)         { this.id = id; }
    public void setName(String name)   { this.name = name; }
    public void setStatus(boolean s)   { this.status = s; } 
    
    public double getPrice()       { return price; }
    public void   setPrice(double p)  { this.price = p; }
    
    public String getHotelName()     { return hotelName; }
    public void   setHotelName(String hn) { this.hotelName = hn; }
    
    public String getRoomTypeName()  { return roomTypeName; }
    public void   setRoomTypeName(String rtn) { this.roomTypeName = rtn; }

}

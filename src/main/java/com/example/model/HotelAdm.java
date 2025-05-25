package com.example.model;

import java.util.LinkedList;

public class HotelAdm extends User {
    private String hotelName;
    private LinkedList<BookingHistory> hotelHistories = new LinkedList<>();

    public HotelAdm() {
        super();
        this.role = "HotelAdm";
    }

    public HotelAdm(int userId, String username, String password, String role,
                    String hotelName, LinkedList<BookingHistory> hotelHistories) {
        super(userId, username, password, role);
        this.hotelName      = hotelName;
        this.hotelHistories = hotelHistories;
    }

    public String getHotelName() { return hotelName; }
    public void setHotelName(String hotelName) { this.hotelName = hotelName; }

    public LinkedList<BookingHistory> getHotelHistories() { return hotelHistories; }
    public void setHotelHistories(LinkedList<BookingHistory> h) { this.hotelHistories = h; }
}

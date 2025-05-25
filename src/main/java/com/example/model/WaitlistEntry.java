package com.example.model;

import java.time.LocalDate;

public class WaitlistEntry {
    private final int customerId;
    private final int hotelId;
    private final int roomId;
    private final LocalDate requestDate; 

    public WaitlistEntry(int customerId, int hotelId, int roomId, LocalDate requestDate) {
        this.customerId = customerId;
        this.hotelId    = hotelId;
        this.roomId     = roomId;
        this.requestDate= requestDate;
    }

    public int getCustomerId() { return customerId; }
    public int getHotelId()    { return hotelId;    }
    public int getRoomId()     { return roomId;     }
    public LocalDate getRequestDate() { return requestDate; }
}

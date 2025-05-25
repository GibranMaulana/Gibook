package com.example.model;

import java.util.LinkedList;

public class Customer extends User {
    private LinkedList<BookingHistory> bookingHistories = new LinkedList<>();

    public Customer() { 
        super();
        this.role = "Customer";
    }

    public Customer(int userId, String username, String password, String role,
                    LinkedList<BookingHistory> bookingHistories) {
        super(userId, username, password, role);
        this.bookingHistories = bookingHistories;
    }

    public LinkedList<BookingHistory> getBookingHistories() { return bookingHistories; }
    public void setBookingHistories(LinkedList<BookingHistory> hist) { this.bookingHistories = hist; }
    public void addBookingHistory(BookingHistory bh) { bookingHistories.add(bh); }

    public BookingHistory bookingRoom(
        Hotel hotel, 
        String bookingDate, 
        int bookingDays, 
        int rtId, 
        int roomId) {
        
            return new BookingHistory(
                hotel,
                rtId,
                roomId,
                this.userId,
                hotel.getRoomTypeList().get(rtId).getPrice() * bookingDays,
                bookingDate,
                bookingDays
            );
    }
}
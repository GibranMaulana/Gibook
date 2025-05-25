package com.example.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ 
  "customerId", "bookingPrice", "bookingDate", "bookingDays", "rtId", "roomId"
})

public class BookingHistory {

  private Hotel hotel; 

  private int customerId;
  private int bookingPrice;
  private String bookingDate;
  private int bookingDays;
  private int rtId;
  private int roomId;

  public BookingHistory() { }

  public BookingHistory(
    Hotel hotel,
    int rtId,
    int roomId,
    int customerId,
    int bookingPrice,
    String bookingDate,
    int bookingDays
    ) {
      this.hotel = hotel;
      this.customerId = customerId;
      this.bookingPrice = bookingPrice;
      this.bookingDate = bookingDate;
      this.bookingDays = bookingDays;
      this.rtId = rtId;
      this.roomId = roomId;
    }

  public Hotel getHotel() { return hotel; }
  public int getCustomerId() { return customerId; }
  public int getBookingPrice() { return bookingPrice; }
  public String getBookingDate() { return bookingDate; }
  public int getBookingDays() { return bookingDays; }
  public int getRtId() { return rtId; } 
  public int getRoomId() { return roomId; }

  public void setHotel(Hotel hotel) { this.hotel = hotel; }
  public void setCustomerId(int customerId) { this.customerId = customerId; }
  public void setBookingPrice(int bookingPrice) { this.bookingPrice = bookingPrice; }
  public void setBookingDate(String bookingDate) { this.bookingDate = bookingDate; }
  public void setBookingDays(int bookingDays) { this.bookingDays = bookingDays; }
  public void setRtId(int rtId) { this.rtId = rtId; } 
  public void setRoomId(int roomId) { this.roomId = roomId; }

  @Override
  public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof BookingHistory)) return false;
      BookingHistory b = (BookingHistory) o;
      return customerId == b.customerId
          && roomId     == b.roomId
          && rtId       == b.rtId
          && bookingDays == b.bookingDays
          && bookingDate.equals(b.bookingDate);
  }

  @Override
  public int hashCode() {
      return Objects.hash(customerId, rtId, roomId, bookingDate, bookingDays);
  }

}
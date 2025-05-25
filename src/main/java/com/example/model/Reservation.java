package com.example.model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Reservation {
    private final ObjectProperty<Room> room = new SimpleObjectProperty<>();
    private final StringProperty guestName   = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> checkIn  = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> checkOut = new SimpleObjectProperty<>();

    public Reservation(Room room, String guestName, LocalDate in, LocalDate out) {
        this.room.set(room);
        this.guestName.set(guestName);
        this.checkIn.set(in);
        this.checkOut.set(out);
    }

    public Reservation() { }

    public Room getRoom()               { return room.get(); }
    public ObjectProperty<Room> roomProp() { return room; }
    public void setRoom(Room r)         { room.set(r); }

    public String getGuestName()        { return guestName.get(); }
    public StringProperty guestNameProp() { return guestName; }
    public void setGuestName(String n)  { guestName.set(n); }

    public LocalDate getCheckIn()       { return checkIn.get(); }
    public ObjectProperty<LocalDate> checkInProp() { return checkIn; }
    public void setCheckIn(LocalDate d) { checkIn.set(d); }

    public LocalDate getCheckOut()      { return checkOut.get(); }
    public ObjectProperty<LocalDate> checkOutProp() { return checkOut; }
    public void setCheckOut(LocalDate d){ checkOut.set(d); }
}

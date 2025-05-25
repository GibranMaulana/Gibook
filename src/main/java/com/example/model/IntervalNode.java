package com.example.model;

import java.time.LocalDate;

public class IntervalNode {
    public LocalDate start, end, maxEnd;
    public BookingHistory data;
    public IntervalNode left, right;

    public IntervalNode(BookingHistory bh) {
        this.data  = bh;
        this.start = LocalDate.parse(bh.getBookingDate());
        this.end   = this.start.plusDays(bh.getBookingDays());
        this.maxEnd = this.end;
    }
}
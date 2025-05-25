package com.example.data;

import com.example.model.BookingHistory;
import com.example.model.Customer;
import java.time.LocalDate;
import java.util.List;

public class BookingService {
    private final BookingIntervalTree tree;

    public BookingService(Customer c) {
        tree = new BookingIntervalTree();
        c.getBookingHistories().forEach(tree::insert);
    }

    public List<BookingHistory> findOverlaps(LocalDate in, LocalDate out) {
        return tree.query(in, out);
    }

    public void add(BookingHistory bh) {
        tree.insert(bh);
    }
}

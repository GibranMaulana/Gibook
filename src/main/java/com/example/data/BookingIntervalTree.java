package com.example.data;

import com.example.model.BookingHistory;
import com.example.model.IntervalNode;
import java.time.LocalDate;
import java.util.*;

public class BookingIntervalTree {
    private IntervalNode root;

    public void insert(BookingHistory bh) {
        root = insertRec(root, bh);
    }
    private IntervalNode insertRec(IntervalNode node, BookingHistory bh) {
        if (node == null) return new IntervalNode(bh);
        LocalDate s = LocalDate.parse(bh.getBookingDate());
        if (s.isBefore(node.start)) node.left  = insertRec(node.left, bh);
        else                         node.right = insertRec(node.right, bh);

        node.maxEnd = Collections.max(
          List.of(node.maxEnd,
                  node.left  != null ? node.left.maxEnd  : node.maxEnd,
                  node.right != null ? node.right.maxEnd : node.maxEnd),
          LocalDate::compareTo);
        return node;
    }

    public List<BookingHistory> query(LocalDate qs, LocalDate qe) {
        List<BookingHistory> out = new ArrayList<>();
        queryRec(root, qs, qe, out);
        return out;
    }
    private void queryRec(IntervalNode n, LocalDate qs, LocalDate qe, List<BookingHistory> out) {
        if (n == null) return;
        if (!(qe.isBefore(n.start) || qs.isAfter(n.end))) out.add(n.data);
        if (n.left != null && n.left.maxEnd.isAfter(qs))
            queryRec(n.left, qs, qe, out);
        queryRec(n.right, qs, qe, out);
    }
}

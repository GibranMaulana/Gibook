package com.example.data;

import com.example.model.Hotel;
import com.example.model.HotelNode;
import java.util.*;

public class HotelDirectory {
    private HotelNode root;

    public void insert(Hotel h) {
        root = insertRec(root, h);
    }
    private HotelNode insertRec(HotelNode node, Hotel h) {
        if (node == null) return new HotelNode(h);

        int cmp = h.getHotelName().compareToIgnoreCase(node.data.getHotelName());
        if (cmp < 0) {
            node.left  = insertRec(node.left,  h);
        }
        else if (cmp > 0) {
            node.right = insertRec(node.right, h);
        }
        else {
            node.data = h;
        }
        return node;
    }

    public List<Hotel> inOrder() {
        List<Hotel> out = new ArrayList<>();
        inOrderRec(root, out);
        return out;
    }
    private void inOrderRec(HotelNode n, List<Hotel> out) {
        if (n == null) return;
        inOrderRec(n.left, out);
        out.add(n.data);
        inOrderRec(n.right, out);
    }

    public List<Hotel> searchPrefix(String prefix) {
        List<Hotel> out = new ArrayList<>();
        collectPrefix(root, prefix.toLowerCase(), out);
        return out;
    }
    private void collectPrefix(HotelNode n, String p, List<Hotel> out) {
        if (n == null) return;
        String name = n.data.getHotelName().toLowerCase();
        if (name.startsWith(p)) {
            collectPrefix(n.left, p, out);
            out.add(n.data);
            collectPrefix(n.right, p, out);
        } else if (name.compareTo(p) < 0) {
            collectPrefix(n.right, p, out);
        } else {
            collectPrefix(n.left, p, out);
        }
    }
}

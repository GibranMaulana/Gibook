package com.example.data;

import com.example.model.WaitlistEntry;

import java.time.LocalDate;
import java.util.*;

public class WaitlistQueue {
    private final Map<Integer, Queue<WaitlistEntry>> byRoom = new HashMap<>();
    private static final WaitlistQueue INSTANCE = new WaitlistQueue();
    private final WaitlistDAO dao = new WaitlistDAO();

    private WaitlistQueue() {
        Map<Integer, List<WaitlistEntry>> raw = dao.loadAll();
        raw.forEach((roomId, list) ->
            byRoom.put(roomId, new ArrayDeque<>(list))
        );
    }
    
    public static WaitlistQueue getInstance() { return INSTANCE; }

    private void persist() {
        Map<Integer, List<WaitlistEntry>> toSave = new HashMap<>();
        byRoom.forEach((rid, q) -> toSave.put(rid, new ArrayList<>(q)));
        dao.saveAll(toSave);
    }

    public void join(int customerId, int hotelId, int roomId) {
        Queue<WaitlistEntry> q = byRoom.computeIfAbsent(roomId, k -> new ArrayDeque<>());
                q.offer(new WaitlistEntry(customerId, hotelId, roomId, LocalDate.now()));
                persist();
    }

    public WaitlistEntry peekNext(int roomId) {
        Queue<WaitlistEntry> q = byRoom.get(roomId);
        return (q == null || q.isEmpty()) ? null : q.peek();
    }

    public WaitlistEntry popNext(int roomId) {
        Queue<WaitlistEntry> q = byRoom.get(roomId);

        if (q == null) return null;

        WaitlistEntry e = q.poll();

        if (q.isEmpty()) byRoom.remove(roomId);
        persist();
        return e;
    }

    public List<WaitlistEntry> listForRoom(int roomId) {
        Queue<WaitlistEntry> q = byRoom.get(roomId);
        if (q == null) return Collections.emptyList();
        return new ArrayList<>(q);
    }
}

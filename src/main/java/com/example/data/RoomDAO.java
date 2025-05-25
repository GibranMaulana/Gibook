package com.example.data;

import com.example.model.Room;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.*;

public class RoomDAO {
    private static final String ROOM_FILE = "rooms.json";
    private final ObjectMapper mapper = new ObjectMapper();

    public List<Room> loadAll() {
        File f = new File(ROOM_FILE);
        if (!f.exists()) return new ArrayList<>();
        try (InputStream in = new FileInputStream(f)) {
            return mapper.readValue(in, new TypeReference<List<Room>>(){});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void saveAll(List<Room> rooms) {
        try {
            mapper.writerWithDefaultPrettyPrinter()
                  .writeValue(new File(ROOM_FILE), rooms);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

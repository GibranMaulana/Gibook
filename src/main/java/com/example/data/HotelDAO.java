package com.example.data;

import com.example.model.Hotel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.*;

public class HotelDAO {
    private static final String HOTEL_FILE = "hotels.json";
    private final ObjectMapper mapper = new ObjectMapper();

    private List<Hotel> hotels;
    private final Map<Integer,Hotel> byId = new HashMap<>();
    private static final HotelDAO INSTANCE = new HotelDAO();
    private HotelDirectory directory = new HotelDirectory();
    
    private HotelDAO() {
        
        File f = new File(HOTEL_FILE);
        if (!f.exists()) {
            hotels = new ArrayList<>();
        } else {
            try (InputStream in = new FileInputStream(f)) {
                hotels = mapper.readValue(in, new TypeReference<List<Hotel>>() {});
            } catch (IOException e) {
                e.printStackTrace();
                hotels = new ArrayList<>();
            }
        }

        for (Hotel h : hotels) {
        byId.put(h.getHotelId(), h);
        directory.insert(h);
        }
    }

    public static HotelDAO getInstance() {
        return INSTANCE;
    }

    public List<Hotel> getAll() {
        return new ArrayList<>(byId.values());
    }

    public void addOrUpdateHotel(Hotel h) {
        byId.put(h.getHotelId(), h);            
        directory.insert(h);                    
        saveAll();  
    }

    public List<Hotel> getAllSorted() {
        return directory.inOrder();
    }

    public List<Hotel> searchByPrefix(String prefix) {
        return directory.searchPrefix(prefix);
    }

    private void saveAll() {
        try {
            mapper.writerWithDefaultPrettyPrinter()
                  .writeValue(new File(HOTEL_FILE), hotels);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

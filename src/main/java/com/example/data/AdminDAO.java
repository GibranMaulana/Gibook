package com.example.data;

import com.example.model.HotelAdm;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.*;

public class AdminDAO {

    private static final AdminDAO INSTANCE = new AdminDAO();
    private static final String ADMIN_FILE = "admins.json";
    private final ObjectMapper mapper = new ObjectMapper();

    public List<HotelAdm> loadAll() {
        File f = new File(ADMIN_FILE);
        if (!f.exists()) return new ArrayList<>();
        try (InputStream in = new FileInputStream(f)) {
            return mapper.readValue(in, new TypeReference<List<HotelAdm>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static AdminDAO getInstance() {
        return INSTANCE;
    }

    public void saveAll(List<HotelAdm> list) {
        try {
            mapper.writerWithDefaultPrettyPrinter()
                  .writeValue(new File(ADMIN_FILE), list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

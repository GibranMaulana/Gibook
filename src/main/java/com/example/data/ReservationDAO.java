package com.example.data;

import com.example.model.Reservation;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.util.*;

public class ReservationDAO {
    private static final String RES_FILE = "reservations.json";

    private final ObjectMapper mapper = new ObjectMapper()
                                            .registerModule(new JavaTimeModule())
                                            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private List<Reservation> all;

    private static final ReservationDAO INSTANCE = new ReservationDAO();
    private ReservationDAO() {
        File f = new File(RES_FILE);
        if (!f.exists()) {
            all = new ArrayList<>();
        } else {
            try (InputStream in = new FileInputStream(f)) {
                all = mapper.readValue(in, new TypeReference<List<Reservation>>() {});
            } catch (IOException e) {
                e.printStackTrace();
                all = new ArrayList<>();
            }
        }
    }

    public static ReservationDAO getInstance() {
        return INSTANCE;
    }

    public List<Reservation> getAll() {
        return Collections.unmodifiableList(all);
    }

    public void addReservation(Reservation r) {
        all.add(r);
        saveAll();
    }

    private void saveAll() {
        try {
            mapper.writerWithDefaultPrettyPrinter()
                  .writeValue(new File(RES_FILE), all);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

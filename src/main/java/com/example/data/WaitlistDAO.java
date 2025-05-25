package com.example.data;

import com.example.model.WaitlistEntry;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class WaitlistDAO {
    private static final String PATH = "waitlists.json";
    private final ObjectMapper mapper = new ObjectMapper()  
                                            .registerModule(new JavaTimeModule())
                                            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);;

    public Map<Integer, List<WaitlistEntry>> loadAll() {
        try (InputStream in = Files.newInputStream(Paths.get(PATH))) {
            TypeReference<Map<Integer,List<WaitlistEntry>>> ref =
                new TypeReference<>() {};
            return mapper.readValue(in, ref);
        } catch (IOException e) {
            return new HashMap<>();
        }
    }

    public void saveAll(Map<Integer, List<WaitlistEntry>> data) {
        try (OutputStream out = Files.newOutputStream(Paths.get(PATH))) {
            mapper.writerWithDefaultPrettyPrinter()
                  .writeValue(out, data);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}

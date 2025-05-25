package com.example.data;

import com.example.model.BookingHistory;
import com.example.model.Customer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.util.*;

public class CustomerDAO {
  private static final String FILE = "customers.json";
  private final ObjectMapper mapper = new ObjectMapper();
  private List<Customer> all;

  private static final CustomerDAO INSTANCE = new CustomerDAO();
  
  private CustomerDAO() {
        this.all = loadAll();
  }

  public List<Customer> loadAll() { 
    File f = new File(FILE);

    if(!f.exists()) return new ArrayList<>();

    try (InputStream in = new FileInputStream(f)) {
      return mapper.readValue(in, new TypeReference<List<Customer>>() {});
    } catch (IOException e) {
      e.printStackTrace();
      return new ArrayList<>();
    }
  }

  public static CustomerDAO getInstance() { return INSTANCE; }
  public void saveAll() {
    try {
      mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE), all);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void saveAll(List<Customer> cust) {
    try {
      mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE), cust);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void addBookingHistory(int userId, BookingHistory bh) {
    
    for (Customer c : all) {
        if (c.getUserId() == userId) {
            c.addBookingHistory(bh);
            saveAll();
            return;
        }
    }
  }
  
  public void removeBookingHistory(BookingHistory bh) {

      for (Customer c : all) {
          Iterator<BookingHistory> it = c.getBookingHistories().iterator();
          while (it.hasNext()) {
              if (it.next().equals(bh)) {
                  it.remove();
                  saveAll();
                  return;
              }
          }
      }
      throw new IllegalArgumentException("BookingHistory not found");
  }
}

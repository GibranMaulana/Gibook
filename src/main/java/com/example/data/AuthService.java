package com.example.data;

import com.example.model.Customer;
import com.example.model.HotelAdm;
import com.example.model.User;

import java.util.*;
import java.util.stream.*;


public class AuthService {
    private static final AuthService INSTANCE = new AuthService();

    private final CustomerDAO customerDAO = CustomerDAO.getInstance();
    private final AdminDAO    adminDAO    = new AdminDAO();

    
    private final List<Customer>  customerList;
    private final Set<String>     customerNames;

    private final List<HotelAdm>  adminList;
    private final Set<String>     adminNames;

    private AuthService() {
        
        this.customerList  = new ArrayList<>(customerDAO.loadAll());
        this.customerNames = customerList.stream()
                                         .map(Customer::getUsername)
                                         .collect(Collectors.toSet());

        this.adminList     = new ArrayList<>(adminDAO.loadAll());
        this.adminNames    = adminList.stream()
                                      .map(HotelAdm::getUsername)
                                      .collect(Collectors.toSet());
    }

    public static AuthService getInstance() {
        return INSTANCE;
    }

    public boolean register(int    userId,
                            String username,
                            String password,
                            String role,
                            String hotelName) {
        if ("Customer".equalsIgnoreCase(role)) {
            
            if (!customerNames.add(username)) {
                return false;
            }
            
            Customer c = new Customer(
                userId,
                username,
                password,
                "Customer",          
                new LinkedList<>()   
            );
            customerList.add(c);
            
            customerDAO.saveAll(customerList);
            return true;

        } else if ("HotelAdm".equalsIgnoreCase(role)) {
            if (!adminNames.add(username)) {
                return false;
            }
            HotelAdm a = new HotelAdm(
                userId,
                username,
                password,
                "HotelAdm",          
                hotelName,
                new LinkedList<>()   
            );
            adminList.add(a);
            adminDAO.saveAll(adminList);
            return true;

        } else {
            
            return false;
        }
    }

    
    public Optional<User> authenticate(String username, String password) {
        
        return Stream.concat(
                customerList.stream(),
                adminList.stream()
            )
            .filter(u -> u.getUsername().equals(username)
                      && u.getPassword().equals(password))
            .findFirst();
    }
}

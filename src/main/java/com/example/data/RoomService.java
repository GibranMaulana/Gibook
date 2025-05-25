package com.example.data;

import com.example.model.Hotel;
import com.example.model.Reservation;
import com.example.model.Room;
import com.example.model.RoomType;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class RoomService {
    private final HotelDAO hotelDao        = HotelDAO.getInstance();
    private final ReservationDAO resDao    = ReservationDAO.getInstance();

    private static final RoomService INSTANCE = new RoomService();
    private RoomService() { }
    public static RoomService getInstance() { return INSTANCE; }

    
    public boolean bookRoom(Room room, String guest, LocalDate in, LocalDate out) {
        for (Hotel h : hotelDao.getAll()) {
            for (RoomType rt : h.getRoomTypeList()) {
                for (Room r : rt.getRoomList()) {
                    if (r.getId() == room.getId() && r.isStatus()) {
                        r.setStatus(false);
                        hotelDao.addOrUpdateHotel(h);
                        Reservation res = new Reservation(r, guest, in, out);
                        resDao.addReservation(res);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public List<Room> getAllRooms() {
        return hotelDao.getAll().stream()
            .flatMap(h -> h.getRoomTypeList().stream()
                .flatMap(rt -> rt.getRoomList().stream()
                    .peek(r -> { 
                        r.setPrice(rt.getPrice());
                        r.setHotelName(h.getHotelName());
                        r.setRoomTypeName(rt.getName());
                    })
                )
            )
            .collect(Collectors.toList());
    }

    public List<Room> getAvailableRooms() {
        return getAllRooms().stream()
                            .filter(Room::isStatus)
                            .collect(Collectors.toList());
    }

    public void toggleRoom(int roomId, boolean available) {
        for (Hotel h : HotelDAO.getInstance().getAll()) {
            for (Room r : h.getRoomTypeList()
                           .stream()
                           .flatMap(rt -> rt.getRoomList().stream())
                           .collect(Collectors.toList())) {
                if (r.getId() == roomId) {
                    r.setStatus(available);
                    HotelDAO.getInstance().addOrUpdateHotel(h);
                    return;
                }
            }
        }
        throw new IllegalArgumentException("Unknown roomId: " + roomId);
    }

}

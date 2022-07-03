package api;

import models.Customer;
import models.IRoom;
import models.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {

    public static Customer getCustomer(String email){
        return CustomerService.getCustomer(email);

    }

    public static void addRoom(List<IRoom> rooms){
        for (IRoom room: rooms){
            ReservationService.addRoom(room);
        }
    }

    public static Collection<IRoom> getAllRooms() {
        return ReservationService.getRoomList();

    }

    public static Collection<Customer> getAllCustomer() {
        return CustomerService.getAllCustomers();

    }

    public static void displayAllReservations() {

        ReservationService.printAllReservation();
    }
}

package api;

import models.Customer;
import models.IRoom;
import service.CustomerService;
import service.ReservationService;
import java.util.Collection;
import java.util.List;

public class AdminResource {

    private static AdminResource INSTANCE;

    public Customer getCustomer(String email){
        return CustomerService.getInstance().getCustomer(email);

    }

    public void addRoom(List<IRoom> rooms){
        for (IRoom room: rooms){
            ReservationService.getINSTANCE().addRoom(room);
        }
    }

    public Collection<IRoom> getAllRooms() {
        return ReservationService.getINSTANCE().getRoomList();

    }

    public Collection<Customer> getAllCustomer() {
        return CustomerService.getInstance().getAllCustomers();

    }

    public void displayAllReservations() {

        ReservationService.getINSTANCE().printAllReservation();
    }

    public static AdminResource getInstance(){

        if(INSTANCE == null){
            INSTANCE = new AdminResource();
        }

        return INSTANCE;
    }
}

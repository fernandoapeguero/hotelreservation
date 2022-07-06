package api;

import models.Customer;
import models.IRoom;
import models.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;


public class HotelResource {

    private static HotelResource INSTANCE;

    public Customer getCustomer(String email){
        return CustomerService.getInstance().getCustomer(email);
    }


    public void createACustomer(String email, String firstName, String lastName) {
      CustomerService.getInstance().addCustomer(email, firstName,lastName);
    }

    public IRoom getRoom (String roomNumber){
        return ReservationService.getINSTANCE().getARoom(roomNumber);

    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate){

        Customer customer = CustomerService.getInstance().getCustomer(customerEmail);


        return ReservationService.getINSTANCE().reserveARoom(customer, room, checkInDate, checkOutDate);

    }

    public Collection<Reservation> getCustomerReservations(String customerEmail) {

        Customer customer = CustomerService.getInstance().getCustomer(customerEmail);

        return ReservationService.getINSTANCE().getCustomerReservation(customer);

    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        return ReservationService.getINSTANCE().findRooms(checkIn, checkOut);

    }

    public static HotelResource getInstance(){

        if(INSTANCE == null){
            INSTANCE = new HotelResource();
        }

        return INSTANCE;
    }

}

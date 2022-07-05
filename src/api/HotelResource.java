package api;

import models.Customer;
import models.IRoom;
import models.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.regex.Pattern;

public class HotelResource {

    public static Customer getCustomer(String email){
        return CustomerService.getInstance().getCustomer(email);
    }


    public static void createACustomer(String email, String firstName, String lastName) {

      CustomerService.getInstance().addCustomer(email, firstName,lastName);
    }

    public static IRoom getRoom (String roomNumber){
        return ReservationService.getARoom(roomNumber);

    }

    public static Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate){

        Customer customer = CustomerService.getInstance().getCustomer(customerEmail);


        return ReservationService.reserveARoom(customer, room, checkInDate, checkOutDate);

    }

    public static Collection<Reservation> getCustomerReservations(String customerEmail) {

        Customer customer = CustomerService.getInstance().getCustomer(customerEmail);

        return ReservationService.getCustomerReservation(customer);

    }

    public static Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        return ReservationService.findRooms(checkIn, checkOut);

    }

}

package api;

import models.Customer;
import models.IRoom;
import models.Reservation;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class HotelResource {

    public static Customer getCustomer(String email){
        return null;

    }


    public static void createACustomer(String email, String firstName, String lastName) {

    }

    public static IRoom getRoom (String roomNumber){
        return null;

    }

    public static Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate){
        return null;

    }

    public static Collection<Reservation> getCustomerReservations(String customerEmail) {
        return null;

    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        return null;

    }


}

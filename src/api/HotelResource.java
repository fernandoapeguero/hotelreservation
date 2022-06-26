package api;

import models.Customer;
import models.IRoom;
import models.Reservation;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class HotelResource {

    public static Customer getCustomer(String email){

    }


    public static void createACustomer(String email, String firstName, String lastName) {

    }

    public static IRoom getRoom (String roomNumber){

    }

    public static Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate){

    }

    public static Collection<Reservation> getCustomerReservations(String customerEmail) {

    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {

    }


}

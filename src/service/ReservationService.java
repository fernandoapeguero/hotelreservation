package service;

import models.Customer;
import models.IRoom;
import models.Reservation;
import models.Room;

import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class ReservationService {

    private static Collection<Reservation> reservations = new ArrayList<>();
    public static Collection<IRoom> rooms = new ArrayList<>();

    public static void addRoom(IRoom room){
        rooms.add(room);

    }

    public static IRoom getARoom(String roomId) {


        for (IRoom room: rooms){
            if (room.getRoomNumber().equals(roomId)){
                return  room;
            }
        }

        throw new IllegalArgumentException("Room Not Found");
    }

    public static Reservation reserveARoom(Customer customer, IRoom room, Date checkinDate, Date checkoutDate) {

        Reservation reserve = new Reservation(
                customer,
                room,
                checkinDate,
                checkoutDate
        );

        reservations.add(reserve);

        return reserve;
    }
    public static Collection<IRoom> findRooms(Date checkInDate, Date checkoutDate) {


        Collection<IRoom> rentableRooms = new ArrayList<>();

        for (IRoom room: rooms) {

            rentableRooms.add(room);
        }

        return rentableRooms;

    }

    public static Collection<Reservation> getCustomerReservation(Customer customer){

        Collection<Reservation> customerReservations = new ArrayList<>();

        for (Reservation reserve: reservations){

            if(customer.equals(reserve.getCustomer())){
                customerReservations.add(reserve);
            }
        }

        return customerReservations;
    }

    public static void printAllReservation() {

        if (reservations.size() == 0)  {
            System.out.println("There are no current reservations.");
        } else {
            for (Reservation reservation: reservations) {
                System.out.println(reservation);
            }
        }

    }
}

package service;

import models.Customer;
import models.IRoom;
import models.Reservation;
import models.Room;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class ReservationService {

    public static Collection<Reservation> reservations = new ArrayList<>();
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

        return rooms;

    }

    public static Collection<Reservation> getCustomerReservation(Customer customer){

        return reservations;
    }

    public static void printAllReservation() {
        for (Reservation reservation: reservations) {
            System.out.println(reservation);
        }
    }
}

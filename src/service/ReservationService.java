package service;

import models.Customer;
import models.IRoom;
import models.Reservation;
import models.Room;

import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReservationService {

    private static List<Reservation> reservations = new ArrayList<>();
    public static List<IRoom> rooms = new ArrayList<>();

    public static void addRoom(IRoom room){
        boolean isNotADuplicated = true;

        for (IRoom r: rooms){

            if(r.getRoomNumber().equals(room.getRoomNumber())){
                isNotADuplicated = false;
            }
        }

        if (isNotADuplicated){

            rooms.add(room);
        } else{
            throw new IllegalArgumentException("The id provided already exist");
        }

    }

    public static IRoom getARoom(String roomId) {


        for (IRoom room: rooms){
            if (room.getRoomNumber().equals(roomId)){
                return  room;
            }
        }

        throw new IllegalArgumentException("Room Not Found");
    }

    public static Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {

        Reservation reserve = new Reservation(
                customer,
                room,
                checkInDate,
                checkOutDate
        );

        reservations.add(reserve);

        return reserve;
    }
    public static Collection<IRoom> findRooms(Date checkInDate, Date checkoutDate) {


        List<IRoom> rentableRooms = new ArrayList<>();
        int count = 0;

        while(count < 2) {

            for (IRoom currentRoom : rooms) {
                boolean addRoomToList = true;

                for (Reservation reserve : reservations) {

                    if (reserve.getRoom().equals(currentRoom)) {

                        if (!reserve.getCheckInDate().before(checkInDate) && !reserve.getCheckoutDate().before(checkInDate) || !reserve.getCheckInDate().after(checkoutDate) && !reserve.getCheckoutDate().after(checkoutDate)) {
                            System.out.println("Current room have been reserve for this date ");
                            addRoomToList = false;
                        }

                    }
                }

                if (addRoomToList) {
                    rentableRooms.add(currentRoom);
                }

            }

            if (rentableRooms.size() == 0) {

                Calendar calendar = Calendar.getInstance();

                calendar.setTime(checkInDate);
                calendar.add(Calendar.DATE, 7);

                checkInDate = calendar.getTime();

                calendar.setTime(checkoutDate);
                calendar.add(Calendar.DATE, 7);
                System.out.println("Rooms not available for provided dates checking other dates.");
                checkoutDate = calendar.getTime();
                System.out.println(checkInDate);
                System.out.println(checkoutDate);


            } else  {
                break;
            }

            count++;
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

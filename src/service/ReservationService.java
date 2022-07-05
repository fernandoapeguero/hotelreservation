package service;

import models.Customer;
import models.IRoom;
import models.Reservation;
import java.util.*;

public class ReservationService {

    private static ReservationService INSTANCE;
    private static final List<Reservation> reservations = new ArrayList<>();
    private static final List<IRoom> rooms = new ArrayList<>();

    public void addRoom(IRoom room){
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

    public IRoom getARoom(String roomId) {


        for (IRoom room: rooms){
            if (room.getRoomNumber().equals(roomId)){
                return  room;
            }
        }

        throw new IllegalArgumentException("Room Not Found");
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {

        Reservation reserve = new Reservation(
                customer,
                room,
                checkInDate,
                checkOutDate
        );

        reservations.add(reserve);

        return reserve;
    }
    public Collection<IRoom> findRooms(Date checkInDate, Date checkoutDate) {

        List<IRoom> rentableRooms = new ArrayList<>();

            for (IRoom currentRoom : rooms) {
                boolean addRoomToList = true;

                for (Reservation reserve : reservations) {

                    if (reserve.getRoom().equals(currentRoom)) {

                        if (!checkInDate.after(reserve.getCheckoutDate()) && !checkoutDate.after(reserve.getCheckoutDate())) {
                            addRoomToList = false;
                        }

                    }
                }

                if (addRoomToList) {
                    rentableRooms.add(currentRoom);
                }

            }

        return rentableRooms;

    }

    public Collection<Reservation> getCustomerReservation(Customer customer){

        Collection<Reservation> customerReservations = new ArrayList<>();

        for (Reservation reserve: reservations){

            if(customer.equals(reserve.getCustomer())){
                customerReservations.add(reserve);
            }
        }

        return customerReservations;
    }

    public void printAllReservation() {

        if (reservations.size() == 0)  {
            System.out.println("There are no current reservations.");
        } else {
            for (Reservation reservation: reservations) {
                System.out.println(reservation);
            }
        }

    }

    public Collection<IRoom> getRoomList(){
        return rooms;
    }

    public static ReservationService getINSTANCE() {

        if (INSTANCE == null){
            INSTANCE = new ReservationService();
        }

        return INSTANCE;
    }
}

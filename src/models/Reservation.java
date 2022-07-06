package models;

import java.util.Date;

public class Reservation {

    private Customer customer;
    private final IRoom room;
    private final Date checkInDate;
    private final Date checkoutDate;


    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkoutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkoutDate = checkoutDate;
    }


    @Override
    public String toString() {
        return "Customer Name: " + customer.getFirstName() + " Room Number: " + room.getRoomNumber() + "Check In Date: " + checkInDate+ "Check Out Date " + checkoutDate;
    }

    public IRoom getRoom() {
        return room;
    }


    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public Customer getCustomer() {
        return customer;
    }

}

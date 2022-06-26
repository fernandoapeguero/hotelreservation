package models;

import java.util.Date;

public class Reservation {

    private Customer customer;
    private IRoom room;
    private Date checkInDate;
    private Date checkoutDate;


    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkoutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkoutDate = checkoutDate;
    }


    @Override
    public String toString() {
        return "Customer Name: " + customer.getFirstName() + " Check In Date: " + checkInDate + " Check Out Date " + checkoutDate;
    }
}

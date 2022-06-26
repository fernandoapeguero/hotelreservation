package service;

import models.Customer;
import models.Reservation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

public class CustomerService {

    private Collection<Reservation> reservations = new ArrayList<Reservation>();

    public static void addCustomer(String email, String firstName, String lastName) {

        String emailPattern = "^(.+)@(.+).com$";
        Pattern pattern = Pattern.compile(emailPattern);

        if(pattern.matcher(email).matches() || firstName.isEmpty() || lastName.isEmpty()) {

            Customer customer = new Customer(firstName, lastName, email);
            
        }
    }


    public static Customer getCustomer(String customerEmail){

    }

    public static Collection<Customer> getAllCustomers() {

    }
}

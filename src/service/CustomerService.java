package service;

import models.Customer;
import models.Reservation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

public class CustomerService {

    private static final Collection<Customer> customers = new ArrayList<>();

    public static void addCustomer(String email, String firstName, String lastName) {

        String emailPattern = "^(.+)@(.+).com$";
        Pattern pattern = Pattern.compile(emailPattern);

        if(!pattern.matcher(email).matches() || firstName.isEmpty() || lastName.isEmpty()) {
            throw  new IllegalArgumentException("The data is invalid please fill out correctly");
        }

        try {
            Customer customer = new Customer(firstName, lastName, email);
            customers.add(customer);
        } catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }
    }


    public static Customer getCustomer(String customerEmail){
        Customer customer = null;


        for (Customer person : customers){

            if(person.getEmail().equals(customerEmail)) {
                customer = person;
                break;
            }
        }

        if(customer == null) {
            throw new NullPointerException("Customer not found ");
        }

        return customer;
    }

    public static Collection<Customer> getAllCustomers() {
        return customers;
    }
}

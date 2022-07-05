package service;

import models.Customer;
import models.Reservation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

public class CustomerService {

    private static CustomerService INSTANCE;

    private static final Collection<Customer> customers = new ArrayList<>();

    public void addCustomer(String email, String firstName, String lastName) {

        String emailPattern = "^(.+)@(.+).com$";
        Pattern pattern = Pattern.compile(emailPattern);

        if(!pattern.matcher(email).matches() || firstName.isEmpty() || lastName.isEmpty()) {
            throw  new IllegalArgumentException("The data is invalid please fill out correctly");
        }

        for (Customer customer: customers){

            if (email.equals(customer.getEmail())){
                throw new IllegalArgumentException("There is already and account with this email.");
            }
        }

        try {
            Customer customer = new Customer(firstName, lastName, email);
            customers.add(customer);
        } catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }
    }


    public Customer getCustomer(String customerEmail){
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

    public  Collection<Customer> getAllCustomers() {
        return customers;
    }

    public static CustomerService getInstance(){

        if(INSTANCE == null){
            INSTANCE = new CustomerService();
        }

        return  INSTANCE;
    }
}

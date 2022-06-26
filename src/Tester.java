import models.Customer;

public class Tester {

    public static void main(String[] args) {
        Customer customer = new Customer("First" , "last" , "javin@gmail.com");

        System.out.println(customer);
    }
}

import models.*;
import service.CustomerService;
import service.ReservationService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {

    public static void main(String[] args) {


        System.out.println("Welcome to the Hotel Reservation Application");
        System.out.println();

        mainMenu();
    }


    public static void mainMenu () {
        boolean loop = true;

        try(Scanner scanner = new Scanner(System.in)) {
            while (loop) {


                System.out.println("Main Menu");
                System.out.println("---------------------------------------------");
                System.out.println("1. Find and reserve a room.");
                System.out.println("2. See my reservations.");
                System.out.println("3. Create and account.");
                System.out.println("4. Admin");
                System.out.println("5. Exit");
                System.out.println("---------------------------------------------");

                loop = getUserInput();

            }
        }

    }

    private static void adminMenu() {

        boolean loop = true;

        while(loop) {

            System.out.println("Admin Menu");
            System.out.println();
            System.out.println("---------------------------------------------");
            System.out.println("1. See all Customers.");
            System.out.println("2. See all Rooms.");
            System.out.println("3. See all Reservations.");
            System.out.println("4. Add a room.");
            System.out.println("5. Back to Main Menu.");
            System.out.println("---------------------------------------------");

           loop =  getAdminInput();

        }

    }


    public static Boolean getUserInput() {
            Scanner scanner  = new Scanner(System.in);
                try {
                   return selectedMainMenuOption(scanner.nextLine());
                } catch (Exception e) {
                    System.out.println(e.getLocalizedMessage());
                }

            return true;
    }

    public static Boolean getAdminInput() {
           Scanner scanner  = new Scanner(System.in);
                try {
                    String input = scanner.nextLine();
                    return selectedAdminMenuOption(input);
                } catch (Exception e) {
                    System.out.println(e.getLocalizedMessage());
                }

            return true;

    }


    public static Boolean selectedMainMenuOption(String selected) {

        switch (selected) {
            case "1" -> findAndReserveRoom();
            case "2" -> seeReservations();
            case "3" -> createAccount();
            case "4" -> adminMenu();
            case "5" -> {
                System.out.println("Exiting program");
                return false;
            }

            default -> {
                System.out.println("Please enter a valid choice.");

            }
        }

        return true;
    }

    private static Boolean selectedAdminMenuOption(String selected) {

        switch (selected) {
            case "1" -> seeAllCustomers();
            case "2" -> seeAllRooms();
            case "3" -> seeAllReservations();
            case "4" -> addRoom();
            case "5" -> {
                mainMenu();
                return false;
            }
            default -> {
                System.out.println("Please enter a valid choice.");

            }
        }

        return true;
    }

//    Customer Menu Options
    public static void findAndReserveRoom() {
        System.out.println("--------------------- Available Rooms ---------------------");

        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        boolean bookingRoom = true;

        Date checkInDate = null;
        Date checkOutDate = null;

        Collection<IRoom> rooms = null;

        System.out.println("Enter your check in and check out dates");
        System.out.println("Date format is as follow day-month-year or dd-mm-year");
        System.out.println();


        while (loop) {

            try {
                System.out.println("Check in Date: ");
                String checkIn = scanner.nextLine();
                System.out.println("Check out Date: ");
                String checkOut = scanner.nextLine();

                Calendar calendar = Calendar.getInstance();

                SimpleDateFormat checkInFormatter = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat checkOutFormatter = new SimpleDateFormat("dd-MM-yyyy");

                calendar.setTime(checkInFormatter.parse(checkIn));
                checkInDate = calendar.getTime();
                calendar.setTime(checkOutFormatter.parse(checkOut));
                checkOutDate = calendar.getTime();

                if (checkInDate.after(checkOutDate)){
                    throw new IllegalArgumentException("The check in date must be before your checkout date.");

                }


                rooms = ReservationService.findRooms(checkInDate, checkOutDate);

                if (rooms.size() == 0){
                    System.out.println("No rooms available");
                } else {
                    for(IRoom room : rooms){
                        System.out.println(room);
                    }

                }

                System.out.println();
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
                System.out.println("Date format day/month/year");
                System.out.println();
            }


            if (rooms != null) {
                while(bookingRoom) {
                    System.out.println("Would you like to book a room Y/N");
                    String response = scanner.nextLine();

                    Customer customer;

                    System.out.println("Do you have an account with us ? Y/N");
                    String hasAnAccount = scanner.nextLine();

                    bookingRoom = isLoop(scanner, true, hasAnAccount);

                    System.out.println("Enter your email");
                    String email = scanner.nextLine();

                    customer = CustomerService.getCustomer(email);

                    System.out.println("Enter the room id you wish to reserve");
                    String roomId = scanner.nextLine();

                    IRoom room = ReservationService.getARoom(roomId);

                    ReservationService.reserveARoom(customer, room, checkInDate, checkOutDate);

                    String addAnotherRoom = "";
                    System.out.println("Do you wan to book another room ?");
                    bookingRoom = isLoop(scanner, true, addAnotherRoom);
                }
            }


            String response = "";
            System.out.println("Are you done or check more available dates ?");
            loop = isLoop(scanner, true, response);


        }

    }

    private static boolean isLoop(Scanner scanner, boolean loop, String response) {

        while(!response.equalsIgnoreCase("y") || !response.equalsIgnoreCase("n")){
            response = scanner.nextLine();
            if(response.equalsIgnoreCase("y")){
                break;
            } else if(response.equalsIgnoreCase("n")){
                loop = false;
                break;
            } else {
                System.out.println("Please enter a valid choice Y or N ");
            }
        }
        return loop;
    }

    public static void seeReservations() {
        System.out.println("All Reservations");

        Scanner scanner = new Scanner(System.in);

        System.out.println("enter you email");
        String email = scanner.nextLine();

        Customer currentCustomer = CustomerService.getCustomer(email);

        Collection<Reservation> customerReservations =  ReservationService.getCustomerReservation(currentCustomer);

        for (Reservation reserve: customerReservations){
            System.out.println(reserve);
        }


    }

    public static void createAccount () {
        Scanner scanner = new Scanner(System.in);

        boolean loop = true;

        while(loop){

            try{

                System.out.println("Enter your email: ");
                String email = scanner.nextLine();

                System.out.println("First Name: ");
                String firstName = scanner.nextLine();

                System.out.println("Last Name: ");
                String lastName = scanner.nextLine();

                CustomerService.addCustomer(email, firstName,lastName);
            }
            catch (Exception e){
                System.out.println("Inputted information invalid");
            }

            String response = "";
            System.out.println("Would you like to create another account ?");
            loop = isLoop(scanner, loop, response);

        }
    }

//    Admin Menu Options

    public static void seeAllCustomers() {

        System.out.println("Customer Directory");
        System.out.println("----------------------------------------------");
        Collection<Customer> customers = CustomerService.getAllCustomers();

        if (customers.size() > 0){

            for(Customer customer : customers) {

                System.out.println(customer);
            }
            System.out.println("----------------------------------------------");
            System.out.println();
        } else {
            System.out.println("There are no customer at the current moment");
        }

    }

    public static void seeAllRooms() {
        System.out.println("This are all the rooms available");
        for (IRoom r: ReservationService.rooms){
            System.out.println(r);
        }


    }

    public static void seeAllReservations() {
        System.out.println("Reservations Directory");

        System.out.println("----------------------------------------------");
        System.out.println();
        ReservationService.printAllReservation();
        System.out.println("----------------------------------------------");

    }

    public static void addRoom() {

        boolean loop = true;
        Room room = null;

        while(loop) {
            Scanner scanner = new Scanner(System.in);
            try {
                System.out.println("Room ID: ");
                String roomID =  scanner.nextLine();

                System.out.println("Room price: ");
                Double price = scanner.nextDouble();

                System.out.println("Room Type 1 For Single, 2 For Double");
                scanner.nextLine();
                String type = scanner.nextLine();
                RoomType roomType = null;

                if(type.equals("1")) {
                    roomType = RoomType.SINGLE;

                } else if (type.equals("2")){
                    roomType = RoomType.DOUBLE;
                }


                System.out.println("Room successfully added");
                if(price.intValue() == 0) {
                    room = new FreeRoom(roomID, roomType);
                } else {
                    room = new Room(roomID, price, roomType);
                }

                ReservationService.addRoom(room);

            } catch (Exception e){

                if (e.getLocalizedMessage() == null){
                    System.out.println(e.getLocalizedMessage());
                } else {
                    System.out.println("the input provided was wrong.");
                }
                scanner.nextLine();

            }

            String response = "";
            System.out.println("Do you want to add another room Y/N ");
            loop = isLoop(scanner, loop, response);


        }
    }

}


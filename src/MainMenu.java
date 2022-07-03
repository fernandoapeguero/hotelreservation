import api.AdminResource;
import api.HotelResource;
import models.*;
import service.CustomerService;
import service.ReservationService;

import javax.lang.model.element.AnnotationMirror;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

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

            default -> System.out.println("Please enter a valid choice.");


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
            default -> System.out.println("Please enter a valid choice.");
        }

        return true;
    }

//    Customer Menu Options
    public static void findAndReserveRoom() {
        System.out.println("--------------------- Available Rooms ---------------------");

        Scanner scanner = new Scanner(System.in);
        boolean loop = true;

        Collection<Reservation> reservations = new ArrayList<>();

        System.out.println("Enter your check in and check out dates");
        System.out.println("Date format is as follow day-month-year or dd-mm-year");
        System.out.println();


        while (loop) {

            boolean bookingRoom = true;

            Date checkInDate = null;
            Date checkOutDate = null;

            Collection<IRoom> rooms = null;

            try {
                System.out.println("Check in Date: ");
                String checkIn = scanner.nextLine();
                System.out.println("Check out Date: ");
                System.out.println();
                String checkOut = scanner.nextLine();

                Calendar calendar = Calendar.getInstance();

                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

                calendar.setTime(dateFormatter.parse(checkIn));
                checkInDate = calendar.getTime();
                calendar.setTime(dateFormatter.parse(checkOut));
                checkOutDate = calendar.getTime();

                if (checkInDate.after(checkOutDate)){
                    throw new IllegalArgumentException("The check in date must be before your checkout date.");

                }

                int count = 0;
                while(count < 2) {
                    rooms = HotelResource.findARoom(checkInDate, checkOutDate);

                    if (rooms.size() == 0 && count != 1) {

                        calendar.setTime(checkInDate);
                        calendar.add(Calendar.DATE, 7);

                        checkInDate = calendar.getTime();

                        calendar.setTime(checkOutDate);
                        calendar.add(Calendar.DATE, 7);
                        checkOutDate  = calendar.getTime();

                        System.out.println("checking alternated date for room availability.");

                    } else {

                        if (count  == 1 && rooms.size() > 0){
                            System.out.println();
                            System.out.println("New check in and check out dates");
                            System.out.println("Check In Date: " + checkInDate);
                            System.out.println("Check Out Date: " + checkOutDate);
                        }

                        break;
                    }

                    count++;
                }

                if (rooms.size() == 0) {
                    System.out.println("No rooms available");
                } else {
                    for (IRoom room : rooms) {
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

                while(true) {

                    System.out.println("Would you like to book a room Y/N");
                    String response = scanner.nextLine();

                    if (response.equalsIgnoreCase("n")){
                        bookingRoom = false;
                        break;
                    } else if (response.equalsIgnoreCase("y")){
                        break;
                    } else {
                        System.out.println("Enter a valid choice");
                    }

                }

                while(bookingRoom) {

                    try {

                        Customer customer;
                        String hasAnAccount;
                        while(true) {
                            System.out.println("Do you have an account with us ? Y/N");
                             hasAnAccount = scanner.nextLine();

                            if(hasAnAccount.equalsIgnoreCase("y")){
                                System.out.println("Enter your email");
                                String email = scanner.nextLine();
                                customer = HotelResource.getCustomer(email);
                                break;
                            } else if (hasAnAccount.equalsIgnoreCase("n")){
                                createAccount();
                            }
                        }

                        System.out.println("Enter the room id you wish to reserve");
                        String roomId = scanner.nextLine();

                        IRoom room = HotelResource.getRoom(roomId);

                        reservations.add(HotelResource.bookARoom(customer.getEmail(), room, checkInDate, checkOutDate));

                        bookingRoom = false;

                    } catch (Exception e ){
                        System.out.println(e.getLocalizedMessage());
                    }

                }
            }


            String response = "";
            System.out.println("Do you want to book another room ? Y/N");
            loop = isLoop(scanner, true, response);


        }

        if (reservations.size() > 0) {
            System.out.println("------------------Reservations--------------------");
            for (Reservation reserve: reservations){
                System.out.println(reserve);
            }
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
        System.out.println("---------------------Reservations---------------------");

        Scanner scanner = new Scanner(System.in);

        System.out.println("enter you email");
        String email = scanner.nextLine();

        Collection<Reservation> customerReservations =  HotelResource.getCustomerReservations(email);

        if (customerReservations.size() > 0) {
            for (Reservation reserve: customerReservations){
                System.out.println(reserve);
            }

        } else  {
            System.out.println("You have no reservations at this moment.");
        }

    }

    public static void createAccount () {
        Scanner scanner = new Scanner(System.in);

        boolean loop = true;

        System.out.println("Creating new account.");
        System.out.println();

        while(loop){

            try{

                System.out.println("Enter your email: ");
                String email = scanner.nextLine();

                System.out.println("First Name: ");
                String firstName = scanner.nextLine();

                System.out.println("Last Name: ");
                String lastName = scanner.nextLine();

                HotelResource.createACustomer(email, firstName,lastName);
            }
            catch (Exception e){
                System.out.println("Inputted information invalid");
            }

            String response = "";
            System.out.println("Would you like to create another account ? Y/N");
            loop = isLoop(scanner, loop, response);

        }
    }

//    Admin Menu Options

    public static void seeAllCustomers() {

        System.out.println("Customer Directory");
        System.out.println("----------------------------------------------");
        Collection<Customer> customers = AdminResource.getAllCustomer();

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
        System.out.println("----------------Rooms List----------------");
        Collection<IRoom> rooms = AdminResource.getAllRooms();

        if (rooms.size() == 0) {
            System.out.println("There are no rooms available");
        } else {
            for (IRoom room: rooms){
                System.out.println(room);
            }
        }

    }

    public static void seeAllReservations() {
        System.out.println("Reservations Directory");

        System.out.println("----------------------------------------------");
        System.out.println();
        AdminResource.displayAllReservations();
        System.out.println("----------------------------------------------");

    }

    public static void addRoom() {

        boolean loop = true;
        List<IRoom> rooms = new ArrayList<>();

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
                } else  {
                    throw  new IllegalArgumentException("Please choose 1 or 2 for room type");
                }

                System.out.println("Room successfully added");
                if(price.intValue() == 0) {
                    rooms.add(new FreeRoom(roomID, roomType));
                } else {
                    rooms.add(new Room(roomID, price, roomType));
                }

            } catch (Exception e){

                if (e.getLocalizedMessage() != null){
                    System.out.println(e.getLocalizedMessage());
                } else {
                    System.out.println("the input provided is invalid.");
                }
            }

            String response = "";
            System.out.println("Do you want to add another room Y/N ");
            loop = isLoop(scanner, true, response);

        }

        AdminResource.addRoom(rooms);
    }

}


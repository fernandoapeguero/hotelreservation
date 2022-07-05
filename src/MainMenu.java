import api.AdminResource;
import api.HotelResource;
import models.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainMenu {

    public static void main(String[] args) {


        System.out.println("Welcome to the Hotel Reservation Application");
        System.out.println();

        mainMenu();
    }

    /**
     * Main Menu function display a list of options for a customer to choose from.
     * you can choose from multiple option booking a room seen you reservations and more
     *
    * */
    static void mainMenu () {
        boolean loop = true;

        try(Scanner ignored = new Scanner(System.in)) {
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

    /**
     * Admin menu display option for a hotel administrator to check on customer, rooms and reservations.
     * it has functionality to add new rooms to the hotel.
     *
     * */
    static void adminMenu() {

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

    /**
     * getUserInput takes the input for the main menu and call the selectedMainMenuOption that
     * the customer choose.
     *
     * */
    static Boolean getUserInput() {
            Scanner scanner  = new Scanner(System.in);
                try {
                   return selectedMainMenuOption(scanner.nextLine());
                } catch (Exception e) {
                    System.out.println(e.getLocalizedMessage());
                }

            return true;
    }

    /**
     * getAdminInput gets the input for the admin menu option and choose from the admin chosen option.
     *
     * */
    static Boolean getAdminInput() {
           Scanner scanner  = new Scanner(System.in);
                try {
                    return selectedAdminMenuOption(scanner.nextLine());
                } catch (Exception e) {
                    System.out.println(e.getLocalizedMessage());
                }

            return true;

    }


    /**
     * SelectedMainMenuOption is used to choose the correct menu option base on the input string value.
     *
     * @param selected - used to determine witch menu option to choose.
     * */
    static Boolean selectedMainMenuOption(String selected) {

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

    /**
     * SelectedAdminMenuOption is use to choose the correct menu option base on admin input using the selected string variable.
     *
     * @param selected - used to determine witch menu option to choose.
     * */
    static Boolean selectedAdminMenuOption(String selected) {

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


    /**
     * Find and Reserve Room - this method allow you to reserve a room base on the available date for the rooms.
     * */
    static void findAndReserveRoom() {
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
                String checkOut = scanner.nextLine();
                System.out.println();

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
                    rooms = HotelResource.getInstance().findARoom(checkInDate, checkOutDate);

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
                            System.out.println();
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
                                System.out.println("Enter your email: name@domain.com");
                                String email = scanner.nextLine();
                                customer = HotelResource.getInstance().getCustomer(email);
                                break;
                            } else if (hasAnAccount.equalsIgnoreCase("n")){
                                createAccount();
                            }
                        }

                        System.out.println("Enter the room id you wish to reserve");
                        String roomId = scanner.nextLine();

                        IRoom room = HotelResource.getInstance().getRoom(roomId);

                        reservations.add(HotelResource.getInstance().bookARoom(customer.getEmail(), room, checkInDate, checkOutDate));

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

    /**
     * isLoop - this method is use to determine if the while loop should continue looping or not
     * since the code was repeated it was decided to create a method for it for better readability and
     * functionality.
     * */
    static boolean isLoop(Scanner scanner, boolean loop, String response) {

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

    /**
     * seeReservations - allows you to see the reservations that you have made with the hotel.
     * */
    static void seeReservations() {
        System.out.println("---------------------Reservations---------------------");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your email: name@domain.com");
        String email = scanner.nextLine();

        Collection<Reservation> customerReservations =  HotelResource.getInstance().getCustomerReservations(email);

        if (customerReservations.size() > 0) {
            for (Reservation reserve: customerReservations){
                System.out.println(reserve);
            }

        } else  {
            System.out.println("You have no reservations at this moment.");
        }

    }

    /**
     * CreateAccount - allows you to create an account with the hotel.
     * */
     static void createAccount () {
        Scanner scanner = new Scanner(System.in);

        boolean loop = true;

        System.out.println("Creating new account.");
        System.out.println();

        while(loop){

            try{

                System.out.println("Enter your email: name@domain.com");
                String email = scanner.nextLine();

                System.out.println("First Name: ");
                String firstName = scanner.nextLine();

                System.out.println("Last Name: ");
                String lastName = scanner.nextLine();

                HotelResource.getInstance().createACustomer(email, firstName,lastName);
            }
            catch (Exception e){
                System.out.println(e.getLocalizedMessage());
            }

            String response = "";
            System.out.println("Would you like to create another account ? Y/N");
            loop = isLoop(scanner, true, response);

        }
    }

//    Admin Menu Options

    /**
     * seeAllCustomers - Allow you to see all customer that have sign up for and account with the hotel.
     * */
    static void seeAllCustomers() {

        System.out.println("Customer Directory");
        System.out.println("----------------------------------------------");
        Collection<Customer> customers = AdminResource.getInstance().getAllCustomer();

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

    /**
     * seeAllRooms - allows you to see all the rooms in the system.
     * */
    static void seeAllRooms() {
        System.out.println("----------------Rooms List----------------");
        Collection<IRoom> rooms = AdminResource.getInstance().getAllRooms();

        if (rooms.size() == 0) {
            System.out.println("There are no rooms available");
        } else {
            for (IRoom room: rooms){
                System.out.println(room);
            }
        }

    }

    /**
     * method allow you to see all reservations made by customer in you hotel.
     * */
    static void seeAllReservations() {
        System.out.println("Reservations Directory");

        System.out.println("----------------------------------------------");
        System.out.println();
        AdminResource.getInstance().displayAllReservations();
        System.out.println("----------------------------------------------");

    }

    /**
     * addRoom - gives you the ability as an administrator to add rooms to the system for customer to book
     * */
    static void addRoom() {

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
                RoomType roomType;

                if(type.equals("1")) {
                    roomType = RoomType.SINGLE;

                } else if (type.equals("2")){
                    roomType = RoomType.DOUBLE;
                } else  {
                    throw  new IllegalArgumentException("Please choose 1 or 2 for room type");
                }

                for (IRoom room : rooms){
                    if (roomID.equals(room.getRoomNumber())){
                        throw new IllegalArgumentException("The room id you enter already exist");
                    }
                }

                if(price.intValue() == 0) {
                    rooms.add(new FreeRoom(roomID, roomType));
                } else {
                    rooms.add(new Room(roomID, price, roomType));
                }

                System.out.println("Room successfully added");

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

        AdminResource.getInstance().addRoom(rooms);
    }

}


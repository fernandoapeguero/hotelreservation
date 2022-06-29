import models.IRoom;
import models.Room;
import models.RoomType;
import service.ReservationService;

import java.util.Scanner;

public class MainMenu {

    public static void main(String[] args) {


        System.out.println("Welcome to the Hotel Reservation Application");
        System.out.println();

        mainMenu();
    }


    public static void mainMenu () {
        boolean loop = true;

        while(loop) {


            System.out.println("Main Menu");
            System.out.println("---------------------------------------------");
            System.out.println("1. Find and reserve a room.");
            System.out.println("2. See my reservations.");
            System.out.println("3. Create and account.");
            System.out.println("4. Admin");
            System.out.println("5. Exit");
            System.out.println("---------------------------------------------");

          loop =   getUserInput();

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
        System.out.println("Finding and reserving a room for you.");


    }

    public static void seeReservations() {
        System.out.println("Seen my reservations");

    }

    public static void createAccount () {
        System.out.println("Creating account");

    }

//    Admin Menu Options

    public static void seeAllCustomers() {
        System.out.println("Seen all Customers");

    }

    public static void seeAllRooms() {
        System.out.println("This are all the rooms available");
        for (IRoom r: ReservationService.rooms){
            System.out.println(r);
        }


    }

    public static void seeAllReservations() {
        System.out.println("This are all the reservations");

    }

    public static void addRoom() {

        boolean loop = true;

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
                Room room = new Room(roomID, price, roomType);

                ReservationService.addRoom(room);

            } catch (Exception e){
                System.out.println("The values provided are invalid.");
                scanner.nextLine();

            }

            String text = "";

            while (!text.equalsIgnoreCase("y") || text.equalsIgnoreCase("n")){
                System.out.println("Do you want to add another room Y/N ");

                text = scanner.nextLine();

                if(text.equals("n") ) {
                    loop = false;
                    break;
                } else if(text.equalsIgnoreCase("y")){
                    break;
                }

            }


        }
    }

}


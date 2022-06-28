import java.util.Scanner;

public class MainMenu {

    public static void main(String[] args) {


        System.out.println("Welcome to the Hotel Reservation Application");
        System.out.println();

        mainMenu();
    }


    public static void mainMenu () {

        System.out.println("Main Menu");
        System.out.println("---------------------------------------------");
        System.out.println("1. Find and reserve a room.");
        System.out.println("2. See my reservations.");
        System.out.println("3. Create and account.");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.println("---------------------------------------------");

        getUserInput();
    }

    private static void adminMenu() {

        System.out.println("Admin Menu");
        System.out.println();
        System.out.println("---------------------------------------------");
        System.out.println("1. See all Customers.");
        System.out.println("2. See all Rooms.");
        System.out.println("3. See all Reservations.");
        System.out.println("4. Add a room.");
        System.out.println("5. Back to Main Menu.");
        System.out.println("---------------------------------------------");

        getAdminInput();

    }


    public static void getUserInput() {
        try (Scanner scanner  = new Scanner(System.in)) {
                try {
                    selectedMainMenuOption(scanner.nextLine());
                } catch (Exception e) {
                    System.out.println(e.getLocalizedMessage());
                }
        }
    }

    public static void getAdminInput() {
        try (Scanner scanner  = new Scanner(System.in)) {

            try {
                selectedAdminMenuOption(scanner.nextLine());
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
    }


    public static void selectedMainMenuOption(String selected) {

        switch (selected) {
            case "1" -> findAndReserveRoom();
            case "2" -> seeReservations();
            case "3" -> createAccount();
            case "4" -> adminMenu();
            case "5" -> System.out.println("Exiting program");
            default -> {
                System.out.println("Please enter a valid choice.");
                mainMenu();
            }
        }
    }

    private static void selectedAdminMenuOption(String selected) {

        switch (selected) {
            case "1" -> seeAllCustomers();
            case "2" -> seeAllRooms();
            case "3" -> seeAllReservations();
            case "4" -> addRoom();
            case "5" -> mainMenu();
            default -> {
                System.out.println("Please enter a valid choice.");
                adminMenu();
            }
        }

    }

//    Customer Menu Options
    public static void findAndReserveRoom() {
        System.out.println("Finding and reserving a room for you.");

        mainMenu();
    }

    public static void seeReservations() {
        System.out.println("Seen my reservations");
        mainMenu();
    }

    public static void createAccount () {
        System.out.println("Creating account");
        mainMenu();
    }

//    Admin Menu Options

    public static void seeAllCustomers() {
        System.out.println("Seen all Customers");
        adminMenu();
    }

    public static void seeAllRooms() {
        System.out.println("This are all the rooms available");
        adminMenu();
    }

    public static void seeAllReservations() {
        System.out.println("This are all the reservations");
        adminMenu();
    }

    public static void addRoom() {
        System.out.println("adding a room");
        adminMenu();
    }

}


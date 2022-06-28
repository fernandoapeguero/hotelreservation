import models.Customer;
import models.Room;
import models.RoomType;
import service.CustomerService;
import service.ReservationService;

import java.util.Calendar;
import java.util.Date;

public class Tester {

    public static void main(String[] args) {
        Customer customer = new Customer("First" , "last" , "javin@gmail.com");

        System.out.println(customer);

        ReservationService.addRoom(new Room("201", 150.00, RoomType.SINGLE));

        System.out.println(ReservationService.getARoom("201"));

        Calendar calendar = Calendar.getInstance();
        calendar.set(2022,Calendar.JULY,14);

        Date checkinDate = calendar.getTime();

        calendar.set(2022, Calendar.JULY,25);

        Date checkOutDate = calendar.getTime();
//
//
//        ReservationService.findRooms(checkinDate, checkOutDate);
    }
}

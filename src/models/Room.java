package models;

public class Room implements IRoom{

    private final String roomNumber;
    private final Double price;
    private final RoomType enumeration;

    public Room(String roomNumber, Double price, RoomType enumeration) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }


    @Override
    public String toString() {
        return "Room Number: " + roomNumber + " Price: " + price + " Room Type: " + enumeration;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public RoomType getRoomType() {
        return enumeration;
    }

    @Override
    public boolean isFree() {
        return price == 0;
    }

    @Override
    public boolean equals(IRoom obj) {
        return roomNumber.equals(obj.getRoomNumber());
    }
}

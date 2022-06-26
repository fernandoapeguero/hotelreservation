package models;

public class FreeRoom extends Room{

    public FreeRoom(String RoomNumber, RoomType enumeration) {
        super(RoomNumber, 0.0, enumeration);

    }

    @Override
    public String toString() {
        return "Room Number: " + this.getRoomNumber() + "Price: " + this.getRoomPrice() + " Room Type: " + this.getRoomType();
    }
}

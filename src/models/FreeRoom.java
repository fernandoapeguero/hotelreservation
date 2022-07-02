package models;

public class FreeRoom extends Room{

    public FreeRoom(String RoomNumber, RoomType enumeration) {
        super(RoomNumber, 0.0, enumeration);

    }

    @Override
    public String toString() {
        return "Room Number: " + this.getRoomNumber() + " Price: Free Room Type: " + this.getRoomType();
    }
}

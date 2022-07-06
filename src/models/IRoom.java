package models;

public interface IRoom {

    String getRoomNumber();

    Double getRoomPrice();

    RoomType getRoomType();

    boolean isFree();

    boolean equals(IRoom obj);
}

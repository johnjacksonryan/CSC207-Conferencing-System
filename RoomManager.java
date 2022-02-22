package scheduling;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Use Case managing Rooms
 *
 * @author Justin
 * @author John
 * @author Yukang
 * @version 2.0
 */
public class RoomManager {
    private ArrayList<Room> rooms;

    /**
     * Default constructor
     */
    public RoomManager() {
        this.rooms = new ArrayList<>();
    }

    /**
     * @return the list of rooms with id and capacity
     */
    public HashMap<Integer, String> getRooms() {
        HashMap<Integer, String> rooms = new HashMap<>();
        for (Room room: this.rooms) {
            rooms.put(room.getNumber(), String.format("capacity: %s", room.getCapacity()));
        }
        return rooms;
    }
    /**
     * get specific room
     *
     * @param roomNum room number of room
     * @return room
     */
    public Room getRoom(int roomNum) {
        for (Room room : this.rooms)
            if (room.getNumber() == roomNum)
                return room;

        return null;
    }
    /**
     * add new room
     *
     * @param roomNum the number of the room to be added
     * @return true iff roomNumber is new and capacity is positive
     */
    public boolean addRoom(int roomNum, int capacity, boolean projector, boolean tables) {

        for (Room someRoom : this.rooms)
            if (someRoom.getNumber() == roomNum)
                return false;

        this.rooms.add(new Room(roomNum, capacity, projector, tables));
        return true;
    }

    /**
     * get capacity of specific room
     *
     * @param roomNum room number of room
     * @return a room's capacity
     */
    public int getRoomCapacity(int roomNum) {
        return getRoom(roomNum).getCapacity();
    }
    /**
     * set capacity of specific room
     *
     * @param roomNum room number of room
     * @param capacity new capacity
     * @return true iff new capacity is positive number
     */
    public boolean setRoomCapacity(int roomNum, int capacity) {
        Room room = getRoom(roomNum);
        if (room != null && capacity > 0) {
            room.setCapacity(capacity);
            return true;
        }
        return false;
    }


    /**
     * Get the status of projector in the room.
     * @param roomNum room number of room.
     * @return true iff the room has a projector.
     */
    public boolean RoomHasProjector(int roomNum) {
        Room room = getRoom(roomNum);
        return room.HasProjector();
    }
    /**
     * Get whether a room has tables
     * @param roomNum room number of room.
     * @return true iff the room has tables.
     */
    public boolean RoomHasTables(int roomNum) {
        Room room = getRoom(roomNum);
        return room.HasTables();
    }
    /**
     * Set whether a room has a projector.
     * @param roomNum room number of room.
     * @param projector true iff room has a projector.
     * @return true iff projector status is successfully changed.
     */
    public boolean setRoomHasProjector(int roomNum, boolean projector) {
        Room room = getRoom(roomNum);
        room.setHasProjector(projector);
        return true;
    }
    /**
     * Set whether a room has tables
     * @param roomNum room number of room.
     * @param tables true iff room has a projector.
     * @return true iff tables status is successfully changed.
     */
    public boolean setHasRoomTables(int roomNum, boolean tables) {
        Room room = getRoom(roomNum);
        room.setHasTables(tables);
        return true;
    }

    /**
     * @return the list of rooms with a projector.
     */
    public HashMap<Integer, String> getRoomsWithProjector() {
        HashMap<Integer, String> rooms = new HashMap<>();
        for (Room room: this.rooms) {
            if (room.HasProjector()) {
                rooms.put(room.getNumber(), String.format("capacity: %s", room.getCapacity()));
            }
        }
        return rooms;
    }
    /**
     * @return the list of rooms with tables.
     */
    public HashMap<Integer, String> getRoomsWithTables() {
        HashMap<Integer, String> rooms = new HashMap<>();
        for (Room room: this.rooms) {
            if (room.HasTables()) {
                rooms.put(room.getNumber(), String.format("capacity: %s", room.getCapacity()));
            }
        }
        return rooms;
    }
    /**
     * @return the list of rooms with a projector and tables
     */
    public HashMap<Integer, String> getRoomsWithProjectorAndTables() {
        HashMap<Integer, String> rooms = new HashMap<>();
        for (Room room : this.rooms) {
            if (room.HasProjector() && room.HasTables()) {
                rooms.put(room.getNumber(), String.format("capacity: %s", room.getCapacity()));
            }
        }
        return rooms;
    }
    public HashMap<Integer, String> getRoomsWithCapacity(int capacity) {
        HashMap<Integer, String> rooms = new HashMap<>();
        for (Room room : this.rooms) {
            if (room.getCapacity() >= capacity) {
                rooms.put(room.getNumber(), String.format("capacity: %s", room.getCapacity()));
            }
        }
        return rooms;
    }
}
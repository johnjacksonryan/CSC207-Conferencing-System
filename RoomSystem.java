package gateway.facade.system;

import gateway.RoomUpdater;

/**
 * Interface for methods of RoomUpdater.
 */
public interface RoomSystem extends System {
    /**
     * @return the gateway that updates rooms
     */
    RoomUpdater getRoomUpdater();

    /**
     * Adds a new room to the database.
     *
     * @param  roomNum       the number of the room
     * @param  capacity      how many people can fit into the room
     * @param  hasProjector  whether the room has a projector
     * @param  hasTables     whether the room has tables
     */
    default void addRoom(int roomNum, int capacity, boolean hasProjector, boolean hasTables) {
        getRoomUpdater().addRoom(roomNum, capacity, hasProjector, hasTables);
    }
}
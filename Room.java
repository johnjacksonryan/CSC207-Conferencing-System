package scheduling;

/**
 * The location where an scheduling.Event is held.
 *
 * @author Justin
 * @author Yukang
 * @version 2.0
 */
public class Room {
    private final int number;
    private int capacity;
    private boolean hasProjector;
    private boolean hasTables;
    //^^ new room parameters

    /**
     * Default constructor
     *
     * @param  number  a unique positive number denoting the room number
     * @param capacity the capacity of room
     * @param hasProjector true iff the room has a projector
     * @param hasTables true iff the room has tables
     */
    public Room(int number, int capacity, boolean hasProjector, boolean hasTables) {
        this.number = number;
        this.capacity = capacity;
        this.hasProjector = hasProjector;
        this.hasTables = hasTables;
    }

    /**
     * @return  the room's unique positive number
     */
    public int getNumber() {
        return this.number;
    }

    /**
     * @return  the room's capacity
     */
    public int getCapacity() {
        return this.capacity;
    }
    /**
     * set the room's capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * set the status of projector in the room.
     * @param projector true iff the room has a projector.
     */
    public void setHasProjector(boolean projector) { this.hasProjector = projector; }
    /**
     * set the status of tables in the room.
     * @param tables true iff the room has tables.
     */
    public void setHasTables(boolean tables) { this.hasTables = tables; }
    /**
     * get the status of projector in the room.
     * @return true iff the room has a projector.
     */
    public boolean HasProjector() { return this.hasProjector; }
    /**
     * get the status of tables in the room.
     * @return true iff the room has tables.
     */
    public boolean HasTables() { return this.hasTables; }

    /**
     * @return string representation of this room
     */
    @Override
    public String toString() {
        String attributes  = " Has no projector or tables.";
        if (this.HasProjector() && this.HasTables()) {
            attributes = " Has projector and tables";
        }
        else if (this.HasProjector() && !this.HasTables()) {
            attributes = " Has projector but no tables";
        }
        else if (!this.HasProjector() && this.HasTables()) {
            attributes = " Has tables but no projector";
        }
        return this.number + String.format("capacity: %s", this.capacity) + attributes;
    }
}
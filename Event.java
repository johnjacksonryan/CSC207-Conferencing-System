package scheduling;

import java.util.ArrayList;
import java.util.StringJoiner;

/**
 * A class representing a message
 *
 * @author John
 * @author Justin
 * @version 1.2
 */

public class Event {

    private final int id;
    private final String name;

    private int room;

    private int startTime;
    private int duration;

    private int capacity;

    private ArrayList<Integer> hosts;
    private final ArrayList<Integer> attendees;

    private boolean vip;
    private boolean needsProjector;
    private boolean needsTables;

    /**
     * @param id             Event id
     * @param name           This event's name
     *
     * @param room           The id of the room this event will be held
     *
     * @param duration       This event's length
     * @param startTime      This event's start time
     *
     * @param hosts          The id of the speakers at this event
     *
     * @param vip            VIP event
     * @param needsProjector true iff this event needs a projector
     * @param needsTables    true iff this event needs tables
     */
    public Event(int id, String name, int room, int startTime, int duration, boolean vip,
                 ArrayList<Integer> hosts, boolean needsProjector, boolean needsTables, int capacity) {
        this.id = id;
        this.name = name;

        this.room = room;

        this.startTime = startTime;
        this.duration = duration;

        this.capacity = capacity;

        this.hosts = hosts;
        this.attendees = new ArrayList<>();

        this.vip = vip;
        this.needsProjector = needsProjector;
        this.needsTables = needsTables;
    }

    /**
     * @return the id of the event
     */
    public int getId() {
        return this.id;
    }
    /**
     * @return the name of the event
     */
    public String getName() {
        return this.name;
    }

    //Time
    /**
     * @return the starting time of the event
     */
    public int getStartTime() {
        return this.startTime;
    }
    /**
     * @param startTime the start time of the event to be set
     */
    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }
    /**
     * @return the time duration of the event
     */
    public int getDuration() {
        return this.duration;
    }
    /**
     * @param duration the duration of the event to be set
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * @return the capacity of the event
     */
    public int getCapacity(){
        return this.capacity;
    }
    /**
     * change the capacity of the event
     * @param capacity new capacity of the event
     */
    public void setCapacity(int capacity){
        this.capacity = capacity;
    }

    //Room
    /**
     * @return the room the event is being held
     */
    public int getRoom(){
        return this.room;
    }
    /**
     * set the room for the event
     * @param room
     */
    public void setRoom(int room) {
        this.room = room;
    }

    //Hosts
    /**
     * @return the speaker of the event
     */
    public ArrayList<Integer> getHosts() {
        return this.hosts;
    }
    /**
     * @param id the id of the host to be added
     */
    public void addHost(Integer id) {
        this.hosts.add(id);
    }
    /**
     * @param id the id of the host to be removed
     */
    public void removeHost(Integer id) {
        this.attendees.remove(id);
    }
    /**
     * @return string of host ids
     */
    public String stringHosts() {
        StringJoiner joiner = new StringJoiner(";");
        for (int id : this.getHosts()) {
            joiner.add(String.valueOf(id));
        }
        return joiner.toString();
    }

    //Attendees
    /**
     * @return the attendees signed up for the event
     */
    public ArrayList<Integer> getAttendees() {
        return this.attendees;
    }
    /**
     * @param id the id of the attendee to be added
     */
    public void addAttendee(Integer id) {
        this.attendees.add(id);
    }
    /**
     * @param id the id of the attendee to be removed
     */
    public void removeAttendee(Integer id) {
        this.attendees.remove(id);
    }
    /**
     * String of attendee ids
     */
    public String stringAttendees() {
        StringJoiner joiner = new StringJoiner(";");
        for (int id : this.getAttendees()) {
            joiner.add(String.valueOf(id));
        }
        return joiner.toString();
    }

    //Special Parameters
    /**
     * @return true if event is vip, false otherwise
     */
    public boolean getVip() {
        return this.vip;
    }
    /**
     * @param vip vip event.
     */
    public void setVip(boolean vip) {
        this.vip = vip;
    }
    /**
     * Getter for the needsProjector parameter.
     * @return true iff this event needs a projector.
     */
    public boolean getNeedsProjector() { return this.needsProjector; }
    /**
     * Setter for the needsProjector parameter.
     * @param projector true iff this event needs a projector.
     */
    public void setNeedsProjector(boolean projector) { this.needsProjector = projector; }
    /**
     * Getter for the needsTables parameter.
     * @return true iff this event needs tables.
     */
    public boolean getNeedsTables() { return this.needsTables; }
    /**
     * Setter for the needsTables parameter.
     * @param tables true iff this event needs tables
     */
    public void setNeedsTables(boolean tables) { this.needsTables = tables; }




    @Override
    public String toString() {
        return String.format("%s: %s:00 - %s:00 [%s hour(s)] ", this.name, this.startTime, this.startTime + this.duration,
                this.duration) + String.format(", (vip): %s", this.vip) + String.format(", (uses projector): %s",
                this.needsProjector) + String.format(", (uses tables): %s", this.needsTables);
    }
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Event)) {
            return false;
        }
        Event other = (Event) obj;
        return this.id == other.id;

    }

}

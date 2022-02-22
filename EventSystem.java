package gateway.facade.system;

import gateway.EventUpdater;

import java.util.ArrayList;

/**
 * Interface for methods of EventUpdater.
 *
 * @author Justin
 * @version 1.0
 */
public interface EventSystem extends System {
    /**
     * @return the gateway that updates events
     */
    EventUpdater getEventUpdater();

    /**
     * Adds a new event to the database.
     *
     */
    default void addEvent(int id, String name, int roomNum, int time, int duration, int capacity, boolean vip,
                         ArrayList<Integer> hosts, boolean needsProjector, boolean needsTables) {
        getEventUpdater().addEvent(id, name, roomNum, time, duration, capacity, vip, hosts, needsProjector, needsTables);
    }
    /**
     * Removes an event with the id from the database.
     *
     * @param eventId the unique id integer of the event
     */
    default void removeEvent(int eventId) {
        getEventUpdater().removeEvent(eventId);
    }
    /**
     * Updates the event with the id from the database.
     *
     * @param eventId the unique id integer of the event
     * @param time the new time integer of when the event starts
     */
    default void updateEvent(int eventId, int time) {
        getEventUpdater().updateEvent(eventId, time);
    }
    /**
     * Updates an event either from the addition of removal of an attendee.
     *
     * @param  attendeeId  id of the attendee being modified
     * @param  eventId     id of the event being modified
     * @param  remove      true iff attendee is being removed from the event
     */
    default void updateAttendee(int attendeeId, int eventId, boolean remove) {
        getEventUpdater().updateAttendee(attendeeId, eventId, remove);
    }
}
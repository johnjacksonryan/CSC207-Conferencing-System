package users.facade.manager_interface;

import scheduling.Event;
import scheduling.EventManager;
import scheduling.RoomManager;
import users.base.UserManager;

import java.util.ArrayList;
import java.util.HashMap;

public interface EventInterface {
    EventManager getEventManager();

    /**
     * get room manager
     * @return room manager
     */
    default RoomManager getRoomManager() {
        return getEventManager().getRoomManager();
    }

    /**
     * get user manager
     * @return user manager
     */
    default UserManager getUserManager() {
        return getEventManager().getUserManager();
    }

    /**
     * get specific event
     *
     * @param id id of event
     * @return event
     */
    default Event getEvent(int id) {
        return getEventManager().getEvent(id);
    }

    /**
     * get string info of specific event for event updater
     *
     * @param eventId id of event
     * @return list of string info about event
     */
    default String[] getEventInfo(int eventId) {
        return getEventManager().getEventInfo(eventId);
    }

    /**
     * get number of events
     *
     * @return number of events
     */
    default int getNumEvents() {
        return getEventManager().getNumEvents();
    }

    /**
     * get all scheduled events
     *
     * @param attendeeId id of attendee when registering, -1 to just viewing events
     * @return list of events
     */
    default HashMap<Integer, String> getEvents(int attendeeId) {
        return getEventManager().getEvents(attendeeId);
    }

    /**
     * @return id of event iff the event is successfully added, otherwise -1
     */
    default int addEvent(String name, int roomNum, int time, int duration, int capacity,
                         boolean vip, ArrayList<Integer> hosts, boolean needsProjector, boolean needsTables) {
        return getEventManager().addEvent(name, roomNum, time, duration, capacity, vip, hosts, needsProjector, needsTables);
    }

    /**
     *
     * @param id the id of event to be removed
     */
    default void removeEvent(int id) {
        getEventManager().removeEvent(id);
    }

    /**
     *
     * @param  id   the id of event to be rescheduled
     * @param  newTime the time the event will be switched to
     * @return true iff the event time is successfully changed
     */
    default boolean rescheduleEvent(int id, int newTime) {
        return getEventManager().rescheduleEvent(id, newTime);
    }

    /**
     * Register an attendee with an event
     *
     * @param  attendeeId the id of desired attendee
     * @param  eventId    the id of event to register
     * @return true if the attendee was successfully registered, false otherwise
     */
    default boolean addAttendee(int attendeeId, int eventId) {
        return getEventManager().addAttendee(attendeeId, eventId);
    }

    /**
     * Cancel an attendee's registration
     * Precondition: attendee is already registered for the event
     *
     * @param  attendeeId the id of desired attendee
     * @param  eventId the id of event to cancel
     */
    default void removeAttendee(int attendeeId, int eventId) {
        getEventManager().removeAttendee(attendeeId, eventId);
    }

    /**
     * get capacity of event
     *
     * @param eventId the id of event
     * @return current capacity
     */
    default int getCapacity(int eventId) {
        return getEventManager().getCapacity(eventId);
    }

    /**
     * get available capacity of event
     * @param eventId id of event
     * @return remaining capacity
     */
    default int getAvailableCapacity(int eventId) {
        return getEventManager().getAvailableCapacity(eventId);
    }

    /**
     * change capacity of event
     *
     * @param eventId the id of event
     * @param capacity the new capacity
     * @return true iff new capacity is positive value and compatible with current attendees,
     */
    default boolean changeCapacity(int eventId, int capacity) {
        return getEventManager().changeCapacity(eventId, capacity);
    }

    /**
     * get all attendee registered in specific event
     *
     * @param eventId id of event
     * @return list of attendee's id
     */
    default ArrayList<Integer> getAttendeesOfEvent(int eventId) {
        return getEventManager().getAttendeesOfEvent(eventId);
    }

    /**
     * get all events the attendee is signed up for
     *
     * @param eventId the id of attendee whose events we want
     * @return the list of events this attendee is signed up for
     */
    default HashMap<Integer, String> getEventsOfAttendee(int eventId) {
        return getEventManager().getEventsOfAttendee(eventId);
    }

    /**
     * get all events the speaker is hosting
     *
     * @param speakerId the id of speaker whose events we want
     * @return the list of events this speaker is hosting
     */
    default HashMap<Integer, String> getEventsOfHost(int speakerId) {
        return getEventManager().getEventsOfHost(speakerId);
    }

    /**
     * get all event's speakers which this attendee id signed up for
     *
     * @param attendeeId the id of attendee
     * @return list of speaker's id
     */
    default ArrayList<Integer> getSpeakersForAttendee(int attendeeId) {
        return getEventManager().getSpeakersForAttendee(attendeeId);
    }

    /**
     * get all attendee registered in events this speaker is hosting
     *
     * @param speakerId the id of Speaker
     * @return list of attendee's id
     */
    default ArrayList<Integer> getAttendeesForSpeaker(int speakerId) {
        return getEventManager().getAttendeesForSpeaker(speakerId);
    }

    /**
     * Sets whether or not the event needs a projector.
     * @param eventId the id of the event.
     * @param needsProjector true iff the event needs a projector.
     * @return true iff the projector status is successfully updated.
     */
    default boolean setNeedsProjector(int eventId, boolean needsProjector) {
        return getEventManager().setNeedsProjector(eventId, needsProjector);
    }

    /**
     * Gets whether or not the event needs a projector.
     * @param eventId the id of the event.
     * @return true iff the event needs a projector.
     */
    default boolean getNeedsProjector(int eventId) {
        return getEventManager().getNeedsProjector(eventId);
    }

    /**
     * Sets whether or not the event needs tables.
     * @param eventId the id of the event.
     * @param needsTables true iff the event needs tables.
     * @return true iff the status of tables is successfully updated.
     */
    default boolean setNeedsTables(int eventId, boolean needsTables) {
        return getEventManager().setNeedsTables(eventId, needsTables);
    }

    /**
     * Gets whether or no the event needs tables.
     * @param eventId the id of the event.
     * @return true iff the event needs tables.
     */
    default boolean getNeedsTables(int eventId) {
        return getEventManager().getNeedsTables(eventId);
    }
}

package scheduling;

import users.base.UserManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The system for events.
 *
 * @author Carl
 * @author John
 * @author Justin
 * @author Janardan
 * @author Yukang
 * @version 2.2
 */

public class EventManager {
    private final ArrayList<Event> events;
    private final RoomManager roomManager;
    private final UserManager userManager;
    private int id;

    /**
     * Default constructor
     */
    public EventManager(RoomManager roomManager, UserManager userManager) {
        this.events = new ArrayList<>();
        this.roomManager = roomManager;
        this.userManager = userManager;
        this.id = 0;
    }

    /**
     * get room manager
     * @return room manager
     */
    public RoomManager getRoomManager() {
        return roomManager;
    }
    /**
     * get user manager
     * @return user manager
     */
    public UserManager getUserManager() {
        return userManager;
    }

    /**
     * get specific event
     *
     * @param id id of event
     * @return event
     */
    public Event getEvent(int id) {
        for (Event event: this.events) {
            if (event.getId() == id) {
                return event;
            }
        }
        return null;
    }
    /**
     * get all scheduled events not attended by attendee
     *
     * @param attendeeId id of attendee when registering, -1 to just viewing events
     * @return list of events
     */
    public HashMap<Integer, String> getEvents(int attendeeId) {
        HashMap<Integer, String> events = new HashMap<>();
        for (Event event: this.events) {
            if (!event.getAttendees().contains(attendeeId)) {
                int eventId = event.getId();
                int slot = getAvailableCapacity(eventId);
                events.put(eventId, event.toString() + ". Capacity: " + (slot == 0 ? "FULL" : String.valueOf(slot)));
            }
        }
        return events;
    }
    /**
     * @return id of event iff the event is successfully added, otherwise -1
     */
    public int addEvent(String name, int roomNum, int time, int duration, int capacity, boolean vip,
                        ArrayList<Integer> hosts, boolean needsProjector, boolean needsTables) {
        Event event = new Event(this.id, name, roomNum, time, duration, vip, hosts, needsProjector, needsTables, capacity);
        if (!events.contains(event) && canAdd(event, event.getStartTime(), event.getHosts())) {
            this.events.add(event);
            this.id = this.id + 1;
            return id - 1;
        }
        return -1;
    }
    /**
     * remove an event
     * @param id the id of event to be removed
     * @return true iff the even is successfully removed
     */
    public boolean removeEvent(int id) {
        Event event = getEvent(id);
        if (event != null) {
            this.events.remove(event);
            return true;
        }
        return false;
    }
    /**
     * reschedule an event
     * @param  id   the id of event to be rescheduled
     * @param  newTime the time the event will be switched to
     * @return true iff the event time is successfully changed
     */
    public boolean rescheduleEvent(int id, int newTime) {
        Event event = getEvent(id);
        ArrayList<Integer> host = event.getHosts();
        if (canAdd(event, newTime, host)) {
            event.setStartTime(newTime);
            return true;
        }
        return false;
    }
    /**
     * get string info of specific event for event updater
     *
     * @param eventId id of event
     * @return list of string info about event
     */
    public String[] getEventInfo(int eventId) {
        Event event = getEvent(eventId);
        return new String[] {
                event.getName(), String.valueOf(event.getRoom()), String.valueOf(event.getStartTime()),
                String.valueOf(event.getDuration()), Boolean.toString(event.getVip()),
                Boolean.toString(event.getNeedsProjector()), Boolean.toString(event.getNeedsTables()),
                event.stringHosts(), event.stringAttendees()};
    }
    /**
     * get number of events
     *
     * @return number of events
     */
    public int getNumEvents() {
        return this.events.size();
    }

    //registration
    /**
     * Register an attendee with an event
     *
     * @param  attendeeId the id of desired attendee
     * @param  eventId    the id of event to register
     * @return true if the attendee was successfully registered, false otherwise
     */
    public boolean addAttendee(int attendeeId, int eventId) {
        Event event = getEvent(eventId);

        for (int id: getEventsOfAttendee(attendeeId).keySet()) {
            // Time overlap
            Event attendingEvent = getEvent(id);
            int startTime = attendingEvent.getStartTime();
            int endTime = startTime + attendingEvent.getDuration();
            if (startTime <= event.getStartTime() && event.getStartTime() < endTime) {
                return false;
            } if (event.getStartTime() <= startTime && startTime < event.getStartTime() + event.getDuration()) {
                return false;
            }
        }
        // scheduling.Event is full
        if (event.getAttendees().size() == getCapacity(eventId)) {
            return false;
        }
        // event is vip and attendee is not vip
        if (event.getVip() && !this.userManager.getUserVip(attendeeId)) {
            return false;
        }
        event.addAttendee(attendeeId);
        return true;
    }
    /**
     * Cancel an attendee's registration
     * Precondition: attendee is already registered for the event
     *
     * @param  attendeeId the id of desired attendee
     * @param  eventId the id of event to cancel
     */
    public void removeAttendee(int attendeeId, int eventId) {
        getEvent(eventId).removeAttendee(attendeeId);
    }

    //capacity
    /**
     * get capacity of event
     *
     * @param eventId the id of event
     * @return current capacity
     */
    public int getCapacity(int eventId) {
        return getEvent(eventId).getCapacity();
    }
    /**
     * change capacity of event
     *
     * @param eventId the id of event
     * @param capacity the new capacity
     * @return true iff new capacity is positive value and compatible with current attendees,
     */
    public boolean changeCapacity(int eventId, int capacity) {
        Event event = getEvent(eventId);
        if (capacity < event.getAttendees().size() ||
                roomManager.getRoom(event.getRoom()).getCapacity() < capacity) {
            return false;
        }
        event.setCapacity(capacity);
        return true;
    }
    /**
     * get available capacity of event
     * @param eventId id of event
     * @return remaining capacity
     */
    public int getAvailableCapacity(int eventId) {
        return getCapacity(eventId) - getEvent(eventId).getAttendees().size();
    }
    /**
     * Sets whether or not the event needs a projector.
     * @param eventId the id of the event.
     * @param needsProjector true iff the event needs a projector.
     * @return true iff the projector status is successfully updated.
     */

    //special params
    public boolean setNeedsProjector(int eventId, boolean needsProjector) {
        Event event = getEvent(eventId);
        event.setNeedsProjector(needsProjector);
        return true;
    }
    /**
     * Gets whether or not the event needs a projector.
     * @param eventId the id of the event.
     * @return true iff the event needs a projector.
     */
    public boolean getNeedsProjector(int eventId) {
        Event event = getEvent(eventId);
        return event.getNeedsProjector();
    }
    /**
     * Sets whether or not the event needs tables.
     * @param eventId the id of the event.
     * @param needsTables true iff the event needs tables.
     * @return true iff the status of tables is successfully updated.
     */
    public boolean setNeedsTables(int eventId, boolean needsTables) {
        Event event = getEvent(eventId);
        event.setNeedsTables(needsTables);
        return true;
    }
    /**
     * Gets whether or no the event needs tables.
     * @param eventId the id of the event.
     * @return true iff the event needs tables.
     */
    public boolean getNeedsTables(int eventId) {
        Event event = getEvent(eventId);
        return event.getNeedsTables();
    }


    //get list for users
    /**
     * get all attendee registered in specific event
     *
     * @param eventId id of event
     * @return list of attendee's id
     */
    public ArrayList<Integer> getAttendeesOfEvent(int eventId) {
        return getEvent(eventId).getAttendees();
    }
    /**
     * get all events the attendee is signed up for
     *
     * @param eventId the id of attendee whose events we want
     * @return the list of events this attendee is signed up for
     */
    public HashMap<Integer, String> getEventsOfAttendee(int eventId) {
        HashMap<Integer, String> result = new HashMap<>();
        for (Event event : events) {
            if (event.getAttendees().contains(eventId)) {
                result.put(event.getId(), event.toString());
            }
        }
        return result;
    }
    /**
     * get all events the speaker is hosting
     *
     * @param speakerId the id of speaker whose events we want
     * @return the list of events this speaker is hosting
     */
    public HashMap<Integer, String> getEventsOfHost(int speakerId) {
        HashMap<Integer, String> result = new HashMap<>();
        for (Event event : events)
            if (event.getHosts().contains(speakerId))
                result.put(event.getId(), event.toString());
        return result;
    }
    /**
     * get all event's speakers which this attendee id signed up for
     *
     * @param attendeeId the id of attendee
     * @return list of speaker's id
     */
    public ArrayList<Integer> getSpeakersForAttendee(int attendeeId) {
        ArrayList<Integer> speakerIds = new ArrayList<>();
        for (Event event: this.events) {
            if (event.getAttendees().contains(attendeeId)) {
                speakerIds.addAll(event.getHosts());
            }
        }
        return speakerIds;
    }
    /**
     * get all attendee registered in events this speaker is hosting
     *
     * @param speakerId the id of Speaker
     * @return list of attendee's id
     */
    public ArrayList<Integer> getAttendeesForSpeaker(int speakerId) {
        ArrayList<Integer> attendeeIds = new ArrayList<>();
        for (Event event : this.events) {
            if (event.getHosts().contains(speakerId)) {
                attendeeIds.addAll(event.getAttendees());
            }
        }
        return attendeeIds;
    }


    /** Check if an event can be added at the specific time/ or with specific new hosts
     *
     * @param event1  the event to be added
     * @param newTime the time the event will be switched to
     * @return true iff the event time is can be added at given time
     */
    private boolean canAdd(Event event1, int newTime, ArrayList<Integer> newHosts) {
        int room1 = event1.getRoom();
        Room room = getRoomManager().getRoom(room1);
        if ((event1.getNeedsProjector() && !room.HasProjector()) ||
                (event1.getNeedsTables() && !room.HasTables()) ||
                room.getCapacity() < event1.getCapacity()) {
            //if the event needs a projector/tablets and the room doesn't have.
            // or room capacity too small although precondition exists in controller
            return false;
        }

        int newEnd = newTime + event1.getDuration();
        for (Event event2 : events) {
            int room2 = event2.getRoom();
            int start = event2.getStartTime();
            int end = start + event2.getDuration();
            ArrayList<Integer> hosts = event2.getHosts();

            //if two events aren't the same and have time conflict
            if (!event1.equals(event2) && newTime < end && start < newEnd){
                // if such two events share rooms
                if (room1 == room2){
                    return false;
                }
                // if one of the host who are participating in the event has time conflict
                // (other event also has such host at the same time)
                for (int host: hosts) {
                    if (newHosts.contains(host)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}

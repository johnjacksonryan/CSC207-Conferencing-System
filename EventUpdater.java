package gateway;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;
import scheduling.EventManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Loads or saves user data to database
 *
 * @author Dickson Li
 * @author Carl
 * @author Yukang
 * @author Janardan
 * @version 2.2
 */
public class EventUpdater {
    private final EventManager eventManager;
    private MongoCollection<Document> events;

    /**
     * Default constructor.
     *
     * @param eventManager the use case for handling Events in the program.
     */
    public EventUpdater(EventManager eventManager, Database database) {
        this.eventManager = eventManager;
        this.events = database.getDatabase().getCollection("events");
    }

    /**
     * Loads all event data from database
     */
    public void loadEvents() {
        for (Document document : this.events.find(new Document())) {
            String name = document.getString("name");
            int roomNum = document.getInteger("roomNum");
            int time = document.getInteger("time");
            int duration = document.getInteger("duration");
            int capacity = document.getInteger("capacity");
            boolean vip = document.getBoolean("vip");
            List<Integer> hosts = document.getList("hosts", Integer.class);
            ArrayList<Integer> host = new ArrayList<>(hosts);
            boolean needsProjector = document.getBoolean("needsProjector");
            boolean needsTables = document.getBoolean("needsTables");
            int id = eventManager.addEvent(name, roomNum, time, duration, capacity, vip, host, needsProjector, needsTables);
            List<Integer> attendeeIDs = document.getList("attendees", Integer.class);
            loadAttendees(attendeeIDs, id);
            ObjectId objectId = document.getObjectId("_id");
            this.events.findOneAndUpdate(Filters.eq("_id", objectId), Updates.set("id", id));
        }
    }

    /**
     * Loads all attendees attending an event
     */
    private void loadAttendees(List<Integer> attendeeIDs, int eventId) {
        for (int attendeeID : attendeeIDs)
            eventManager.addAttendee(attendeeID, eventId);
    }

    /**
     * Adds a new event to the database
     */
    public void addEvent(int id, String name, int roomNum, int time, int duration, int capacity,
                         boolean vip, ArrayList<Integer> hosts, boolean needsProjector, boolean needsTables) {
        Document document = new Document();
        document.put("id", id);
        document.put("name", name);
        document.put("roomNum", roomNum);
        document.put("time", time);
        document.put("duration", duration);
        document.put("capacity", capacity);
        document.put("vip", vip);
        document.put("hosts", hosts);
        document.put("needsProjector", needsProjector);
        document.put("needsTables", needsTables);
        ArrayList<Integer> attendeeIds = new ArrayList<>();
        document.put("attendees", attendeeIds);
        this.events.insertOne(document);
    }

    /**
     * Removes an event with the id from the database
     *
     * @param eventId the unique id integer of the event
     */
    public void removeEvent(int eventId) {
        this.events.deleteOne(Filters.eq("id", eventId));
    }

    /**
     * Updates the event with the id from the database
     *
     * @param eventId the unique id integer of the event
     * @param time the new time integer of when the event starts
     */
    public void updateEvent(int eventId, int time) {
        this.events.findOneAndUpdate(Filters.eq("id", eventId), Updates.set("time", time));
    }

    public void changeCapacity(int  eventId, int capacity){
        this.events.findOneAndUpdate(Filters.eq("id", eventId), Updates.set("capacity", capacity));
    }

    public void changeDuration(int eventId, int duration) {
        this.events.findOneAndUpdate(Filters.eq("id", eventId), Updates.set("duration", duration));
    }

    public void changeRoom(int eventId, int roomNum) {
        this.events.findOneAndUpdate(Filters.eq("id", eventId), Updates.set("roomNum", roomNum));
    }

    public void updateHost(int hostId, int eventId, boolean remove) {
        if (remove) {
            this.events.findOneAndUpdate(Filters.eq("id", eventId), Updates.combine(
                    Updates.pull("hosts", hostId)
            ));
        } else {
            this.events.findOneAndUpdate(Filters.eq("id", eventId), Updates.combine(
                    Updates.push("hosts", hostId)
            ));
        }
    }

    public void updateAttendee(int attendeeId, int eventId, boolean remove) {
        if (remove) {
            this.events.findOneAndUpdate(Filters.eq("id", eventId), Updates.combine(
                    Updates.pull("attendees", attendeeId)
            ));
        } else {
            this.events.findOneAndUpdate(Filters.eq("id", eventId), Updates.combine(
                    Updates.push("attendees", attendeeId)
            ));
        }
    }

}


package gateway;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import scheduling.RoomManager;

/**
 * Loads or saves rooms to the database
 *
 * @author Dickson Li
 * @author Yukang
 * @author Janardan
 * @version 2.1
 */

public class RoomUpdater {

    /*
    Rooms format
    roomNum, capacity, hasProjector, hasTablets
     */
    /**
     * Loads / saves rooms from a database.
     */

    private final RoomManager roomManager;
    private MongoCollection<Document> rooms;

    /**
     * Default constructor
     *
     * @param roomManager the use case for handling Rooms in the program
     * @param database    the database holding the collection of rooms
     */
    public RoomUpdater(RoomManager roomManager, Database database) {
        this.roomManager = roomManager;
        this.rooms = database.getDatabase().getCollection("rooms");
    }

    /**
     * Loads all rooms from database
     */
    public void loadRooms() {
        for (Document document : this.rooms.find(new Document())) {
            int room_num = document.getInteger("roomNum");
            int capacity = document.getInteger("capacity");
            boolean projector = document.getBoolean("hasProjector");
            boolean tables = document.getBoolean("hasTables");
            roomManager.addRoom(room_num, capacity, projector, tables);
        }
    }

    /**
     * Adds a new room to the database
     */
    public void addRoom(int roomNum, int capacity, boolean hasProjector, boolean hasTables) {
        Document document = new Document();
        document.put("roomNum", roomNum);
        document.put("capacity", capacity);
        document.put("hasProjector", hasProjector);
        document.put("hasTables", hasTables);
        this.rooms.insertOne(document);
    }

    public void changeCapacity(int roomNum, int newCapacity) {
        Bson filter = findRoom(roomNum);
        this.rooms.findOneAndUpdate(filter, Updates.set("capacity", newCapacity));
    }
    public void changeProjector(int roomNum) {
        Bson filter = findRoom(roomNum);
        boolean current = this.rooms.find(filter).first().getBoolean("hasProjector");
        this.rooms.findOneAndUpdate(filter, Updates.set("hasProjector", !current));
    }
    public void changeTablets(int roomNum) {
        Bson filter = findRoom(roomNum);
        boolean current = this.rooms.find(filter).first().getBoolean("hasTablets");
        this.rooms.findOneAndUpdate(filter, Updates.set("hasTablets", !current));
    }
    private Bson findRoom(int roomNum){
        return Filters.eq("roomNum", roomNum);
    }
}

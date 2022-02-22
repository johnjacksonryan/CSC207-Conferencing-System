package gateway;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import users.attendee.AttendeeManager;
import users.base.UserManager;

import java.util.ArrayList;

/**
 * Loads or saves user data to the database
 *
 * @author Dickson Li
 */

public class UserUpdater {

    private final MongoCollection<Document> users;
    private final UserManager userManager;

    /**
     * Default constructor
     *
     * @param userManager the use case for handling Users in the program
     * @param database the database which the mondodb collections are stored
     */
    public UserUpdater(UserManager userManager, Database database) {
        this.userManager = userManager;
        this.users = database.getDatabase().getCollection("users");
    }

    /**
     * Loads all user data from the database
     */
    public void loadUsers() {
        for (Document document : this.users.find(new Document())) {
            String name = document.getString("name");
            UserManager.UserType type = UserManager.UserType.valueOf(document.getString("type"));
            boolean vip = document.getBoolean("vip", false);
            int id = userManager.addUser(type, name, vip);
            if (type == UserManager.UserType.ATTENDEE) {
                AttendeeManager attendeeManager = new AttendeeManager(userManager);
                attendeeManager.setContacts(id, document.getList("contacts", Integer.class));
                attendeeManager.setRequests(id, document.getList("contactRequests", Integer.class));
                this.userManager.receiveUsers(attendeeManager);
            }
        }
    }

    /**
     * Adds a user to the database
     *
     * @param id the id of the user
     * @param type the type of the user
     * @param name the name of the user
     * @param vip whether or not the user is a vip (only applicable for Attendees)
     */
    public void addUser(int id, UserManager.UserType type, String name, boolean vip) {
        Document document = new Document();
        document.put("id", id);
        document.put("name", name);
        document.put("type", type.name());
        if (type == UserManager.UserType.ATTENDEE) {
            document.put("vip", vip);
            document.put("contacts", new ArrayList<Integer>());
            document.put("contactRequests", new ArrayList<Integer>());
        }
        this.users.insertOne(document);
    }


    public void changeName(int userId, String NewName){
        this.users.findOneAndUpdate(Filters.eq("id", userId), Updates.set("name", NewName));
    }

    public void changePassword(){

    }
    /**
     * Updating contact requests from on user to another
     *
     * @param userID The user id doing the request
     * @param contactID The user id that is being requested
     */
    public void requestContact(int userID, int contactID) {
        this.users.findOneAndUpdate(Filters.eq("id", userID), Updates.push("contacts", contactID));
        this.users.findOneAndUpdate(Filters.eq("id", contactID), Updates.push("contactRequests", userID));
    }

    /**
     * Updating contact request when accepting contacts
     *
     * @param userID The user id doing the request
     * @param contactID The user id that is being requested
     */
    public void acceptContact(int userID, int contactID) {
        this.users.findOneAndUpdate(Filters.eq("id", userID), Updates.combine(
                Updates.pull("contactRequests", contactID),
                Updates.push("contacts", contactID)
        ));
    }

}

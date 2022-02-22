package users.base;

import users.attendee.Attendee;
import users.organizer.Organizer;
import users.speaker.Speaker;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The use case for User entities.
 *
 * @author Yukang
 * @author Justin
 * @author Linjia
 * @version 2.3
 */
public class UserManager {

    /**
     * The types of users that can be registered into the program.
     */
    public enum UserType {
        ATTENDEE,
        ORGANIZER,
        SPEAKER,
    }
    private ArrayList<User> users;

    /**
     * Default constructor.
     */
    public UserManager() {
        this.users = new ArrayList<>();
    }
    /**
     * Setter for users.
     *
     * @param  userManager  the user manager passing users.
     */
    public void receiveUsers(UserManager userManager) {
        this.users = userManager.getUsers();
    }

    /**
     * Get user with the inputted id.
     *
     * @param   id  the id of desired user
     * @return the user with the matching id if they exist; otherwise return null
     */
    public User getUser(int id) {
        for (User user : this.users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }
    /**
     * Get name of user with the inputted id.
     *
     * @param  id  the id of the desired user
     * @return the name of the user with the matching id
     */
    public String getUserName(int id) {
        return getUser(id).getName();
    }
    /**
     * Get type of user by its id.
     *
     * @param  id  the id of user
     * @return user type if user exists, otherwise null
     */
    public UserType getUserType(int id) {
        User user = getUser(id);
        if (user instanceof Attendee) {
            return UserType.ATTENDEE;
        } else if (user instanceof Speaker) {
            return UserType.SPEAKER;
        } else if (user instanceof Organizer) {
            return UserType.ORGANIZER;
        } else {
            return null;
        }
    }
    /**
     * @return list of users
     */
    public ArrayList<User> getUsers() {
        return this.users;
    }
    /**
     * Get list of users corresponding to list of ids.
     *
     * @param  ids  list of user ids
     * @return list of users
     */
    public HashMap<Integer, String> getListOfUsers(ArrayList<Integer> ids) {
        HashMap<Integer, String> users = new HashMap<>();
        for (int id: ids) {
            users.put(id, getUserName(id));
        }
        return users;
    }
    /**
     * @return list of users who are Attendees
     */
    public HashMap<Integer, String> getAttendees() {
        HashMap<Integer, String> attendees = new HashMap<>();
        for (User user: this.users) {
            if (user instanceof Attendee) {
                attendees.put(user.getId(), user.getName());
            }
        }
        return attendees;
    }
    /**
     * @return list of users who are Speakers
     */
    public HashMap<Integer, String> getSpeakers() {
        HashMap<Integer, String> speakers = new HashMap<>();
        for (User user: this.users) {
            if (user instanceof Speaker) {
                speakers.put(user.getId(), user.getName());
            }
        }
        return speakers;
    }
    /**
     * @return list of users who are Organizers
     */
    public HashMap<Integer, String> getOrganizers() {
        HashMap<Integer, String> organizers = new HashMap<>();
        for (User user: this.users) {
            if (user instanceof Organizer) {
                organizers.put(user.getId(), user.getName());
            }
        }
        return organizers;
    }

    /**
     * Register a new user to Conference System.
     *
     * @param  type      the type of user that is being registered
     * @param  name      the name of user
     */
    public int addUser(UserType type, String name, boolean vip) {
        User user;

        switch(type) {
            case ORGANIZER:
                user = new Organizer(users.size(), name);
                break;
            case SPEAKER:
                user = new Speaker(users.size(), name);
                break;
            default:
                user = new Attendee(users.size(), name, vip);
        }

        users.add(user);
        return user.getId();
    }

    public boolean getUserVip(int id) {
        User user = getUser(id);
        if (user instanceof Attendee) {
            return ((Attendee) user).isVip();
        } else {
            return false;
        }
    }
}

package users.attendee;

import users.base.User;

import java.util.ArrayList;

/**
 * Participants at the conference who attend talks (events)
 *
 * @author Justin
 * @author Carl
 * @author Yukang
 * @version 1.3
 */
public class Attendee extends User {

    private final boolean vip;
    private final ArrayList<Integer> contacts;
    private final ArrayList<Integer> contactRequests;

    /**
     * Default constructor.
     * Attendees begin with an empty contacts list
     *
     * @param  id        the attendee's unique identification number
     * @param  name      the attendee's name
     */
    public Attendee(int id, String name, boolean vip) {
        super(id, name);
        this.vip = vip;
        this.contacts = new ArrayList<>();
        this.contactRequests = new ArrayList<>();
    }

    /**
     * @return  true if the attendee is a vip, false otherwise
     */
    public boolean isVip() {
        return this.vip;
    }

    /**
     * @return  the attendee's list of contacts
     */
    public ArrayList<Integer> getContacts() {
        return this.contacts;
    }
    /**
     * @return  the attendee's list of contact requests
     */
    public ArrayList<Integer> getContactRequests() {
        return this.contactRequests;
    }
    public boolean addContact(int user) {
        if (!hasContact(user) && user != this.getId()) {
            this.contacts.add(user);
            return true;
        }

        return false;
    }
    /**
     * @param   user  the user requesting contact
     * @return  true iff new contact isn't already in contact requests, otherwise false
     */
    public boolean addContactRequest(int user) {
        if (!this.contactRequests.contains(user) && user != this.getId()) {
            this.contactRequests.add(user);
            return true;
        }
        return false;
    }
    /**
     * @param   user  the contact request ro remove
     * @return  true iff contact request exists, otherwise false
     */
    public boolean removeContactRequest(int user) {
        if (this.contactRequests.contains(user)) {
            this.contactRequests.remove(Integer.valueOf(user));
            return true;
        }
        return false;
    }
    /**
     * @param   user  the id of a user
     * @return  true iff user is already in contacts
     */
    public boolean hasContact(int user) {
        return this.contacts.contains(user);
    }
}
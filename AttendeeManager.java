package users.attendee;

import users.base.UserManager;

import java.util.HashMap;
import java.util.List;

/**
 * The use case managing the Attendee entities
 *
 * @author Justin
 * @author Linjia
 * @author Yukang
 * @version 2.3
 */
public class AttendeeManager extends UserManager {

    public AttendeeManager(UserManager userManager) {
        receiveUsers(userManager);
    }

    /**
     * Set Attendee's contact for updater uses
     *
     * @param attendeeId the id of Attendee having contacts
     * @param contacts   the list of contact ids
     */
    public void setContacts(int attendeeId, List<Integer> contacts) {
        for (int contact : contacts) {
            ((Attendee) getUser(attendeeId)).addContact(contact);
        }
    }
    /**
     * Set Attendee's contact request for updater uses
     *
     * @param attendeeId the id of Attendee having requests
     * @param requests   the list of requesting ids
     */
    public void setRequests(int attendeeId, List<Integer> requests) {
        for (int request : requests) {
            ((Attendee) getUser(attendeeId)).addContactRequest(request);
        }
    }

    /**
     * Add an Attendee to contact requests
     *
     * @param senderId   the id of user requesting contact
     * @param receiverId the id of user receiving request
     */
    public void addContactRequest(int senderId, int receiverId) {
        Attendee sender = (Attendee) getUser(senderId);
        Attendee receiver = (Attendee) getUser(receiverId);
        receiver.addContactRequest(senderId);
        sender.addContact(receiverId);
    }
    /**
     * Add an Attendee to contacts
     *
     * @param receiverId the id of user receiving request
     * @param senderId   the id of user requesting contact
     */
    public void removeContactRequest(int receiverId, int senderId) {
        Attendee receiver = (Attendee) getUser(receiverId);
        receiver.removeContactRequest(senderId);
        receiver.addContact(senderId);
    }
    /**
     * Cast the user's contacts into a list of Attendee objects
     *
     * @param attendeeId the id of user getting contacts
     * @return the user's contacts
     */
    public HashMap<Integer, String> getContacts(int attendeeId) {
        HashMap<Integer, String> result = new HashMap<>();
        for (int id : ((Attendee) getUser(attendeeId)).getContacts()) {
            result.put(id, getUser(id).getName());
        }
        return result;
    }
    /**
     * Cast the user's contacts into a list of Attendee objects
     *
     * @param attendeeId the id of user getting contact requests
     * @return the user's contacts
     */
    public HashMap<Integer, String> getContactRequests(int attendeeId) {
        HashMap<Integer, String> result = new HashMap<>();
        for (int id : ((Attendee) getUser(attendeeId)).getContactRequests()) {
            result.put(id, getUser(id).getName());
        }
        return result;
    }
    /**
     * Get list of attendee who can be added in this user's contact
     *
     * @param attendeeId the id of user trying to add attendees
     * @return list of attendee.
     */
    public HashMap<Integer, String> getAddableContacts(int attendeeId) {
        HashMap<Integer, String> addableContacts = new HashMap<>();
        HashMap<Integer, String> currentContacts = getContacts(attendeeId);
        HashMap<Integer, String> currentRequests = getContactRequests(attendeeId);
        for (int id : getAttendees().keySet()) {
            if (attendeeId != id && currentContacts.get(id) == null && currentRequests.get(id) == null
                    && !getContactRequests(id).containsKey(attendeeId)) {
                addableContacts.put(id, getUser(id).getName());
            }
        }
        return addableContacts;
    }
    /**
     * Cast the user's contacts into a list of messageable Attendee objects
     *
     * @param attendeeId the id of user getting contacts
     * @return the user's contacts
     */
    public HashMap<Integer, String> getMessageableContacts(int attendeeId) {
        HashMap<Integer, String> result = new HashMap<>();
        for (int id : ((Attendee) getUser(attendeeId)).getContacts()) {
            if (!((Attendee) getUser(id)).getContactRequests().contains(attendeeId)) {
                result.put(id, getUser(id).getName());
            }
        }
        return result;
    }
}

package users.facade;

import messaging.MessageManager;
import scheduling.EventManager;
import users.attendee.AttendeeManager;
import users.base.UserManager;

import java.util.HashMap;
import java.util.List;

public class AttendeeManagerFacade extends UserManagerFacade {
    private AttendeeManager attendeeManager;

    public AttendeeManagerFacade(MessageManager messageManager, EventManager eventManager, AttendeeManager attendeeManager) {
        super(attendeeManager, messageManager, eventManager);
        this.attendeeManager = attendeeManager;
    }

    /**
     * Set Attendee's contact request for updater uses
     *
     * @param attendeeId the id of Attendee having requests
     * @param requests   the list of requesting ids
     */
    public void setRequests(int attendeeId, List<Integer> requests) {
        attendeeManager.setRequests(attendeeId, requests);
    }

    /**
     * Add an Attendee to contact requests
     *
     * @param senderId   the id of user requesting contact
     * @param receiverId the id of user receiving request
     */
    public void addContactRequest(int senderId, int receiverId) {
        attendeeManager.addContactRequest(senderId, receiverId);
    }

    /**
     * Add an Attendee to contacts
     *
     * @param receiverId the id of user receiving request
     * @param senderId   the id of user requesting contact
     */
    public void removeContactRequest(int receiverId, int senderId) {
        attendeeManager.removeContactRequest(receiverId, senderId);
    }

    /**
     * Cast the user's contacts into a list of Attendee objects
     *
     * @param attendeeId the id of user getting contacts
     * @return the user's contacts
     */
    public HashMap<Integer, String> getContacts(int attendeeId) {
        return attendeeManager.getContacts(attendeeId);
    }

    /**
     * Cast the user's contacts into a list of Attendee objects
     *
     * @param attendeeId the id of user getting contact requests
     * @return the user's contacts
     */
    public HashMap<Integer, String> getContactRequests(int attendeeId) {
        return attendeeManager.getContactRequests(attendeeId);
    }

    /**
     * Get list of attendee who can be added in this user's contact
     *
     * @param attendeeId the id of user trying to add attendees
     * @return list of attendee.
     */
    public HashMap<Integer, String> getAddableContacts(int attendeeId) {
        return attendeeManager.getAddableContacts(attendeeId);
    }

    /**
     * Cast the user's contacts into a list of messageable Attendee objects
     *
     * @param attendeeId the id of user getting contacts
     * @return the user's contacts
     */
    public HashMap<Integer, String> getMessageableContacts(int attendeeId) {
        return attendeeManager.getMessageableContacts(attendeeId);
    }
}

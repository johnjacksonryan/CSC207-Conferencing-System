package gateway.facade.system;

import gateway.UserUpdater;
import users.base.UserManager;

/**
 * Interface for methods of UserUpdater.
 */
public interface UserSystem extends System {
    /**
     * @return the gateway the updates users
     */
    UserUpdater getUserUpdater();

    /**
     * Adds a user to the database.
     *
     * @param id the id of the user
     * @param type the type of the user
     * @param name the name of the user
     * @param vip whether or not the user is a vip (only applicable for Attendees)
     */
    default void addUser(int id, UserManager.UserType type, String name, boolean vip) {
        getUserUpdater().addUser(id, type, name, vip);
    }

    /**
     * Updating contact requests from on user to another.
     *
     * @param userID The user id doing the request
     * @param contactID The user id that is being requested
     */
    default void requestContact(int userID, int contactID) {
        getUserUpdater().requestContact(userID, contactID);
    }

    /**
     * Updating contact request when accepting contacts.
     *
     * @param userID The user id doing the request
     * @param contactID The user id that is being requested
     */
    default void acceptContact(int userID, int contactID) {
        getUserUpdater().acceptContact(userID, contactID);
    }
}
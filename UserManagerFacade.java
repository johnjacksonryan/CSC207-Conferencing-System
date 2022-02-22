package users.facade;

import messaging.MessageManager;
import scheduling.Event;
import scheduling.EventManager;
import users.base.User;
import users.base.UserManager;
import users.facade.manager_interface.EventInterface;
import users.facade.manager_interface.MessageInterface;

import java.util.ArrayList;
import java.util.HashMap;

public class UserManagerFacade implements MessageInterface, EventInterface {
    private UserManager userManager;
    private MessageManager messageManager;
    private EventManager eventManager;

    public UserManagerFacade(UserManager userManager, MessageManager messageManager, EventManager eventManager) {
        this.userManager = userManager;
        this.messageManager = messageManager;
        this.eventManager = eventManager;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    //UserManager methods
    /**
     * Get user with the inputted id.
     *
     * @param   id  the id of desired user
     * @return the user with the matching id if they exist; otherwise return null
     */
    public User getUser(int id) {
        return userManager.getUser(id);
    }

    /**
     * Get name of user with the inputted id.
     *
     * @param  id  the id of the desired user
     * @return the name of the user with the matching id
     */
    public String getUserName(int id) {
        return userManager.getUserName(id);
    }

    /**
     * Get type of user by its id.
     *
     * @param  id  the id of user
     * @return user type if user exists, otherwise null
     */
    public UserManager.UserType getUserType(int id) {
        return userManager.getUserType(id);
    }

    /**
     * @return list of users
     */
    public ArrayList<User> getUsers() {
        return userManager.getUsers();
    }

    /**
     * Setter for users.
     *
     * @param  userManager  the user manager passing users.
     */
    public void receiveUsers(UserManager userManager) {
        userManager.receiveUsers(userManager);
    }

    /**
     * Get list of users corresponding to list of ids.
     *
     * @param  ids  list of user ids
     * @return list of users
     */
    public HashMap<Integer, String> getListOfUsers(ArrayList<Integer> ids) {
        return userManager.getListOfUsers(ids);
    }

    /**
     * @return list of users who are Attendees
     */
    public HashMap<Integer, String> getAttendees() {
        return userManager.getAttendees();
    }

    /**
     * @return list of users who are Speakers
     */
    public HashMap<Integer, String> getSpeakers() {
        return userManager.getSpeakers();
    }

    /**
     * @return list of users who are Organizers
     */
    public HashMap<Integer, String> getOrganizers() {
        return userManager.getOrganizers();
    }

    /**
     * Register a new user to Conference System.
     *
     * @param  type      the type of user that is being registered
     * @param  name      the name of user
     */
    public int addUser(UserManager.UserType type, String name, boolean vip) {
        return userManager.addUser(type, name, vip);
    }

    public boolean getUserVip(int id) {
        return userManager.getUserVip(id);
    }
    public ArrayList<Integer> getUnreadMessages(){
        return getMessageManager().getUnreadMessages();
    }
}

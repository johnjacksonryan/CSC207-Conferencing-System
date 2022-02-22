package users.base;

import display.AttendeePresenter;
import display.UserPresenter;
import gateway.facade.UserUpdaterFacade;
import users.facade.UserManagerFacade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The controller for UserManager use case.
 *
 * @author Justin
 * @author John
 * @author Janardan
 * @author Yukang
 * @author Linjia
 * @version 2.5
 */
public abstract class UserController extends Controller {

    private UserManagerFacade manager;
    private final UserPresenter userPresenter;
    private final UserUpdaterFacade updater;
    private final int currentUserId;

    /**
     * Default constructor
     *
     * @param manager use case manager responsible for executing actions
     */
    public UserController(UserManagerFacade manager, UserUpdaterFacade updater, int id) {
        this.manager = manager;
        this.updater = updater;
        this.userPresenter = new AttendeePresenter();
        this.currentUserId = id;
    }

    /**
     * View all scheduled events
     */
    public void viewEvents() {
        userPresenter.printMap(manager.getEvents(-1));
    }

    /**
     * @param id the id of the user in question
     * @return the type of the user corresponding to the id
     */
    public UserManager.UserType getUserType(int id) {
        return manager.getUserType(id);
    }

    /**
     * Archives a message from the current user and the other user
     */
    public void archiveMessage() {
        ArrayList<Integer> messaged = manager.getUnArchivedMessages();
        if(manager.getAllMessagedUsers().isEmpty()){
            userPresenter.noConversation();
            return;
        }else if (messaged.isEmpty()) {
            userPresenter.noUnArchivedMessage();
            return;
        }

        HashMap<Integer, String> users = manager.getListOfUsers(messaged);
        int id = requestFromMap(users, false);

        manager.archiveMessages(id);
        updater.archiveMessages(getCurrentUserId(), id);

        userPresenter.succeedArchiveMessage();
    }
    public void unarchiveMessage() {
        ArrayList<Integer> messaged = manager.getArchivedMessages();
        if(manager.getAllMessagedUsers().isEmpty()){
            userPresenter.noConversation();
            return;
        }else if (messaged.isEmpty()) {
            userPresenter.noArchivedMessage();
            return;
        }

        HashMap<Integer, String> users = manager.getListOfUsers(messaged);
        int id = requestFromMap(users, false);

        manager.unarchiveMessages(id);
        updater.unarchiveMessages(getCurrentUserId(), id);

        userPresenter.succeeUnarchiveMessage();
    }

    /**
     * Views Unread Messages from one user to another user.
     */
    public void unreadMessage() {
        ArrayList<Integer> messaged = manager.getAllReadMessages();
        if (messaged.isEmpty()) {
            userPresenter.noConversation();
            return;
        }
        HashMap<Integer, String> users = manager.getListOfUsers(messaged);
        int id = requestFromMap(users, false);
        manager.unreadMessages(id);
        updater.unreadMessages(getCurrentUserId(), id);

        userPresenter.succeedUnreadMessage();
    }

    public void getMessages() {
        ArrayList<Integer> userIds = manager.getAllMessagedUsers();
        if (userIds.isEmpty()) {
            userPresenter.noConversation();
            return;
        }
        HashMap<Integer, String> users = manager.getListOfUsers(userIds);
        int userId = requestFromMap(users, false);
        boolean archived = manager.alreadyArchived(userId);
        if (archived) {
            userPresenter.presentArchiveMessages(userId);
        }else{
            userPresenter.presentViewMessages(manager.getMessages(userId));
        }
        manager.readMessage(userId);
    }
    /**
     * First, display a list of users the current user has a conversation history with.
     * Then, prompt the user to select a specific user.
     * After selection, display their entire conversation history with that user.
     */

    public void getUnreadMessages() {
        ArrayList<Integer> unreadIds = manager.getUnreadMessages();
        if (unreadIds.isEmpty()) {
            userPresenter.noUnreadMesages();
            return;
        }
        HashMap<Integer, String> users = manager.getListOfUsers(unreadIds);
        int userId = requestFromMap(users, false);
        boolean archived = manager.alreadyArchived(userId);
        if (archived) {
            userPresenter.presentArchiveMessages(userId);
        }else{
            userPresenter.presentViewMessages(manager.getMessages(userId));
        }
        manager.readMessage(userId);
    }

    public void deleteMessage(){
        ArrayList<Integer> sentIds = manager.getSentUsers(getCurrentUsername());
        if (sentIds.isEmpty()) {
            userPresenter.noConversation();
            return;
        }
        HashMap<Integer, String> users = manager.getListOfUsers(sentIds);
        int userId = requestFromMap(users, false);
        boolean archived = manager.alreadyArchived(userId);
        if (archived) {
            userPresenter.presentArchiveMessages(userId);
        }else{
            List<String> sent = manager.getSentMessages(userId,getCurrentUsername());

            userPresenter.presentIndexedViewMessages(sent);
            int index = requestInt("Please select the index");
            manager.deleteMessage(userId, getCurrentUsername(),index);
            updater.deleteMessage(userId, getCurrentUserId(), index);
        }
    }


    /**
     * @return the user that is currently logged in
     */
    public int getCurrentUserId() {
        return this.currentUserId;
    }

    public String getCurrentUsername(){
        return this.manager.getUserName(this.currentUserId);
    }

    /**
     * Get list of users with desired user type
     *
     * @return list of users
     */
    public HashMap<Integer, String> requestUsers() {
        HashMap<Integer, String> users = new HashMap<>();
        int index = requestFromList(getUserTypes(), false);
        if (index == 0) {
            users = manager.getAttendees();
        }
        if (index == 1) {
            users = manager.getOrganizers();
        }
        if (index == 2) {
            users = manager.getSpeakers();
        }
        users.remove(currentUserId);
        return users;
    }

    /**
     * get user type selected by user
     *
     * @return user type
     */
    public UserManager.UserType requestUserType() {
        int index = requestFromList(getUserTypes(), false);
        if (index == 0) {
            return UserManager.UserType.ATTENDEE;
        }
        if (index == 1) {
            return UserManager.UserType.ORGANIZER;
        } else {
            return UserManager.UserType.SPEAKER;
        }
    }

    public void messageUser(HashMap<Integer, String> users) {
        int senderId = currentUserId;
        int receiverId = requestFromMap(users, true);

        if (receiverId == -1) {
            return;
        }

        String message = requestString("message");
        String time = manager.messageUser(manager.getUserName(senderId), receiverId, message);
        updater.messageUser(senderId, receiverId, message, time);
        userPresenter.succeedSendMessage();
    }

    public void messageAttendees(ArrayList<Integer> attendeeIds) {
        if (attendeeIds.isEmpty()) {
            userPresenter.noAttendeeAvailable();
            return;
        }

        int senderId = currentUserId;
        String message = requestString("message");

        for (int receiverId : attendeeIds) {
            String time = manager.messageUser(manager.getUserName(senderId), receiverId, message);
            updater.messageUser(senderId, receiverId, message, time);
        }

        userPresenter.succeedSendMessage();
    }

    public abstract void load();

    public abstract void displayMainMenu();
}
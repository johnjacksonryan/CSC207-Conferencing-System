package users.organizer;

import display.OrganizerPresenter;
import gateway.facade.OrganizerUpdaterFacade;
import security.Credentials;
import security.PasswordSystem;
import users.base.UserController;
import users.base.UserManager;
import users.facade.OrganizerManagerFacade;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Controller for Organizer
 *
 * @author Yukang
 * @author John
 * @author Justin
 * @author Linjia
 * @author Janardan
 * @version 3.3
 */
public class OrganizerController extends UserController {
    private final OrganizerManagerFacade manager;
    private final OrganizerPresenter presenter;
    private final OrganizerUpdaterFacade updater;
    private final PasswordSystem passwordSystem;

    /**
     * Default constructor.
     *
     * @param  manager  facade that combines interfaces for the required managers
     * @param  updater  facade that combines interfaces for the required updaters
     * @param  id       id of the logged in user
     */
    public OrganizerController(OrganizerManagerFacade manager, OrganizerUpdaterFacade updater, int id) {
        super(manager, updater, id);
        this.manager = manager;
        this.presenter = new OrganizerPresenter();
        this.updater = updater;
        this.passwordSystem = new PasswordSystem(manager.getCredentialManager());
    }

    /**
     * Main menu for Organizer
     */
    public void displayMainMenu() {
        while (true) {
            int index = requestFromList(presenter.getMenu(), true);
            if (index == -1) {
                presenter.logOut();
                return;
            }
            if (index == 0) {
                createUser();
            }
            if (index == 1) {
                viewEvents();
            }
            if (index == 2) {
                scheduleEvent();
            }
            if (index == 3) {
                cancelEvent();
            }
            if (index == 4) {
                rescheduleEvent();
            }
            if (index == 5) {
                registerRoom();
            }
            if (index == 6) {
                messageMenu();
            }
            if (index == 7) {
                changeEventCapacity();
            }
        }
    }

    /**
     * Message Menu for Organizer
     */
    public void messageMenu() {
        while (true) {
            int index = requestFromList(presenter.getMessageMenu(), true);
            if (index == -1) {
                presenter.returnToMainMenu();
                return;
            } else if (index == 0) {
                getMessages();
            } else if (index == 1) {
                getUnreadMessages();
            } else if (index == 2) {
                messageUser();
            } else if (index == 3) {
                messageAllUsers();
            } else if (index == 4) {
                messageAllAttendeesInEvent();
            } else if (index == 5) {
                archiveMessage();
            } else if (index == 6) {
                unarchiveMessage();
            } else if (index == 7) {
                unreadMessage();
            }else if (index == 8){
                deleteMessage();
            }
        }
    }

    /**
     * Create a User account in the conference system
     */
    public void createUser() {
        UserManager.UserType type = requestUserType();
        String name = requestString("name");
        char[] password = requestString("password").toCharArray();
        boolean vip = false;
        if (type == UserManager.UserType.ATTENDEE) {
            vip = requestBoolean("vip");
        }

        createPassword(password);

        int id = manager.register(type, name, vip);

        updater.addUser(id, type, name, vip);
        presenter.successCreateUser(id);
    }

    /**
     * Helper function that creates the password salt for the user
     *
     * @param password the password of the user
     */
    private void createPassword(char[] password){
        Credentials credential = passwordSystem.hash(password);
        passwordSystem.addCredentials(credential);
        updater.addCredentials(credential.getPassword(), credential.getSalt());
    }

    /**
     * Add a new event to the master list of events
     */
    public void scheduleEvent() {
        HashMap<Integer, String> speakers = manager.getSpeakers();
        HashMap<Integer, String> roomsWithProjector = manager.getRoomManager().getRoomsWithProjector();
        HashMap<Integer, String> roomsWithTables = manager.getRoomManager().getRoomsWithTables();
        HashMap<Integer, String> roomsWithProjectorAndTables = manager.getRoomManager().getRoomsWithProjectorAndTables();

        if (speakers.isEmpty()) {
            presenter.noSpeakerFailure();
            return;
        }
        String name = requestString("name");
        int roomNum;
        int time = requestInt("time");
        int duration = requestInt("duration");
        int capacity = requestInt("capacity");

        HashMap<Integer, String> roomsWithCapacity = manager.getRoomManager().getRoomsWithCapacity(capacity);

        boolean vip = requestBoolean("vip");
        boolean needsProjector = requestBoolean("needs projector");
        boolean needsTables = requestBoolean("needs tables");
        if (needsProjector && needsTables) {
            if (roomsWithProjectorAndTables.isEmpty()) {
                presenter.noRoomFailure();
                return;
            }
            roomNum = requestFromMap(roomsWithProjectorAndTables, false);
        } else if (needsProjector) {
            if (roomsWithProjector.isEmpty()) {
                presenter.noRoomFailure();
                return;
            }
            roomNum = requestFromMap(roomsWithProjector, false);
        } else if (needsTables) {
            if (roomsWithTables.isEmpty()) {
                presenter.noRoomFailure();
                return;
            }
            roomNum = requestFromMap(roomsWithTables, false);
        } else{
            if(roomsWithCapacity.isEmpty()) {
                presenter.noRoomFailure();
                return;
            }
            roomNum = requestFromMap(roomsWithCapacity, false);
        }

        ArrayList<Integer> hosts = new ArrayList<>();
        boolean wantSpeakers = requestBoolean("speakers");
        while (wantSpeakers) {
            for (int speakerId: hosts) {
                speakers.remove(speakerId);
            }
            if (speakers.isEmpty()) {
                break;
            }
            int speakerId = requestFromMap(speakers, true);
            if (!hosts.isEmpty() && speakerId == -1) {
                wantSpeakers = false;
            } else if (speakerId != -1) {
                hosts.add(speakerId);
            } else {
                presenter.returnToMainMenu();
                return;
            }
        }
        int id = manager.addEvent(name, roomNum, time, duration, capacity, vip, hosts, needsProjector, needsTables);
        if (id != -1) {
            presenter.successScheduleEvent();
            updater.addEvent(id, name, roomNum, time, duration,capacity, vip, hosts, needsProjector, needsTables);
            return;
        }
        presenter.failedScheduleEvent();
    }

    /**
     * Cancel an existing event
     */
    public void cancelEvent() {
        HashMap<Integer, String> events = manager.getEvents(-1);

        if (events.isEmpty()) {
            presenter.noEventAvailable();
            return;
        }

        int eventId = requestFromMap(events, false);

        manager.removeEvent(eventId);
        updater.removeEvent(eventId);
        presenter.successCancelEvent();
    }

    /**
     * Reschedule an existing event
     */
    public void rescheduleEvent() {
        HashMap<Integer, String> events = manager.getEvents(-1);
        if (events.isEmpty()) {
            presenter.noEventAvailable();
            return;
        }
        int eventId = requestFromMap(events, false);
        int time = requestInt("time");
        if (manager.rescheduleEvent(eventId, time)) {
            presenter.successRescheduleEvent();
            updater.updateEvent(eventId, time);
            return;
        }
        presenter.failedRescheduleEvent();
    }

    /**
     * Register a new Room into the conference system
     */
    public void registerRoom() {
        int roomNum = requestInt("roomNum");
        int capacity = requestInt("capacity");
        boolean hasProjector = requestBoolean("has projector");
        boolean hasTables = requestBoolean("has tables");
        if (manager.getRoomManager().addRoom(roomNum, capacity, hasProjector, hasTables)) {
            presenter.successRegisterRoom();
            updater.addRoom(roomNum, capacity, hasProjector, hasTables);
            return;
        }
        presenter.failedRegisterRoom();
    }

    /**
     * Message a specific User
     */
    public void messageUser() {
        HashMap<Integer, String> receivers = requestUsers();
        if (receivers.isEmpty()) {
            presenter.noUserExistWithType();
            return;
        }
        messageUser(receivers);
    }

    /**
     * Message all registered Users with specific user type
     */
    public void messageAllUsers() {
        ArrayList<Integer> receiverIds = new ArrayList<>(requestUsers().keySet());

        if (receiverIds.isEmpty()) {
            return;
        }

        int senderId = getCurrentUserId();
        String message = requestString("message");

        for (int receiverId : receiverIds) {
            String time = manager.messageUser(manager.getUserName(senderId), receiverId, message);
            updater.messageUser(senderId, receiverId, message, time);
        }

        presenter.succeedSendMessage();
    }

    /**
     * Message all Attendees in specific event
     */
    public void messageAllAttendeesInEvent() {
        HashMap<Integer, String> events = manager.getEvents(-1);
        if (events.isEmpty()) {
            presenter.noEventAvailable();
            return;
        }
        int eventId = requestFromMap(events, false);
        ArrayList<Integer> receiverIds = manager.getAttendeesOfEvent(eventId);
        messageAttendees(receiverIds);
    }

    /**
     * Change the capacity of an event.
     */
    public void changeEventCapacity() {
        HashMap<Integer, String> events = manager.getEvents(-1);
        if (events.isEmpty()) {
            presenter.noEventAvailable();
            return;
        }
        int eventId = requestFromMap(events, false);
        int newCapacity = requestInt("capacity");
        if (manager.changeCapacity(eventId, newCapacity)) {
            updater.updateCapacity(eventId, newCapacity);
            presenter.successChangeCapacity();
            return;
        }
        presenter.failChangeCapacity();
    }

    /**
     * Load data from database.
     */
    public void load() {
        updater.load(getCurrentUserId());
    }
}
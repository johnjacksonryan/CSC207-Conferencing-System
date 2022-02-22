package users.attendee;

import display.AttendeePresenter;
import gateway.facade.AttendeeUpdaterFacade;
import users.base.UserController;
import users.facade.AttendeeManagerFacade;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The controller for AttendeeManager use case.
 *
 * @author Linjia
 * @author John
 * @author Justin
 * @author Dickson Li
 * @author Yukang
 * @author Janardan
 * @version 2.4
 */
public class AttendeeController extends UserController {

    private final AttendeeManagerFacade manager;
    private final AttendeePresenter presenter;
    private final AttendeeUpdaterFacade updater;

    /**
     * Default constructor.
     *
     * @param  manager  facade that combines interfaces for all the required managers
     * @param  updater  facade that combines interfaces fro all the required updaters
     * @param  id       id of the logged in user
     */
    public AttendeeController(AttendeeManagerFacade manager, AttendeeUpdaterFacade updater, int id) {
        super(manager, updater, id);
        this.manager = manager;
        this.presenter = new AttendeePresenter();
        this.updater = updater;
    }

    /**
     * Display the main menu and prompt attendee to select an action.
     */
    public void displayMainMenu() {
        while (true) {
            int index = requestFromList(presenter.getMenu(), true);

            if (index == -1) {
                presenter.logOut();
                return;
            }

            if (index == 0)
                viewSchedule();
            else if (index == 1)
                viewEvents();
            else if (index == 2)
                registerForEvent();
            else  if (index == 3)
                unregisterForEvent();
            else if (index == 4)
                contactMenu();
            else if (index == 5)
                messageMenu();
        }
    }

    /**
     * Display the contact menu and prompt attendee to select an action.
     */
    public void contactMenu() {
        while (true) {
            int index = requestFromList(presenter.getContactMenu(), true);

            if (index == -1) {
                presenter.returnToMainMenu();
                return;
            }

            if (index == 0)
                viewContacts();
            else if (index == 1)
                requestContact();
            else if (index == 2)
                acceptContactRequest();
        }
    }

    /**
     * Display the message menu and prompt attendee to select an action.
     */
    public void messageMenu() {
        while (true) {
            int index = requestFromList(presenter.getMessageMenu(), true);

            if (index == -1) {
                presenter.returnToMainMenu();
                return;
            }

            else if (index == 0)
                getMessages();
            else if (index == 1)
                getUnreadMessages();
            else if (index == 2)
                messageAttendee();
            else if (index == 3)
                messageSpeaker();
            else if(index == 4)
                archiveMessage();
            else if(index == 5)
                unarchiveMessage();
            else if(index == 6)
                unreadMessage();
            else if(index == 7)
                deleteMessage();

        }
    }

    /**
     * View the events the attendee is signed up for.
     */
    public void viewSchedule() {
        HashMap<Integer, String> schedule = manager.getEventsOfAttendee(getCurrentUserId());
        if (schedule.isEmpty()) {
            presenter.noEventAvailable();
        } else {
            presenter.printMap(schedule);
        }
    }

    /**
     * Sign the attendee up for an event.
     */
    public void registerForEvent() {
        HashMap<Integer, String> events = manager.getEvents(getCurrentUserId());
        if (events.isEmpty()) {
            presenter.noEventAvailable();
            return;
        }
        int eventId = requestFromMap(events, true);
        if(eventId == -1) {
            presenter.returnToMainMenu();
            return;
        }
        if (manager.addAttendee(getCurrentUserId(), eventId)) {
            updater.updateAttendee(getCurrentUserId(), eventId, false);
            presenter.succeedRegister();
        } else {
            presenter.failedRegister();
        }
    }

    /**
     * Cancel the attendee's enrolment in an event.
     */
    public void unregisterForEvent() {
        HashMap<Integer, String> events = manager.getEventsOfAttendee(getCurrentUserId());
        if (events.isEmpty()) {
            presenter.noRegisteredEventAvailable();
            return;
        }
        int eventId = requestFromMap(events, true);
        if(eventId == -1) {
            presenter.returnToMainMenu();
            return;
        }
        manager.removeAttendee(getCurrentUserId(), eventId);
        updater.updateAttendee(getCurrentUserId(), eventId, true);
        presenter.succeedUnregister();
    }

    /**
     * View all the attendees the attendee has added to contacts.
     */
    public void viewContacts() {
        HashMap<Integer, String> contacts = manager.getContacts(getCurrentUserId());
        if (contacts.isEmpty()) {
            presenter.noContact();
        } else {
            presenter.printMap(contacts);
        }
    }

    /**
     * Add an attendee to contacts.
     */
    public void requestContact() {
        HashMap<Integer, String> addableContacts = manager.getAddableContacts(getCurrentUserId());
        if (addableContacts.isEmpty()) {
            presenter.noAttendeeAvailable();
            return;
        }
        int attendeeId = requestFromMap(addableContacts, true);
        if (attendeeId == -1) {
            return;
        }
        manager.removeContactRequest(getCurrentUserId(), attendeeId);
        manager.addContactRequest(getCurrentUserId(), attendeeId);
        updater.requestContact(getCurrentUserId(), attendeeId);
        presenter.succeedAddContact();
    }

    /**
     * Accept a pending contact request.
     */
    public void acceptContactRequest() {
        HashMap<Integer, String> pendingRequests = manager.getContactRequests(getCurrentUserId());
        if (pendingRequests.isEmpty()) {
            presenter.noRequest();
            return;
        }
        int attendeeId = requestFromMap(pendingRequests, true);
        if (attendeeId == -1) {
            return;
        }
        manager.removeContactRequest(getCurrentUserId(), attendeeId);
        updater.acceptContact(getCurrentUserId(), attendeeId);
        presenter.succeedAcceptContact();
    }

    /**
     * Message a specific Attendee
     */
    public void messageAttendee() {
        HashMap<Integer, String> attendees = manager.getMessageableContacts(getCurrentUserId());
        if (attendees.isEmpty()) {
            presenter.noAttendeeAvailable();
            return;
        }
        messageUser(attendees);
    }

    /**
     * Message a specific Speaker
     */
    public void messageSpeaker() {
        ArrayList<Integer> speakerIds = manager.getSpeakersForAttendee(getCurrentUserId());
        if (speakerIds.isEmpty()) {
            presenter.noSpeakerAvailable();
            return;
        }
        HashMap<Integer, String> speakers = manager.getListOfUsers(speakerIds);
        messageUser(speakers);
    }

    /**
     * Load data from the database.
     */
    public void load() {
        updater.load(getCurrentUserId());
    }
}
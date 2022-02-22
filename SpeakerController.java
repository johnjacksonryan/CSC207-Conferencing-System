package users.speaker;

import display.SpeakerPresenter;
import gateway.facade.SpeakerUpdaterFacade;
import gateway.facade.UserUpdaterFacade;
import users.base.UserController;
import users.facade.SpeakerManagerFacade;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The controller for AttendeeManager use case.
 *
 * @author John
 * @author Janardan
 * @author Yukang
 * @version 2.2
 */
public class SpeakerController extends UserController {
    private final SpeakerManagerFacade manager;
    private final SpeakerUpdaterFacade updater;
    private final SpeakerPresenter speakerPresenter;

    /**
     * Default constructor.
     *
     * @param  manager  facade that combines interfaces for the required managers
     * @param  updater  facade that combines interfaces for the required updaters
     * @param  id       id of the logged in user
     */
    public SpeakerController(SpeakerManagerFacade manager, SpeakerUpdaterFacade updater, int id) {
        super(manager, updater, id);
        this.manager = manager;
        this.updater = updater;
        this.speakerPresenter = new SpeakerPresenter();
    }

    /**
     * Display the main menu and prompt attendee to select an action.
     */

    public void displayMainMenu(){
        while (true) {
            int index = requestFromList(speakerPresenter.getMenu(), true);
            if (index == -1) {
                speakerPresenter.logOut();
                return;
            } else if (index == 0) {
                viewTalks();
            } else if (index == 1) {
                messageMenu();
            }
        }
    }

    /**
     * Display the message menu and prompt attendee to select an action.
     */

    public void messageMenu() {
        while (true) {
            int index = requestFromList(speakerPresenter.getMessageMenu(), true);
            if (index == -1) {
                speakerPresenter.returnToMainMenu();
                return;
            } else if (index == 0) {
                getMessages();
            } else if (index == 1) {
                messageAttendee();
            } else if (index == 2) {
                messageAttendeesAtEvent();
            } else if (index == 3) {
                messageAllEvents();
            } else if (index == 4) {
                archiveMessage();
            } else if (index == 5) {
                unarchiveMessage();
            } else if (index == 6) {
                deleteMessage();
            }
        }
    }

    /**
     * Display the event menu and prompt attendee to select an action.
     */

    public void messageAttendeesAtEvent() {
        HashMap<Integer, String> events = manager.getEventsOfHost(getCurrentUserId());
        if (events.isEmpty()) {
            speakerPresenter.noEventAvailable();
            return;
        }
        int eventId = requestFromMap(events, true);
        if (eventId == -1) {
            return;
        }
        ArrayList<Integer> receiverIds = manager.getAttendeesOfEvent(eventId);
        messageAttendees(receiverIds);
    }

    /**
     * Message a specific Attendee.
     */
    public void messageAttendee() {
        HashMap<Integer, String> attendees = manager.getAttendees();
        ArrayList<Integer> attendee = manager.getAttendeesForSpeaker(getCurrentUserId());
        ArrayList<Integer> ids = new ArrayList<>(attendees.keySet());
        for(int id: ids){
            if(!attendee.contains(id)){
                attendees.remove(id);
            }
        }
        if (attendees.isEmpty()) {
            speakerPresenter.noAttendeeAvailable();
            return;
        }
        messageUser(attendees);
    }

    /**
     * Message Attendees at all the events the Speaker is attending.
     */
    public void messageAllEvents() {
        ArrayList<Integer> receiverIds = manager.getAttendeesForSpeaker(getCurrentUserId());
        if (receiverIds.isEmpty()) {
            speakerPresenter.noAttendeeAvailable();
            return;
        }
        messageAttendees(receiverIds);
    }

    /**
     * View the talks the Speaker is giving.
     */
    public void viewTalks() {
        speakerPresenter.printTalk(manager.getEventsOfHost(getCurrentUserId()));
    }

    /**
     * Load data from the database.
     */
    public void load() {
        updater.load(getCurrentUserId());
    }
}
package factory;

import gateway.*;
import gateway.facade.*;
import messaging.MessageManager;
import scheduling.EventManager;
import scheduling.RoomManager;
import security.CredentialManager;
import users.attendee.AttendeeController;
import users.attendee.AttendeeManager;
import users.base.Controller;
import users.base.UserManager;
import users.facade.AttendeeManagerFacade;
import users.facade.OrganizerManagerFacade;
import users.facade.SpeakerManagerFacade;
import users.guest.GuestController;
import users.guest.GuestManager;
import users.organizer.OrganizerController;
import users.organizer.OrganizerManager;
import users.speaker.SpeakerController;
import users.speaker.SpeakerManager;

/**
 * Factory that creates the appropriate Controller.
 *
 * @author Justin
 * @version 1.0
 */
public class ControllerFactory {

    private final CredentialManager credentialManager;
    private final CredentialUpdater credentialUpdater;
    private final UserManager userManager;
    private final UserUpdater userUpdater;
    private final MessageManager messageManager;
    private final MessageUpdater messageUpdater;
    private final RoomUpdater roomUpdater;
    private final EventManager eventManager;
    private final EventUpdater eventUpdater;


    /**
     * Default constructor.
     *
     * @param  database  the database that the updaters modify
     */
    public ControllerFactory(Database database) {
        userManager = new UserManager();
        userUpdater = new UserUpdater(userManager, database);

        messageManager = new MessageManager();
        messageUpdater = new MessageUpdater(messageManager, userManager, database);

        RoomManager roomManager = new RoomManager();
        roomUpdater = new RoomUpdater(roomManager, database);

        eventManager = new EventManager(roomManager, userManager);
        eventUpdater = new EventUpdater(eventManager, database);

        credentialManager = new CredentialManager();
        credentialUpdater = new CredentialUpdater(database, credentialManager);
    }

    /**
     * @return the controller for clients who are not logged in
     */
    public GuestController getGuestController() {
        GuestManager manager = new GuestManager(userManager, credentialManager);
        GuestUpdaterFacade updater = new GuestUpdaterFacade(userUpdater, credentialUpdater);
        return new GuestController(manager, updater);
    }

    /**
     * Get the controller for users who have logged in.
     *
     * @param  id  id of the user that logged in
     * @return the controller for the user type matching user with given id
     */
    public Controller getController(int id) {

        switch (userManager.getUserType(id)) {
            case ATTENDEE:
                return attendeeController(id);
            case ORGANIZER:
                return organizerController(id);
            default:
                return speakerController(id);
        }
    }

    private AttendeeController attendeeController(int id) {
        AttendeeManager attendeeManager = new AttendeeManager(userManager);
        AttendeeManagerFacade attendeeManagerFacade = new AttendeeManagerFacade(messageManager, eventManager, attendeeManager);
        AttendeeUpdaterFacade updater = new AttendeeUpdaterFacade(userUpdater, messageUpdater, roomUpdater, eventUpdater);
        return new AttendeeController(attendeeManagerFacade, updater, id);
    }

    private OrganizerController organizerController(int id) {
        OrganizerManager organizerManager = new OrganizerManager(userManager);
        OrganizerManagerFacade manager = new OrganizerManagerFacade(organizerManager, messageManager, eventManager, credentialManager);
        OrganizerUpdaterFacade updater = new OrganizerUpdaterFacade(userUpdater, messageUpdater, eventUpdater,
                roomUpdater, credentialUpdater);
        return new OrganizerController(manager, updater, id);
    }

    private SpeakerController speakerController(int id) {
        SpeakerManager speakerManager = new SpeakerManager(userManager);
        SpeakerManagerFacade manager = new SpeakerManagerFacade(messageManager, eventManager, speakerManager);
        SpeakerUpdaterFacade updater = new SpeakerUpdaterFacade(userUpdater, messageUpdater, roomUpdater, eventUpdater);
        return new SpeakerController(manager, updater, id);
    }
}
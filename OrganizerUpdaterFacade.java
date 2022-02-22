package gateway.facade;

import gateway.*;
import gateway.facade.system.CredentialSystem;
import gateway.facade.system.RoomSystem;

/**
 * Facade that combines interfaces for UserSystem, MessageSystem, EventSystem, RoomSystem, CredentialSystem.
 *
 * @author Justin
 * @version 2.0
 */
public class OrganizerUpdaterFacade extends AttendeeUpdaterFacade implements RoomSystem, CredentialSystem {

    private final RoomUpdater roomUpdater;
    private final CredentialUpdater credentialUpdater;

    /**
     * Default constructor.
     *
     * @param  userUpdater        gateway that updates users
     * @param  messageUpdater     gateway that updates messages
     * @param  eventUpdater       gateway that updates events
     * @param  roomUpdater        gateway that updates rooms
     * @param  credentialUpdater  gateway that updates credentials
     */
    public OrganizerUpdaterFacade(UserUpdater userUpdater, MessageUpdater messageUpdater, EventUpdater eventUpdater,
                                  RoomUpdater roomUpdater, CredentialUpdater credentialUpdater) {
        super(userUpdater, messageUpdater, roomUpdater, eventUpdater);
        this.roomUpdater = roomUpdater;
        this.credentialUpdater = credentialUpdater;
    }


    public void updateCapacity(int eventId, int capacity) {
        this.getEventUpdater().changeCapacity(eventId, capacity);

    }

    /**
     * @return the gateway that updates rooms
     */


    @Override
    public RoomUpdater getRoomUpdater() {
        return roomUpdater;
    }

    /**
     * @return the gateway that updates credentials
     */
    @Override
    public CredentialUpdater getCredentialUpdater() {
        return credentialUpdater;
    }

    public void load(int id) {
        super.load(id);
        credentialUpdater.loadCredentials();
    }
}
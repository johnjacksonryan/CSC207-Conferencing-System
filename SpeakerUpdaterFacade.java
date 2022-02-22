package gateway.facade;

import gateway.EventUpdater;
import gateway.MessageUpdater;
import gateway.RoomUpdater;
import gateway.UserUpdater;

/**
 * Facade that combines interfaces for UserSystem, MessageSystem.
 *
 * @author Janardan
 * @version 2.0
 */

public class SpeakerUpdaterFacade extends UserUpdaterFacade{
    private final RoomUpdater roomUpdater;
    private final EventUpdater eventUpdater;

    /**
     * Default constructor.
     *
     * @param  userUpdater     gateway that updates users
     * @param  messageUpdater  gateway that updates messages
     * @param  eventUpdater    gateway that updates events
     */
    public SpeakerUpdaterFacade(UserUpdater userUpdater, MessageUpdater messageUpdater,
                                 RoomUpdater roomUpdater, EventUpdater eventUpdater) {
        super(userUpdater, messageUpdater);
        this.roomUpdater = roomUpdater;
        this.eventUpdater = eventUpdater;
    }

    public void load(int id) {
        super.load(id);
        roomUpdater.loadRooms();
        eventUpdater.loadEvents();
    }
}

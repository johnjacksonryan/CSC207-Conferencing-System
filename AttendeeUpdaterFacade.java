package gateway.facade;

import gateway.EventUpdater;
import gateway.MessageUpdater;
import gateway.RoomUpdater;
import gateway.UserUpdater;
import gateway.facade.system.EventSystem;

/**
 * Facade that combines interfaces for UserSystem, MessageSystem, EventSystem.
 *
 * @author Justin
 * @version 2.0
 */

public class AttendeeUpdaterFacade extends UserUpdaterFacade implements EventSystem {

    private final RoomUpdater roomUpdater;
    private final EventUpdater eventUpdater;

    /**
     * Default constructor.
     *
     * @param  userUpdater     gateway that updates users
     * @param  messageUpdater  gateway that updates messages
     * @param  eventUpdater    gateway that updates events
     */
    public AttendeeUpdaterFacade(UserUpdater userUpdater, MessageUpdater messageUpdater,
                                 RoomUpdater roomUpdater, EventUpdater eventUpdater) {
        super(userUpdater, messageUpdater);
        this.roomUpdater = roomUpdater;
        this.eventUpdater = eventUpdater;
    }

    /**
     * @return the gateway that updates events
     */
    @Override
    public EventUpdater getEventUpdater() {
        return eventUpdater;
    }

    public void load(int id) {
        super.load(id);
        roomUpdater.loadRooms();
        eventUpdater.loadEvents();
    }
}
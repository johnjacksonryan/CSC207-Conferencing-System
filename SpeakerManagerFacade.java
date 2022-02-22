package users.facade;

import messaging.MessageManager;
import scheduling.EventManager;
import users.speaker.SpeakerManager;

public class SpeakerManagerFacade extends UserManagerFacade {

    public SpeakerManagerFacade(MessageManager messageManager, EventManager eventManager, SpeakerManager userManager) {
        super(userManager, messageManager, eventManager);
    }
}

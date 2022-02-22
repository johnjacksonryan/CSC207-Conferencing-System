package users.speaker;

import users.base.UserManager;

/**
 * The use case managing the Speaker entities
 *
 * @author Linjia
 * @author Justin
 * @author Yukang
 * @version 2.2
 */
public class SpeakerManager extends UserManager {

    public SpeakerManager(UserManager userManager) {
        receiveUsers(userManager);
    }
}
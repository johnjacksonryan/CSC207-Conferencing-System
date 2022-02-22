package users.speaker;

import users.base.User;

/**
 * A participant at the conference who hosts at least one talk
 *
 * @author Justin
 * @author Carl
 * @author Yukang
 * @version 1.1
 */
public class Speaker extends User {

    /**
     * Default constructor
     *
     * @param  id    the speaker's unique identification number
     * @param  name  the speaker's name
     */
    public Speaker(int id, String name) {
        super(id, name);
    }
}
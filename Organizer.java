package users.organizer;

import users.base.User;

/**
 * Someone who oversees and manages the conference.
 *
 * @author Justin
 * @author Carl
 * @author Yukang
 * @version 1.1
 */
public class Organizer extends User {

    /**
     * Default constructor
     *
     * @param  id       the organizer's unique identification number
     * @param  name     the organizer's name
     */
    public Organizer(int id, String name) {
        super(id, name);
    }
}
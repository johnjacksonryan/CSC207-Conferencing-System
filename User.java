package users.base;

/**
 * A class representing a user who interacts with the Conference System.
 *
 * @author Yukang
 * @author Justin
 * @version 1.5
 */
public abstract class User {

    private final int id;
    private String name;

    /**
     * Default constructor
     *
     * @param  id    the user's unique identification number
     * @param  name  the user's name
    */
    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * @return the user's unique identification number
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return the user's name
     */
    public String getName() {
        return this.name;
    }
    /**
     * @param name the desired name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return String representation of this user
     */
    @Override
    public String toString() {
        return this.name + " " + this.getId();
    }
    /**
     * @param  obj the object being compared to this user
     * @return true iff all the instance variables of this user
     *         match all the instance variables of obj and obj
     *         is of type User
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            User _obj = (User)obj;
            return id == _obj.id && name.equals(_obj.name);
        }

        return false;
    }
}
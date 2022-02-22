package security;

/**
 * Information needed to authenticate user logins.
 *
 * @author Justin
 * @author Linjia
 * @version 1.0
 */
public class Credentials {

    private final byte[] password;
    private final byte[] salt;

    /**
     * Default constructor.
     *
     * @param password  the (hashed) password
     * @param salt      the salt used in the password hash
     */
    public Credentials(byte[] password, byte[] salt) {
        this.password = password;
        this.salt = salt;
    }

    /**
     * @return  the hashed password
     */
    public byte[] getPassword() {
        return this.password;
    }

    /**
     * @return  the salt used in the password hash
     */
    public byte[] getSalt() {
        return this.salt;
    }
}
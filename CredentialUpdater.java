package gateway;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.Binary;
import security.CredentialManager;

/**
 * Loads or saves passwords and salts (Credentials) to the database
 *
 * @author Janardan
 * @version 1.0
 */

public class CredentialUpdater {

    private final MongoCollection<Document> credentials;
    private final CredentialManager credentialManager;

    /**
     * Constructor for the Credential Updater
     *
     * @param database the database with the collection of credentials
     * @param credentialManager the use case manager to create credentials
     */
    public CredentialUpdater(Database database, CredentialManager credentialManager){
        this.credentialManager = credentialManager;
        this.credentials = database.getDatabase().getCollection("credentials");
    }

    /**
     * Loads all passwords and salts from the database
     */
    public void loadCredentials(){
        for(Document document: this.credentials.find(new Document())){
            byte[] password = document.get("password", Binary.class).getData();
            byte[] salt = document.get("salt", Binary.class).getData();
            credentialManager.addCredentials(password, salt);
        }
    }

    /**
     * Saves a new users credentials (password and salt)
     *
     * @param password A list of bytes representing the password
     * @param salt A list of bytes representing the salt
     */
    public void addCredentials(byte[] password, byte[] salt){
        Document document = new Document();
        document.put("password", password);
        document.put("salt", salt);
        this.credentials.insertOne(document);
    }

}

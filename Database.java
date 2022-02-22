package gateway;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Database storing the collections.
 *
 * @author Janardan
 * @author Dickson
 * @version 1.0
 */
public class Database {

    private final MongoDatabase database;

    /**
     * Creates and connects the mongodb
     */
    public Database() {
        System.out.println("Connecting to the database...");
        //hide console output when connecting to the database
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb+srv://admin:123@conferencesystem.jtrx5.mongodb.net/database?retryWrites=true&w=majority"));
        this.database = mongoClient.getDatabase("database");
        System.out.println("Database connected!");
    }

    /**
     * Getter for the database
     * @return the database
     */
    public MongoDatabase getDatabase(){
        return this.database;
    }

}

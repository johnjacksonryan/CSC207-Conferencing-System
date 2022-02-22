package messaging;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A class representing a message
 *
 * @author Janardan
 * @author Dickson Li
 */

public class Message {
    private final LocalDateTime time;
    private final String sender;
    private final String message;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");

    /**
     * Default constructor
     *
     * @param time    the time of the message is sent
     * @param sender  the user's name who sent the message
     * @param message the message as a string
     */
    public Message(LocalDateTime time, String sender, String message){
        this.time = time;
        this.sender = sender;
        this.message = message;
    }
    /**
     * Another constructor
     *
     * @param time    the time of the message is sent as a string
     * @param sender  the user's name who sent the message
     * @param message the message as a string
     */
    public Message(String time, String sender, String message){
        this.time = LocalDateTime.parse(time, dateTimeFormatter);
        this.sender = sender;
        this.message = message;
    }

    /**
     * @return the sender's name of the message
     */
    public String getSender() {
        return sender;
    }
    /**
     * @return the formatted time
     */
    public String getTime() {
        return time.format(dateTimeFormatter);
    }
    /**
     * @return the message of the message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * @return the message in the format of "time| name: message"
     */
    @Override
    public String toString() {
        return getTime() + "| " + sender + ": " + message;
    }
    /***
     * Two methods are considered same if they have the same time, sender, and message
     * @param obj the object this message is comparing to
     * @return if two objects are same
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Message) {
            Message other = (Message) obj;
            return time.equals(other.time) && sender.equals(other.sender) && message.equals(other.message);
        } else {
            return false;
        }
    }
}

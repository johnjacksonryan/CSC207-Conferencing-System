package messaging;

import gateway.Database;
import gateway.MessageUpdater;
import users.base.UserManager;

import java.time.LocalDateTime;
import java.util.*;

/**
 * A class that manages all messages for every pair of users
 *
 * @author Janardan
 * @author Dickson Li
 * @author Yukang
 * @version 2.1
 */
public class MessageManager {


    private final Map<Integer, List<Message>> messages = new HashMap<>();
    private final ArrayList<Integer> unreadMessages = new ArrayList<>();
    private final ArrayList<Integer> archivedMessages = new ArrayList<>();


    public String messageUser(String senderName, int receiverId, String messageText, Object given) {
        Message message;
        if (given.getClass() == LocalDateTime.class) {
            message = createMessage(senderName, messageText, (LocalDateTime) given);
        }
        else{
            message = createMessage(senderName, messageText, (String) given);
        }
        addMessage(receiverId, message);
        return message.getTime();

    }


    public List<String> getMessages(int id) {
        List<Message> messages = this.messages.get(id);
        if (messages.size() == 0) {
            return null;
        }
        ArrayList<String> stringMessages = new ArrayList<>();
        for (Message message : messages) {
            stringMessages.add(message.toString());
        }
        return stringMessages;
    }
    public List<String> getSentMessageStrings(int id, String sender){
        List<Message> messages = getSentMessage(id, sender);
        if (messages.size() == 0) {
            return null;
        }
        ArrayList<String> stringMessages = new ArrayList<>();
        for (Message message : messages) {
            stringMessages.add(message.toString());
        }
        return stringMessages;
    }

    public ArrayList<Integer> getAllMessagedUsers() {
        ArrayList<Integer> result = new ArrayList<>();

        for (int id : messages.keySet()) {
            if(!messages.get(id).isEmpty()) {
                result.add(id);
            }
        }
        return result;
    }
    public ArrayList<Integer> getUnArchivedMessages() {
        ArrayList<Integer> unarchived = new ArrayList<>();
        ArrayList<Integer> allMessages = this.getAllMessagedUsers();
        for (int userId : allMessages) {
            if (!alreadyArchived(userId)) {
                unarchived.add(userId);
            }
        }
        return unarchived;
    }
    public ArrayList<Integer> getArchivedMessages() {
        return this.archivedMessages;
    }
    public ArrayList<Integer> getReadMessages() {
        ArrayList<Integer> read = new ArrayList<>();
        ArrayList<Integer> allMessages = this.getAllMessagedUsers();
        for (int userId : allMessages) {
            if (!alreadyUnread(userId)) {
                read.add(userId);
            }
        }
        return read;
    }
    public ArrayList<Integer> getUnreadMessages() {
        return this.unreadMessages;
    }
    public ArrayList<Integer> getAllSentUsers(String sender){
        ArrayList<Integer> result = new ArrayList<>();
        for (int id : messages.keySet()) {
            if (!getSentMessage(id, sender).isEmpty()) {
                result.add(id);
            }
        }
        return result;
    }
    public ArrayList<Message> getSentMessage(int id, String sender) {
        List<Message> messages = this.messages.get(id);
        ArrayList<Message> sentMessages = new ArrayList<>();
        for (Message message : messages) {
            if (message.getSender() == sender) {
                sentMessages.add(message);
            }
        }
        return sentMessages;
    }


    public void unreadMessages(int id) {
        this.unreadMessages.add(id);
    }
    public void readMessage(int id) {
        this.unreadMessages.remove(Integer.valueOf(id));
    }
    public void archiveMessages(int id) {
        if (alreadyArchived(id)) {
            return;
        }
        this.archivedMessages.add(id);
    }
    public void unarchiveMessage(int id){
        if (!alreadyArchived(id)) {
            return;
        }
        this.archivedMessages.remove(id);
    }
    public void deleteMessage(int id, String sender, int index){
        Message deletedMessage = getSentMessage(id,sender).get(index);
        List<Message> messages = this.messages.get(id);
        for (Message message : messages){
            if (message == deletedMessage){
                messages.remove(message);
                break;
            }
        }
    }

    public boolean alreadyArchived(int id) {
        for (int i : this.archivedMessages) {
            if (i == id) {
                return true;
            }
        }
        return false;
    }
    public boolean alreadyUnread(int id) {
        for (int i : this.unreadMessages) {
            if (i == id) {
                return true;
            }
        }
        return false;
    }


    private Message createMessage(String senderName, String message, String given) {
        return new Message(given, senderName, message);
    }
    private Message createMessage(String senderName, String message, LocalDateTime given) {
        return new Message(given, senderName, message);
    }
    private void addMessage(int id, Message message) {
        if (!messages.keySet().contains(id)){
            messages.put(id, new ArrayList<Message>());
        }
        messages.get(id).add(message);
    }


}

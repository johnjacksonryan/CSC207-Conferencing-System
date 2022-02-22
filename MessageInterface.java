package users.facade.manager_interface;

import messaging.Message;
import messaging.MessageManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface MessageInterface {
    MessageManager getMessageManager();

    /**
     * Gets the messages between two users
     *
     * @return a list of messages between the two users, or null if no messages exist for the two users
     */
    default List<String> getMessages(int id) {
        return getMessageManager().getMessages(id);
    }
    default List<String> getSentMessages(int id, String sender){
        return getMessageManager().getSentMessageStrings(id, sender);
    }
    default ArrayList<Integer> getAllMessagedUsers() {
        return getMessageManager().getAllMessagedUsers();
    }
    default ArrayList<Integer> getUnArchivedMessages() {
        return getMessageManager().getUnArchivedMessages();
    }
    default ArrayList<Integer> getArchivedMessages() {
        return getMessageManager().getArchivedMessages();
    }
    default ArrayList<Integer> getAllReadMessages() {
        return getMessageManager().getReadMessages();
    }
    default ArrayList<Integer> getAllUnreadMessages() {
        return getMessageManager().getUnreadMessages();
    }
    default ArrayList<Integer> getSentUsers(String sender){
        return getMessageManager().getAllSentUsers(sender);
    }


    /**
     * Message a specific User (new message)
     *
     * @param messageText the message to be sent
     * @return the time the message was sent
     */
    default String messageUser(String senderName, int receiverId, String messageText) {
        return getMessageManager().messageUser(senderName, receiverId, messageText, LocalDateTime.now());
    }



    default void unreadMessages(int id) {
        getMessageManager().unreadMessages(id);
    }
    default void readMessage(int userId){
        getMessageManager().readMessage(userId);
    }
    default void archiveMessages(int id) {
        getMessageManager().archiveMessages(id);
    }
    default void unarchiveMessages(int id) {
        getMessageManager().unarchiveMessage(id);
    }
    default void deleteMessage(int id, String sender, int index){
       getMessageManager().deleteMessage(id, sender, index);
    }


    default boolean alreadyArchived(int id) {
        return getMessageManager().alreadyArchived(id);
    }
    default boolean alreadyUnread(int id) {
        return getMessageManager().alreadyUnread(id);
    }


}

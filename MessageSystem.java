package gateway.facade.system;

import gateway.MessageUpdater;

/**
 * Interface for methods of MessageUpdater.
 */
public interface MessageSystem extends System {
    /**
     * @return the gateway that updates messages
     */
    MessageUpdater getMessageUpdater();
    /**
     * Saves new messages to the database.
     *
     * @param senderId The id of the person who sent the message
     * @param receiverId The id of the receiver of the message
     * @param message The message the sender sent to the receiver
     * @param time The time the message was sent
     */
    default void messageUser(int senderId, int receiverId, String message, String time) {
        getMessageUpdater().messageUser(senderId, receiverId, message, time);
    }
    /**
     * Set a conversation as unread.
     *
     * @param primaryUser   id of user who decides to unread the messages
     * @param secondaryUser id of user whose conversation with primaryUser will be unread
     */
    default void unreadMessages(int primaryUser, int secondaryUser) {
        getMessageUpdater().unreadMessages(primaryUser, secondaryUser);
    }
    /**
     * Set a conversation as unread.
     *
     * @param primaryUser   id of user who has read the messages
     * @param secondaryUser id of user whose conversation with primaryUser will be read
     */
    default void readMessages(int primaryUser, int secondaryUser) {
        getMessageUpdater().readMessages(primaryUser, secondaryUser);
    }
    /**
     * Archive a conversation.
     *
     * @param primaryUser   id of user who decides to archive the messages
     * @param secondaryUser id of user whose conversation with primaryUser will be archived
     */
    default void archiveMessages(int primaryUser, int secondaryUser) {
        getMessageUpdater().archiveMessages(primaryUser, secondaryUser);
    }
    /**
     * Archive a conversation.
     *
     * @param primaryUser   id of user who decides to unarchive the messages
     * @param secondaryUser id of user whose conversation with primaryUser will be unarchived
     */
    default void unarchiveMessages(int primaryUser, int secondaryUser) {
        getMessageUpdater().unarchiveMessages(primaryUser, secondaryUser);
    }
    /**
     * delete a message
     * @param userId the receiver of the message
     * @param currentId the sender of the message
     * @param index the n'th message sent by the sender
     */
    default void deleteMessage(int userId, int currentId, int index) {
        getMessageUpdater().deleteMessages(userId, currentId, index);
    }
}
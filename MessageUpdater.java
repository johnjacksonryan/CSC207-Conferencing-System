package gateway;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import messaging.MessageManager;
import org.bson.Document;
import org.bson.conversions.Bson;
import users.base.UserManager;

import javax.print.Doc;
import java.util.*;

/**
 * Loads or saves user data to file
 *
 * @author Carl
 * @author Yukang
 * @author Dickson
 * @version 1.3
 */
public class MessageUpdater {

    /*
     Conversation format
     user1, user2, unreadUser1, archivedUser1, unreadUser2, archivedUser2, messages:document
     */

    private final MessageManager messageManager;
    private final UserManager userManager;
    private final MongoCollection<Document> conversations;

    /**
     * Default constructor
     *
     * @param messageManager the use case for handling Messages in the program
     * @param userManager    the use case for handling Users in the program
     * @param database the database containing the collection conversations
     */
    public MessageUpdater(MessageManager messageManager, UserManager userManager, Database database) {
        this.messageManager = messageManager;
        this.userManager = userManager;
        this.conversations = database.getDatabase().getCollection("conversations");
    }
    /**
     * Loads all messages applicable to the user from database
     *
     * @param userId user id
     */
    public void loadMessages(int userId) {
        for (Document document : this.conversations.find(new Document())) {
            int user1Id = document.getInteger("user1");
            int user2Id = document.getInteger("user2");

            //if one of the user is the logged in user
            if (user1Id == userId) {
                loadMessage(document, 1, user2Id);
            }
            else if (user2Id == userId) {
                loadMessage(document, 2, user1Id);
            }
        }
    }
    /**
     * load one conversation from database
     * @param document the document of conversation
     * @param primary the "current user"
     * @param id    the id of the secondary user
     */
    private void loadMessage(Document document, int primary, int id){
            boolean unread = document.getBoolean("unreadUser"+primary);
            boolean archived = document.getBoolean("archivedUser"+primary);

            //load message of given conversation
            for (Document messageDocument : document.getList("messages", Document.class)) {
                int senderId = messageDocument.getInteger("sender");
                this.messageManager.messageUser(
                        this.userManager.getUserName(senderId),
                        id,
                        messageDocument.getString("message"),
                        messageDocument.getString("time")
                );
            }
        if (archived) {
            this.messageManager.archiveMessages(id);
        }
        if (unread) {
            this.messageManager.unreadMessages(id);
        }
    }


    //interactions
    /**
     * Saves new messages to the database
     *
     * @param senderId The id of the person who sent the message
     * @param receiverId The id of the reciever of the message
     * @param message The message the sender sent to the receiver
     * @param time The time the message was sent
     *
     */
    public void messageUser(int senderId, int receiverId, String message, String time) {
        //create the message
        Document document = new Document();
        document.put("sender", senderId);
        document.put("message", message);
        document.put("time", time);

        //check if the conversation exists
        Bson filter = filter(senderId, receiverId);
        if (this.conversations.find(filter).first() != null) {
            this.conversations.findOneAndUpdate(filter, Updates.push("messages", document));
            unreadMessages(receiverId, senderId);
        } else {
            //create new conversation
            Document conversation = new Document();
            conversation.put("user1", senderId);
            conversation.put("user2", receiverId);
            conversation.put("unreadUser1", false);
            conversation.put("unreadUser2", true);
            conversation.put("archivedUser1", false);
            conversation.put("archivedUser2", false);
            conversation.put("messages", Collections.singletonList(document));
            this.conversations.insertOne(conversation);
        }
    }
    /**
     * unread a conversation
     *
     * @param primaryUser   id of user who decides to unread the messages
     * @param secondaryUser id of user whose conversation with primaryUser will be unread
     */
    public void unreadMessages(int primaryUser, int secondaryUser) {
        Bson filter = filter(primaryUser, secondaryUser);
        Document document = this.conversations.find(filter).first();
        int user1 = document.getInteger("user1");
        if (user1 == primaryUser) {
            this.conversations.findOneAndUpdate(filter, Updates.set("unreadUser1", true));
        } else {
            this.conversations.findOneAndUpdate(filter, Updates.set("unreadUser2", true));
        }
    }
    /**
     * read a conversation
     *
     * @param primaryUser   id of user who has read the messages
     * @param secondaryUser id of user whose conversation with primaryUser will be read
     */
    public void readMessages(int primaryUser, int secondaryUser) {
        Bson filter = filter(primaryUser, secondaryUser);
        Document document = this.conversations.find(filter).first();
        int user1 = document.getInteger("user1");
        if (user1 == primaryUser) {
            this.conversations.findOneAndUpdate(filter, Updates.set("unreadUser1", false));
        } else {
            this.conversations.findOneAndUpdate(filter, Updates.set("unreadUser2", false));
        }
    }
    /**
     * archive a conversation
     *
     * @param primaryUser   id of user who decides to archive the messages
     * @param secondaryUser id of user whose conversation with primaryUser will be archived
     */
    public void archiveMessages(int primaryUser, int secondaryUser) {
        Bson filter = filter(primaryUser, secondaryUser);
        Document document = this.conversations.find(filter).first();
        int user1 = document.getInteger("user1");
        if (user1 == primaryUser) {
            this.conversations.findOneAndUpdate(filter, Updates.set("archivedUser1", true));
        } else {
            this.conversations.findOneAndUpdate(filter, Updates.set("archivedUser2", true));

        }

    }
    /**
     * archive a conversation
     *
     * @param primaryUser   id of user who decides to unarchive the messages
     * @param secondaryUser id of user whose conversation with primaryUser will be unarchived
     */
    public void unarchiveMessages(int primaryUser, int secondaryUser) {
        Bson filter = filter(primaryUser, secondaryUser);
        Document document = this.conversations.find(filter).first();
        int user1 = document.getInteger("user1");
        if (user1 == primaryUser) {
            this.conversations.findOneAndUpdate(filter, Updates.set("archivedUser1", false));
        } else {
            this.conversations.findOneAndUpdate(filter, Updates.set("archivedUser2", false));

        }

    }
    /**
     * delete a message (with following identifiers)
     * @param receiverId the receiver of the message
     * @param senderId the sender of the message
     * @param index the n'th message sent
     */
    public void deleteMessages(int receiverId, int senderId, int index) {
        Bson filter = filter(receiverId, senderId);
        Document document = this.conversations.find(filter).first();
        List<Document> messages = document.getList("messages", Document.class);
        ArrayList<Document> newList = new ArrayList<>();

        int i = -1;
        int actualIndex = 0;
        for (Document message : messages) {
            if (message.getInteger("sender") == senderId) {
                i += 1;
                if (i == index) {
                    break;
                }
            }
            actualIndex += 1;
        }
        i = 0;
        for (Document message : messages) {

            if (i != actualIndex) {
                System.out.println(message);
                newList.add(message);
            }
            i += 1;
        }
        this.conversations.findOneAndUpdate(filter, Updates.set("messages", newList));
    }


    /**
     * find the list of conversations with desired ids
     *
     * @param senderId   The user id of the person who sent the message
     * @param receiverId The user id of the person who recieving the message
     * @return return Bson document with list of conversations
     */
    private Bson filter(int senderId, int receiverId) {
        return Filters.or(
                Filters.and(Filters.eq("user1", senderId), Filters.eq("user2", receiverId)),
                Filters.and(Filters.eq("user1", receiverId), Filters.eq("user2", senderId)));
    }
}
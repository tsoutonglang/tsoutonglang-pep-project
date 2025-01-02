package Service;

import Model.Message;
import DAO.MessageDAO;
import DAO.AccountDAO;

import java.util.List;

public class MessageService {
    public MessageDAO messageDAO;
    public AccountDAO accountDAO;

    /* 
     * No-args constructor for messageService which creates an MessageDAO.
     */
    public MessageService() {
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }


    /*
     * Message shouldn't be blank.
     * Message should be less than 255 characters.
     * Posted by a real username and user_id
     * @param Message object
     * @return Message that was posted
     */
    public Message newMessage(Message message) {
        // Message should not be blank
        if (message.message_text.isBlank()) {
            System.out.println("Message is blank");
            return null;
        }

        // Message should be less than 255 characters
        if (message.message_text.length() > 255) {
            System.out.println("Message is too long");
            return null;
        }

        // Message should be posted by an existing username and user id
        if (accountDAO.findAccountID(message.getPosted_by()) == null) {
            return null;
        } else {
            System.out.println("Message created :)");
            return messageDAO.postMessage(message);
        }
    }

    /* 
    * @return List of all messages in the database
    */
    public List<Message> getAllMessages() {
        System.out.println("All messages available retrieved");
        return messageDAO.getAllMessagesSent();
    }

    /* 
     * Message ID should exist.
     * @return Message object
     */
    public Message findMessageByID(int messageID) {
        System.out.println("Message ID: " + messageID);
        return messageDAO.findMessagesByID(messageID);
    }

    /* 
     * Message ID should exist.
     * @return Successful deletion
     */
    public Message deleteMessageByID(int messageID) {
        System.out.println("Retrieved message ID: " + messageID);
        return messageDAO.deleteMessage(messageID);
    }

    /*
     * Message shouldn't be blank.
     * Message should be less than 255 characters.
     * Message ID should exist.
     * @return Message object
     */
    public Message updateMessage(int messageID, String updatedText) {
        // Message should not be blank
        if (updatedText.isBlank()) {
            System.out.println("Message is blank");
            return null;
        }

        // Message should be less than 255 characters
        if (updatedText.length() > 255) {
            System.out.println("Message is too long");
            return null;
        }

        System.out.println("Message ID: " + messageID);
        System.out.println("Message Text: " + updatedText);

        // Message should exist. If found, update the message
        if (messageDAO.findMessagesByID(messageID) != null) {
            System.out.println("Message updated");
            return messageDAO.updateMessage(messageID, updatedText);
        } else {
            System.out.println("Message not found");
            return null;
        }
    }

    /* 
     * Account ID should exist.
     * @return Message object
     */
    public List<Message> getAllUserMessages(int accountID) {
        System.out.println("Account ID: " + accountID);
        System.out.println("All messages available retrieved");
        return messageDAO.getAllUserMessages(accountID);
    }
}
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
    * TODO: Retrieve all messages
    * @return List of all messages in the database
    */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessagesSent();
    }

    /* 
     * TODO: Retrieve all messages by its ID
     * @return Message object
     */

    /* 
     * TODO: Delete a message by its ID
     * @return Successful deletion
     */

    /*
     * TODO: Update a message by its ID
     * @return Message object
     */

    /* 
     * TODO: Retrieve all messages by a user
     * @return Message object
     */
}
package DAO;

import Util.ConnectionUtil;
import Model.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * table: message
 * message_id int primary key
 * posted_by int
 * message_text varchar(255)
 * time_posted_epoch bigint
 * foreign key (posted_by) references account(account_id)
 */

public class MessageDAO {
    Connection connection = ConnectionUtil.getConnection();

    /*
     * @param A message object.
     * @return New message
     */
    public Message postMessage(Message message) {        
        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int generated_account_id = rs.getInt(1);
                message.setMessage_id(generated_account_id);
            }

            return message;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /* 
     * @return all messages
     */
    public List<Message> getAllMessagesSent() {
        List<Message> messages = new ArrayList<>();

        try {
            String sql = "SELECT * FROM message";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Message message = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch"));

                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return messages;
    }

    /* 
     * @return Message object
     */
    public Message findMessagesByID(int messageID) {
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, messageID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Message foundMessage = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );

                System.out.println(foundMessage.toString());
                return foundMessage;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /* 
     * @return Successful deletion
     */
    public Message deleteMessage(int messageID) {
        try {
            // Search for message
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, messageID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Message foundMessage = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );

                System.out.println(foundMessage.toString());

                // Delete the message if found
                sql = "DELETE FROM message WHERE message_id = ?";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, messageID);
                ps.executeUpdate();

                return foundMessage;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /*
     * TODO: Update a message by its ID
     * @return Message object
     */
    public Message updateMessage(int messageID, String updatedText) {
        try {
            // Search for message
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, messageID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Message foundMessage = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
                foundMessage.setMessage_text(updatedText);

                // Update message
                sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
                ps = connection.prepareStatement(sql);
                ps.setString(1, updatedText);
                ps.setInt(2, messageID);
                ps.executeUpdate();

                return foundMessage;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /* 
     * TODO: Retrieve all messages by a user
     * @return Message object
     */
}

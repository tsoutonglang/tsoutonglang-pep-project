package DAO;

import Util.ConnectionUtil;
import Model.Message;

import java.sql.*;

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
}

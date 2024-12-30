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
    
}

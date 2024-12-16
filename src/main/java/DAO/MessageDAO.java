package DAO;

import Util.ConnectionUtil;
import Model.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    
}

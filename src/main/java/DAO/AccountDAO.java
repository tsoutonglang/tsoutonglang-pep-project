package DAO;

import Util.ConnectionUtil;
import Model.Account;

import java.sql.*;

/*
 * table: account
 * account_id int primary key
 * username varchar(255)
 * password varchar(255)
 */

public class AccountDAO {
    Connection connection = ConnectionUtil.getConnection();

    /* 
     * Insert the account into the database.
     */
    public Account createAccount(Account account) {
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";

            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int generated_account_id = rs.getInt(1);
                account.setAccount_id(generated_account_id);
            }
            
            return account;
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /*
     * Search for the account through the id.
     */
    public Account findAccountID(int accountID) {
        try {
            String sql = "SELECT * FROM account WHERE account_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, accountID);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Account account = new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password"));

                return account;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /*
     * Check if the username has been registered.
     */
    public Account getAccountByUsername(String username) {
        try {
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Account account = new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password"));

                return account;
            }

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /* 
     * Find the account's password.
     */

    public Account findPassword(String username) {
        try {
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Account account = new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password"));

                return account;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

}
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
     * @param An Account object.
     * @return If a new account was added, null if it wasn't.
     */
    public Account createAccount(Account account) {
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";

            // Insert user info into sql statement
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.executeUpdate();

            System.out.println("Account added to database :)");

            // Generate the account id
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
     * Check if the username has already been registered.
     * @param String username.
     * @return Account found or null.
     */
    public Account getAccountByUsername(String username) {
        try {
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            // if an account was found
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
     * TODO: process User logins.
     * @param Account username.
     * @return Account if it was found, null if not.
     */

    public Account findPassword(String username) {
        try {
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, username);
            System.out.println(ps.toString());
            ResultSet rs = ps.executeQuery();

            // Return the found account
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
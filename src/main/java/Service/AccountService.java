package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    public AccountDAO accountDAO;

    /* 
     * No-args constructor for accountService which creates an AccountDAO.
     */
    public AccountService() {
        accountDAO = new AccountDAO();
    }

    /*
     * Username must not be blank.
     * Password must be at least 4 characters long.
     * Username must not already exist.
     * @param Account object.
     * @return account if it was successfully added, null if it wasn't.
     */
    public Account addAccount(Account account) {
        // Username must not be blank.
        if (account.getUsername().isBlank()) {
            System.out.println("Username is empty.");
            return null;
        }

        // Password must be at least 4 characters long.
        if (account.getPassword().length() < 4) {
            System.out.println("Password is not long enough.");
            return null;
        }

        // Username must not already exist.
        if (accountDAO.getAccountByUsername(account.getUsername()) != null) {
            System.out.println("Account already exists.");
            return null;
        }

        // Add account
        System.out.println("Account created :)");
        return accountDAO.createAccount(account);
    }
    
    /* 
     * Username and password provided in the request body JSON match a real account existing on the database.
     * @param Account object.
     * @return Account if it was succesfully found, null if not.
     */
    public Account loginAccount(Account account) {
        String username = account.getUsername();
        String password = account.getPassword();

        // Username must not be blank.
        if (username.isBlank()) {
            System.out.println("Username is empty.");
            return null;
        }

        // Password must not be blank.
        if (password.isBlank()) {
            System.out.println("Password is empty.");
            return null;
        }

        Account foundAccount = accountDAO.getAccountByUsername(username);

        // Username must exist.
        if (foundAccount == null) {
            System.out.println("Username does not exist.");
            return null;
        } else {
            System.out.println("Username exists.");
        }

        // Password must match username.
        if (foundAccount.getPassword().equals(password)) {
            System.out.println("Password matches username.");
            return foundAccount;
        } else {
            System.out.println("Password does not match username.");
            return null;
        }
    }
}

package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

/*
 * TODO: You will need to write your own endpoints and handlers for your controller. 
 */

public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    /* 
     * No-args constructor for service classes.
     */
    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.post("/register", this::postAccountRegistrationHandler);
        app.post("/login", this::postLoginHandler);

        return app;
    }

    /**
     * Handler to post a new user.
     * If successful: 200
     * If not successful: 400
     */
    private void postAccountRegistrationHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);

        if(addedAccount != null) {
            ctx.json(mapper.writeValueAsString(addedAccount));
        } else{ 
            ctx.status(400);
        }
    }

    /* 
     * Handler to process user logins.
     * If successful: 200
     * If not successful: 401
     */
    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account foundAccount = accountService.loginAccount(account);

        if(foundAccount != null) {
            ctx.json(mapper.writeValueAsString(foundAccount));
        } else{ 
            ctx.status(401);
        }
    }

     /*
      * TODO: POST localhost:8080/messages : process the creation of new messages
      * Handler to post new messages.
      * If successful: 200
      * If not successful: 400
      */
    private void postMessage(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message newMessage = messageService.newMessage(message);

        if(newMessage != null) {
            ctx.json(mapper.writeValueAsString(newMessage));
        } else{ 
            ctx.status(400);
        }
    }

     /*
      * TODO: GET localhost:8080/messages : retrieve all messages
      * Handler to retrieve all messages.
      * If successful: 200
      */
      
     /* 
      * TODO: GET localhost:8080/messages/{message_id} : retrieve a message by its ID
      * Handler to retrieve a message by its ID.
      * If successful: 200
      */

     /* 
      * TODO: DELETE localhost:8080/messages/{message_id} : delete a message identified by a message ID
      * Handler to delete a message by its ID.
      * If successful: 200
      */

     /* 
      * TODO: PATCH localhost:8080/messages/{message_id} : update a message text identified by a message ID
      * Handler to update a messages by its ID.
      * If successful: 200
      * If not successful: 400
      */
      
     /*
      * TODO: GET localhost:8080/accounts/{account_id}/messages : retrieve all messages written by a particular user
      * Handler to retrieve all messages by a user.
      * If successful: 200
      */
}
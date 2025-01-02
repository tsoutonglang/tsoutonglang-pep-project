package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

/*
 * TODO: You will need to write your own endpoints and handlers for your controller. 
 */

public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;
    ObjectMapper mapper;

    /* 
     * No-args constructor for service classes.
     */
    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
        this.mapper = new ObjectMapper();
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
        app.post("/messages", this::postNewMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::patchMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getAllUserMessagesHandler);

        return app;
    }

    /**
     * Handler to post a new user.
     * If successful: 200
     * If not successful: 400
     */
    private void postAccountRegistrationHandler(Context ctx) throws JsonProcessingException {
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
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account foundAccount = accountService.loginAccount(account);

        if(foundAccount != null) {
            ctx.json(mapper.writeValueAsString(foundAccount));
        } else{ 
            ctx.status(401);
        }
    }

     /*
      * Handler to post new messages.
      * If successful: 200
      * If not successful: 400
      */
    private void postNewMessageHandler(Context ctx) throws JsonProcessingException {
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message newMessage = messageService.newMessage(message);

        if(newMessage != null) {
            ctx.json(mapper.writeValueAsString(newMessage));
        } else{ 
            ctx.status(400);
        }
    }

     /*
      * Handler to retrieve all messages.
      * If successful: 200
      */
    private void getAllMessagesHandler(Context ctx) {
        ctx.json(messageService.getAllMessages());
    }
      
     /* 
      * Handler to retrieve a message by its ID.
      * If successful: 200
      */
    private void getMessageHandler(Context ctx) throws JsonProcessingException {
        int messageID = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.findMessageByID(messageID);

        if (message != null) {
            ctx.json(message);
        } else {
            ctx.status(200).result("");
        }
    }

     /* 
      * Handler to delete a message by its ID.
      * If successful: 200
      */
    private void deleteMessageHandler(Context ctx) {
        int messageID = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.deleteMessageByID(messageID);

        if (message != null) {
            ctx.json(message);
        } else {
            ctx.status(200).result("");
        }
    }

     /* 
      * Handler to update a messages by its ID.
      * If successful: 200
      * If not successful: 400
      */
    private void patchMessageHandler(Context ctx) throws JsonProcessingException {
        int messageID = Integer.parseInt(ctx.pathParam("message_id"));
        String newMessageText = mapper.readTree(ctx.body()).get("message_text").asText();
        Message message = messageService.updateMessage(messageID, newMessageText);

        if (message != null) {
            ctx.json(message);
        } else {
            ctx.status(400);
        }
    }
      
     /*
      * Handler to retrieve all messages by a user.
      * If successful: 200
      */
    private void getAllUserMessagesHandler(Context ctx) {
        int accountID = Integer.parseInt(ctx.pathParam("account_id"));
        ctx.json(messageService.getAllUserMessages(accountID));
    }
}
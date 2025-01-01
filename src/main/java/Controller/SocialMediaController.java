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
     * Handler to post a new author.
     * The JSON ObjectMapper will automatically convert the JSON of the POST request into an Account object.
     * If AccountService returns a null account (meaning posting an Account was unsuccessful), the API will return a 400
     * message (client error).
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. It will
     *            be available to this method automatically thanks to the app.post method.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
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
     * TODO: POST localhost:8080/login : process User logins
     * Handler to process user logins.
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
      */

     /*
      * TODO: GET localhost:8080/messages : retrieve all messages
      * Handler to retrieve all messages.
      */
      
     /* 
      * TODO: GET localhost:8080/messages/{message_id} : retrieve a message by its ID
      * Handler to retrieve a message by its ID.
      */

     /* 
      * TODO: DELETE localhost:8080/messages/{message_id} : delete a message identified by a message ID
      * Handler to delete a message by its ID.
      */

     /* 
      * TODO: PATCH localhost:8080/messages/{message_id} : update a message text identified by a message ID
      * Handler to update a messages by its ID.
      */
      
     /*
      * TODO: GET localhost:8080/accounts/{account_id}/messages : retrieve all messages written by a particular user
      * Handler to retrieve all messages by a user.
      */
}
package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. 
 * The endpoints you will need can be found in readme.md as well as the test cases. 
 * 
 * POST localhost:8080/
 *  process new user registrations and user logins
 *  process the creation of new messages
 * 
 * GET localhost:8080/
 *  retrieve all messages or by its id
 * 
 * DELETE localhost:8080/messages/{message_id}
 *  delete a message
 * 
 * PATCH localhost:8080/messages/{message_id}
 *  edit a message
 * 
 * GET localhost:8080/accounts/{account_id}/messages
 *  get a message by a user
 * 
 * You should refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
AccountService accountService;
MessageService messageService;

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::postAccountRegHandler);
        app.post("/login", this::postAccountLoginUserHandler);

        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getMessageHandler);

        app.delete("/messages", this::deleteMessage);

        app.patch("/messages", this::editMessageHandler);

        app.get("messages", this::getMessageUserHandler);

        app.start(8000);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    /**
     * Handler to post a new user.
     * The registration will be successful if and only if the username is not blank, the password is at least 4 characters long, 
     * and an Account with that username does not already exist. If all these conditions are met, the response body should contain a 
     * JSON of the Account, including its account_id. 
     * The response status should be 200 OK, which is the default. The new account should be persisted to the database.
     * If the registration is not successful, the response status should be 400. (Client error)
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException will be thrown if there is an issue
     */
    private void postAccountRegHandler(Context ctx) {
        ObjectMapper mapper = new ObjectMapper();

        // create an Account object
        Account account = mapper.readValue(ctx.body(), Account.class);

        // call the service method to register the account
        Account addedAccount = accountService.addedAccount(account);

        // check if the registration was successful
        if(addedAccount == null){
            ctx.status(400);
        } else{
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(addedAccount));
        }
    }


}
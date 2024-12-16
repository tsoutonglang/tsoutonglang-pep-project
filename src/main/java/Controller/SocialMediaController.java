package Controller;

import Service.AccountService;
import Service.MessageService;
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
        app.get("/register", this::registerUserHandler);
        app.get("/login", this::loginUserHandler);
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


}
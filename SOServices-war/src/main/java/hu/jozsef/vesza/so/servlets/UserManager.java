package hu.jozsef.vesza.so.servlets;

import hu.jozsef.vesza.so.model.User;
import hu.jozsef.vesza.so.utils.ErrorManager;
import hu.jozsef.vesza.so.utils.OfyService;
import hu.jozsef.vesza.so.utils.RequestProcessor;
import hu.jozsef.vesza.so.utils.UserParser;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.googlecode.objectify.Objectify;
import java.util.logging.Level;

public class UserManager extends HttpServlet
{

    private static final long serialVersionUID = -5529712982282774961L;
    private static final Logger log = Logger.getLogger(UserManager.class.getName());
    Objectify objectify = OfyService.ofy();

    List<User> fetchedUsers = objectify.load().type(User.class).list();
    boolean fetchedUsersExist = !fetchedUsers.isEmpty();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {

        log.severe(("Received GET request at " + this.getClass()));
        resp.setContentType("application/json ; charset=UTF-8");

        String username = req.getParameter("username");
        log.log(Level.SEVERE, "Username: {0}", username);
        String password = req.getParameter("password");
        log.log(Level.SEVERE, "Password: {0}", password);
        if (username == null)
        {
            log.severe("Username missing");
            ErrorManager.respondWithError("missingUsername", resp);
            return;
        } else
        {
            if (password == null)
            {
                log.severe("Password missing");
                ErrorManager.respondWithError("missingPassword", resp);
                return;
            }
        }

        User loggedInUser = null;

        if (fetchedUsersExist)
        {
            log.severe("Users exist in the datastore");
            for (User user : fetchedUsers)
            {
                boolean usernameExists = username.equals(user.getUsername());
                boolean passwordIsCorrect = password.equals(user.getPassword());
                if (usernameExists && passwordIsCorrect)
                {
                    log.log(Level.SEVERE, "Found user: {0} password: {1}", new Object[]
                    {
                        user.getUsername(), user.getPassword()
                    });
                    loggedInUser = user;
                }
            }

            if (loggedInUser == null)
            {
                log.severe("User not found");
                ErrorManager.respondWithError("badCredentials", resp);
            } else
            {
                log.log(Level.SEVERE, "Everything OK, should return user: {0}", loggedInUser.getUsername());
                String returnString = UserParser.writeSingleUserToJSON(loggedInUser);
                resp.getWriter().print(returnString);
            }
        } else
        {
            log.severe("Users do not exist in the datastore");
            ErrorManager.respondWithError("userDoesNotExist", resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.setContentType("application/json ; charset=UTF-8");
        JSONObject parsedParams = RequestProcessor.getBody(req);

        String username = (String) parsedParams.get("username");
        log.log(Level.SEVERE, "Register username: {0}", username);
        String password = (String) parsedParams.get("password");
        log.log(Level.SEVERE, "Register password: {0}", password);
        if (username == null)
        {
            log.severe("Username missing");
            ErrorManager.respondWithError("missingUsername", resp);
            return;
        } else
        {
            if (password == null)
            {
                log.severe("Password missing");
                ErrorManager.respondWithError("missingPassword", resp);
                return;
            }
        }
        User registeredUser = null;

        if (fetchedUsersExist)
        {
            log.severe("Users exist in the datastore");
            for (User user : fetchedUsers)
            {
                boolean usernameIsTaken = username.equals(user.getUsername());
                if (usernameIsTaken)
                {
                    log.severe("Username is already taken");
                    ErrorManager.respondWithError("usernameTaken", resp);
                    return;
                }
            }

            registeredUser = this.registerUser(username, password);
        } else
        {
            registeredUser = this.registerUser(username, password);
        }

        if (registeredUser != null)
        {
            log.log(Level.SEVERE, "Everything OK, should return generated user: {0} password:{1}", new Object[]
            {
                registeredUser.getUsername(), registeredUser.getPassword()
            });
            objectify.save().entity(registeredUser).now();
            String returnString = UserParser.writeSingleUserToJSON(registeredUser);
            resp.getWriter().print(returnString);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        log.severe("Deleting all users");
        objectify.delete().entities(fetchedUsers).now();
        resp.getWriter().print("All users deleted");
    }

    private User registerUser(String anUsername, String aPassword)
    {
        log.severe("Generating new user..");
        User returnUser = new User();
        returnUser.setUsername(anUsername);
        returnUser.setPassword(aPassword);
        return returnUser;
    }
}

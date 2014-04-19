package hu.jozsef.vesza.so.servlets;

import hu.jozsef.vesza.so.model.Meal;
import hu.jozsef.vesza.so.model.User;
import hu.jozsef.vesza.so.utils.OfyService;
import hu.jozsef.vesza.so.utils.RequestProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.googlecode.objectify.Objectify;
import java.util.logging.Level;

public class MealPaymentManagerServlet extends HttpServlet
{

    private static final long serialVersionUID = 5583298733087283574L;
    private static final Logger log = Logger.getLogger(MealPaymentManagerServlet.class.getName());
    Objectify objectify = OfyService.ofy();

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        log.log(Level.SEVERE, "Received DELETE request at {0}", this.getClass());
        resp.setContentType("application/json ; charset=UTF-8");
//        JSONObject parsedParams = RequestProcessor.getBody(req);

        // Get the active user from the request
        Long userId = new Long(req.getParameter("user"));

        // Get the meals to remove from the request
//        ArrayList<JSONObject> mealsFromRequest = (ArrayList<JSONObject>) parsedParams.get("meals");
        // Load the correct user from store
        User activeUser = objectify.load().type(User.class).id(userId).now();
        log.log(Level.SEVERE, "Check for user: {0}", activeUser);

        List<Meal> fetchedMeals = objectify.load().type(Meal.class).list();
        boolean fetchedMealsExist = !fetchedMeals.isEmpty();

        if (fetchedMealsExist)
        {
            for (Meal meal : fetchedMeals)
            {
                if (meal.getOwner() != null)
                {
                    if (meal.getOwner().get().getIdentifier() == activeUser.getIdentifier())
                    {
                        objectify.delete().entity(meal).now();
                        log.log(Level.SEVERE, "Removed meal with id: {0}", meal.getIdentifier());
                    }
                }
            }
        }

        log.severe("Everything OK, meal should be removed");

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("response", "successful payment");
        String resposeJSON = JSONObject.toJSONString(responseMap);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        resp.getWriter().print(resposeJSON);
    }
}

package hu.jozsef.vesza.so.servlets;

import hu.jozsef.vesza.so.model.Meal;
import hu.jozsef.vesza.so.utils.OfyService;
import hu.jozsef.vesza.so.utils.RequestProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.googlecode.objectify.Objectify;
import java.util.logging.Level;

public class MealRatingManagerServlet extends HttpServlet
{

    private static final long serialVersionUID = -6591433290250369455L;
    private static final Logger log = Logger.getLogger(MealRatingManagerServlet.class.getName());
    Objectify objectify = OfyService.ofy();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        List<Meal> fetchedMeals = objectify.load().type(Meal.class).list();
        boolean fetchedMealsExist = !fetchedMeals.isEmpty();
        log.log(Level.SEVERE, "Received PUT request at {0}", this.getClass());
        resp.setContentType("application/json ; charset=UTF-8");
        JSONObject parsedParams = RequestProcessor.getBody(req);
        ArrayList<JSONObject> meals = (ArrayList<JSONObject>) parsedParams.get("meals");

        for (JSONObject meal : meals)
        {
            String name = (String) meal.get("name");
            int rating = (int) (long) meal.get("rating");

            for (Meal fetchedMeal : fetchedMeals)
            {
                if (name.equals(fetchedMeal.getName()))
                {
                    log.log(Level.SEVERE, "Updating ratig for meal: {0}", fetchedMeal.getName());
                    int total = fetchedMeal.getRating() * fetchedMeal.getTotalRatings();
                    fetchedMeal.setTotalRatings(fetchedMeal.getTotalRatings() + 1);
                    total += rating;
                    fetchedMeal.setRating(total / fetchedMeal.getTotalRatings());
                    log.severe("Everything OK, meal rating updated");
                    objectify.save().entities(fetchedMeal).now();
                }
            }
        }
    }
}

package hu.jozsef.vesza.so.servlets;

import hu.jozsef.vesza.so.model.Meal;
import hu.jozsef.vesza.so.model.User;
import hu.jozsef.vesza.so.utils.ErrorManager;
import hu.jozsef.vesza.so.utils.MealParser;
import hu.jozsef.vesza.so.utils.OfyService;
import hu.jozsef.vesza.so.utils.RequestProcessor;
import hu.jozsef.vesza.so.utils.UserParser;

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

public class MealManagerServlet extends HttpServlet
{
	private static final long serialVersionUID = 5486448572613042901L;
	private static final Logger log = Logger.getLogger(MealManagerServlet.class.getName());

	Objectify objectify = OfyService.ofy();
	List<Meal> fetchedMeals = objectify.load().type(Meal.class).list();
	boolean fetchedMealsExist = !fetchedMeals.isEmpty();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		log.severe("Received GET request at " + this.getClass());
		resp.setContentType("application/json ; charset=UTF-8");
		if (!fetchedMealsExist)
		{
			log.severe("No meals exist in the datastore");
			ErrorManager.respondWithError("noMeals", resp);
		}
		else
		{
			log.severe("Everything OK, should return list of meals");
			String returnString = MealParser.writeListToJSON(fetchedMeals);
			resp.getWriter().print(returnString);
		}
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		log.severe("Received PUT request at " + this.getClass());
		resp.setContentType("application/json ; charset=UTF-8");
	    JSONObject parsedParams = RequestProcessor.getBody(req);
	    
	    Long userId = new Long((long) parsedParams.get("user"));
	    ArrayList<JSONObject> meals = (ArrayList<JSONObject>) parsedParams.get("meals");
	    
	    User activeUser = objectify.load().type(User.class).id(userId).now();
	    
	    
	    for (JSONObject meal : meals)
		{
			for (Meal fetched : fetchedMeals)
			{
				String name = (String) meal.get("name");
				int amount = (int) (long) meal.get("amount");
				if (name.equals(fetched.getName()))
				{
					log.severe("Adding Meal: " + name + " to User: " + activeUser.getUsername());
					activeUser.addToMeals(fetched, amount);
				}
			}
		}
	    
	    objectify.save().entity(activeUser).now();
	    
	    log.severe("Everything OK, should send order");
	    String returnString = UserParser.writeSingleUserToJSON(activeUser);
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().print(returnString);
	    
	}

}

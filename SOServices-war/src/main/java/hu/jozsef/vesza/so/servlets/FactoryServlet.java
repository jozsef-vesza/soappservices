package hu.jozsef.vesza.so.servlets;

import hu.jozsef.vesza.so.model.Event;
import hu.jozsef.vesza.so.model.Meal;
import hu.jozsef.vesza.so.model.Table;
import hu.jozsef.vesza.so.utils.EventParser;
import hu.jozsef.vesza.so.utils.MealParser;
import hu.jozsef.vesza.so.utils.OfyService;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.Objectify;

public class FactoryServlet extends HttpServlet
{
	private static final long serialVersionUID = 197887461429670874L;
	private static final Logger log = Logger.getLogger(UserManager.class.getName());
	Objectify objectify = OfyService.ofy();

	List<Event> fetchedEvents = objectify.load().type(Event.class).list();
	boolean fetchedEventsExist = !fetchedEvents.isEmpty();
	
	List<Meal> fetchedMeals = objectify.load().type(Meal.class).list();
	boolean fetchedMealsExist = !fetchedMeals.isEmpty();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		if (!fetchedEventsExist)
		{
			List<Event> events = EventParser.readDataFromJSON();
			for (Event event : events)
			{
				for (Table table : event.getLocation().getTables())
				{
					objectify.save().entity(table).now();
				}
				objectify.save().entity(event.getLocation()).now();
				log.severe("Created event: " + event.getEventTitle());
			}
			objectify.save().entities(events).now();
			
			resp.getWriter().print("Events generated\n");
		}
		else
		{
			resp.getWriter().print("Events already exist\n");
		}
		
		if (!fetchedMealsExist)
		{
			List<Meal> meals = MealParser.readListFromJSON();
			for (Meal meal : meals)
			{
				log.severe("Creating meal: " + meal.getName());
			}
			objectify.save().entities(meals).now();
			System.out.println("Creating meals..");
			resp.getWriter().print("Meals generated\n");
		}
		else
		{
			resp.getWriter().print("Meals already exist\n");
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		objectify.delete().entities(fetchedEvents).now();
		log.severe("Deleting events...");
		resp.getWriter().print("Events deleted\n");
		objectify.delete().entities(fetchedMeals).now();
		log.severe("Deleting existing meals..");
		resp.getWriter().print("Meals deleted\n");
	}

}

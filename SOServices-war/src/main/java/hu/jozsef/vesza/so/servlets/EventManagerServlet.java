package hu.jozsef.vesza.so.servlets;

import hu.jozsef.vesza.so.model.Event;
import hu.jozsef.vesza.so.model.User;
import hu.jozsef.vesza.so.utils.ErrorManager;
import hu.jozsef.vesza.so.utils.EventParser;
import hu.jozsef.vesza.so.utils.OfyService;
import hu.jozsef.vesza.so.utils.RequestProcessor;
import hu.jozsef.vesza.so.utils.UserParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Ref;
import java.util.ArrayList;
import java.util.logging.Level;

public class EventManagerServlet extends HttpServlet
{

    private static final long serialVersionUID = -2605132662874643879L;
    private static final Logger log = Logger.getLogger(EventManagerServlet.class.getName());

    Objectify objectify = OfyService.ofy();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        List<Event> fetchedEvents = objectify.load().type(Event.class).list();
        boolean fetchedEventsExist = !fetchedEvents.isEmpty();
        log.log(Level.SEVERE, "Received GET request at {0}", this.getClass());
        resp.setContentType("application/json ; charset=UTF-8");
        if (!fetchedEventsExist)
        {
            log.severe("No events exist in the datastore");
            ErrorManager.respondWithError("noEvents", resp);
        } else
        {
            log.severe("Everything OK, should return list of events");
            List<Event> defaultEvents = new ArrayList<>();
            for (Event event : fetchedEvents)
            {
                if (event.getOwner() == null)
                {
                    log.log(Level.SEVERE, "Loaded event: {0}", event.getEventTitle());
                    defaultEvents.add(event);
                }
            }
            String returnString = EventParser.writeListToJSON(defaultEvents);
            resp.getWriter().print(returnString);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        List<Event> fetchedEvents = objectify.load().type(Event.class).list();
        boolean fetchedEventsExist = !fetchedEvents.isEmpty();
        log.log(Level.SEVERE, "Received PUT request at {0}", this.getClass());
        resp.setContentType("application/json ; charset=UTF-8");
        JSONObject parsedParams = RequestProcessor.getBody(req);

        Long eventId = (long) parsedParams.get("event");
        Long userId = (long) parsedParams.get("user");
        Long tableId = null;
        if (parsedParams.get("table") != null)
        {
            tableId = (long) parsedParams.get("table");
        }
        int amount = (int) (long) parsedParams.get("amount");

        Event foundEvent = objectify.load().type(Event.class).id(eventId).now();
        User activeUser = objectify.load().type(User.class).id(userId).now();

        boolean bothEntitiesFound = foundEvent != null && activeUser != null;

        if (bothEntitiesFound)
        {
            log.log(Level.SEVERE, "User: {0} Event: {1} both found", new Object[]{activeUser.getUsername(), foundEvent.getEventTitle()});
//            Event returnEvent = activeUser.addToEvents(foundEvent, amount, tableId);
            Event returnEvent = new Event(foundEvent);
            returnEvent.setTicketsPurchased(amount);
            returnEvent.setSelectedTable(tableId);
            returnEvent.setPaid(true);
            returnEvent.setOwner(Ref.create(activeUser));
            objectify.save().entity(returnEvent).now();
//            objectify.save().entity(activeUser).now();
            String returnString = EventParser.writeSingleEventToJSON(returnEvent);
            log.severe("Everything OK, should order ticket");
            resp.getWriter().print(returnString);
        } else
        {
            if (foundEvent == null)
            {
                log.severe("Event not found, make sure phone cache is empty");
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        log.log(Level.SEVERE, "Received DELETE request at {0}", this.getClass());
        Long eventId = new Long(req.getParameter("event"));
        Long userId = new Long(req.getParameter("user"));
        
        Event eventToBeRemoved = objectify.load().type(Event.class).id(eventId).now();
        objectify.delete().entity(eventToBeRemoved).now();
        log.log(Level.SEVERE, "Removed event with title: {0}", eventToBeRemoved.getEventTitle());
    }
}

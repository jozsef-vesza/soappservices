package hu.jozsef.vesza.so.model;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class User
{

    @Id
    private Long identifier;
    private String username;
    private String password;
    private List<Event> orderedEvents;
    private List<Meal> orderedMeals;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public List<Event> getOrderedEvents()
    {
        return orderedEvents;
    }

    public void addToEvents(Event event)
    {
        if (this.orderedEvents == null)
        {
            this.orderedEvents = new ArrayList<>();
        }

        this.orderedEvents.add(event);

    }

    public void removeFromEvents(Event event)
    {
        this.orderedEvents.remove(event);
    }

    public void addToMeals(Meal meal)
    {
        if (this.orderedMeals == null)
        {
            this.orderedMeals = new ArrayList<>();
        }
        this.orderedMeals.add(meal);
    }

    public void removeFromMeals(Meal meal)
    {
        this.orderedMeals.remove(meal);
    }

    public List<Meal> getOrderedMeals()
    {
        return orderedMeals;
    }

    public Long getIdentifier()
    {
        return identifier;
    }
    
    
}

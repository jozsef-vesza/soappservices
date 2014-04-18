package hu.jozsef.vesza.so.model;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class User
{
	@Id private Long identifier;
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

	public Event addToEvents(Event event, int times, Long table)
	{
		Event eventToAdd = new Event(event);
		eventToAdd.setTicketsPurchased(times);
		eventToAdd.setPaid(true);
		if (table != null)
		{
			eventToAdd.setSelectedTable(table);
		}

		if (this.orderedEvents == null)
		{
			this.orderedEvents = new ArrayList<>();
		}

		this.orderedEvents.add(eventToAdd);

		return eventToAdd;
	}

	public void removeFromEvents(Event event)
	{
		this.orderedEvents.remove(event);
	}

	public void addToMeals(Meal meal, int times)
	{
		Meal mealToAdd = new Meal(meal);
		mealToAdd.setAmount(times);
		
		if (this.orderedMeals == null)
		{
			this.orderedMeals = new ArrayList<>();
		}
		this.orderedMeals.add(mealToAdd);
	}
	
	public void removeFromMeals(Meal meal)
	{
		this.orderedMeals.remove(meal);
	}

	public List<Meal> getOrderedMeals()
	{
		return orderedMeals;
	}
}

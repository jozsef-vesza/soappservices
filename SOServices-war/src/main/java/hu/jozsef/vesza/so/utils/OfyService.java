package hu.jozsef.vesza.so.utils;

import hu.jozsef.vesza.so.model.Event;
import hu.jozsef.vesza.so.model.Location;
import hu.jozsef.vesza.so.model.Meal;
import hu.jozsef.vesza.so.model.Table;
import hu.jozsef.vesza.so.model.User;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class OfyService
{
	static
	{
		ObjectifyService.register(Event.class);
		ObjectifyService.register(User.class);
		ObjectifyService.register(Meal.class);
		ObjectifyService.register(Location.class);
		ObjectifyService.register(Table.class);
	}
	
	public static Objectify ofy()
	{
		return ObjectifyService.ofy();
	}
	
	public static ObjectifyFactory factory()
	{
		return ObjectifyService.factory();
	}
}

package hu.jozsef.vesza.so.utils;

import hu.jozsef.vesza.so.model.Event;
import hu.jozsef.vesza.so.model.Table;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class EventParser
{
	static Gson gson = new Gson();
	static String fileName = "WEB-INF/mockEvents.json";
	static File file = new File(fileName);

	public static String writeListToJSON(List<Event> events)
	{

		String jsonString = gson.toJson(events);
		return jsonString;
	}
	
	public static String writeSingleEventToJSON(Event event)
	{
		String jsonString = gson.toJson(event);
		return jsonString;
	}

	public static List<Event> readDataFromJSON() throws IOException
	{
		InputStreamReader inReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
		String stringFromRes = null;
		try (BufferedReader br = new BufferedReader(inReader))
		{
			StringBuilder sb = new StringBuilder();
			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null)
			{
				sb.append(sCurrentLine);
				sb.append(System.lineSeparator());
			}

			stringFromRes = sb.toString();

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		List<Event> events = new ArrayList<Event>();
		Type listOfTestObject = new TypeToken<List<Event>>(){}.getType();
		events = gson.fromJson(stringFromRes, listOfTestObject);
		Random randomGenerator = new Random();
		
//		for (Event event : events)
//		{
//			for (Table table : event.getLocation().getTables())
//			{
//				int identifier = 1 + randomGenerator.nextInt(100);
//				table.setIdentifier((long) identifier);
//			}
//		}

		return events;
	}
}

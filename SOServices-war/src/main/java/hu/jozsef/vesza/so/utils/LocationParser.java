package hu.jozsef.vesza.so.utils;

import hu.jozsef.vesza.so.model.Location;
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

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;

public class LocationParser
{
	static Gson gson = new Gson();
	
	public static String writeLocationToJSON(Location aLocation)
	{
		String jsonString = gson.toJson(aLocation);
		return jsonString;
	}
	
	public static List<Location> readListFromJSON() throws IOException
	{
		String locationFileName = "WEB-INF/mockLocations.json";
		File locationFile = new File(locationFileName);
		
		InputStreamReader inReader = new InputStreamReader(new FileInputStream(locationFile), "UTF-8");
		String stringFromRes = null;
		try (BufferedReader br = new BufferedReader(inReader))
		{
			StringBuilder sb = new StringBuilder();
			String currentLine;
			
			while ((currentLine = br.readLine()) != null)
			{
				sb.append(currentLine);
				sb.append(System.lineSeparator());
			}
			
			stringFromRes = sb.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		List<Location> locations = new ArrayList<>();
		Type listOFLocations = new TypeToken<List<Location>>(){}.getType();
		locations = gson.fromJson(stringFromRes, listOFLocations);
		Random randomGenerator = new Random();
		for (Location location : locations)
		{
			for (Table table : location.getTables())
			{
				int identifier = 1 + randomGenerator.nextInt(100);
				table.setIdentifier((long) identifier);
			}
		}
		
		return locations;
	}
}

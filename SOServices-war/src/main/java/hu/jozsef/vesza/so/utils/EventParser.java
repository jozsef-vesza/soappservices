package hu.jozsef.vesza.so.utils;

import hu.jozsef.vesza.so.model.Event;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javax.servlet.ServletContext;

public class EventParser
{

    static Gson gson = new Gson();
    static String fileName = "/WEB-INF/mockEvents.json";

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

    public static List<Event> readDataFromJSON(ServletContext context) throws IOException
    {
        String filePath =  context.getRealPath(fileName);
        InputStreamReader inReader = new InputStreamReader(new FileInputStream(new File(filePath)), "UTF-8");
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

        return events;
    }
}

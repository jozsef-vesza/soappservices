package hu.jozsef.vesza.so.utils;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RequestProcessor
{

    public static JSONObject getBody(HttpServletRequest request) throws IOException
    {

        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();

        try
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line).append('\n');
            }
        } 
        finally
        {
            reader.close();
        }

        String raw = sb.toString();
        JSONObject json = null;

        JSONParser parser = new JSONParser();
        try
        {
            json = (JSONObject) parser.parse(raw);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }

        return json;
    }

}

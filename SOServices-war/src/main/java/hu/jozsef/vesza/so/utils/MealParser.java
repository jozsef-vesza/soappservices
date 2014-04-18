package hu.jozsef.vesza.so.utils;

import hu.jozsef.vesza.so.model.Meal;

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

public class MealParser
{
    static String fileName = "/WEB-INF/menu.json";
    static Gson gson = new Gson();

    public static String writeListToJSON(List<Meal> meals)
    {
        String jsonsString = gson.toJson(meals);
        return jsonsString;
    }

    public static String writeSingleMealToJSON(Meal meal)
    {
        String jsonString = gson.toJson(meal);
        return jsonString;
    }

    public static List<Meal> readListFromJSON(ServletContext context) throws IOException
    {
        
        String filePath = context.getRealPath(fileName);
        InputStreamReader inReader = new InputStreamReader(new FileInputStream(new File(filePath)), "UTF-8");
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

        List<Meal> menu = new ArrayList<Meal>();
        Type listOfMeals = new TypeToken<List<Meal>>()
        {
        }.getType();
        menu = gson.fromJson(stringFromRes, listOfMeals);
        return menu;
    }
}

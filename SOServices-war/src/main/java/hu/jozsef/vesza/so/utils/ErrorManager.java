package hu.jozsef.vesza.so.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class ErrorManager
{

    public static void respondWithError(String problem, HttpServletResponse resp) throws IOException
    {
        String errorString = null;

        if (problem.equals("missingUsername"))
        {
            errorString = "Missing username parameter";
        }
        if (problem.equals("userDoesNotExist"))
        {
            errorString = "No such user";
        }
        if (problem.equals("missingPassword"))
        {
            errorString = "Missing password parameter";
        }
        if (problem.equals("usernameTaken"))
        {
            errorString = "Username already taken";
        }
        if (problem.equals("No events available"))
        {
            errorString = "Missing password parameter";
        }
        if (problem.equals("noMeals"))
        {
            errorString = "No meals available";
        }

        Map<String, String> error = new HashMap<>();
        error.put("error", errorString);

        Gson gson = new Gson();
        String jsonString = gson.toJson(error);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        resp.getWriter().print(jsonString);

        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}

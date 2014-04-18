package hu.jozsef.vesza.so.utils;

import java.util.List;

import com.google.gson.Gson;

import hu.jozsef.vesza.so.model.User;

public class UserParser
{
	static Gson gson = new Gson();
	
	public static String writeSingleUserToJSON(User user)
	{
		String jsonString = gson.toJson(user);
		return jsonString;
	}
	
	public static String writeListOfUsersToJSON(List<User> users)
	{
		String jsonString = gson.toJson(users);
		return jsonString;
	}
}

package org.example;

import com.google.gson.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static Gson gson;

    public static void main(String[] args) {
        gson = new Gson();
        User u = new User(1, "Vilhelmas", "Rudys", "https://reqres.in/img/faces/1-image.jpg", "ne@tavo.reikalas");
        User u2 = new User(2, "Vilhelmas", "Rudys", "https://reqres.in/img/faces/1-image.jpg", "vilhelmasvcs@vilniuscoding.lt");
        deleteUser(u2);

    }

    public static void deleteUser(User user) {
        List<User> users = getUsers();
        users.stream()
                .filter(u -> u.equals(user))
                .findFirst()
                .map(u -> users.remove(user))
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + user.getId() + " not found."));
        updateJson(users);
    }

    public static void updateJson(List<User> users) {
        //The method takes a List<User> object as a parameter, which represents the list of users to be written to the JSON file.
        try (FileWriter writer = new FileWriter("users.json")) { //It tries to create a FileWriter object to write to the "users.json" file.
            //It uses the gson object (which is assumed to be a Gson instance) to convert the List<User> object to a JSON string.
            gson.toJson(users, writer);//It writes the JSON string to the file using the FileWriter.
        } catch (IOException e) {
            throw new RuntimeException(e);
            //If any IOException occurs during the writing process, it catches the exception and wraps it in a RuntimeException, which is then thrown.
        }
    }

    public static void addUserOld(User user) {
        try (FileWriter writer = new FileWriter("user.json", true)) {
            gson.toJson(user, writer);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static User getUser(long id) {
        try (FileReader reader = new FileReader("users.json")) {
            //Parse json file
            JsonElement jsonElement = JsonParser.parseReader(reader);
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            //Iterate through the JSON array
            for (JsonElement element : jsonArray) {
                JsonObject jsonObject = element.getAsJsonObject();
                //Extract fields from JSON object
                long userId = jsonObject.get("id").getAsLong();
                if (id == userId) {
                    String name = jsonObject.get("firstName").getAsString();
                    String surname = jsonObject.get("surname").getAsString();
                    String avatar = jsonObject.get("avatar").getAsString();
                    String email = jsonObject.get("email").getAsString();
                    //Create User object and add to list
                    User user = new User();
                    user.setId(userId);
                    user.setFirstName(name);
                    user.setLastName(surname);
                    user.setEmail(email);
                    user.setAvatar(avatar);
                    return user;
                }
            }

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("kazkas nutiko");
        }
        return new User();
    }

    public static List<User> getUsers() {
        //The method creates an empty list of User objects using an ArrayList.
        List<User> users = new ArrayList<>();
        try (FileReader reader = new FileReader("users.json")) {
            //It tries to read the "users.json" file using a FileReader.Analyze the JSON file
            JsonElement jsonElement = JsonParser.parseReader(reader);
            //It parses the JSON file using a JsonParser and gets a JsonElement object.
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            //It converts the JsonElement to a JsonArray object, which represents an array of JSON objects.
            // Iterate through the JSON object
            for (JsonElement element : jsonArray) {
                //For each JsonElement in the array, it converts it to a JsonObject and extracts the following fields.
                // It iterates through the JsonArray using a for-each loop.
                JsonObject jsonObject = element.getAsJsonObject();
                // Extract fields from JSON object
                long id = jsonObject.get("id").getAsLong();
                String name = jsonObject.get("firstName").getAsString();
                String surname = jsonObject.get("lastName").getAsString();
                String avatar = jsonObject.get("avatar").getAsString();
                String email = jsonObject.get("email").getAsString();
                // Create User object and add to list
                User user = new User();
                //It creates a new User object and sets its fields using the extracted values.
                user.setId(id);
                user.setFirstName(name);
                user.setLastName(surname);
                user.setEmail(email);
                user.setAvatar(avatar);
                users.add(user);
                //It adds the User object to the list of users.
            }
        } catch (Exception e) {
            System.out.println(e);
            //If any exception occurs during the process, it catches the exception and prints it to the console.
        }
        return users;
        //Finally, it returns the list of User objects.
    }
}
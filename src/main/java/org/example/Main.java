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
        User u2 = new User(2, "Vilhelmas", "Rudys", "https://reqres.in/img/faces/1-image.jpg","vilhelmasvcs@vilniuscoding.lt");
        deleteUser(u2);

    }
    public static void deleteUser(User user){
        List<User> users = getUsers();
        users.stream()
                .filter(u ->u.equals(user))
                .findFirst()
                .map(u ->users.remove(user))
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + user.getId() + " not found."));
            updateJson(users);
    }
    public static void updateJson(List <User> users){
        try(FileWriter writer = new FileWriter("users.json")){
            gson.toJson(users, writer);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    public static void addUserOld(User user){
        try(FileWriter writer = new FileWriter("user.json", true)){
            gson.toJson(user,writer);
        }catch (IOException e){
            throw new RuntimeException();
        }
    }
    public static User getUser(long id){
        try(FileReader reader = new FileReader("users.json")){
            //Parse json file
            JsonElement jsonElement = JsonParser.parseReader(reader);
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            //Iterate through the JSON array
            for (JsonElement element : jsonArray){
                JsonObject jsonObject = element.getAsJsonObject();
                //Extract fields from JSON object
                long userId = jsonObject.get("id").getAsLong();
                if (id == userId){
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

        }catch (Exception e){
            System.out.println(e);
            System.out.println("kazkas nutiko");
        }
        return new User();
    }
    public static List<User> getUsers(){
        List<User> users = new ArrayList<>();
        try(FileReader reader = new FileReader("users.json")){
            //Parse the JSON file
            JsonElement jsonElement = JsonParser.parseReader(reader);
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            //Iterate through the JSON object
            for (JsonElement element : jsonArray){
                JsonObject jsonObject = element.getAsJsonObject();
                // Extract fields from JSON object
                long id = jsonObject.get("id").getAsLong();
                String name = jsonObject.get("firstName").getAsString();
                String surname = jsonObject.get("lastName").getAsString();
                String avatar = jsonObject.get("avatar").getAsString();
                String email = jsonObject.get("email").getAsString();
                // Create User object and add to list
                User user = new User();
                user.setId(id);
                user.setFirstName(name);
                user.setLastName(surname);
                user.setEmail(email);
                user.setAvatar(avatar);
                users.add(user);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return users;
    }
}
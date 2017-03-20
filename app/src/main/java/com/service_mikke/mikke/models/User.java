package com.service_mikke.mikke.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by takuya on 3/18/17.
 */

public class User {
    private List<Genre> selected_genres;
    private List<Service> used_services;

    private static User ourInstance = new User();

    public static User getInstance(){
        return ourInstance;
    }

    private User(){

    }

    public List<Genre> getSelected_genres(){
        selected_genres = selected_genres  == null ? new ArrayList<Genre>() : selected_genres;
        return selected_genres;
    }

    public void addSelectedGenres(Genre genre){
        if(!selected_genres.contains(genre)){
            selected_genres.add(genre);
        }
    }

    public List<Service> getUsed_services(){
        used_services = used_services == null ? new ArrayList<Service>() : used_services;
        return used_services;
    }

    public void addUsedService(Service service){
        if(!used_services.contains(service)){
            used_services.add(service);
        }
    }

}

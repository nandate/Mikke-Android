package com.service_mikke.mikke_android.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by takuya on 3/18/17.
 */

public class User extends Observable{

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
            setChanged();
            notifyObservers(selected_genres.size());
        }
    }

    public void removeSelectedGenre(Genre genre){
        selected_genres.remove(genre);
        setChanged();
        notifyObservers(selected_genres.size());

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

    public boolean hasGenre(Genre genre){
        return  selected_genres.contains(genre);
    }

    @Override
    public void notifyObservers(Object arg){
        super.notifyObservers(arg);
    }
}

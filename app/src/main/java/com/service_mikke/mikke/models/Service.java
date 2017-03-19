package com.service_mikke.mikke.models;

import java.util.ArrayList;

/**
 * Created by takuya on 3/18/17.
 */

public class Service {


    private String id,name,title,link,description,cost;
    private ArrayList<String> pics,tags;

    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getLink(){
        return link;
    }

    public void setLink(String link){
        this.link = link;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getCost(){
        return cost;
    }

    public void setCost(String cost){
        this.cost = cost;
    }

    public ArrayList<String> getPics(){
        return pics;
    }

    public void setPics(ArrayList<String> pics){
        this.pics = pics;
    }


    public ArrayList<String> getTags(){
        return tags;
    }

    public void setTags(ArrayList<String> tags){
        this.tags = tags;
    }

}

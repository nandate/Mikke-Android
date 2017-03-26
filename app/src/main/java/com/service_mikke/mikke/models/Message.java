package com.service_mikke.mikke.models;

/**
 * Created by takuya on 3/27/17.
 */

public class Message {
    private java.lang.String title,content;
    private java.lang.String from,to;


    public Message(String title,String content){
        this.title = title;
        this.content = content;
    }


    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getContent(){
        return this.content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getFrom(){
        return this.from;
    }

    public void setFrom(String from){
        this.from = from;
    }

    public String getTo(){
        return this.to;
    }

    public void setTo(String to){
        this.to = to;
    }


}

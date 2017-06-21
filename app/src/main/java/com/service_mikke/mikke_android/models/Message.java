package com.service_mikke.mikke_android.models;

/**
 * Created by takuya on 3/27/17.
 */

public class Message {
    private String title,content;
    private Long created_at;
    private String photoUrl;


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

    public Long getCreated_at(){
        return this.created_at;
    }

    public void setCreated_at(Long created_at){
        this.created_at = created_at;
    }

    public String getPhotoUrl(){
        return this.photoUrl;
    }

    public void setPhotoUrl(String photoUrl){
        this.photoUrl = photoUrl;
    }



}

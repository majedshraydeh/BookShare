package com.app.bookshare.models;


import com.google.firebase.firestore.PropertyName;

import java.util.ArrayList;

public class Material
{

    @PropertyName("postID")
    private String postID;

    @PropertyName("subId")
    private String subId;

    @PropertyName("listFavorite")
    private ArrayList<String> listFavorite;

    @PropertyName("subject")
    private String subject="";

    @PropertyName("time")
    private String time="";

    @PropertyName("teacher")
    private String teacher="";

    @PropertyName("file_name")
    private String file_name="";

    @PropertyName("user")
    private User user=null;




    public Material() {
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<String> getListFavorite() {
        return listFavorite;
    }

    public void setListFavorite(ArrayList<String> listFavorite) {
        this.listFavorite = listFavorite;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Material{" +
                "postID='" + postID + '\'' +
                ", subId='" + subId + '\'' +
                ", listFavorite=" + listFavorite +
                ", subject='" + subject + '\'' +
                ", time='" + time + '\'' +
                ", teacher='" + teacher + '\'' +
                ", file_name='" + file_name + '\'' +
                ", user=" + user +
                '}';
    }
}

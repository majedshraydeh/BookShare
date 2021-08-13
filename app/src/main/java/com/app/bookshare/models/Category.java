package com.app.bookshare.models;


import com.google.firebase.firestore.PropertyName;

public class Category {

    @PropertyName("id")
    private String id="";

    @PropertyName("name")
    private String name="";

    @PropertyName("image")
    private String image="";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}

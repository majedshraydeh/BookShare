package com.app.bookshares.models;


import com.google.firebase.firestore.PropertyName;

public class SubCategory {

    @PropertyName("id")
    private String id="";

    @PropertyName("name")
    private String name="";

    @PropertyName("category_id")
    private String categoryId="";

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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "SubCategory{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", categoryId='" + categoryId + '\'' +
                '}';
    }
}

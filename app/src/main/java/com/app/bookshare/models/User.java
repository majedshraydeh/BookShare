package com.app.bookshare.models;



import com.google.firebase.firestore.PropertyName;

public class User
{

    @PropertyName("user_id")
    private String user_id;

    @PropertyName("user_name")
    private String user_name="";

    @PropertyName("email")
    private String email="";

    @PropertyName("phone_number")
    private String phone_number="";

    @PropertyName("image")
    private String image="";



    public User() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", email='" + email + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}

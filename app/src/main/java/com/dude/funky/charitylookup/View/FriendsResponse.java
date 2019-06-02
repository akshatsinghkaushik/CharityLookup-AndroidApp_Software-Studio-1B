package com.dude.funky.charitylookup.View;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class FriendsResponse {
    private String uid;
    private String name;
    private String email;


    public FriendsResponse() {
    }

    public FriendsResponse(String uid,String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUID() {
        return uid;
    }

    public void setUID(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //public String getImage() {
        //return image;
    //}

   // public void setImage(String image) {
     //   this.image = image;
    //}
}

package com.technomads.citygo;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User {
    private DatabaseReference myRef;
    private String USER = "User";
    public String name, surname;

    public User(){}
    public User(String name, String surname)
    {
        this.name = name;
        this.surname = surname;
    }

    public void addEvent(Event event)
    {
        myRef.push().setValue(event.getId());
        myRef = FirebaseDatabase.getInstance().getReference(USER);
    }

}

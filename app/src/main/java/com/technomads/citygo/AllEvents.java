package com.technomads.citygo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllEvents extends AppCompatActivity {

    private Button search_event_btn, show_map, addEvent, addEventSubmit, menu, signOut, exit;
    private EditText search_event_field, new_event_title, new_event_time, new_event_desc;
    private DatabaseReference myRef;
    private String EVENT = "Event";
    private LinearLayout add_event_layout, menu_layout;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private ArrayList<Event> list;
    private int new_event_id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_events);
        init();
        show_map.setOnClickListener(this::onShowMap);
        addEvent.setOnClickListener(this::addNewEvent);
        addEventSubmit.setOnClickListener(this::setAddEventSubmit);
        search_event_btn.setOnClickListener(this::searchEvent);
        menu.setOnClickListener(this::menu);
        signOut.setOnClickListener(this::signOut);
        exit.setOnClickListener(this::exit);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        myAdapter = new MyAdapter(this, list);
        recyclerView.setAdapter(myAdapter);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Event event1 = dataSnapshot.getValue(Event.class);
                    list.add(event1);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        new_event_id = list.size();
    }
    private void init()
    {
        menu_layout = findViewById(R.id.menu_linear_layout);
        menu = findViewById(R.id.menu);
        signOut = findViewById(R.id.signOut);
        exit = findViewById(R.id.exit);
        search_event_field = findViewById(R.id.search_field);
        search_event_btn = findViewById(R.id.search);
        myRef = FirebaseDatabase.getInstance().getReference(EVENT);
        new_event_desc = findViewById(R.id.event_desc);
        add_event_layout = findViewById(R.id.add_event_layout);
        addEventSubmit = findViewById(R.id.add_event_submit);
        new_event_title = findViewById(R.id.event_title);
        new_event_time = findViewById(R.id.event_time);
        show_map = findViewById(R.id.show_events2);
        addEvent = findViewById(R.id.add_new_event);
        recyclerView = findViewById(R.id.events_recycler);
    }

    private void onShowMap(View view)
    {
        startActivity(new Intent(this, MainActivity.class));
    }

    private void addNewEvent(View view)
    {
        add_event_layout.setVisibility(View.VISIBLE);
    }

    private void setAddEventSubmit(View view)
    {
        if(new_event_title.getText().toString() != "" && new_event_time.getText().toString() != "" && new_event_desc.getText().toString() != "")
        {
            list.clear();
            Event event = new Event(new_event_title.getText().toString(), new_event_time.getText().toString(), new_event_desc.getText().toString(), new_event_id);
            myRef.push().setValue(event);
            new_event_title.setText("");
            new_event_time.setText("");
            new_event_desc.setText("");
            Toast.makeText(AllEvents.this, "New event added successfully!", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(AllEvents.this, "Something went wrong!", Toast.LENGTH_LONG).show();
        }
        add_event_layout.setVisibility(View.GONE);
    }

    private void searchEvent(View view)
    {
        ArrayList<Event> search_list = new ArrayList<>();
        for(Event event: list)
        {
            if((event.getEvent_desc() + event.getEvent_title()).contains(search_event_field.getText().toString()))
            {
                search_list.add(event);
            }
        }
        search_event_btn.setText("Back");
        search_event_btn.setOnClickListener(this::backSearch);
        myAdapter = new MyAdapter(this, search_list);
        recyclerView.setAdapter(myAdapter);
    }

    private void backSearch(View view)
    {
        search_event_btn.setText(R.string.search_button);
        search_event_btn.setOnClickListener(this::searchEvent);
        myAdapter = new MyAdapter(this, list);
        recyclerView.setAdapter(myAdapter);
    }

    private void menu(View view)
    {
        if(menu_layout.getVisibility() != View.VISIBLE)
        {
            menu_layout.setVisibility(View.VISIBLE);
        }
        else
        {
            menu_layout.setVisibility(View.GONE);
        }
    }

    private void signOut(View view)
    {
        FirebaseAuth.getInstance().signOut();
        System.exit(0);
    }

    private void exit(View view)
    {
        finish();
    }

}

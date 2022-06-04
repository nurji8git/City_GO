package com.technomads.citygo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllEvents extends AppCompatActivity {

    private Button show_map, search_in_events, addEvent, addEventSubmit;
    private EditText search_field, new_event_title, new_event_time, new_event_desc;
    private DatabaseReference myRef;
    private String EVENT = "Event";
    private LinearLayout add_event_layout;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private ArrayList<Event> list;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_events);
        init();
        show_map.setOnClickListener(this::onShowMap);
        addEvent.setOnClickListener(this::addNewEvent);
        addEventSubmit.setOnClickListener(this::setAddEventSubmit);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        myAdapter = new MyAdapter(this, list);
        recyclerView.setAdapter(myAdapter);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
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

    }
    private void init()
    {
        myRef = FirebaseDatabase.getInstance().getReference(EVENT);
        new_event_desc = findViewById(R.id.event_desc);
        add_event_layout = findViewById(R.id.add_event_layout);
        addEventSubmit = findViewById(R.id.add_event_submit);
        new_event_title = findViewById(R.id.event_title);
        new_event_time = findViewById(R.id.event_time);
        show_map = findViewById(R.id.show_events2);
        search_in_events = findViewById(R.id.search_in_events);
        search_field = findViewById(R.id.search_field_in_events);
        addEvent = findViewById(R.id.add_new_event);
        recyclerView = findViewById(R.id.events_recycler);
    }

    private void onShowMap(View view)
    {
        startActivity(new Intent(AllEvents.this, MainActivity.class));
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
            Event event = new Event(new_event_title.getText().toString(), new_event_time.getText().toString(), new_event_desc.getText().toString());
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

}

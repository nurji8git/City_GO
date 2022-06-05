package com.technomads.citygo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button show_events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        show_events.setOnClickListener(this::onShowEvents);
    }
    private void init()
    {
        show_events = findViewById(R.id.show_events);
    }

    //функция для показа всех евентов
    public void onShowEvents(View view)
    {
        System.exit(0);
    }
}
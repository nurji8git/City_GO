package com.technomads.citygo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>
{
    Context context;

    ArrayList<Event> list;

    public MyAdapter(Context context, ArrayList<Event> list)
    {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.event, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        Event event = list.get(position);
        holder.eventTitle.setText(event.getEvent_title());
        holder.eventTime.setText(event.getEvent_time());
        holder.eventDesc.setText(event.getEvent_desc());
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView eventTitle, eventTime, eventDesc;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            eventTitle = itemView.findViewById(R.id.event_title_tv);
            eventTime = itemView.findViewById(R.id.event_time_tv);
            eventDesc = itemView.findViewById(R.id.event_desc_tv);
        }
    }
}

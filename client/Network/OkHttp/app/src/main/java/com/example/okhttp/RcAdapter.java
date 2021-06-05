package com.example.okhttp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RcAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<RcData> list = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.layout_rc_item,parent,false);

        return new RcViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RcViewHolder){
            ((RcViewHolder) holder).setItem(list.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(RcData data){
        list.add(data);
    }
    class RcViewHolder extends RecyclerView.ViewHolder{
        private TextView id;
        private TextView title;


        public RcViewHolder(@NonNull  View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.json_id);
            title = itemView.findViewById(R.id.json_title);

        }
        private void setItem(RcData data){
            id.setText(String.valueOf(data.getId()));
            title.setText(data.getTitle());
        }
    }
}

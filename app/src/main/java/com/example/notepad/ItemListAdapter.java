package com.example.notepad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import model.Items;
import model.Task;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemListHolder> {
    private Context context;
    private ArrayList<Task> tasks;

    public ItemListAdapter(Context context,ArrayList<Task> tasks){
        this.context = context;
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public ItemListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListHolder holder = new ItemListHolder(LayoutInflater.from(context).inflate(R.layout.item_details,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListHolder holder, int position) {
        Task current = tasks.get(position);
        holder.mTvTitle.setText(current.taskTitle);
//        holder.mTvItems.setText(current.taskItems);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ItemListHolder extends RecyclerView.ViewHolder{
        private TextView mTvTitle;
        private TextView mTvItems;


        public ItemListHolder(@NonNull View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvItems = itemView.findViewById(R.id.tv_items);

        }
    }
}

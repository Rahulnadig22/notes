package com.example.notepad;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import model.Items;
import model.Task;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskListHolder> {


    private Context context;
    private ArrayList<Task> tasks;

    private TaskListClickListener listener;
    public TaskListAdapter(Context context, ArrayList<Task> tasks){
        this.context = context;
        this.tasks = tasks;
    }

    public void setListener(TaskListClickListener listener){
        this.listener = listener;
    }
    @NonNull
    @Override
    public TaskListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskListHolder(LayoutInflater.from(context).inflate(R.layout.item_details,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull TaskListHolder holder, int position) {
        Task item = tasks.get(position);
        holder.mTvTaskTitle.setText(item.taskTitle);

        ArrayList<Items> itemList = Items.convertJSONArrayStringToArrayList(item.taskItems);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onTaskClicked(item);
                }
            }
        });

        holder.mIvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onDeleteClicked(item);
                }
            }
        });


        holder.mLlTaskItems.removeAllViews();


        for(final Items val:itemList){
            View view = LayoutInflater.from(context).inflate(R.layout.cell_item_view,null);
            TextView mTvItemName = view.findViewById(R.id.tv_view_items);
            CheckBox mChItem = view.findViewById(R.id.ch_view_item);
            mTvItemName.setText(val.itemName);

            mChItem.setChecked(val.isChecked);

            if (val.isChecked){
                mTvItemName.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }

            mChItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(listener!=null){
                        listener.onItemUpdateClicked(val,item,isChecked);
                    }
                }
            });

            holder.mLlTaskItems.addView(view);
        }

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class TaskListHolder extends RecyclerView.ViewHolder{

        private TextView mTvTaskTitle;
        private LinearLayout mLlTaskItems;
        private CardView mCardView;
        private ImageView mIvDelete;

        public TaskListHolder(@NonNull View itemView) {
            super(itemView);
            mTvTaskTitle = itemView.findViewById(R.id.tv_title);
            mLlTaskItems = itemView.findViewById(R.id.ll_view_item_details);
            mCardView = itemView.findViewById(R.id.CdView);
            mIvDelete = itemView.findViewById(R.id.delete_btn);
        }
    }
    public interface TaskListClickListener{
        void onItemUpdateClicked(Items updateItem, Task task, boolean checkedValue);
        void onTaskClicked(Task task);
        void onDeleteClicked(Task task);
    }
}

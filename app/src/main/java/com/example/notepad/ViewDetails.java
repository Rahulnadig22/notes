package com.example.notepad;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import model.Items;
import model.Task;


public class ViewDetails extends AppCompatActivity implements TaskListAdapter.TaskListClickListener {
    private RecyclerView mRvItem;

    private DataBaseHelper dbhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

        mRvItem = findViewById(R.id.rv_notes_details);
        mRvItem.setLayoutManager(new GridLayoutManager(this,3));

        dbhelper = new DataBaseHelper(ViewDetails.this);
        setDataToRecycler();
    }

    public void onAddNewTaskClicked(View view){
        startActivityForResult(new Intent(ViewDetails.this,MainActivity.class),120);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 120 || requestCode == 101) && resultCode == Activity.RESULT_OK){
            setDataToRecycler();
        }
    }

    private void setDataToRecycler(){
        ArrayList<Task> task = dbhelper.getTasksFromDataBase(dbhelper.getReadableDatabase());
        TaskListAdapter adapter = new TaskListAdapter(ViewDetails.this,task);
        adapter.setListener(this);
        mRvItem.setAdapter(adapter);
    }

    @Override
    public void onItemUpdateClicked(Items updateItem, Task task, boolean checkedValue) {
        ArrayList<Items> oldItemValues = Items.convertJSONArrayStringToArrayList(task.taskItems);

        if(oldItemValues!=null && oldItemValues.size()>0){
            for(Items oldItem:oldItemValues){
                if(oldItem.id== updateItem.id){
                    oldItem.isChecked = checkedValue;
                }
            }
        }

        Task updateTask = new Task();
        updateTask.id = task.id;
        updateTask.taskTitle = task.taskTitle;
        updateTask.taskItems = Items.convertArrayListToJSONArraySrring(oldItemValues);

        dbhelper.updateItemsInDatabase(dbhelper.getWritableDatabase(),updateTask);

        setDataToRecycler();
    }

    @Override
    public void onTaskClicked(Task task) {

        startActivityForResult(new Intent(ViewDetails.this,MainActivity.class).putExtra(MainActivity.keyword,task).putExtra(MainActivity.update,true),101);

    }

    @Override
    public void onDeleteClicked(Task task) {
        dbhelper.deleteClicked(dbhelper.getWritableDatabase(),task);
        setDataToRecycler();
    }
}
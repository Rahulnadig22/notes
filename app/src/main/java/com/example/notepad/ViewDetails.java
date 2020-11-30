package com.example.notepad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import model.Items;
import model.Task;


public class ViewDetails extends AppCompatActivity {
    private RecyclerView mRvItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

        mRvItem = findViewById(R.id.rv_notes_details);
        mRvItem.setLayoutManager(new LinearLayoutManager(ViewDetails.this,RecyclerView.VERTICAL,false));
        Bundle data = getIntent().getExtras();
        Task taskDetails = (Task) data.getSerializable(MainActivity.keyword);
        ArrayList<Task> details = new ArrayList<>();
        details.add(taskDetails);
        ItemListAdapter adapter = new ItemListAdapter(ViewDetails.this,details);
        mRvItem.setAdapter(adapter);




    }
}
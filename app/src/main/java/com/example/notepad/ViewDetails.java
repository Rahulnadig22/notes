package com.example.notepad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

public class ViewDetails extends AppCompatActivity{
    private RecyclerView mRvItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

        mRvItem = findViewById(R.id.rv_notes_details);
        mRvItem.setLayoutManager(new LinearLayoutManager(ViewDetails.this,RecyclerView.VERTICAL,false));

    }
}
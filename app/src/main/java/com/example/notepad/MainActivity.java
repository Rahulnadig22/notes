package com.example.notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Matrix;
import android.icu.util.EthiopicCalendar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import model.Items;
import model.Task;

public class MainActivity extends AppCompatActivity {
    private EditText mEtTitle;
    private Button mBtnAddItem;
    private Button mBtnAddTask;
    private LinearLayout mLlDynamicLayout;
    private int itemId = 0;
    private int taskid = 0;

    public static String keyword = "TASK";

    private ArrayList<Items> taskItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEtTitle = findViewById(R.id.et_task_title);
        mBtnAddItem = findViewById(R.id.btn_add_item);
        mBtnAddTask = findViewById(R.id.btn_add_task);
        mLlDynamicLayout = findViewById(R.id.Ll_dynamic_layouts);

        mBtnAddTask.setEnabled(false);

        taskItems = new ArrayList<>();

    }

    public void onAddItemClicked(View view){
        mBtnAddItem.setEnabled(false);
        itemId++;
        View dView = LayoutInflater.from(MainActivity.this).inflate(R.layout.cell_item_entry,null);
        final EditText mEtItem = dView.findViewById(R.id.et_item);
        final ImageView mIvDone = dView.findViewById(R.id.iv_item_done);

        mIvDone.setVisibility(View.INVISIBLE);

        mEtItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mIvDone.setVisibility(s.length()>0?View.VISIBLE:View.INVISIBLE);
            }
        });
        mIvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtItem.setEnabled(false);
                mBtnAddItem.setEnabled(true);
                mBtnAddTask.setEnabled(true);

                Items newItem = new Items();
                newItem.id = itemId;
                newItem.itemName = mEtItem.getText().toString();
                newItem.isChecked = false;
                taskItems.add(newItem);

            }
        });
        mLlDynamicLayout.addView(dView);
    }

    public void onAddTaskClicked(View view){
        if(mEtTitle.getText().toString().isEmpty()){
            return;
        }else {
            taskid++;
            Task newTask = new Task();
            newTask.taskTitle = mEtTitle.getText().toString();
            newTask.taskItems = taskItems;
            newTask.id = taskid;
            Intent data = new Intent(MainActivity.this,ViewDetails.class);
            data.putExtra(keyword,newTask);
            startActivity(data);
        }
    }
}
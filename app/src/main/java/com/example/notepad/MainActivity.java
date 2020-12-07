package com.example.notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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

    private DataBaseHelper dbhelper;
    private boolean isUpdate = false;

    public static String keyword = "TASK";
    public static String update = "isUpdate";

    private Task updatedTask;

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
        dbhelper = new DataBaseHelper(MainActivity.this);
        taskItems = new ArrayList<>();

        Bundle data = getIntent().getExtras();
        if(data!=null){
            isUpdate = data.getBoolean(update);
            updatedTask = (Task) data.getSerializable(keyword);

            if(isUpdate && updatedTask!=null){
                ArrayList<Items> updatedItems = Items.convertJSONArrayStringToArrayList(updatedTask.taskItems);
                mBtnAddTask.setEnabled(true);
                mBtnAddTask.setText("SUBMIT CHANGES");
                mEtTitle.setText(updatedTask.taskTitle);
                for(int i=0;i < updatedItems.size();i++){
                    View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.cell_item_entry,null);
                    final EditText mEtItem = view.findViewById(R.id.et_item);
                    final ImageView mIvDone = view.findViewById(R.id.iv_item_done);


                    mEtItem.setText(updatedItems.get(i).itemName);

                    mIvDone.setVisibility(View.INVISIBLE);
                    mLlDynamicLayout.addView(view);

                    itemId = i+1;

                    taskItems.add(updatedItems.get(i));
                }
            }
        }

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
            Task newTask = new Task();
            newTask.taskTitle = mEtTitle.getText().toString();
            newTask.taskItems = Items.convertArrayListToJSONArraySrring(taskItems);

            if (isUpdate) {
                newTask.id = updatedTask.id;
                dbhelper.updateItemsInDatabase(dbhelper.getWritableDatabase(), newTask);
            } else {
                dbhelper.insertDataToDataBase(dbhelper.getWritableDatabase(), newTask);
            }
            setResult(Activity.RESULT_OK);
            finish();
        }
    }
}
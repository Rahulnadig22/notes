package com.example.notepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import model.Task;

public class DataBaseHelper extends SQLiteOpenHelper {

    private final static String COL_ID = "id";
    private final static String COL_TITLE = "title";
    private final static String COL_ITEMS = "items";

    private final static String TABLE_NAME = "tasks";

    private final static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_TITLE + " TEXT," + COL_ITEMS + " TEXT)";


    public DataBaseHelper(@Nullable Context context) {
        super(context, "task.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertDataToDataBase(SQLiteDatabase db, Task task){
        ContentValues cv = new ContentValues();
        cv.put(COL_ITEMS,task.taskItems);
        cv.put(COL_TITLE,task.taskTitle);

        db.insert(TABLE_NAME,null,cv);
    }

    public ArrayList<Task> getTasksFromDataBase(SQLiteDatabase db){
        ArrayList<Task> items = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        if(cursor.moveToFirst()){
            do {
                Task newTask = new Task();
                newTask.id = cursor.getInt(cursor.getColumnIndex(COL_ID));
                newTask.taskTitle = cursor.getString(cursor.getColumnIndex(COL_TITLE));
                newTask.taskItems = cursor.getString(cursor.getColumnIndex(COL_ITEMS));

                items.add(newTask);
            }while (cursor.moveToNext());
        }

        return items;
    }

    public void updateItemsInDatabase(SQLiteDatabase db,Task task){
        ContentValues cv = new ContentValues();
        cv.put(COL_TITLE,task.taskTitle);
        cv.put(COL_ITEMS,task.taskItems);
        db.update(TABLE_NAME,cv,COL_ID+"="+task.id,null);
    }

    public void deleteClicked(SQLiteDatabase db,Task task){
        db.delete(TABLE_NAME,COL_ID+"="+task.id,null);
    }
}

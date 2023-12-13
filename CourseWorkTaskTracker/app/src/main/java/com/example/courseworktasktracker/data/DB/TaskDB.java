package com.example.courseworktasktracker.data.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.courseworktasktracker.data.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskDB {

    private TaskDB.DBHelper dbHelper;

    public TaskDB(Context context){
        dbHelper = new TaskDB.DBHelper(context);
    }

    public Task get(Task task){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("tasks", null, "id = ?",
                new String[] {String.valueOf(task.getId())},
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("name");
            int categoryIdIndex = c.getColumnIndex("categoryId");
            Task tr = new Task();
            tr.setId(c.getInt(idIndex));
            tr.setName(c.getString(nameIndex));
            tr.setCategoryId(c.getInt(categoryIdIndex));
            if (tr.getId() == (task.getId())) {
                dbHelper.close();
                return tr;
            }
        }
        dbHelper.close();
        return null;
    }

    public void add(Task task){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", task.getName());
        cv.put("categoryId", task.getCategoryId());
        long taskId = db.insert("tasks", null, cv);
        dbHelper.close();
    }

    public void update(Task task){
        if (get(task) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", task.getName());
        cv.put("categoryId", task.getCategoryId());
        db.update("tasks", cv, "id = ?", new String[] {String.valueOf(task.getId())});
        dbHelper.close();
    }

    public void delete(Task task){
        if(get(task) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("tasks", "id = " + task.getId(), null);
        dbHelper.close();
    }

    public List<Task> readAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Task> taskList = new ArrayList<Task>();
        Cursor c = db.query("tasks", null, null, null,
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("name");
            int categoryIdIndex = c.getColumnIndex("categoryId");
            do{
                Task tr = new Task();
                tr.setId(c.getInt(idIndex));
                tr.setName(c.getString(nameIndex));
                tr.setCategoryId(c.getInt(categoryIdIndex));
                taskList.add(tr);
            } while(c.moveToNext());
        }
        dbHelper.close();
        return taskList;
    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "tasksqqq", null, 2);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table tasks ("
                    + "id integer primary key autoincrement,"
                    + "name text,"
                    + "categoryId integer" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}

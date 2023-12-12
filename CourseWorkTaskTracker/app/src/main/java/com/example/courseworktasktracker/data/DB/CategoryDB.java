package com.example.courseworktasktracker.data.DB;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.courseworktasktracker.data.Category;

import java.util.ArrayList;
import java.util.List;


public class CategoryDB {

    private CategoryDB.DBHelper dbHelper;

    public CategoryDB(Context context){
        dbHelper = new CategoryDB.DBHelper(context);
    }

    public Category get(Category category){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("categories", null, "id = ?",
                new String[] {String.valueOf(category.getId())},
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("name");
            Category tr = new Category();
            tr.setId(c.getInt(idIndex));
            tr.setName(c.getString(nameIndex));
            if (tr.getId() == (category.getId())) {
                dbHelper.close();
                return tr;
            }
        }
        dbHelper.close();
        return null;
    }

    public void add(Category category){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", category.getName());
        long categoryId = db.insert("categories", null, cv);
        dbHelper.close();
    }

    public void update(Category category){
        if (get(category) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", category.getName());
        db.update("categories", cv, "id = ?", new String[] {String.valueOf(category.getId())});
        dbHelper.close();
    }

    public void delete(Category category){
        if(get(category) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("categories", "id = " + category.getId(), null);
        dbHelper.close();
    }

    public List<Category> readAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Category> categoryList = new ArrayList<Category>();
        Cursor c = db.query("categories", null, null, null,
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("name");
            int descriptionIndex = c.getColumnIndex("description");
            int priceIndex = c.getColumnIndex("price");
            do{
                Category tr = new Category();
                tr.setId(c.getInt(idIndex));
                tr.setName(c.getString(nameIndex));
                categoryList.add(tr);
            } while(c.moveToNext());
        }
        dbHelper.close();
        return categoryList;
    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "categoriesqqq", null, 2);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table categories ("
                    + "id integer primary key autoincrement,"
                    + "name text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}

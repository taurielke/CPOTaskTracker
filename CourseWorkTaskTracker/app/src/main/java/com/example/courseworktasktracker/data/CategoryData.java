package com.example.courseworktasktracker.data;
import android.content.Context;
import com.example.courseworktasktracker.data.DB.CategoryDB;

import java.util.ArrayList;
import java.util.List;

public class CategoryData {

    private static ArrayList<Category> categories  = new ArrayList<Category>();
    CategoryDB categoriesDB;

    public CategoryData(Context context){
        categoriesDB = new CategoryDB(context);
        readAll();
    }

    public Category getCategory(int id){
        Category tr = new Category();
        tr.setId(id);
        return categoriesDB.get(tr);
    }

    public List<Category> findAllCategory(){
        return categories;
    }

    public void addCategory(String name){
        Category category = new Category();
        category.setName(name);
        categoriesDB.add(category);
        readAll();
    }
    public void updateCategory(int id, String name){
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        categoriesDB.update(category);
        readAll();
    }
    public void deleteCategory(int id){
        Category category = new Category();
        category.setId(id);
        categoriesDB.delete(category);
        readAll();
    }
    private void readAll(){
        List<Category> brs = categoriesDB.readAll();
        categories.clear();
        for(Category category : brs){
            categories.add(category);
        }
    }
}

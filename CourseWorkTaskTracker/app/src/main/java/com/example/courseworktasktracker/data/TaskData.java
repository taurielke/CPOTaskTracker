package com.example.courseworktasktracker.data;
import android.content.Context;

import com.example.courseworktasktracker.data.DB.TaskDB;
import com.example.courseworktasktracker.data.DB.TaskDB;

import java.util.ArrayList;
import java.util.List;

public class TaskData {
    private static ArrayList<Task> tasks  = new ArrayList<Task>();
    TaskDB tasksDB;

    public TaskData(Context context){
        tasksDB = new TaskDB(context);
        readAll();
    }

    public Task getTask(int id){
        Task tr = new Task();
        tr.setId(id);
        return tasksDB.get(tr);
    }

    public List<Task> findAllTask(){
        return tasks;
    }

    public List<Task> findAllTaskByCategory(int categoryId){
        readAll();
        List<Task> tasksByCategoryId = new ArrayList<>();
        for(Task task : tasks){
            if(task.getCategoryId() == categoryId){
                tasksByCategoryId.add(task);
            }
        }
        return tasksByCategoryId;
    }

    public void addTask(String name, int categoryId){
        Task task = new Task();
        task.setName(name);
        task.setCategoryId(categoryId);
        //task.setDeadline(deadline);
        tasksDB.add(task);
        readAll();
    }
    public void updateTask(int id, String name, int categoryId){
        Task task = new Task();
        task.setId(id);
        task.setName(name);
        task.setCategoryId(categoryId);
       // task.setDeadline(deadline);
        tasksDB.update(task);
        readAll();
    }
    public void deleteTask(int id){
        Task task = new Task();
        task.setId(id);
        tasksDB.delete(task);
        readAll();
    }

    public void deleteAllTaskByCategory(int categoryId){
        for(Task task : tasks){
            if(task.getCategoryId() == categoryId){
                tasksDB.delete(task);
            }
        }
        readAll();
    }

    public void deleteAll(){
        tasks.clear(); // Очищаем список категорий в памяти
        tasksDB.deleteAll(); // Удаляем все записи в базе данных
        readAll();
    }

    private void readAll(){
        List<Task> brs = tasksDB.readAll();
        tasks.clear();
        for(Task task : brs){
            tasks.add(task);
        }
    }
}

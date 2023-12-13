package com.example.courseworktasktracker.data;

public class Task {

    private int id;
    private String name;
    private String deadline;
    private int categoryId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeadline(){return deadline;}

    public void setDeadline(String deadline) {this.deadline = deadline;}

    public int getCategoryId(){return categoryId;}

    public void setCategoryId(int categoryId) {this.categoryId = categoryId;}


    @Override
    public String toString() {
        return name;
    }
}

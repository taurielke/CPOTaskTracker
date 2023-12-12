package com.example.courseworktasktracker.data;

public class Task {

    private int id;
    private String name;
    private String deadline;
    private String category;

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

    public String getCategory(){return category;}

    public void setCategory(String category) {this.category = category;}


    @Override
    public String toString() {
        return name;
    }
}

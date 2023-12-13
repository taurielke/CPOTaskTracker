package com.example.courseworktasktracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.courseworktasktracker.data.Task;
import com.example.courseworktasktracker.data.TaskData;

import java.util.List;

public class TasksActivity extends AppCompatActivity {

    TaskData taskData;
    ArrayAdapter<Task> adapterTask;
    ListView listViewTasks;
    int categoryId;
    String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        categoryId = getIntent().getIntExtra("categoryId", 0);
        categoryName = getIntent().getStringExtra("categoryName");

        taskData = new TaskData(this);

        Button btnAdd = findViewById(R.id.buttonAdd);
        listViewTasks = findViewById(R.id.listViewTasks);

        Button btnBack = findViewById(R.id.btnBack);
        TextView tvCat = findViewById(R.id.textViewCategory);
        tvCat.setText(categoryName);

        adapterTask = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                taskData.findAllTaskByCategory(categoryId));
        listViewTasks.setAdapter(adapterTask);
        listViewTasks.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapterTask.notifyDataSetChanged();

        btnAdd.setOnClickListener(v -> {
            openAddDialog(categoryId);
            adapterTask.notifyDataSetChanged();
        });

        btnBack.setOnClickListener(v -> {
            onBackPressed();
        });

        listViewTasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Получите выбранный элемент списка
                Task selectedItem = (Task)adapterView.getItemAtPosition(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(TasksActivity.this);
                builder.setTitle("Выберите действие")
                        .setMessage("Выбранный элемент: " + selectedItem);

                final EditText input = new EditText(TasksActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("Изменить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newName = input.getText().toString().trim();
                        if (!newName.isEmpty()) {
                            taskData.updateTask(selectedItem.getId(), newName, categoryId);
                            adapterTask.notifyDataSetChanged();
                        }
                    }
                });

                // Обработка нажатия на кнопку "Удалить"
                builder.setNegativeButton("Удалить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Удалите выбранный элемент из списка
                        taskData.deleteTask(selectedItem.getId());
                        adapterTask.notifyDataSetChanged();
                    }
                });

                // Обработка нажатия на кнопку "Отмена"
                builder.setNeutralButton("Отмена", null);
                // Показать диалоговое окно
                builder.show();
                return true;
            }
        });
    }

    private void openAddDialog(int categoryId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Добавить задачу");

        final EditText input = new EditText(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        input.setLayoutParams(layoutParams);
        builder.setView(input);

        builder.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (input.getText().equals("")){
                    Toast.makeText(TasksActivity.this, "Введите задачу",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                String name = input.getText().toString();
                taskData.addTask(name, categoryId);
            }
        });

        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

}
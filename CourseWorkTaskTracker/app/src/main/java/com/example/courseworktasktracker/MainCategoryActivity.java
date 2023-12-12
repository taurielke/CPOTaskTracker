package com.example.courseworktasktracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.DialogInterface;
import android.content.Intent;
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


import com.example.courseworktasktracker.data.Category;
import com.example.courseworktasktracker.data.CategoryData;

public class MainCategoryActivity extends AppCompatActivity {

    CategoryData categoryData;
    ArrayAdapter<Category> adapter;
    ListView listViewCategories;
    GestureDetectorCompat gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_category);

        categoryData = new CategoryData(this);

        Button btnAdd = findViewById(R.id.buttonAdd);
        listViewCategories = findViewById(R.id.listViewCat);

        adapter = new ArrayAdapter<Category>(this, android.R.layout.simple_list_item_1,
                categoryData.findAllCategory());
        listViewCategories.setAdapter(adapter);
        listViewCategories.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter.notifyDataSetChanged();

        btnAdd.setOnClickListener(v -> {
            openAddDialog();
            adapter.notifyDataSetChanged();
        });

        listViewCategories.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Получите выбранный элемент списка
                Category selectedItem = (Category)adapterView.getItemAtPosition(position);

                // Создайте диалоговое окно с полем ввода текста
                AlertDialog.Builder builder = new AlertDialog.Builder(MainCategoryActivity.this);
                builder.setTitle("Выберите действие")
                        .setMessage("Выбранный элемент: " + selectedItem);

                // Создайте поле ввода текста в диалоговом окне
                final EditText input = new EditText(MainCategoryActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Обработка нажатия на кнопку "Изменить"
                builder.setPositiveButton("Изменить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newCategoryName = input.getText().toString().trim();
                        if (!newCategoryName.isEmpty()) {
                            categoryData.updateCategory(selectedItem.getId(), newCategoryName);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

                // Обработка нажатия на кнопку "Удалить"
                builder.setNegativeButton("Удалить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Удалите выбранный элемент из списка
                        categoryData.deleteCategory(selectedItem.getId());
                        adapter.notifyDataSetChanged();
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

    private void openAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Добавить категорию");

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
                    Toast.makeText(MainCategoryActivity.this, "Insert a name",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                String categoryName = input.getText().toString();
                categoryData.addCategory(categoryName);
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
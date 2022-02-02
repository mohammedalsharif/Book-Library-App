package com.examples.finaleproject.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.examples.finaleproject.R;
import com.examples.finaleproject.data.MyDatabase;
import com.examples.finaleproject.model.Category;

public class CreateCategory extends AppCompatActivity {
    EditText et_CategoryName;
    Button btn_CreateCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category);
        setTitle("Create New Category");

        et_CategoryName=findViewById(R.id.et_CategoryName);
        btn_CreateCategory=findViewById(R.id.btn_CreateCategory);

        btn_CreateCategory.setOnClickListener(view -> {
            String CateName=et_CategoryName.getText().toString();
            if (CateName.isEmpty()){
                Toast.makeText(this, "Category Name Can't be empty", Toast.LENGTH_SHORT).show();
            }else {
            MyDatabase database=MyDatabase.getInstance(this);
           boolean create = database.InsertCtegory(new Category(CateName));
           if (create){
               Toast.makeText(this, "Category Created", Toast.LENGTH_SHORT).show();
               et_CategoryName.setText("");
           }}

        });


    }
}
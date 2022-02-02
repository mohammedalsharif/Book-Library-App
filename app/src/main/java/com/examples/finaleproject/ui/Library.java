package com.examples.finaleproject.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.examples.finaleproject.R;
import com.examples.finaleproject.adapter.RecCategoryAdapter;
import com.examples.finaleproject.data.MyDatabase;
import com.examples.finaleproject.model.Category;

import java.util.ArrayList;

public class Library extends AppCompatActivity {

    public static final String intentCatgoreyId = "CategoryId";

    ArrayList<Category> categoryList = new ArrayList<>();
    MyDatabase database = MyDatabase.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        categoryList = database.getCategoryList();

        recConfig(adapterConfig(categoryList));

    }


    private RecCategoryAdapter adapterConfig(ArrayList<Category> categoryList) {
        RecCategoryAdapter adapter = new RecCategoryAdapter(this, categoryList, new RecCategoryAdapter.OnItemSelected() {
            @Override
            public void getItemSelected(int id) {
                Intent intent = new Intent(getBaseContext(), BooksShow.class);
                intent.putExtra(intentCatgoreyId, categoryList.get(id).getId());
                intent.putExtra("CategoryName", categoryList.get(id).getName());
                startActivity(intent);
                //      Toast.makeText(Library.this, categoryList.get(id).getId()+"", Toast.LENGTH_SHORT).show();
            }
        });
        return adapter;
    }


    private void recConfig(RecCategoryAdapter adapter) {
        RecyclerView rec_Category = findViewById(R.id.Rec_Category);
        rec_Category.setLayoutManager(new LinearLayoutManager(this));
        rec_Category.setHasFixedSize(true);
        rec_Category.setAdapter(adapter);
    }

}
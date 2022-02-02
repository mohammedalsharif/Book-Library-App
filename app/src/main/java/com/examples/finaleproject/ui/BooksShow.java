package com.examples.finaleproject.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.examples.finaleproject.R;
import com.examples.finaleproject.adapter.RecBookAdabter;
import com.examples.finaleproject.data.MyDatabase;
import com.examples.finaleproject.model.Book;

import java.util.ArrayList;

public class BooksShow extends AppCompatActivity {
    public static final String intentBook = "BookId";
    RecyclerView recyclerBooks;
    RecBookAdabter adabter;
    ArrayList<Book> listBooks = new ArrayList<>();
    MyDatabase database = MyDatabase.getInstance(this);
    int categoryId = 0;
    String categoryName="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_show);
        recyclerBooks = findViewById(R.id.Rec_Books);
        Intent intent = getIntent();
        categoryId = intent.getIntExtra(Library.intentCatgoreyId, -1);
        categoryName=intent.getStringExtra("CategoryName");
        setTitle(categoryName);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ShowBook();

    }

    private void ShowBook() {
        listBooks = database.getBook(categoryId);
        adabter = new RecBookAdabter(this,listBooks, new RecBookAdabter.OnItemCliked() {
            @Override
            public void OnClickIem(int posation) {
                Intent intent22 = new Intent(getBaseContext(), BookDetails.class);
                intent22.putExtra(intentBook, posation);
                intent22.putExtra(Library.intentCatgoreyId, categoryId);
                startActivity(intent22);
            }
        });

        recyclerBooks.setAdapter(adabter);
        recyclerBooks.setLayoutManager(new LinearLayoutManager(this));
        recyclerBooks.setHasFixedSize(true);
    }
}
package com.examples.finaleproject.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.examples.finaleproject.R;
import com.examples.finaleproject.adapter.RecAdapterFavorites;
import com.examples.finaleproject.data.MyDatabase;
import com.examples.finaleproject.model.Book;

import java.util.ArrayList;

public class Favorites extends AppCompatActivity {

    RecyclerView rec_fav;
    ArrayList<Book>books=new ArrayList<>();
    RecAdapterFavorites adapter;
    MyDatabase database=MyDatabase.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        rec_fav=findViewById(R.id.Rec_BooksFav);

        books=database.getAllfavList();

        adapter=new RecAdapterFavorites(this,books);
        rec_fav.setLayoutManager(new LinearLayoutManager(this));
        rec_fav.setHasFixedSize(true);
        rec_fav.setAdapter(adapter);



    }
}
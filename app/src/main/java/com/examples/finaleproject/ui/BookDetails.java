package com.examples.finaleproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.examples.finaleproject.R;
import com.examples.finaleproject.data.MyDatabase;
import com.examples.finaleproject.model.Book;
import com.examples.finaleproject.model.Category;

import java.util.ArrayList;

public class BookDetails extends AppCompatActivity {
    public static final String intentBookEdit="editbook";
    TextView tv_bookName,
            tv_AuthorName,tv_releasYear,tv_pagesNumber,tv_bookCategory;
    ImageView im_book;
    Button btn_edt;
    MyDatabase database=MyDatabase.getInstance(this);
    ArrayList<Book>bookList=new ArrayList<Book>();
    String bookName="";
    int intentBook;
    int intentCategoryId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        tv_bookName=findViewById(R.id.dt_bookName);
        tv_AuthorName=findViewById(R.id.dt_bookAuthorName);
        tv_releasYear=findViewById(R.id.dt_bookRYear);
        tv_pagesNumber=findViewById(R.id.dt_bookPNumber);
        tv_bookCategory=findViewById(R.id.dt_bookCategory);
        im_book=findViewById(R.id.im_book_details);
        btn_edt=findViewById(R.id.btn_edit);

        Intent intent =getIntent();


         intentBook= intent.getIntExtra(BooksShow.intentBook,-1);
        intentCategoryId=intent.getIntExtra(Library.intentCatgoreyId,-1);


    }

    @Override
    protected void onResume() {
        super.onResume();
        showBookDetails();
    }

    private void showBookDetails() {

        bookList = database.getBook(intentCategoryId);
        Book book= bookList.get(intentBook);
        bookName=book.getName();
        setTitle(bookName);

        configBook(book.getName(),book.getAuthorName(),book.getReleaseYear(),book.getPagesNumber());
        tv_bookCategory.setText(String.valueOf(book.getCategoryId()));
        Bitmap bitmap = BitmapFactory.decodeByteArray(book.getImageUrl(),0,book.getImageUrl().length);
        im_book.setImageBitmap(bitmap);

        btn_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getBaseContext(),BookEdit.class);
                intent1.putExtra(intentBookEdit,book);
                startActivity(intent1);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_delete_menu , menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_menu:
                Toast.makeText(this, "Book Dleted", Toast.LENGTH_SHORT).show();
                database.deleteBook(bookList.get(intentBook).getId());
                onBackPressed();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void configBook(String bookName , String AuthorName, int ReleasYear, int PagesNumber){
        tv_bookName.setText(bookName);
        tv_AuthorName.setText(AuthorName);
        tv_releasYear.setText(String.valueOf(ReleasYear));
        tv_pagesNumber.setText(String.valueOf(PagesNumber));
    }

}
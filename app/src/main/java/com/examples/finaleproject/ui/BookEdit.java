package com.examples.finaleproject.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.examples.finaleproject.R;
import com.examples.finaleproject.data.MyDatabase;
import com.examples.finaleproject.model.Book;
import com.examples.finaleproject.model.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.examples.finaleproject.ui.CreateBook.getBytes;
import static com.examples.finaleproject.ui.CreateBook.printToast;

public class BookEdit extends AppCompatActivity {
    private ImageView im_book;
    private FloatingActionButton btn_addImg;
    private EditText et_bookName, et_authorName, et_releaseYear, et_pagesNumber, et_CategoryId;
    private MyDatabase database = MyDatabase.getInstance(this);
    private Button btn_Edit;
    private byte[] imgNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_edit);
        im_book = findViewById(R.id.im_Editimgbook);
        btn_addImg = findViewById(R.id.btn_Editimg);
        et_bookName = findViewById(R.id.et_bookNameEdit);
        et_authorName = findViewById(R.id.et_authorNameEdit);
        et_releaseYear = findViewById(R.id.et_releaseYearEdit);
        et_pagesNumber = findViewById(R.id.et_pagesNumberEdit);
        et_CategoryId = findViewById(R.id.et_CategoryIdEdit);
        btn_Edit = findViewById(R.id.btn_Edit);


        Intent intent = getIntent();
        Book book = (Book) intent.getSerializableExtra(BookDetails.intentBookEdit);
        Bitmap bitmap = BitmapFactory.decodeByteArray(book.getImageUrl(), 0, book.getImageUrl().length);
        im_book.setImageBitmap(bitmap);
        imgNew=getBytes(bitmap);
        et_bookName.setText(book.getName());
        et_authorName.setText(book.getAuthorName());
        et_releaseYear.setText(String.valueOf(book.getReleaseYear()));
        et_pagesNumber.setText(String.valueOf(book.getPagesNumber()));
        et_CategoryId.setText(String.valueOf(book.getCategoryId()));

        btn_addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imgIntent = new Intent(Intent.ACTION_GET_CONTENT);
                imgIntent.setType("image/*");
                startActivityForResult(imgIntent, 2020);

            }
        });
        btn_Edit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                String nameBook = et_bookName.getText().toString();
                String author = et_authorName.getText().toString();
                String releaseYear = et_releaseYear.getText().toString();
                String pagesNumber = et_pagesNumber.getText().toString();
                String CategoryId = et_CategoryId.getText().toString();
                int releaseYearInt = 0;
                int pagesNumberInt;
                int CategoryIdInt = 0;


                if (imgNew.length == 0) {
                    printToast(getBaseContext(), getString(R.string.inalidImg));
                } else if (nameBook.isEmpty()) {
                    printToast(getBaseContext(), getString(R.string.invalidBookName));
                } else if (author.isEmpty()) {
                    printToast(getBaseContext(), getString(R.string.invalidAuthorName));
                } else {
                    if (releaseYear.isEmpty() || Integer.parseInt(releaseYear) < 1000) {
                        printToast(getBaseContext(), getString(R.string.invalidReleaseYear));
                    } else {
                        releaseYearInt = Integer.parseInt(releaseYear);
                    }
                    if (CategoryId.isEmpty()) {
                        printToast(getBaseContext(), getString(R.string.invalidReleaseYear));
                    } else {
                        CategoryIdInt = Integer.parseInt(CategoryId);
                    }

                    if (pagesNumber.isEmpty() || Integer.parseInt(pagesNumber) <= 0) {
                        printToast(getBaseContext(), getString(R.string.invalidPagesNumber));
                    } else {
                        pagesNumberInt = Integer.parseInt(pagesNumber);
                        boolean update = database.updateBook(new Book(nameBook, author, imgNew, releaseYearInt, pagesNumberInt, CategoryIdInt,book.getId()));
                        if (update) {
                            printToast(getBaseContext(), "book UPDATE");
                            onBackPressed();
                        }

                    }

                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data.getData() != null && resultCode == RESULT_OK) {
            Uri imguri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(imguri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                im_book.setImageBitmap(bitmap);
                imgNew = CreateBook.getBytes(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }

    }

}
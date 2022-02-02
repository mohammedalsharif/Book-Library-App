package com.examples.finaleproject.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
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
import java.io.InputStream;
import java.util.ArrayList;


public class CreateBook extends AppCompatActivity {
    private ImageView im_book;
    private FloatingActionButton btn_addImg;
    private Spinner sp_category;
    private EditText et_bookName, et_authorName, et_releaseYear, et_pagesNumber;
    private Button btn_create;
    private ArrayList<Category> categoryList = new ArrayList<>();
    private MyDatabase database = MyDatabase.getInstance(this);
    private byte[] imgurl;

    private int IdCategorySelcted = -10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_book);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Add New Book");
        im_book = findViewById(R.id.im_addimgbook);
        btn_addImg = findViewById(R.id.btn_addimg);
        sp_category = findViewById(R.id.sp_categore);
        et_bookName = findViewById(R.id.et_bookName);
        et_authorName = findViewById(R.id.et_authorName);
        et_releaseYear = findViewById(R.id.et_releaseYear);
        et_pagesNumber = findViewById(R.id.et_pagesNumber);
        btn_create = findViewById(R.id.btn_create);

        btn_addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 12);
            }
        });
        categoryList = database.getCategoryList();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getNamefromCategory(categoryList));
        sp_category.setAdapter(adapter);


        btn_create.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                if (!categoryList.isEmpty()) {
                    int spSelectedId = (int) sp_category.getSelectedItemId();
                    Category categorySelcted = categoryList.get(spSelectedId);
                    IdCategorySelcted = categorySelcted.getId();
                }
                String nameBook = et_bookName.getText().toString();
                String author = et_authorName.getText().toString();
                String releaseYear = et_releaseYear.getText().toString();
                String pagesNumber = et_pagesNumber.getText().toString();
                int releaseYearInt = 0;
                int pagesNumberInt;


                if (imgurl==null) {
                    printToast(getBaseContext(), getString(R.string.inalidImg));
                } else if (sp_category.getSelectedItemId() < 0) {
                    printToast(getBaseContext(), getString(R.string.invalidSp));
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
                    if (pagesNumber.isEmpty() || Integer.parseInt(pagesNumber) <= 0) {
                        printToast(getBaseContext(), getString(R.string.invalidPagesNumber));
                    } else {
                        pagesNumberInt = Integer.parseInt(pagesNumber);
                        // boolean create= database.insertOneBook(new Book("Book","Ali","",10,2120,50));
                        Book book=new Book(nameBook, author, imgurl, releaseYearInt, pagesNumberInt, IdCategorySelcted);
                        book.setIsFavourite("0");
                        boolean create = database.insertOneBook(book);
                        if (create) {
                            et_bookName.setText("");
                            et_authorName.setText("");
                            et_releaseYear.setText("");
                            et_pagesNumber.setText("");
                            im_book.setImageResource(R.drawable.open_book);
                            onBackPressed();
                            printToast(getBaseContext(), "book Created");
                        }

                    }

                }
            }
        });


    }

    private ArrayList<String> getNamefromCategory(ArrayList<Category> categoryList) {
        ArrayList<String> categoryNameList = new ArrayList<>();
        if (!categoryList.isEmpty()) {
            for (int i = 0; i < categoryList.size(); i++) {
                categoryNameList.add(categoryList.get(i).getName());
            }
        }
        return categoryNameList;
    }

    public static void printToast(Context context, String StrToast) {
        Toast.makeText(context, StrToast, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Uri imageuri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageuri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                im_book.setImageBitmap(bitmap);

                imgurl=getBytes(bitmap);

            } catch (Exception e) {

            }

            printToast(this, getString(R.string.imgTrue));
        } else {
            printToast(this, getString(R.string.imgFalse));
        }
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0,stream);
        return stream.toByteArray();
    }
}
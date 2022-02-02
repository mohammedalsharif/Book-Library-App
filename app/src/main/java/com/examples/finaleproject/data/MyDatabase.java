package com.examples.finaleproject.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.examples.finaleproject.model.Book;
import com.examples.finaleproject.model.Category;

import java.util.ArrayList;

public class MyDatabase extends SQLiteOpenHelper {
    public static final String DATA_BASE_NAME = "DBbook";
    public static final int VIRSION_DB = 10;

    public static final String TABLE_NAME = "books";

    public static final String B_CULOMN_ID = "id";
    public static final String B_CULOMN_NAME = "name";
    public static final String B_CULOMN_AUTHOUR = "authorname";
    public static final String B_CULOMN_IMGURL = "imgurl";
    public static final String B_CULOMN_RELEASE = "releaseyear";
    public static final String B_CULOMN_PAGESNAMBER = "pagesnamber";
    public static final String B_CULOMN_CATEGORY_ID = "categoryid";
    public static final String B_CULOMN_Favorites = "fStatus";

    //  category Table
    public static final String C_TABLE_NAME = "category";

    public static final String C_CULOMN_ID = "id";
    public static final String C_CULOMN_NAME = "name";
    public static MyDatabase Instance;


    public MyDatabase(Context context) {
        super(context, DATA_BASE_NAME, null, VIRSION_DB);

    }

    // singleton design pattern
    public static MyDatabase getInstance(Context context) {
        if (Instance == null) {
            Instance = new MyDatabase(context);
        }
        return Instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + C_TABLE_NAME + " ( " + C_CULOMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + C_CULOMN_NAME + " TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " ( " +
                B_CULOMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + B_CULOMN_NAME + "  TEXT ,"
                + B_CULOMN_AUTHOUR + " TEXT , "
                + B_CULOMN_IMGURL + " BLOB , " +
                B_CULOMN_RELEASE + " INTEGEAR , " +
                B_CULOMN_PAGESNAMBER + " INTEGER , " +
                B_CULOMN_Favorites + " TEXT , " +
                B_CULOMN_CATEGORY_ID + " INTEGER ,FOREIGN KEY (" + B_CULOMN_CATEGORY_ID + ") REFERENCES " + C_TABLE_NAME + "(" + C_CULOMN_ID + ") ON DELETE CASCADE)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + C_TABLE_NAME + "");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + "");
        onCreate(sqLiteDatabase);
    }

    public boolean InsertCtegory(Category category) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(C_CULOMN_NAME, category.getName());
        long ruselt = database.insert(C_TABLE_NAME, null, contentValues);

        return ruselt != -1;
    }


    public ArrayList<Category> getCategoryList() {
        ArrayList<Category> categoriesList = new ArrayList<>();
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + C_TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                int category_Id = cursor.getInt(0);
                String category_Name = cursor.getString(1);
                categoriesList.add(new Category(category_Id, category_Name));
            } while (cursor.moveToNext());
            cursor.close();

        }

        return categoriesList;
    }



    public boolean insertOneBook(Book book) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(B_CULOMN_NAME, book.getName());
        contentValues.put(B_CULOMN_AUTHOUR, book.getAuthorName());
        contentValues.put(B_CULOMN_IMGURL, book.getImageUrl());
        contentValues.put(B_CULOMN_RELEASE, Integer.valueOf(book.getReleaseYear()));
        contentValues.put(B_CULOMN_PAGESNAMBER, Integer.valueOf(book.getPagesNumber()));
        contentValues.put(B_CULOMN_Favorites,book.getIsFavourite());
        contentValues.put(B_CULOMN_CATEGORY_ID, Integer.valueOf(book.getCategoryId()));
        long ruselt = database.insert(TABLE_NAME, null, contentValues);

        return ruselt != -1;

    }

    public boolean updateBook(Book book) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(B_CULOMN_NAME, book.getName());
        contentValues.put(B_CULOMN_AUTHOUR, book.getAuthorName());
        contentValues.put(B_CULOMN_IMGURL, book.getImageUrl());
        contentValues.put(B_CULOMN_RELEASE, Integer.valueOf(book.getReleaseYear()));
        contentValues.put(B_CULOMN_PAGESNAMBER, Integer.valueOf(book.getPagesNumber()));
        contentValues.put(B_CULOMN_CATEGORY_ID, Integer.valueOf(book.getCategoryId()));

        int ruslet = database.update(TABLE_NAME, contentValues, "id = ? ", new String[]{String.valueOf(book.getId())});

        return ruslet > 0;
    }

    public ArrayList<Book> getBook(int categoryId) {
        ArrayList<Book> Books = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + B_CULOMN_CATEGORY_ID + " = ? ", new String[]{Integer.toString(categoryId)});
        if (cursor.moveToFirst()) {
            int indexID = cursor.getColumnIndex(B_CULOMN_ID);
            int indexName = cursor.getColumnIndex(B_CULOMN_NAME);
            int indexAuthorName = cursor.getColumnIndex(B_CULOMN_AUTHOUR);
            int indexReleaseYear = cursor.getColumnIndex(B_CULOMN_RELEASE);
            int indexImgUrl = cursor.getColumnIndex(B_CULOMN_IMGURL);
            int indexPagesNumber = cursor.getColumnIndex(B_CULOMN_PAGESNAMBER);
            int indexFavStutes = cursor.getColumnIndex(B_CULOMN_Favorites);
            int indexCategoryId = cursor.getColumnIndex(B_CULOMN_CATEGORY_ID);

            do {
                int Id = cursor.getInt(indexID);
                String name = cursor.getString(indexName);
                String authorName = cursor.getString(indexAuthorName);
                byte[] imgUrl = cursor.getBlob(indexImgUrl);
                int releaseYear = cursor.getInt(indexReleaseYear);
                int PagesNumber = cursor.getInt(indexPagesNumber);
                String FavStutes = cursor.getString(indexFavStutes);
                int CategoryId = cursor.getInt(indexCategoryId);
                Book book = new Book(name, authorName, imgUrl, releaseYear, PagesNumber, CategoryId, Id);
                book.setIsFavourite(FavStutes);
                Books.add(book);
            } while (cursor.moveToNext());
        }
        return Books;
    }

    public boolean deleteBook(int bookId) {
        SQLiteDatabase database = getWritableDatabase();
        int ruselt = database.delete(TABLE_NAME, "id = ?", new String[]{Integer.toString(bookId)});

        return ruselt > 0;
    }

    public void delete_fav(String bookId) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("UPDATE " + TABLE_NAME + " SET " + B_CULOMN_Favorites + " = '0' WHERE " + B_CULOMN_ID + " = " + bookId + "");
        Log.d("remove", bookId.toString());
    }


    public void set_fav(String bookId) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("UPDATE " + TABLE_NAME + " SET " + B_CULOMN_Favorites + " = '1' WHERE " + B_CULOMN_ID + " = " + bookId + "");
        Log.d("setFav", bookId.toString());
    }

    public ArrayList<Book> getAllfavList() {
        ArrayList<Book> AllFavList = new ArrayList<>();
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + B_CULOMN_Favorites + " = ? ", new String[]{"1"});
        if (cursor.moveToFirst()) {
            int indexID = cursor.getColumnIndex(B_CULOMN_ID);
            int indexName = cursor.getColumnIndex(B_CULOMN_NAME);
            int indexAuthorName = cursor.getColumnIndex(B_CULOMN_AUTHOUR);
            int indexReleaseYear = cursor.getColumnIndex(B_CULOMN_RELEASE);
            int indexImgUrl = cursor.getColumnIndex(B_CULOMN_IMGURL);
            int indexPagesNumber = cursor.getColumnIndex(B_CULOMN_PAGESNAMBER);
            int indexFavStutes = cursor.getColumnIndex(B_CULOMN_Favorites);
            int indexCategoryId = cursor.getColumnIndex(B_CULOMN_CATEGORY_ID);
            do {
                int Id = cursor.getInt(indexID);
                String Name = cursor.getString(indexName);
                String AuthorName = cursor.getString(indexAuthorName);
                byte[] imgeUrl = cursor.getBlob(indexImgUrl);
                int ReleaseYear = cursor.getInt(indexReleaseYear);
                int PagesNumber = cursor.getInt(indexPagesNumber);
                String FavStutes = cursor.getString(indexFavStutes);
                int CategoryId = cursor.getInt(indexCategoryId);
                Book book = new Book(Name, AuthorName, imgeUrl, ReleaseYear, PagesNumber, CategoryId, Id);
                book.setIsFavourite(FavStutes);
                AllFavList.add(book);

            } while (cursor.moveToNext());

        }
        return AllFavList;
    }



}

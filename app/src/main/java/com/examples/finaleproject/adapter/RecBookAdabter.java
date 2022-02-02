package com.examples.finaleproject.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.examples.finaleproject.R;
import com.examples.finaleproject.data.MyDatabase;
import com.examples.finaleproject.model.Book;

import java.util.ArrayList;

public class RecBookAdabter extends RecyclerView.Adapter<RecBookAdabter.viewHolder> {

    ArrayList<Book> bookList;
    OnItemCliked cliked;
    MyDatabase database;
    Context context;

    public RecBookAdabter( Context context,ArrayList<Book> bookList, OnItemCliked cliked) {
        this.context=context;
        this.bookList = bookList;
        this.cliked = cliked;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        database=MyDatabase.getInstance(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custome_item_book, parent, false);
        return new viewHolder(view);
    }

    public interface OnItemCliked {
        void OnClickIem(int posation);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Book book = bookList.get(position);
        if (book.getImageUrl().length!=0){
         Bitmap bitmap = BitmapFactory.decodeByteArray(book.getImageUrl(),0,book.getImageUrl().length);
        holder.im_book.setImageBitmap(bitmap);
    }
        holder.tv_BookName.setText(book.getName());
        holder.tv_AuthorName.setText(book.getAuthorName());
        holder.tv_ReleasYear.setText(String.valueOf(book.getReleaseYear()));
        holder.tv_pageNamber.setText(String.valueOf(book.getPagesNumber()));
        if (book.getIsFavourite()!=null&&book.getIsFavourite().equals("1")){
            holder.btn_fav.setBackgroundResource(R.drawable.favorite_red);
        }else if (book.getIsFavourite()!=null&&book.getIsFavourite().equals("0")){
            holder.btn_fav.setBackgroundResource(R.drawable.favorite_false);
        }

        holder.itemView.setOnClickListener(view -> {
            cliked.OnClickIem(position);

        });

        holder.btn_fav.setOnClickListener(view -> {
            if (book.getIsFavourite().equals("0")){
                book.setIsFavourite("1");
                database.set_fav(Integer.toString(book.getId()));
                holder.btn_fav.setBackgroundResource(R.drawable.favorite_red);
            }else {
                book.setIsFavourite("0");
                database.delete_fav(Integer.toString(book.getId()));
                holder.btn_fav.setBackgroundResource(R.drawable.favorite_false);

            }

        });

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {
        ImageView im_book;
        TextView tv_BookName, tv_AuthorName, tv_ReleasYear, tv_pageNamber;
        Button btn_fav;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            im_book = itemView.findViewById(R.id.image_bookItem);
            tv_BookName = itemView.findViewById(R.id.tv_bookNameItem);
            tv_AuthorName = itemView.findViewById(R.id.tv_bookAuthorNameItem);
            tv_ReleasYear = itemView.findViewById(R.id.tv_bookRYearItem);
            tv_pageNamber = itemView.findViewById(R.id.tv_bookPNumberItem);
            btn_fav=itemView.findViewById(R.id.btn_fav_item_Book);
        }
    }
}

package com.examples.finaleproject.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.examples.finaleproject.R;
import com.examples.finaleproject.data.MyDatabase;
import com.examples.finaleproject.model.Book;

import java.util.ArrayList;

public class RecAdapterFavorites extends RecyclerView.Adapter<RecAdapterFavorites.viewHolder> {
    Context context;
    ArrayList<Book> bookList;
    MyDatabase database;

    public RecAdapterFavorites(Context context, ArrayList<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        database=MyDatabase.getInstance(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custome_item_favorites,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Book book=bookList.get(position);
        Bitmap bitmap=BitmapFactory.decodeByteArray(book.getImageUrl(),0,book.getImageUrl().length);
        holder.im_favImg.setImageBitmap(bitmap);
        holder.tv_bookFav.setText(book.getName());
        if (book.getIsFavourite()!=null&&book.getIsFavourite().equals("1")){
            holder.btn_Fav.setBackgroundResource(R.drawable.favorite_red);
        }else if (book.getIsFavourite()!=null&&book.getIsFavourite().equals("0")){
            holder.btn_Fav.setBackgroundResource(R.drawable.favorite_false);
        }
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView im_favImg;
        TextView tv_bookFav;
        Button btn_Fav;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            im_favImg=itemView.findViewById(R.id.im_fav_item);
            tv_bookFav=itemView.findViewById(R.id.tv_fav_item);
            btn_Fav=itemView.findViewById(R.id.btn_fav_item);
        }
    }
}

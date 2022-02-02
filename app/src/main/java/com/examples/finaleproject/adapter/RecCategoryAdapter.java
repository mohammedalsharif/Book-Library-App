package com.examples.finaleproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.examples.finaleproject.R;
import com.examples.finaleproject.model.Category;

import java.util.ArrayList;

public class RecCategoryAdapter extends RecyclerView.Adapter<RecCategoryAdapter.ViewholderCategory> {
    Context context;
    ArrayList<Category> categoryList ;
    private   OnItemSelected itemSelected;

    public RecCategoryAdapter(Context context, ArrayList<Category> categoryList, OnItemSelected itemSelected) {
        this.context = context;
        this.categoryList = categoryList;
        this.itemSelected = itemSelected;
    }

    @NonNull
    @Override
    public ViewholderCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custome_item_category, parent, false);
        return new ViewholderCategory(view);

    }
     public interface OnItemSelected {
        void getItemSelected(int position);
     }

    @Override
    public void onBindViewHolder(@NonNull ViewholderCategory holder, int position) {
        Category category = categoryList.get(position);
        holder.tv_category.setText(category.getName());
       holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                itemSelected.getItemSelected(position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return categoryList.size();
    }




   public class ViewholderCategory extends RecyclerView.ViewHolder {
        TextView tv_category;

        public ViewholderCategory(@NonNull View itemView) {
            super(itemView);
            tv_category = itemView.findViewById(R.id.tv_category);



        }
    }
}

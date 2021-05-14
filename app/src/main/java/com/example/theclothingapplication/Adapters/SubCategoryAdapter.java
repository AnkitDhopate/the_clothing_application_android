package com.example.theclothingapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theclothingapplication.API.Model.CategoryApiModel;
import com.example.theclothingapplication.GlobalVariables;
import com.example.theclothingapplication.ProductsActivity;
import com.example.theclothingapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.SubCategoryViewholder>
{
    private ArrayList<CategoryApiModel> subCategoryList ;
    private ArrayList<CategoryApiModel> list ;
    private Context context;
//    private String IP = "http://192.168.43.249:" ;
    private String IP = GlobalVariables.IP;

    public SubCategoryAdapter(ArrayList<CategoryApiModel> subCategoryList, Context context, ArrayList<CategoryApiModel> list) {
        this.subCategoryList = subCategoryList;
        this.context = context;
        this.list = list ;
    }

    @NonNull
    @Override
    public SubCategoryViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sub_category_layout, parent, false);
        return new SubCategoryViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SubCategoryViewholder holder, final int position) {
        holder.sub_cate_name.setText(subCategoryList.get(position).getName());
        Picasso.get().load(IP+subCategoryList.get(position).getCategoryImage().split(":", 3)[2]).into(holder.sub_cate_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(context, ProductsActivity.class);
                intent.putExtra("slug", subCategoryList.get(position).getSlug());
                intent.putExtra("list", list);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subCategoryList.size();
    }

    public class SubCategoryViewholder extends RecyclerView.ViewHolder {

        private TextView sub_cate_name;
        private ImageView sub_cate_image;

        public SubCategoryViewholder(@NonNull View itemView) {
            super(itemView);

            sub_cate_name = itemView.findViewById(R.id.sub_category_name);
            sub_cate_image = itemView.findViewById(R.id.sub_category_image);
        }
    }
}

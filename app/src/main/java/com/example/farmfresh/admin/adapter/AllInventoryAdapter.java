package com.example.farmfresh.admin.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.farmfresh.R;
import com.example.farmfresh.admin.ProductInventory;
import com.example.farmfresh.admin.model.ProductModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AllInventoryAdapter extends RecyclerView.Adapter<AllInventoryAdapter.AllInventoryViewHolder> {
    String categoryName;
    Context context;
    List<ProductModel> productList;

    public AllInventoryAdapter(Context context, List<ProductModel> productList, String categoryName) {
        this.categoryName = categoryName;
        this.context = context;
        this.productList = productList;
    }


    @NonNull
    @Override
    public AllInventoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.inventory_items, parent, false);

        return new AllInventoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllInventoryViewHolder holder, final int position) {

        holder.tvname.setText(productList.get(position).getProductName());
        holder.tvprice.setText(productList.get(position).getPrice());
        holder.tvqty.setText(productList.get(position).getQty());
        Picasso.get().load(productList.get(position).getImageurl()).into(holder.ivimage);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ProductInventory.class);
                i.putExtra("category", categoryName);
                i.putExtra("id",productList.get(position).getProductId());
                i.putExtra("name", productList.get(position).getProductName());
                i.putExtra("desc",productList.get(position).getDescription());
                i.putExtra("image", productList.get(position).getImageurl());
                i.putExtra("qty",productList.get(position).getQty());
                i.putExtra("price",productList.get(position).getPrice());
                i.putExtra("availability",productList.get(position).getAvailability());
                i.putExtra("discount", productList.get(position).getDiscount());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                changeIntent(i);
//                context.startActivity(i);
            }
        });
    }

//    /* Interface for change availability, edit and delete menu items */
//    public interface OnProductClickListener {
//        void onAvailableItemClick(ProductModel productItem, int position);
//        void onEditItemClick(String itemName);
//        void onDeleteItemClick(String itemName);
//    }


    public void changeIntent(Intent i){ context.startActivity(i); }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class AllInventoryViewHolder extends RecyclerView.ViewHolder {

        TextView tvname, tvprice, tvqty;
        ImageView ivimage;
        public AllInventoryViewHolder(@NonNull View itemView) {
            super(itemView);


            tvname = itemView.findViewById(R.id.tvname);
            tvprice = itemView.findViewById(R.id.tvprice);
            tvqty = itemView.findViewById(R.id.tvqty);
            ivimage = itemView.findViewById(R.id.ivimage);

//            btnAvailable.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onProductClickListener.onAvailableItemClick(productList.get(getAdapterPosition()), getAdapterPosition());
//                }
//            });
//
//            ivEditItem.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onMenuClickListener.onEditItemClick(productList.get(getAdapterPosition()).getName());
//                }
//            });
//
//            ivDeleteItem.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onMenuClickListener.onDeleteItemClick(productList.get(getAdapterPosition()).getName());
        }
    }
}



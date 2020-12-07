package com.example.farmfresh.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.farmfresh.R;
import com.example.farmfresh.user.ProductDetails;
import com.example.farmfresh.user.model.AllFruitsModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AllFruitsAdapter extends RecyclerView.Adapter<AllFruitsAdapter.AllFruitViewHolder>{
    Context context;
    List<AllFruitsModel> allmenuList;
    String categoryName;

    public AllFruitsAdapter(Context context, List<AllFruitsModel> allmenuList, String categoryName) {
        this.context = context;
        this.allmenuList = allmenuList;
        this.categoryName = categoryName;
    }


    @NonNull
    @Override
    public AllFruitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_col_items, parent, false);

        return new AllFruitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllFruitViewHolder holder, final int position) {

        holder.tvname.setText(allmenuList.get(position).getProductName());
        holder.tvprice.setText("₹ "+allmenuList.get(position).getPrice());
        holder.tvdesc.setText(allmenuList.get(position).getDescription());
        holder.tvqty.setText(allmenuList.get(position).getQty());
        Picasso.get().load(allmenuList.get(position).getImageurl()).into(holder.ivimage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ProductDetails.class);
                i.putExtra("category", categoryName);
                i.putExtra("id",allmenuList.get(position).getProductId());
                i.putExtra("name", allmenuList.get(position).getProductName());
                i.putExtra("desc",allmenuList.get(position).getDescription());
                i.putExtra("image", allmenuList.get(position).getImageurl());
                i.putExtra("qty",allmenuList.get(position).getQty());
                i.putExtra("price",allmenuList.get(position).getPrice());
                i.putExtra("availability",allmenuList.get(position).getAvailability());
                i.putExtra("discount", allmenuList.get(position).getDiscount());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allmenuList.size();
    }

    public class AllFruitViewHolder extends RecyclerView.ViewHolder {
        TextView tvname, tvdesc, tvqty, tvprice;
        ImageView ivimage;
        public AllFruitViewHolder(@NonNull View itemView) {
            super(itemView);


            tvname = itemView.findViewById(R.id.tvname);
            tvdesc = itemView.findViewById(R.id.tvdesc);
            tvqty = itemView.findViewById(R.id.tvqty);
            tvprice = itemView.findViewById(R.id.tvprice);
            ivimage = itemView.findViewById(R.id.ivimage);
        }
    }
}

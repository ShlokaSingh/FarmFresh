package com.example.farmfresh.user.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.farmfresh.R;
import com.example.farmfresh.user.model.CartModel;
import com.squareup.picasso.Picasso;

public class IndividualAdapter extends RecyclerView.Adapter<IndividualAdapter.IndividualViewHolder> {

    Context context;
    List<CartModel> cartList;

    public IndividualAdapter(Context context, List<CartModel> cartList){
        this.context = context;
        this.cartList = cartList;
    }
    @NonNull
    @Override
    public IndividualViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_item, parent, false);

        return new IndividualAdapter.IndividualViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IndividualViewHolder holder, int position) {

        int price, discount, qty, total;
        price = Integer.parseInt(cartList.get(position).getPrice());
        discount = Integer.parseInt(cartList.get(position).getDiscount());
        qty = Integer.parseInt(cartList.get(position).getQuantity());
        total = ((100-discount)*price*qty)/100;

        holder.tvName.setText(cartList.get(position).getProductName());
        holder.tvTotal.setText("₹ "+total);
        holder.tvDiscount.setText(cartList.get(position).getDiscount());
        holder.tvQty.setText(cartList.get(position).getQuantity());
        holder.tvPrice.setText(cartList.get(position).getPrice());
        Picasso.get().load(cartList.get(position).getImageUrl()).into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class IndividualViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvQty, tvPrice, tvDiscount, tvTotal;
        ImageView ivImage;
        public IndividualViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvQty = itemView.findViewById(R.id.tvQty);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDiscount = itemView.findViewById(R.id.tvDiscount);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            ivImage = itemView.findViewById(R.id.ivimage);
        }
    }
}

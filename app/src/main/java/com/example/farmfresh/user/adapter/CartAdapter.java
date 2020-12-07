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
import java.util.concurrent.TimeUnit;

import com.example.farmfresh.R;
import com.example.farmfresh.user.model.CartModel;
import com.squareup.picasso.Picasso;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CardViewHolder> {
    Context context;
    List<CartModel> cartlist;
    int totalBill;

    public CartAdapter(Context context, List<CartModel> cartlist) {
        this.context = context;
        this.cartlist = cartlist;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_items, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        int price, discount, qty, total;
        price = Integer.parseInt(cartlist.get(position).getPrice());
        discount = Integer.parseInt(cartlist.get(position).getDiscount());
        qty = Integer.parseInt(cartlist.get(position).getQuantity());
        total = ((100-discount)*price*qty)/100;

        Log.d("shloka", ""+totalBill);
        holder.tvName.setText(cartlist.get(position).getProductName());
        holder.tvPrice.setText("₹ "+price);
        holder.tvQty.setText(String.valueOf(qty));
        holder.tvTotal.setText(String.valueOf(total));
        Picasso.get().load(cartlist.get(position).getImageUrl()).into(holder.ivimage);
    }

    @Override
    public int getItemCount() {
        return cartlist.size();
    }


    public class CardViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvQty,tvPrice,tvTotal;
        ImageView ivimage;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvQty = itemView.findViewById(R.id.tvQty);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            ivimage= itemView.findViewById(R.id.ivimage);
        }
    }
}

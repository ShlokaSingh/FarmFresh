package com.example.farmfresh.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.farmfresh.R;
import com.example.farmfresh.user.model.CartModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

        return new IndividualViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IndividualViewHolder holder, int position) {

        holder.allMenuName.setText(cartList.get(position).getProductName());
        holder.allMenuPrice.setText("₹ "+cartList.get(position).getPrice());
        holder.allMenuUnit.setText(cartList.get(position).getDiscount());
        holder.allMenuQty.setText(cartList.get(position).getQuantity());
        //picasso
//        holder.allMenuImage.setImageResource(cartList.get(position).getImageUrl());
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class IndividualViewHolder extends RecyclerView.ViewHolder {
        TextView allMenuName, allMenuQty, allMenuUnit, allMenuPrice;
        ImageView allMenuImage;
        public IndividualViewHolder(@NonNull View itemView) {
            super(itemView);

            allMenuName = itemView.findViewById(R.id.all_menu_name);
            allMenuQty = itemView.findViewById(R.id.all_menu_qty);
            allMenuUnit = itemView.findViewById(R.id.all_menu_unit);
            allMenuPrice = itemView.findViewById(R.id.all_menu_price);
            allMenuImage = itemView.findViewById(R.id.all_menu_image);
        }
    }
}

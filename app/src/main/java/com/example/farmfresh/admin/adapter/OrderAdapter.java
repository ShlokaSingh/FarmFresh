package com.example.farmfresh.admin.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.farmfresh.R;
import com.example.farmfresh.admin.IndividualOrder;
import com.example.farmfresh.user.model.OrderModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    Context context;
    List<OrderModel> orderList;

    public OrderAdapter(Context context, List<OrderModel> orderList){
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.orders_list, parent, false);

        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, final int position) {
        holder.allname.setText(orderList.get(position).getName());
        holder.allamount.setText("₹ "+orderList.get(position).getAmount());
        holder.alladdress.setText(orderList.get(position).getArea());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, IndividualOrder.class);
                i.putExtra("id",orderList.get(position).getOrderid());
                i.putExtra("name",orderList.get(position).getName());
                i.putExtra("room_no",orderList.get(position).getRoom_no());
                i.putExtra("building",orderList.get(position).getBuilding());
                i.putExtra("area",orderList.get(position).getArea());
                i.putExtra("price",orderList.get(position).getAmount());
                i.putExtra("phone",orderList.get(position).getPhone());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView allname, alladdress,allamount;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            allname = itemView.findViewById(R.id.name);
            alladdress = itemView.findViewById(R.id.address);
            allamount = itemView.findViewById(R.id.amount);
        }
    }
}

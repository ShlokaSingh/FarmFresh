package com.example.farmfresh.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.farmfresh.R;
import com.example.farmfresh.admin.adapter.OrderAdapter;
import com.example.farmfresh.user.adapter.CartAdapter;
import com.example.farmfresh.user.model.CartModel;
import com.example.farmfresh.user.model.OrderModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class DashboardFragment extends Fragment {


    RecyclerView orderRecycleView;
    OrderAdapter orderAdapter;
    List<OrderModel> orderList = new ArrayList<>();

    DatabaseReference orderNode = FirebaseDatabase.getInstance().getReference().child("Orders");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        orderRecycleView = view.findViewById(R.id.order_list);

        orderNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderList.clear();
                for (DataSnapshot menuSnapshot: dataSnapshot.getChildren()) {
                    OrderModel order = menuSnapshot.getValue(OrderModel.class);

                    //grandTotal += ((100-Integer.parseInt(product.getDiscount()))*Integer.parseInt(product.getPrice())*Integer.parseInt(product.getQuantity()))/100;
                    orderList.add(order);
                }

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                orderRecycleView.setLayoutManager(layoutManager);
                orderAdapter = new OrderAdapter(getContext(), orderList);
                orderRecycleView.setAdapter(orderAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        orderList.add(new OrderModel("1","Ovesh","C4","Malkani Tower","Jogeshwari(west)","2000","9769675237"));
//        orderList.add(new OrderModel("2","Janhavi","C4","Malkani Tower","Jogeshwari(west)","2000","9769675237"));
//        orderList.add(new OrderModel("3","Shloka","C4","Malkani Tower","Jogeshwari(west)","2000","9769675237"));
//        orderList.add(new OrderModel("4","Girish","C4","Malkani Tower","Jogeshwari(west)","2000","9769675237"));
//        orderList.add(new OrderModel("5","Mohnish","C4","Malkani Tower","Jogeshwari(west)","2000","9769675237"));
//        setOrderRecycler(orderList,view);

        return view;
    }
//    private void setOrderRecycler(List<OrderModel> orderList, View v) {
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false);
//        orderRecycleView.setLayoutManager(layoutManager);
//        orderAdapter = new OrderAdapter(v.getContext(),orderList);
//        orderRecycleView.setAdapter(orderAdapter);
//    }
}
package com.example.farmfresh.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.farmfresh.R;
import com.example.farmfresh.admin.adapter.OrderAdapter;
import com.example.farmfresh.user.adapter.IndividualAdapter;
import com.example.farmfresh.user.model.CartModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IndividualOrder extends AppCompatActivity {

    int grandTotal = 0;
    String name,phone,room_no,building,area,price,id;

    TextView tvname,tvphone,tvroom_no,tvbuilding,tvarea,tvprice;

    Button dispatch;

    DatabaseReference orderNode = FirebaseDatabase.getInstance().getReference().child("Orders");
    RecyclerView individualRecyclerView;
    List<CartModel> orderList = new ArrayList<>();
    IndividualAdapter individualAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_order);

        Intent i = getIntent();
        id = i.getStringExtra("id");
        name = i.getStringExtra("name");
        phone = i.getStringExtra("phone");
        room_no = i.getStringExtra("room_no");
        building = i.getStringExtra("building");
        area = i.getStringExtra("area");
        price = i.getStringExtra("price");

        tvname = findViewById(R.id.name);
        tvphone = findViewById(R.id.phone);
        tvroom_no = findViewById(R.id.room_no);
        tvbuilding = findViewById(R.id.building_name);
        tvarea = findViewById(R.id.area);
        tvprice = findViewById(R.id.total);
        dispatch  = findViewById(R.id.dispatch);
        individualRecyclerView = findViewById(R.id.recyclerView);

        tvarea.setText(area);
        tvname.setText(name);
        tvbuilding.setText(building);
        tvroom_no.setText(room_no);
        tvphone.setText(phone);

        orderNode.child(id).child("itemList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderList.clear();
                for (DataSnapshot menuSnapshot: dataSnapshot.getChildren()) {
                    CartModel order = menuSnapshot.getValue(CartModel.class);

                    grandTotal += ((100-Integer.parseInt(order.getDiscount()))*Integer.parseInt(order.getPrice())*Integer.parseInt(order.getQuantity()))/100;
                    orderList.add(order);
                }

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                individualRecyclerView.setLayoutManager(layoutManager);
                individualAdapter = new IndividualAdapter(getApplicationContext(), orderList);
                individualRecyclerView.setAdapter(individualAdapter);
                tvprice.setText("₹ "+String.valueOf(grandTotal));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
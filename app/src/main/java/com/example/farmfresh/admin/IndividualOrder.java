package com.example.farmfresh.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.farmfresh.R;
import com.example.farmfresh.user.adapter.IndividualAdapter;
import com.example.farmfresh.user.model.CartModel;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class IndividualOrder extends AppCompatActivity {

    String name,phone,room_no,building,area,price,id;

    TextView tvname,tvphone,tvroom_no,tvbuilding,tvarea,tvprice;

    Button dispatch;

    RecyclerView individualRecyclerView;

    IndividualAdapter individualAdapter;
    List<CartModel> cartList;

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

        tvprice.setText(price);
        tvarea.setText(area);
        tvname.setText(name);
        tvbuilding.setText(building);
        tvroom_no.setText(room_no);
        tvphone.setText(phone);

        cartList = new ArrayList<>();
        //cartList.add(new CartModel("Strawberry", "200", "3", "KG", "Image URL"));
        setIndividualRecycler(cartList);
    }

    private void setIndividualRecycler(List<CartModel> cartList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        individualRecyclerView.setLayoutManager(layoutManager);
        individualAdapter = new IndividualAdapter(this,cartList);
        individualRecyclerView.setAdapter(individualAdapter);
    }
}
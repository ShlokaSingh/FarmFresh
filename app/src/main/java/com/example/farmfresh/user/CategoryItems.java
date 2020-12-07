package com.example.farmfresh.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.farmfresh.R;
import com.example.farmfresh.user.adapter.AllFruitsAdapter;
import com.example.farmfresh.user.model.AllFruitsModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryItems extends AppCompatActivity {

    int totalNumberOfProduct;
    String categoryName;
    List<AllFruitsModel> allmenuList;

    RecyclerView fruitRecycleView;
    ImageView btnCart,btnSettings,back;
    AllFruitsAdapter fruitsAdapter;

    DatabaseReference categoryNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_items);

        btnCart = findViewById(R.id.imageView);
        btnSettings = findViewById(R.id.imageView2);
        back = findViewById(R.id.back2);
        fruitRecycleView = findViewById(R.id.fruitRecycler);

        Intent i = getIntent();
        categoryName = i.getStringExtra("id");

        if(categoryNode == null) {
            categoryNode = FirebaseDatabase.getInstance().getReference().child("Category").child(categoryName);
            categoryNode.keepSynced(true);
        }

        categoryNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                allmenuList = new ArrayList<>();
                allmenuList.clear();
                int count = 0;
                for (DataSnapshot menuSnapshot: dataSnapshot.getChildren()) {
                    AllFruitsModel product = menuSnapshot.getValue(AllFruitsModel.class);
                    allmenuList.add(product);
                    count++;
                }
//                totalNumberOfProduct = allmenuList.size();
                setFruitRecycler(allmenuList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CategoryItems.this, AllCategory.class);
                startActivity(i);
                finish();
            }
        });

    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//
////        Intent i = getIntent();
////        categoryName = i.getStringExtra("id");
////
////        if(categoryNode == null) {
////            categoryNode = FirebaseDatabase.getInstance().getReference().child("Category").child(categoryName);
////            categoryNode.keepSynced(true);
////        }
////
////        categoryNode.addValueEventListener(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////
////                allmenuList = new ArrayList<>();
////                allmenuList.clear();
////                int count = 0;
////                for (DataSnapshot menuSnapshot: dataSnapshot.getChildren()) {
////                    AllFruitsModel product = menuSnapshot.getValue(AllFruitsModel.class);
////                    allmenuList.add(product);
////                    count++;
////                }
//////                totalNumberOfProduct = allmenuList.size();
////                setFruitRecycler(allmenuList);
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError databaseError) {
////
////            }
////        });
//    }

    private void setFruitRecycler(List<AllFruitsModel> menuDataList) {
        fruitRecycleView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        fruitRecycleView.setLayoutManager(layoutManager);
        fruitsAdapter = new AllFruitsAdapter(this,menuDataList, categoryName);
        fruitRecycleView.setAdapter(fruitsAdapter);
    }
}


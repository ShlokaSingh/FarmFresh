package com.example.farmfresh.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.farmfresh.R;
import com.example.farmfresh.admin.adapter.AllInventoryAdapter;
import com.example.farmfresh.admin.model.ProductModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class Inventory extends AppCompatActivity {

    int totalNumberOfProduct;
    String categoryName;
    ArrayList<ProductModel> productList = new ArrayList<>();

    TextView category;
    ImageView back;
    FloatingActionButton fabAddMenuItem;
    RecyclerView inventoryRecycler;

    AllInventoryAdapter allInventoryAdapter;

    DatabaseReference categoryNode;
    //FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        category = findViewById(R.id.category);
        back = findViewById(R.id.back2);
        fabAddMenuItem = findViewById(R.id.fabAddMenuItem);
        inventoryRecycler = findViewById(R.id.inventoryRecycler);

        Intent i = getIntent();
        categoryName = i.getStringExtra("id");

        if(categoryNode == null) {
            categoryNode = FirebaseDatabase.getInstance().getReference().child("Category").child(categoryName);
            categoryNode.keepSynced(true);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Inventory.this, ManageFragment.class);
                startActivity(i);
                finish();
            }
        });

//        Intent i = getIntent();
//        id = i.getStringExtra("id");
//        productList = new ArrayList<>();
//        ProductModel product = new ProductModel();
//        //Product ke sare attributes set karna hai
//        productList.add(product);
//        setFruitRecycler(productList);


        fabAddMenuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), AddProductActivity.class);
                intent.putExtra("category", categoryName);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        categoryNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Intent i = getIntent();
                categoryName  = i.getStringExtra("id");

                productList = new ArrayList<>();

                productList.clear();
                int count = 0;
                for (DataSnapshot menuSnapshot: dataSnapshot.getChildren()) {
                    ProductModel product = menuSnapshot.getValue(ProductModel.class);
                    productList.add(product);
                    count++;
                }
                inventoryRecycler.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                inventoryRecycler.setLayoutManager(layoutManager);
                allInventoryAdapter = new AllInventoryAdapter(getApplicationContext(), productList, categoryName);
                inventoryRecycler.setAdapter(allInventoryAdapter);
                totalNumberOfProduct = productList.size();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

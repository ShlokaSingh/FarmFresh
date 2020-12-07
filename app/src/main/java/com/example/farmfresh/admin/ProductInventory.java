package com.example.farmfresh.admin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.farmfresh.R;
import com.example.farmfresh.admin.model.ProductModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ProductInventory extends AppCompatActivity  {
    ImageView ivimage;
    EditText etName, etDescription, etPrice, etQuantity, etAvailability, etDiscount;
    Button update;


    String category;
    String id;
    String name;
    String description;
    String qty;
    String price;
    String availability;
    String discount;
    Uri image;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference productNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_inventory);

        ivimage = findViewById(R.id.ivimage);
        etName = findViewById(R.id.etname);
        etDescription = findViewById(R.id.etdescription);
        etPrice = findViewById(R.id.etprice);
        etQuantity = findViewById(R.id.etqty);
        etAvailability = findViewById(R.id.etavail);
        etDiscount = findViewById(R.id.etdiscount);
        update = findViewById(R.id.update);

        Intent i = getIntent();
        id = i.getStringExtra("id");
        category = i.getStringExtra("category");
        name = i.getStringExtra("name");
        description = i.getStringExtra("desc");
        qty = i.getStringExtra("qty");
        price = i.getStringExtra("price");
        availability = i.getStringExtra("availability");
        discount = i.getStringExtra("discount");
        image = Uri.parse(i.getStringExtra("image"));

        productNode = database.getReference().child("Category").child(category).child(id);
        etName.setText(name);
        etDescription.setText(description);
        etQuantity.setText(qty);
        etPrice.setText(price);
        etAvailability.setText(availability);
        etDiscount.setText(discount);
        Picasso.get().load(image).into(ivimage);

        update.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                ProductModel product = new ProductModel();
                product.setProductId(id);
                product.setProductName(etName.getText().toString());
                product.setDescription(etDescription.getText().toString());
                product.setQty(etQuantity.getText().toString());
                product.setPrice(etPrice.getText().toString());
                product.setAvailability(etAvailability.getText().toString());
                product.setDiscount(etDiscount.getText().toString());
                product.setImageurl(String.valueOf(image));
                productNode.setValue(product);

                Toast.makeText(getApplicationContext(), "Update Successful!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Inventory.class);
                intent.putExtra("id", category);
                startActivity(intent);
                finish();

            }
        });

//        ivimage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

    }


}
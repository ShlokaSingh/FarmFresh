package com.example.farmfresh.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.farmfresh.R;
import com.example.farmfresh.admin.model.ProductModel;
import com.example.farmfresh.user.model.CartModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProductDetails extends AppCompatActivity {

    ImageView img, back ,cart, sub, add;
    TextView tvName, tvDesc, tvPrice, tvQty, tvcount, tvDiscount;

    int count = 0;
    String quantity;
    String category, productid, name, description, qty, price, availability, discount, uid;
    Uri image;

    DatabaseReference cartNode;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        uid = firebaseAuth.getUid();

        Intent i = getIntent();
        productid = i.getStringExtra("id");
        category = i.getStringExtra("category");
        name = i.getStringExtra("name");
        description = i.getStringExtra("desc");
        qty = i.getStringExtra("qty");
        price = i.getStringExtra("price");
        availability = i.getStringExtra("availability");
        discount = i.getStringExtra("discount");
        image = Uri.parse(i.getStringExtra("image"));

        cartNode = FirebaseDatabase.getInstance().getReference().child("User").child(uid).child("Cart").child(productid);

        tvName= findViewById(R.id.tvName);
        tvDesc=findViewById(R.id.tvDesc);
        tvPrice=findViewById(R.id.tvPrice);
        tvQty=findViewById(R.id.tvQty);
        tvDiscount=findViewById(R.id.tvDiscount);
        tvcount=findViewById(R.id.tvcount);
        sub=findViewById(R.id.ivsub);
        add=findViewById(R.id.ivadd);
        img = findViewById(R.id.ivimage);
        cart = findViewById(R.id.ivcart);
        //        back = findViewById(R.id.back2);

        tvName.setText(name);
        tvPrice.setText(price);
        tvDesc.setText(description);
        tvQty.setText(qty);
        tvDiscount.setText(discount);
        Picasso.get().load(image).into(img);

        cartNode.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("quantity").getValue() != null){
                    quantity = dataSnapshot.child("quantity").getValue().toString();
                    count = Integer.parseInt(quantity);
                    if(count == 0){
                        dataSnapshot.getRef().removeValue();
                    }
                    tvcount.setText(quantity);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                tvcount.setText(""+count);
                CartModel cartItem = new CartModel();
                cartItem.setProductName(name);
                cartItem.setPrice(price);
                cartItem.setQuantity(String.valueOf(count));
                cartItem.setDiscount(discount);
                cartItem.setImageUrl(String.valueOf(image));
                cartNode.setValue(cartItem);
                //Toast.makeText(getApplicationContext(), "Product added to cart!!", Toast.LENGTH_SHORT).show();
            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count<=0){
                    count=0;
                }
                else{count--;}
                tvcount.setText(""+count);
                CartModel cartItem = new CartModel();
                cartItem.setProductName(name);
                cartItem.setPrice(price);
                cartItem.setQuantity(String.valueOf(count));
                cartItem.setDiscount(discount);
                cartItem.setImageUrl(String.valueOf(image));
                cartNode.setValue(cartItem);
                //Toast.makeText(getApplicationContext(), "Product removed from cart!!", Toast.LENGTH_SHORT).show();
            }
        });




//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent i = new Intent(ProductDetails.this, CategoryItems.class);
//                i.putExtra("id",id);
//                startActivity(i);
//                finish();
//
//            }
//        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Cart.class);
                startActivity(i);
                finish();
            }
        });

    }
}

package com.example.farmfresh.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farmfresh.R;
import com.example.farmfresh.user.adapter.CartAdapter;
import com.example.farmfresh.user.model.CartModel;
import com.example.farmfresh.user.model.OrderModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Cart extends AppCompatActivity {

    RecyclerView rvCart;
    CartAdapter cartAdapter;

    ImageView back;
    Button buy, pay;
    TextView total;

    int grandTotal = 0;
    String uid;
    List<CartModel> cartList = new ArrayList<>();


    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    DatabaseReference cartNode, orderNode, userNode;

    /////
    String userName, userNumber, OrderId, RoomNo, Building, Area;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText room_no,building,area;
    ImageView cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //Take data from data base and set name price qty unit and imageurl
        uid = firebaseAuth.getUid();
        cartNode = FirebaseDatabase.getInstance().getReference().child("User").child(uid).child("Cart");
        orderNode = FirebaseDatabase.getInstance().getReference().child("Orders");
        userNode = FirebaseDatabase.getInstance().getReference().child("User").child(uid);

        buy = findViewById(R.id.buy);
        back = findViewById(R.id.back2);
        total = findViewById(R.id.total);
        rvCart = findViewById(R.id.rvCart);

        cartList = new ArrayList<>();

        cartNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cartList.clear();
                for (DataSnapshot menuSnapshot: dataSnapshot.getChildren()) {
                    CartModel product = menuSnapshot.getValue(CartModel.class);
                    if(Integer.parseInt(product.getQuantity()) == 0){
                        continue;
                    }
                    grandTotal += ((100-Integer.parseInt(product.getDiscount()))*Integer.parseInt(product.getPrice())*Integer.parseInt(product.getQuantity()))/100;
                    cartList.add(product);
                }
                //setCartRecycler(cartList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                rvCart.setLayoutManager(layoutManager);
                cartAdapter = new CartAdapter(getApplicationContext(), cartList);
                rvCart.setAdapter(cartAdapter);

                total.setText("₹ "+ grandTotal);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        userNode.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userName = dataSnapshot.child("name").getValue().toString();
                userNumber = dataSnapshot.child("phone").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewContactDialog();

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Cart.this, menu.class);
                startActivity(i);
                finish();

            }
        });
    }

    public void createNewContactDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View contactPopupView = getLayoutInflater().inflate(R.layout.popup,null);
        room_no = contactPopupView.findViewById(R.id.room_no);
        building = contactPopupView.findViewById(R.id.building);
        area = contactPopupView.findViewById(R.id.area);
        pay = contactPopupView.findViewById(R.id.confirm);
        cancel = contactPopupView.findViewById(R.id.cancel);

        dialogBuilder.setView(contactPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RoomNo = room_no.getText().toString();
                Building = building.getText().toString();
                Area = area.getText().toString();

//                Add address to userNode
                OrderId = orderNode.push().getKey();
                OrderModel order = new OrderModel();
                order.setOrderid(OrderId);
                order.setName(userName);
                order.setRoom_no(RoomNo);
                order.setPhone(userNumber);
                order.setBuilding(Building);
                order.setArea(Area);
                order.setAmount(String.valueOf(grandTotal));
                order.setItemList(cartList);
                order.setUserId(uid);
                orderNode.child(OrderId).setValue(order);

                cartNode.removeValue();
                Toast.makeText(getApplicationContext(), "Your order has been placed!!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), menu.class);
                startActivity(i);
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Cart.this, menu.class);
                startActivity(i);
                finish();
                dialog.dismiss();
            }
        });
    }
}

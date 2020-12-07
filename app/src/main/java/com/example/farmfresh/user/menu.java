package com.example.farmfresh.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farmfresh.R;
import com.example.farmfresh.admin.model.AllCategoryModel;
import com.example.farmfresh.user.adapter.AllCategoryAdapter;
import com.example.farmfresh.user.adapter.DiscountedProductAdapter;
import com.example.farmfresh.user.adapter.RecentlyViewedAdapter;
import com.example.farmfresh.user.model.DiscountedProducts;
import com.example.farmfresh.user.model.RecentlyViewed;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.farmfresh.R.drawable.b1;
import static com.example.farmfresh.R.drawable.b2;
import static com.example.farmfresh.R.drawable.b3;
import static com.example.farmfresh.R.drawable.b4;
import static com.example.farmfresh.R.drawable.card1;
import static com.example.farmfresh.R.drawable.card2;
import static com.example.farmfresh.R.drawable.card3;
import static com.example.farmfresh.R.drawable.card4;
import static com.example.farmfresh.R.drawable.discountberry;
import static com.example.farmfresh.R.drawable.discountbrocoli;
import static com.example.farmfresh.R.drawable.discountmeat;

public class menu extends AppCompatActivity {

    RecyclerView discountRecyclerView, categoryRecyclerView, recentlyViewedRecycler;
    ImageView btnCart,btnSettings;
    DiscountedProductAdapter discountedProductAdapter;
    List<DiscountedProducts> discountedProductsList;

    AllCategoryAdapter categoryAdapter;
    List<AllCategoryModel> categoryList;

    RecentlyViewedAdapter recentlyViewedAdapter;
    List<RecentlyViewed> recentlyViewedList;

    TextView allCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        discountRecyclerView = findViewById(R.id.fruitRecycler);
        categoryRecyclerView = findViewById(R.id.categoryRecycler);
        allCategory = findViewById(R.id.allCategoryImage);
        recentlyViewedRecycler = findViewById(R.id.recently_item);
        btnCart = findViewById(R.id.imageView);
        btnSettings = findViewById(R.id.imageView2);

        allCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(menu.this, AllCategory.class);
                startActivity(i);
            }
        });

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(menu.this, AllCategory.class);
                startActivity(i);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });

        // adding data to model
        discountedProductsList = new ArrayList<>();
        discountedProductsList.add(new DiscountedProducts(1, discountberry));
        discountedProductsList.add(new DiscountedProducts(2, discountbrocoli));
        discountedProductsList.add(new DiscountedProducts(3, discountmeat));
        discountedProductsList.add(new DiscountedProducts(4, discountberry));
        discountedProductsList.add(new DiscountedProducts(5, discountbrocoli));
        discountedProductsList.add(new DiscountedProducts(6, discountmeat));

        // adding data to model
        categoryList = new ArrayList<>();
        categoryList.add(new AllCategoryModel("fruit", R.drawable.ic_fruits));
        categoryList.add(new AllCategoryModel("veg", R.drawable.ic_veggies));
        categoryList.add(new AllCategoryModel("pulse", R.drawable.ic_pulses));
        categoryList.add(new AllCategoryModel("fish", R.drawable.ic_fish));
        categoryList.add(new AllCategoryModel("spice", R.drawable.ic_spices));
        categoryList.add(new AllCategoryModel("egg", R.drawable.ic_egg));
        categoryList.add(new AllCategoryModel("cookie", R.drawable.ic_cookies));
        categoryList.add(new AllCategoryModel("juice", R.drawable.ic_juce));

        // adding data to model
        recentlyViewedList = new ArrayList<>();
        recentlyViewedList.add(new RecentlyViewed("Watermelon", "Watermelon has high water content and also provides some fiber.", "₹ 80", "1", "KG", card4, b4));
        recentlyViewedList.add(new RecentlyViewed("Papaya", "Papayas are spherical or pear-shaped fruits that can be as long as 20 inches.", "₹ 85", "1", "KG", card3, b3));
        recentlyViewedList.add(new RecentlyViewed("Strawberry", "The strawberry is a highly nutritious fruit, loaded with vitamin C.", "₹ 30", "1", "KG", card2, b1));
        recentlyViewedList.add(new RecentlyViewed("Kiwi", "Full of nutrients like vitamin C, vitamin K, vitamin E, folate, and potassium.", "₹ 30", "1", "PC", card1, b2));

        setDiscountedRecycler(discountedProductsList);
        setCategoryRecycler(categoryList);
        setRecentlyViewedRecycler(recentlyViewedList);

    }

    private void setDiscountedRecycler(List<DiscountedProducts> dataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        discountRecyclerView.setLayoutManager(layoutManager);
        discountedProductAdapter = new DiscountedProductAdapter(this,dataList);
        discountRecyclerView.setAdapter(discountedProductAdapter);
    }


    private void setCategoryRecycler(List<AllCategoryModel> categoryDataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryAdapter = new AllCategoryAdapter(this,categoryDataList);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    private void setRecentlyViewedRecycler(List<RecentlyViewed> recentlyViewedDataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recentlyViewedRecycler.setLayoutManager(layoutManager);
        recentlyViewedAdapter = new RecentlyViewedAdapter(this,recentlyViewedDataList);
        recentlyViewedRecycler.setAdapter(recentlyViewedAdapter);
    }

    private void showMenu(View v){
        PopupMenu popupMenu = new PopupMenu(menu.this,v);
        popupMenu.getMenuInflater().inflate(R.menu.popupmenu,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.logout){
                    Toast.makeText(menu.this,"Logging out", Toast.LENGTH_SHORT).show();
                }else if(item.getItemId() == R.id.track_order){
                    Toast.makeText(menu.this,"Tracking Order", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        popupMenu.show();
    }
    //Now again we need to create a adapter and model class for recently viewed items.
    // lets do it fast.))
}

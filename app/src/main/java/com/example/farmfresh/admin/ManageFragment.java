package com.example.farmfresh.admin;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.farmfresh.R;
import com.example.farmfresh.admin.adapter.ManageAdapter;
import com.example.farmfresh.admin.model.AllCategoryModel;
import com.example.farmfresh.user.AllCategory;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ManageFragment extends Fragment {
    RecyclerView AllCategoryRecycler;
    ManageAdapter allCategoryAdapter;
    List<AllCategoryModel> allCategoryModelList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage, container, false);
        AllCategoryRecycler = view.findViewById(R.id.all_category);
        // Inflate the layout for this fragment

        // adding data to model
        allCategoryModelList = new ArrayList<>();
        allCategoryModelList.add(new AllCategoryModel("fruit", R.drawable.ic_fru));
        allCategoryModelList.add(new AllCategoryModel("veg", R.drawable.veg));
        allCategoryModelList.add(new AllCategoryModel("pulse", R.drawable.ic_pulses));
        allCategoryModelList.add(new AllCategoryModel("fish", R.drawable.ic_fis));
        allCategoryModelList.add(new AllCategoryModel("spice", R.drawable.ic_spis));
        allCategoryModelList.add(new AllCategoryModel("egg", R.drawable.egg));
        allCategoryModelList.add(new AllCategoryModel("cookie", R.drawable.ic_cook));
        allCategoryModelList.add(new AllCategoryModel("juice", R.drawable.ic_jui));


        setCategoryRecycler(allCategoryModelList,view);
        return view;
    }
    private void setCategoryRecycler(List<AllCategoryModel> allcategoryModelList,View view) {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view.getContext(), 4);
        AllCategoryRecycler.setLayoutManager(layoutManager);
        AllCategoryRecycler.addItemDecoration(new AllCategory.GridSpacingItemDecoration(4, dpToPx(16), true));
        AllCategoryRecycler.setItemAnimator(new DefaultItemAnimator());
        allCategoryAdapter = new ManageAdapter(view.getContext(),allcategoryModelList);
        AllCategoryRecycler.setAdapter(allCategoryAdapter);
    }

    // now we need some item decoration class for manage spacing

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
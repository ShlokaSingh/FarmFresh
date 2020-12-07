package com.example.farmfresh.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.farmfresh.R;
import com.example.farmfresh.admin.adapter.OrderAdapter;
import com.example.farmfresh.user.model.OrderModel;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class DashboardFragment extends Fragment {


    RecyclerView orderRecycleView;
    OrderAdapter orderAdapter;
    List<OrderModel> orderList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        orderRecycleView = view.findViewById(R.id.order_list);

//        orderList = new ArrayList<>();
//        orderList.add(new OrderModel("1","Ovesh","C4","Malkani Tower","Jogeshwari(west)","2000","9769675237"));
//        orderList.add(new OrderModel("2","Janhavi","C4","Malkani Tower","Jogeshwari(west)","2000","9769675237"));
//        orderList.add(new OrderModel("3","Shloka","C4","Malkani Tower","Jogeshwari(west)","2000","9769675237"));
//        orderList.add(new OrderModel("4","Girish","C4","Malkani Tower","Jogeshwari(west)","2000","9769675237"));
//        orderList.add(new OrderModel("5","Mohnish","C4","Malkani Tower","Jogeshwari(west)","2000","9769675237"));
        setOrderRecycler(orderList,view);

        return view;
    }
    private void setOrderRecycler(List<OrderModel> orderList, View v) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false);
        orderRecycleView.setLayoutManager(layoutManager);
        orderAdapter = new OrderAdapter(v.getContext(),orderList);
        orderRecycleView.setAdapter(orderAdapter);
    }
}
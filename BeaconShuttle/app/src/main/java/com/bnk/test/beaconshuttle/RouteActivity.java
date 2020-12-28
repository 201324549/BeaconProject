package com.bnk.test.beaconshuttle;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bnk.test.beaconshuttle.model.DataRoute;

public class RouteActivity extends AppCompatActivity {

    RecyclerViewAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        init();
        getData();
    }

    private void init() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void getData(){
        DataRoute data = new DataRoute();
        data.setRot_nm("제1노선");
        data.setRot_id(1);
        adapter.addItem(data);
        data.setRot_nm("제2노선");
        data.setRot_id(2);
        adapter.addItem(data);
        data.setRot_nm("제3노선");
        data.setRot_id(3);
        adapter.addItem(data);
    }
}

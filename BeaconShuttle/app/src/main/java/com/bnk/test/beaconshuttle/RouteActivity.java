package com.bnk.test.beaconshuttle;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bnk.test.beaconshuttle.model.DataRoute;
import com.bnk.test.beaconshuttle.model.DataStop;
import com.bnk.test.beaconshuttle.util.DataHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RouteActivity extends AppCompatActivity {

    private DataHelper mDataHelper;
    private SQLiteDatabase db;
    private Cursor cur;
    private String[] routelist = {"ROT_ID", "ROT_NM"};
    private HashMap<Integer, String> rotMap = new HashMap<>();
    private String[] stoplist = {"ROT_ID","STOP_NM", "STAR_TIME", "WORK"};
    private ArrayList<DataStop> stoparr = new ArrayList<>();
    RecyclerViewAdapter adapter;
    private String checkGoLeave;
    private ImageView go;
    private ImageView leave;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        checkGoLeave = new String("출근");
        go = (ImageView) findViewById(R.id.go);
        leave = (ImageView) findViewById(R.id.leave);
        initDataBase();
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
        for(Map.Entry<Integer, String> entry : rotMap.entrySet()){
            DataRoute data = new DataRoute();
            data.setRot_id(entry.getKey());
            data.setRot_nm(entry.getValue());
            int rotId = data.getRot_id();
            for(DataStop ds : stoparr){
                if(ds.getRot_id() == rotId && ds.getWork().equals(checkGoLeave)) {
                    data.getListStop().add(ds);
                }
            }
            adapter.notifyDataSetChanged();
            adapter.addItem(data);
        }

    }

    private void initDataBase() {
        mDataHelper = new DataHelper(this);
        createDataBase();
        db = mDataHelper.getReadableDatabase();
        cur = db.query("BO_STBM_ROT", routelist, null, null, null, null, null);

        if (cur != null) {
            int rotIdCol = cur.getColumnIndex("ROT_ID");
            int rotNmCol = cur.getColumnIndex("ROT_NM");
            while(cur.moveToNext()) {
                int rotId = cur.getInt(rotIdCol);
                String rotNm = cur.getString(rotNmCol);
                Log.d("test", rotId + "  " + rotNm + " test");
                rotMap.put(rotId, rotNm);
            }
            cur.close();
        } else {
            Log.d("test", "cur is null");
        }

        cur = db.query("BO_STBM_STOP", stoplist, null, null, null, null, "STAR_TIME");

        if (cur != null) {
            int stopNmCol = cur.getColumnIndex("STOP_NM");
            int timeCol = cur.getColumnIndex("STAR_TIME");
            int workCol = cur.getColumnIndex("WORK");
            int rotIdCol = cur.getColumnIndex("ROT_ID");
            while(cur.moveToNext()) {
                int rotId = cur.getInt(rotIdCol);
                String stopNm = cur.getString(stopNmCol);
                String starTime = cur.getString(timeCol);
                String work = cur.getString(workCol);
                DataStop ds = new DataStop();
                ds.setRot_id(rotId);
                ds.setStop_nm(stopNm);
                ds.setStar_time(starTime);
                ds.setWork(work);
                stoparr.add(ds);
            }
            cur.close();
        } else {
            Log.d("test", "cur is null");
        }

    }

    private void createDataBase() {
        try {
            mDataHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void goClick(View v){
        go.setVisibility(View.GONE);
        leave.setVisibility(View.VISIBLE);
        checkGoLeave = new String("출근");
        getData();
    }


    public void leaveClick(View v){
        leave.setVisibility(View.GONE);
        go.setVisibility(View.VISIBLE);
        checkGoLeave = new String("퇴근");
        getData();
    }

    public void goBack(View v){
        finish();
    }
}

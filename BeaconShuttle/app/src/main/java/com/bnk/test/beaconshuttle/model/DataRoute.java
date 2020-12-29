package com.bnk.test.beaconshuttle.model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

public class DataRoute {
    private int rot_id;
    private String rot_nm;
    private ArrayList<DataStop> listStop;
    private Context ctx;
    private LayoutInflater inflater;
    private View v;
    private Activity activity;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public DataRoute(){
        this.listStop = new ArrayList<>();
    }

    public void setCtx(Context ctx){
        this.ctx = ctx;
    }
    public void setV(View v){
        this.v = v;
    }

    public View getV(){
        return this.v;
    }

    public void setInflater(LayoutInflater inflater){
        this.inflater = inflater;
    }

    public LayoutInflater getInflater(){
        return inflater;
    }

    public Context getCtx(){
        return this.ctx;
    }

    public void setRot_id(int rot_id) {
        this.rot_id = rot_id;
    }

    public void setListStop(ArrayList<DataStop> listStop) {
        this.listStop = listStop;
    }

    public void setRot_nm(String rot_nm) {
        this.rot_nm = rot_nm;
    }

    public int getRot_id() {
        return rot_id;
    }

    public ArrayList<DataStop> getListStop() {
        return listStop;
    }

    public String getRot_nm() {
        return rot_nm;
    }
}

package com.bnk.test.beaconshuttle.model;

import java.util.ArrayList;

public class DataRoute {
    private int rot_id;
    private String rot_nm;
    private ArrayList<DataStop> listStop;

    public DataRoute(){
        this.listStop = new ArrayList<>();
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

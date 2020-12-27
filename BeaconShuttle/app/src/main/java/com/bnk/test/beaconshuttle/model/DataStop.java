package com.bnk.test.beaconshuttle.model;

public class DataStop {
    private String stop_nm;
    private int rot_id;
    private String img_id;
    private String star_time;
    private String work;
    private String rmrk_cntn;

    public DataStop() {

    }

    public int getRot_id() {
        return rot_id;
    }

    public String getImg_id() {
        return img_id;
    }

    public String getRmrk_cntn() {
        return rmrk_cntn;
    }

    public String getStar_time() {
        return star_time;
    }

    public String getStop_nm() {
        return stop_nm;
    }

    public String getWork() {
        return work;
    }

    public void setImg_id(String img_id) {
        this.img_id = img_id;
    }

    public void setRmrk_cntn(String rmrk_cntn) {
        this.rmrk_cntn = rmrk_cntn;
    }

    public void setRot_id(int rot_id) {
        this.rot_id = rot_id;
    }

    public void setStar_time(String star_time) {
        this.star_time = star_time;
    }

    public void setStop_nm(String stop_nm) {
        this.stop_nm = stop_nm;
    }

    public void setWork(String work) {
        this.work = work;
    }
}

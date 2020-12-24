package com.bnk.test.beaconshuttle.model;

/**
 * Onebiz User
 */
public class User {
    private String id;
    private String cmgrp_id;
    private String cmgrp_cd;
    private String user_id;
    private String user_nm;
    private String hr_hrpm_dept_id;
    private String dpnm;
    private String empe_psit_cd;
    private String empe_psit_nm;
    private String comp_emladr;
    private String push_token;
    private String etco_dt;
    private String secretKey;

    private int pwErrCnt;
    private String pwInitYn;
    private String acntLockedYn;
    private String pwExpiredYn;
    private String use_yn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCmgrp_id() {
        return cmgrp_id;
    }

    public void setCmgrp_id(String cmgrp_id) {
        this.cmgrp_id = cmgrp_id;
    }

    public String getCmgrp_cd() {
        return cmgrp_cd;
    }

    public void setCmgrp_cd(String cmgrp_cd) {
        this.cmgrp_cd = cmgrp_cd;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_nm() {
        return user_nm;
    }

    public void setUser_nm(String user_nm) {
        this.user_nm = user_nm;
    }

    public String getHr_hrpm_dept_id() {
        return hr_hrpm_dept_id;
    }

    public void setHr_hrpm_dept_id(String hr_hrpm_dept_id) {
        this.hr_hrpm_dept_id = hr_hrpm_dept_id;
    }

    public String getDpnm() {
        return dpnm;
    }

    public void setDpnm(String dpnm) {
        this.dpnm = dpnm;
    }

    public String getEmpe_psit_cd() {
        return empe_psit_cd;
    }

    public void setEmpe_psit_cd(String empe_psit_cd) {
        this.empe_psit_cd = empe_psit_cd;
    }

    public String getEmpe_psit_nm() {
        return empe_psit_nm;
    }

    public void setEmpe_psit_nm(String empe_psit_nm) {
        this.empe_psit_nm = empe_psit_nm;
    }

    public String getComp_emladr() {
        return comp_emladr;
    }

    public void setComp_emladr(String comp_emladr) {
        this.comp_emladr = comp_emladr;
    }

    public String getPush_token() {
        return push_token;
    }

    public void setPush_token(String push_token) {
        this.push_token = push_token;
    }

    public String getEtco_dt() {
        return etco_dt;
    }

    public void setEtco_dt(String etco_dt) {
        this.etco_dt = etco_dt;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public int getPwErrCnt() {
        return pwErrCnt;
    }

    public void setPwErrCnt(int pwErrCnt) {
        this.pwErrCnt = pwErrCnt;
    }

    public String getPwInitYn() {
        return pwInitYn;
    }

    public void setPwInitYn(String pwInitYn) {
        this.pwInitYn = pwInitYn;
    }

    public String getAcntLockedYn() {
        return acntLockedYn;
    }

    public void setAcntLockedYn(String acntLockedYn) {
        this.acntLockedYn = acntLockedYn;
    }

    public String getPwExpiredYn() {
        return pwExpiredYn;
    }

    public void setPwExpiredYn(String pwExpiredYn) {
        this.pwExpiredYn = pwExpiredYn;
    }

    public String getUse_yn() {
        return use_yn;
    }

    public void setUse_yn(String use_yn) {
        this.use_yn = use_yn;
    }
}

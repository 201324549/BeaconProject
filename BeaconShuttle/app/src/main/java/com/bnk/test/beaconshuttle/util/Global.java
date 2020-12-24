package com.bnk.test.beaconshuttle.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.minew.beaconset.MinewBeacon;

import java.util.HashMap;
import java.util.Map;

public class Global {

    public static final String SERVICE_CLOSE = "SERVICE_CLOSE";
    public static final String SERVICE_SETTINGS = "SERVICE_SETTINGS";
    public static final int NOTI_ID_SERVICE_IS_RUNNING = 1;
    public static final int NOTI_ID_START_BLUETOOTH = 2;
    public static final String UNKNOWN = " ";
}
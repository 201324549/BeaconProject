package com.bnk.test.beaconshuttle.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BeaconServiceSettings implements Parcelable {

    private long searchPeriod = 30000L;

    protected BeaconServiceSettings(Parcel in) {
        searchPeriod = in.readLong();
    }

    public static final Creator<BeaconServiceSettings> CREATOR = new Creator<BeaconServiceSettings>() {
        @Override
        public BeaconServiceSettings createFromParcel(Parcel in) {
            return new BeaconServiceSettings(in);
        }

        @Override
        public BeaconServiceSettings[] newArray(int size) {
            return new BeaconServiceSettings[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(searchPeriod);
    }

    public long getSearchPeriod() {
        return searchPeriod;
    }

    public void setSearchPeriod(long searchPeriod) {
        this.searchPeriod = searchPeriod;
    }

    public BeaconServiceSettings(long serachPeriod) {
        this.searchPeriod = searchPeriod;
    }
}

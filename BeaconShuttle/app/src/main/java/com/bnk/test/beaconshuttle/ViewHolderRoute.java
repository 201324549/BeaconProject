package com.bnk.test.beaconshuttle;

import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bnk.test.beaconshuttle.model.DataRoute;
import com.bnk.test.beaconshuttle.model.DataStop;
import com.bnk.test.beaconshuttle.util.DataHelper;
import com.bnk.test.beaconshuttle.util.MyApp;

public class ViewHolderRoute extends RecyclerView.ViewHolder {
    TextView rot_nm;
    LinearLayout linearlayout;
    LinearLayout stoplist;
    OnViewHolderItemClickListener onViewHolderItemClickListener;
    public ViewHolderRoute(@NonNull View itemView) {
        super(itemView);

        rot_nm = itemView.findViewById(R.id.rot_nm);
        linearlayout = itemView.findViewById(R.id.linearlayout);
        stoplist = itemView.findViewById(R.id.stoplist);
        linearlayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                onViewHolderItemClickListener.onViewHolderItemClick();
            }
        });

    }

    public void onBind(DataRoute data, int position, SparseBooleanArray selectedItems) {
        rot_nm.setText(data.getRot_nm());
        stoplist.removeAllViews();
        for(DataStop ds : data.getListStop()){
            TextView view1 = new TextView(MyApp.getAppContext());
            view1.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                    alertDialogBuilder.setTitle(ds.getStop_nm()).setMessage("이미지 입니다").setCancelable(false).setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            });
            view1.setText(ds.getStop_nm());
            TextView view2 = new TextView(MyApp.getAppContext());
            view2.setText(ds.getStar_time());
            stoplist.addView(view1);
            stoplist.addView(view2);


        }
        changeVisibility(selectedItems.get(position));
    }

    private void changeVisibility(final boolean isExpanded){
        ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, 600) : ValueAnimator.ofInt(600,0);
        va.setDuration(500);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                stoplist.requestLayout();
                stoplist.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            }
        });
        va.start();
    }

    public void setOnViewHolderItemClickListener(OnViewHolderItemClickListener onViewHolderItemClickListener) {
        this.onViewHolderItemClickListener = onViewHolderItemClickListener;
    }

}

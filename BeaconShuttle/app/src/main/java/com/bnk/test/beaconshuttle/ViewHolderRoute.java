package com.bnk.test.beaconshuttle;

import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bnk.test.beaconshuttle.model.DataRoute;
import com.bnk.test.beaconshuttle.model.DataStop;
import com.bnk.test.beaconshuttle.util.MyApp;


public class ViewHolderRoute extends RecyclerView.ViewHolder {
    TextView rot_nm;
    LinearLayout linearlayout;
    LinearLayout stoplist;
    OnViewHolderItemClickListener onViewHolderItemClickListener;
    private TextView rottitle;
    private TextView stoptitle;
    private ImageView stopimg;
    private TextView stopclose;
    public ViewHolderRoute(@NonNull View itemView) {
        super(itemView);
        rot_nm = itemView.findViewById(R.id.rot_nm);
        linearlayout = itemView.findViewById(R.id.linearlayout);
        stoplist = itemView.findViewById(R.id.stoplist);
        rottitle = itemView.findViewById(R.id.rottitle);
        stoptitle = itemView.findViewById(R.id.stoptitle);
        stopimg = itemView.findViewById(R.id.stopimg);
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
                    LayoutInflater inflater = data.getInflater();
                    View layout = inflater.inflate(R.layout.popup_stop, null);
                    Log.d("test", layout.toString() + "            test");
                    rottitle = (TextView)layout.findViewById(R.id.rottitle);
//                    layout.findViewById()
                    stoptitle = (TextView)layout.findViewById(R.id.stoptitle);
                    stopimg = (ImageView)layout.findViewById(R.id.stopimg);
                    stopclose = (TextView)layout.findViewById(R.id.closestop);
                    rottitle.setText(data.getRot_nm());
                    stoptitle.setText(ds.getStop_nm());
                    String tmpSign = "stop" + ds.getImg_id();
                    stopimg.setImageResource(data.getActivity().getResources().getIdentifier(tmpSign, "drawable", data.getActivity().getPackageName()));
                    AlertDialog.Builder builder = new AlertDialog.Builder(data.getCtx());
                    builder.setView(layout);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    stopclose.setOnClickListener(new View.OnClickListener(){

                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
            });
            view1.setText(ds.getStop_nm());
            view1.setTextSize(12);
            view1.setTypeface(null, Typeface.BOLD);
            view1.setPadding(200,25,200,0);
            view1.setTextColor(Color.BLACK);
            TextView view2 = new TextView(MyApp.getAppContext());
            view2.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    LayoutInflater inflater = data.getInflater();
                    View layout = inflater.inflate(R.layout.popup_stop, null);
                    Log.d("test", layout.toString() + "            test");
                    rottitle = (TextView)layout.findViewById(R.id.rottitle);
//                    layout.findViewById()
                    stoptitle = (TextView)layout.findViewById(R.id.stoptitle);
                    stopimg = (ImageView)layout.findViewById(R.id.stopimg);
                    stopclose = (TextView)layout.findViewById(R.id.closestop);
                    rottitle.setText(data.getRot_nm());
                    stoptitle.setText(ds.getStop_nm());
                    String tmpSign = "stop" + ds.getImg_id();
                    stopimg.setImageResource(data.getActivity().getResources().getIdentifier(tmpSign, "drawable", data.getActivity().getPackageName()));
                    AlertDialog.Builder builder = new AlertDialog.Builder(data.getCtx());
                    builder.setView(layout);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    stopclose.setOnClickListener(new View.OnClickListener(){

                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
            });
            view2.setText(ds.getStar_time());
            view2.setTextSize(10);
            view2.setPadding(200,0,200,13);
            View view3 = new View(MyApp.getAppContext());
            LinearLayout.LayoutParams pp =  new LinearLayout.LayoutParams(600,1);
            view3.setLayoutParams(pp);
            view3.setBackgroundColor(0xFFDDDDDD);
            pp.setMargins(200,0,200,25);
            stoplist.addView(view1);
            stoplist.addView(view2);
            stoplist.addView(view3);

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

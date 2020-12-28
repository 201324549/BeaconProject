package com.bnk.test.beaconshuttle;

import android.animation.ValueAnimator;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bnk.test.beaconshuttle.model.DataRoute;

public class ViewHolderRoute extends RecyclerView.ViewHolder {
    TextView rot_nm;
    TextView testtext;
    LinearLayout linearlayout;

    OnViewHolderItemClickListener onViewHolderItemClickListener;

    public ViewHolderRoute(@NonNull View itemView) {
        super(itemView);

        rot_nm = itemView.findViewById(R.id.rot_nm);
        testtext = itemView.findViewById(R.id.testtext);
        linearlayout = itemView.findViewById(R.id.linearlayout);

        linearlayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                onViewHolderItemClickListener.onViewHolderItemClick();
            }
        });

    }

    public void onBind(DataRoute data, int position, SparseBooleanArray selectedItems) {
        rot_nm.setText(data.getRot_nm());
        testtext.setText(data.getRot_id()+"");
        changeVisibility(selectedItems.get(position));
    }

    private void changeVisibility(final boolean isExpanded){
        ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, 200) : ValueAnimator.ofInt(200,0);
        va.setDuration(500);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                testtext.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            }
        });
        va.start();
    }

    public void setOnViewHolderItemClickListener(OnViewHolderItemClickListener onViewHolderItemClickListener) {
        this.onViewHolderItemClickListener = onViewHolderItemClickListener;
    }
}

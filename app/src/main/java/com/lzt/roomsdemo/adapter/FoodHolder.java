package com.lzt.roomsdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lzt.roomsdemo.R;


/**
 * Created by liangzhongtai on 2017/9/27.
 */

public class FoodHolder extends RecyclerView.ViewHolder{
    public TextView fid;
    public TextView name;
    public TextView animal;
    public TextView aid;
    public FoodHolder(View itemView) {
        super(itemView);
        fid = (TextView) itemView.findViewById(R.id.tv_fid);
        name = (TextView) itemView.findViewById(R.id.tv_name);
        animal = (TextView) itemView.findViewById(R.id.tv_animal_title);
        aid   = (TextView) itemView.findViewById(R.id.tv_aid_title);
    }
}

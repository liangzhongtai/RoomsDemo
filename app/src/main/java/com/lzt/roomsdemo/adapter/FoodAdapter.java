package com.lzt.roomsdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.lzt.roomsdemo.R;
import com.lzt.roomsdemo.entity.Animal;
import com.lzt.roomsdemo.entity.Food;

import java.util.List;


/**
 * Created by liangzhongtai on 2017/9/27.
 */

public class FoodAdapter extends RecyclerView.Adapter{
    public List<Food> foods;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        FoodHolder holder = new FoodHolder(View.inflate(viewGroup.getContext(), R.layout.item_food,null));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        FoodHolder holder = (FoodHolder) viewHolder;
        Food food = foods.get(position);
        holder.fid.setText(food.fId+"");
        holder.name.setText(food.name);
        holder.animal.setText(food.animal);
        holder.aid.setText(food.aid+"");
    }

    @Override
    public int getItemCount() {
        return foods==null?0:foods.size();
    }
}

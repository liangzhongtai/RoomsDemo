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

public class AnimalAdapter extends RecyclerView.Adapter{
    public List<Animal> animals;
    public List<Food> foods;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        AnimalHolder holder = new AnimalHolder(View.inflate(viewGroup.getContext(), R.layout.item_animal,null));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        AnimalHolder holder = (AnimalHolder) viewHolder;
        Animal animal = animals.get(position);
        holder.uid.setText(animal.aid+"");
        holder.sname.setText(animal.scientificName);
        holder.place.setText(animal.place);
        holder.country.setText(animal.country.cname);
        String food ="";
        int count = 0;
        for (int i=0,len = foods==null?0:foods.size();i<len;i++){
            if(foods.get(i).aid==animal.aid){
                food += foods.get(i).name+",";
                count++;
            }
        }
        holder.food.setText(count>1?food.substring(0,food.length()-1):food);
    }

    @Override
    public int getItemCount() {
        return animals==null?0:animals.size();
    }
}

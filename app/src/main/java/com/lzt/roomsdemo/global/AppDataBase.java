package com.lzt.roomsdemo.global;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.lzt.roomsdemo.dao.AnimalDao;
import com.lzt.roomsdemo.dao.FoodDao;
import com.lzt.roomsdemo.entity.Animal;
import com.lzt.roomsdemo.entity.Food;


/**
 * Created by liangzhongtai on 2017/9/11.
 */
@Database(entities = {Animal.class, Food.class},version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract AnimalDao animalDao();
    public abstract FoodDao foodDao();
}

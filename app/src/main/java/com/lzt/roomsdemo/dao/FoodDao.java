package com.lzt.roomsdemo.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.lzt.roomsdemo.entity.Animal;
import com.lzt.roomsdemo.entity.Food;
import com.lzt.roomsdemo.entity.Pojo;


/**
 * Created by liangzhongtai on 2017/9/11.
 */
@Dao
public interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFood(Food... food);

    @Delete
    int deleteFood(Food... food);

    @Update
    int updateFood(Food... food);

    @Query("SELECT * FROM food WHERE fId = :fId")
    Food[] queryFId(int fId);

    @Query("SELECT * FROM food WHERE name LIKE :name")
    Food[] queryName(String name);

    @Query("SELECT * FROM food WHERE animal LIKE :animal")
    Food[] queryAnimal(String animal);

    @Query("SELECT * FROM food WHERE aid = :aid")
    Food[] queryAid(int aid);

    @Query("SELECT * FROM food")
    Food[] query();



}

package com.lzt.roomsdemo.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.lzt.roomsdemo.entity.Animal;
import com.lzt.roomsdemo.entity.Pojo;


/**
 * Created by liangzhongtai on 2017/9/11.
 */
@Dao
public interface AnimalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAnimal(Animal... animal);

    @Delete
    int deleteAnimal(Animal... animal);

    @Update
    int updateAnimal(Animal... animal);

    @Query("SELECT * FROM animal")
    Animal[] query();

    @Query("SELECT * FROM animal WHERE aid = :aid")
    Animal[] queryUid(int aid);

    @Query("SELECT * FROM animal WHERE sname LIKE :sname")
    Animal[] queryName(String sname);

    @Query("SELECT * FROM animal WHERE place LIKE :place")
    Animal[] queryPlace(String place);

    @Query("SELECT * FROM animal WHERE lifeTime BETWEEN :start AND :end")
    Animal[] queryLifeTime(int start,int end);

    @Query("SELECT * FROM animal WHERE viviparity = :vivi")
    Animal[] query(boolean vivi);

    @Query("SELECT * FROM animal WHERE animal.cid = :cid")
    Animal[] queryCid(int cid);

    @Query("SELECt aid,place FROM animal")
    Pojo[] queryPojo();
}

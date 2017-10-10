package com.lzt.roomsdemo.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by liangzhongtai on 2017/9/11.
 * @Entity(foreignKeys = @ForeignKey(entity = Animal.class,parentColumns = "uid",childColumns = "aid",onDelete = CASCADE))
 * 该属性表示实体Animal中包含有Food
 * onDelete = CASCADE 时,在删除Animal时，会同时删除Animal所属的Food
 */
@Entity(tableName = "food" ,foreignKeys = @ForeignKey(entity = Animal.class,parentColumns = "aid",childColumns = "aid",onDelete = CASCADE))
public class Food {
    @PrimaryKey
    public int fId;

    public String name;

    public String animal;

    public int aid;

}

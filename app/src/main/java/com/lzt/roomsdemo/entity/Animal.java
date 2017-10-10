package com.lzt.roomsdemo.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by liangzhongtai on 2017/9/11.
 * @Entity(tableName = "animal")       设置表名，默认为类名
 * @Entity(ColumnInfo(name = "sname")) 设置列名,默认为成员属性名
 */

@Entity(tableName = "animal")
public class Animal{
    @PrimaryKey/*(autoGenerate = true)*/
    public int aid;

    @ColumnInfo(name = "sname")
    public String scientificName;

    public String place;

    public int lifeTime;

    public boolean viviparity;

    @Ignore
    public String icon;

    @Embedded/*(prefix = "country")*/
    public Country country;
}


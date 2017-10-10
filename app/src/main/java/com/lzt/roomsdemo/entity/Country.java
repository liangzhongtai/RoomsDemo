package com.lzt.roomsdemo.entity;


import android.arch.persistence.room.ColumnInfo;

/**
 * Created by liangzhongtai on 2017/9/11.
 */
public class Country {
    @ColumnInfo(name = "cid")
    public int cid;

    public String cname;
}

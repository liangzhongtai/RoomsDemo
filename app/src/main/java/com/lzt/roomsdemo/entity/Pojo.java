package com.lzt.roomsdemo.entity;

import android.arch.persistence.room.ColumnInfo;

/**
 * Created by liangzhongtai on 2017/9/11.
 */

public class Pojo {
    @ColumnInfo(name = "aid")
    public int aid;
    @ColumnInfo(name = "place")
    public String descPlace;
}

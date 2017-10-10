package com.lzt.roomsdemo;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lzt.roomsdemo.adapter.AnimalAdapter;
import com.lzt.roomsdemo.adapter.FoodAdapter;
import com.lzt.roomsdemo.dao.AnimalDao;
import com.lzt.roomsdemo.dao.FoodDao;
import com.lzt.roomsdemo.entity.Animal;
import com.lzt.roomsdemo.entity.Country;
import com.lzt.roomsdemo.entity.Food;
import com.lzt.roomsdemo.global.AppDataBase;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Arrays;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liangzhongtai on 2017/10/9.
 */

public class FoodActivity extends AppCompatActivity{
    private RecyclerView rv;
    private EditText idET;
    private EditText nameET;
    private EditText animalET;

    private TextView insertTV;
    private TextView deleteTV;
    private TextView queryTV;
    private TextView updateTV;

    private FoodAdapter adapter;
    private FoodDao dao;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        rv          = (RecyclerView) findViewById(R.id.rv);
        idET        = (EditText) findViewById(R.id.et_id);
        nameET      = (EditText) findViewById(R.id.et_name);
        animalET     = (EditText) findViewById(R.id.et_animal);

        insertTV    = (TextView) findViewById(R.id.tv_insert);
        deleteTV    = (TextView) findViewById(R.id.tv_delete);
        queryTV     = (TextView) findViewById(R.id.tv_query);
        updateTV    = (TextView) findViewById(R.id.tv_update);
    }

    private void initListener() {

        insertTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Food food = formatFood(new Food());
                Flowable.create(new FlowableOnSubscribe<List>(){
                    @Override
                    public void subscribe(FlowableEmitter<List> e) throws Exception {
                        dao.insertFood(food);
                        List list = Arrays.asList(dao.query());
                        e.onNext(list);
                        e.onComplete();
                    }
                }, BackpressureStrategy.BUFFER)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                        //subscription = s;
                    }
                    @Override
                    public void onNext(List list) {
                        adapter.foods = list;
                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onError(Throwable t) {
                    }
                    @Override
                    public void onComplete() {}
                });
            }
        });
        deleteTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Flowable.create(new FlowableOnSubscribe<List>(){
                    @Override
                    public void subscribe(FlowableEmitter<List> e) throws Exception {
                        Food[] foods = null;
                        if(!TextUtils.isEmpty(idET.getText().toString().trim())){
                            foods = dao.queryFId(Integer.valueOf(idET.getText().toString().trim()));
                        }else if(!TextUtils.isEmpty(nameET.getText().toString().trim())){
                            foods = dao.queryName(nameET.getText().toString().trim());
                        }else if(!TextUtils.isEmpty(animalET.getText().toString().trim())){
                            foods = dao.queryAnimal(animalET.getText().toString().trim());
                        }
                        if(foods!=null)
                        dao.deleteFood(foods);
                        e.onNext(Arrays.asList(dao.query()));
                        e.onComplete();
                    }
                },BackpressureStrategy.BUFFER)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                        //subscription = s;
                    }
                    @Override
                    public void onNext(List list) {
                        adapter.foods = list;
                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onError(Throwable t) {
                    }
                    @Override
                    public void onComplete() {}
                });
            }
        });
        queryTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Flowable.create(new FlowableOnSubscribe<List>(){
                    @Override
                    public void subscribe(FlowableEmitter<List> e) throws Exception {
                        Food[] foods = null;
                        if(!TextUtils.isEmpty(idET.getText().toString().trim())){
                            foods = dao.queryFId(Integer.valueOf(idET.getText().toString().trim()));
                        }else if(!TextUtils.isEmpty(nameET.getText().toString().trim())){
                            foods = dao.queryName(nameET.getText().toString().trim());
                        }else if(!TextUtils.isEmpty(animalET.getText().toString().trim())){
                            foods = dao.queryAnimal(animalET.getText().toString().trim());
                        }else{
                            foods = dao.query();
                        }

                        e.onNext(Arrays.asList(foods));
                        e.onComplete();
                    }
                },BackpressureStrategy.BUFFER)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                        //subscription = s;
                    }
                    @Override
                    public void onNext(List list) {
                        adapter.foods = list;
                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onError(Throwable t) {
                    }
                    @Override
                    public void onComplete() {}
                });
            }
        });
        updateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Flowable.create(new FlowableOnSubscribe<List>(){
                    @Override
                    public void subscribe(FlowableEmitter<List> e) throws Exception {
                        Food[] foods = dao.queryFId(Integer.valueOf(idET.getText().toString().trim()));
                        if(foods!=null&&foods.length>0){
                            foods[0] = formatFood(foods[0]);
                            dao.updateFood(foods[0]);
                        }
                        e.onNext(Arrays.asList(dao.query()));
                        e.onComplete();
                    }
                },BackpressureStrategy.BUFFER)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                        //subscription = s;
                    }
                    @Override
                    public void onNext(List list) {
                        adapter.foods = list;
                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onError(Throwable t) {
                    }
                    @Override
                    public void onComplete() {}
                });
            }
        });
    }

    private Food formatFood(Food food) {
        food.fId = Integer.valueOf(idET.getText().toString().trim());
        food.name = nameET.getText().toString().trim();
        food.aid = formatAnimal(animalET.getText().toString().trim());
        return food;
    }

    private int formatAnimal(String animal) {
        int aid = 0;
        if("熊猫".equals(animal)){
            aid = 100;
        }else if("白头海雕".equals(animal)){
            aid = 200;
        }else if("北极熊".equals(animal)){
            aid = 300;
        }else if("企鹅".equals(animal)){
            aid = 400;
        }
        return aid;
    }

    private void initData() {
        adapter = new FoodAdapter();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        AppDataBase db = Room.databaseBuilder(getApplicationContext(),
                AppDataBase.class, "database-name").build();
        dao = db.foodDao();
    }
}

package com.lzt.roomsdemo;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lzt.roomsdemo.adapter.AnimalAdapter;
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

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private EditText idET;
    private EditText nameET;
    private EditText placeET;
    private EditText countryET;

    private TextView insertTV;
    private TextView deleteTV;
    private TextView queryTV;
    private TextView updateTV;
    private TextView foodTV;

    private AnimalAdapter adapter;
    private AnimalDao dao;
    private FoodDao foodDao;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        rv          = (RecyclerView) findViewById(R.id.rv);
        idET        = (EditText) findViewById(R.id.et_id);
        nameET      = (EditText) findViewById(R.id.et_name);
        placeET     = (EditText) findViewById(R.id.et_place);
        countryET   = (EditText) findViewById(R.id.et_country);

        insertTV    = (TextView) findViewById(R.id.tv_insert);
        deleteTV    = (TextView) findViewById(R.id.tv_delete);
        queryTV     = (TextView) findViewById(R.id.tv_query);
        updateTV    = (TextView) findViewById(R.id.tv_update);
        foodTV      = (TextView) findViewById(R.id.tv_food);
    }

    private void initListener() {

        insertTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animal animal = formatAnimal(new Animal());
                Flowable.create(new FlowableOnSubscribe<List>(){
                    @Override
                    public void subscribe(FlowableEmitter<List> e) throws Exception {
                        dao.insertAnimal(animal);
                        List list = Arrays.asList(dao.query());
                        e.onNext(list);
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
                        adapter.animals = list;
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
                        Animal[] animals = null;
                        if(!TextUtils.isEmpty(idET.getText().toString().trim())){
                            animals = dao.queryUid(Integer.valueOf(idET.getText().toString().trim()));
                        }else if(!TextUtils.isEmpty(nameET.getText().toString().trim())){
                            animals = dao.queryName(nameET.getText().toString().trim());
                        }else if(!TextUtils.isEmpty(placeET.getText().toString().trim())){
                            animals = dao.queryPlace(placeET.getText().toString().trim());
                        }else{
                            animals = dao.queryCid(formatCountry(countryET.getText().toString().trim()));
                        }
                        dao.deleteAnimal(animals);
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
                        adapter.animals = list;
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
                        Animal[] animals = null;
                        Food[] foods = foodDao.query();
                        adapter.foods = Arrays.asList(foods);
                        if(!TextUtils.isEmpty(idET.getText().toString().trim())){
                            animals = dao.queryUid(Integer.valueOf(idET.getText().toString().trim()));
                        }else if(!TextUtils.isEmpty(nameET.getText().toString().trim())){
                            animals = dao.queryName(nameET.getText().toString().trim());
                        }else if(!TextUtils.isEmpty(placeET.getText().toString().trim())){
                            animals = dao.queryPlace(placeET.getText().toString().trim());
                        }else if(!TextUtils.isEmpty(countryET.getText().toString().trim())){
                            animals = dao.queryCid(formatCountry(countryET.getText().toString().trim()));
                        }else{
                            animals = dao.query();
                        }

                        e.onNext(Arrays.asList(animals));
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
                        adapter.animals = list;
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
                        Animal[] animals = dao.queryUid(Integer.valueOf(idET.getText().toString().trim()));
                        if(animals!=null&&animals.length>0){
                            animals[0] = formatAnimal(animals[0]);
                            dao.updateAnimal(animals[0]);
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
                        adapter.animals = list;
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

        foodTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,FoodActivity.class));
            }
        });
    }

    private Animal formatAnimal(Animal animal) {
        animal.aid = Integer.valueOf(idET.getText().toString().trim());
        animal.scientificName = nameET.getText().toString().trim();
        animal.place = placeET.getText().toString().trim();
        Country country = new Country();
        country.cid = formatCountry(countryET.getText().toString().trim());
        country.cname = countryET.getText().toString();
        animal.country = country;
        return animal;
    }

    private int formatCountry(String country) {
        int cid = 0;
        if("中国".equals(country)){
            cid = 100;
        }else if("美国".equals(country)){
            cid = 200;
        }else if("俄罗斯".equals(country)){
            cid = 300;
        }else if("南极".equals(country)){
            cid = 400;
        }
        return cid;
    }

    private void initData() {
        adapter = new AnimalAdapter();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        AppDataBase db = Room.databaseBuilder(getApplicationContext(),
                AppDataBase.class, "database-name")/*.addMigrations(MIGRATION_1_2,MIGRATION_2_3)*/.build();
        dao = db.animalDao();
        foodDao = db.foodDao();
    }

    //app由版本1更新2时，迁移版本1的Animal的Table到版本2中,可以指定迁移哪些字段
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE 'animal' ('uid' INTEGER, "
                    + "'sname' TEXT,'place' TEXT,'viviparity' INTEGER,'cid' INTEGER,'cname' TEXT, PRIMARY KEY(`uid`))");
        }
    };

    //app由版本2更新至3时，给版本2的Animal的Table插入新的字段 liveTime
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE animal "
                    + " ADD COLUMN liveTime INTEGER");
        }
    };
}


package com.mixno.elementinspector_pro;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mixno.elementinspector_pro.adapter.AboutAppAdapter;
import com.mixno.elementinspector_pro.app.Data;
import com.mixno.elementinspector_pro.model.AboutAppModel;

import java.util.ArrayList;

public class DonateActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView listDonate;
    private RecyclerView.Adapter listAdapter;
    private RecyclerView.LayoutManager listLayoutManager;

    private ArrayList<AboutAppModel> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Data.theme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        // Id's
        toolbar = findViewById(R.id.toolbar);
        listDonate = findViewById(R.id.listDonate);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        list.add(new AboutAppModel("WebMoney - WMZ", "Z178194978131", null, true, AboutAppModel.TYPE_COPY));
        list.add(new AboutAppModel("WebMoney - WME", "E721558707620", null, true, AboutAppModel.TYPE_COPY));
        list.add(new AboutAppModel("WebMoney - WMB", "B735984048596", null, true, AboutAppModel.TYPE_COPY));
        list.add(new AboutAppModel("Payeer", "P1068237878", null, true, AboutAppModel.TYPE_COPY));
        list.add(new AboutAppModel("YMoney", "4100117381445741", null, true, AboutAppModel.TYPE_COPY));
        list.add(new AboutAppModel("QIWI", "https://qiwi.com/n/MIXNO35", "https://qiwi.com/n/MIXNO35", true, AboutAppModel.TYPE_LINK));
        list.add(new AboutAppModel("Bitcoin", "3EgHdoNcnYSsCGDLqxZyCXhCPZrqDUayho", null, true, AboutAppModel.TYPE_COPY));
        list.add(new AboutAppModel("LiteCoin", "MWnZwdtaMcvKAFD5zzv5h1mPp71r4JF4hy", null, true, AboutAppModel.TYPE_COPY));
        list.add(new AboutAppModel(getString(R.string.title_say_thanks), "Telegram", "https://t.me/elinther", true, AboutAppModel.TYPE_LINK));
        list.add(new AboutAppModel(getString(R.string.title_rate_app), "Google Play", "https://play.google.com/store/apps/details?id=" + this.getPackageName(), true, AboutAppModel.TYPE_LINK));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listLayoutManager = new LinearLayoutManager(getApplicationContext());
                listDonate.setLayoutManager(listLayoutManager);
                listAdapter = new AboutAppAdapter(list, getApplicationContext());
                listDonate.setAdapter(listAdapter);
            }
        }, 300);
    }
}
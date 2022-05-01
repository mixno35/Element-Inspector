package com.mixno.elementinspector_pro.web;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mixno.elementinspector_pro.MainActivity;
import com.mixno.elementinspector_pro.R;
import com.mixno.elementinspector_pro.adapter.BackHistoryAdapter;
import com.mixno.elementinspector_pro.model.BackHistoryModel;
import com.mixno.elementinspector_pro.widget.WebEI;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class BackHistory {

    private BottomSheetDialog dialog;

    private Button buttonClearHistory;

    private RecyclerView listBackHistory;
    private RecyclerView.Adapter listAdapter;
    private RecyclerView.LayoutManager listLayoutManager;

    public BackHistory(final Context context, final WebEI viewWeb, final ArrayList<BackHistoryModel> list) {
        dialog = new BottomSheetDialog(context);
        View inflate = ((Activity)context).getLayoutInflater().inflate(R.layout.alert_back_history, null);
        dialog.setContentView(inflate);
        dialog.setCancelable(true);

        if (dialog != null) {
            listBackHistory = inflate.findViewById(R.id.listBackHistory);
            buttonClearHistory = inflate.findViewById(R.id.buttonClearHistory);

            listLayoutManager = new LinearLayoutManager(context);
            listBackHistory.setLayoutManager(listLayoutManager);
            listAdapter = new BackHistoryAdapter(list, context, viewWeb);
            listBackHistory.setAdapter(listAdapter);

            if (list.size() > 0) {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        buttonClearHistory.setVisibility(View.VISIBLE);
                    }
                });
            } else {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        buttonClearHistory.setVisibility(View.GONE);
                    }
                });
            }
        }

        buttonClearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                new MainActivity().backHistoryList.clear();
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        buttonClearHistory.setVisibility(View.GONE);
                    }
                });
                listBackHistory.setAdapter(listAdapter);
            }
        });

        if (dialog != null) {
            dialog.show();
        }
    }
}


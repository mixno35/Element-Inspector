package com.mixno.elementinspector_pro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.mixno.elementinspector_pro.app.Data;
import com.mixno.elementinspector_pro.app.DataAnim;
import com.mixno.elementinspector_pro.app.ProxySettings;
import com.mixno.elementinspector_pro.widget.WebEI;

public class ProxySettingsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Switch switch1;
    private LinearLayout otherContent;
    private MaterialButton buttonSave;
    private TextInputEditText editHost, editPort;

    private SharedPreferences shared;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Data.theme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy_settings);

        toolbar = findViewById(R.id.toolbar);
        switch1 = findViewById(R.id.switch1);
        otherContent = findViewById(R.id.otherContent);
        buttonSave = findViewById(R.id.buttonSave);
        editHost = findViewById(R.id.editHost);
        editPort = findViewById(R.id.editPort);

        shared = PreferenceManager.getDefaultSharedPreferences(this);

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

        editHost.setText(shared.getString("KeyProxySettingsHost", ProxySettings.PROXY_DEFAULT_HOST));
        editPort.setText(shared.getString("KeyProxySettingsPort", String.valueOf(ProxySettings.PROXY_DEFAULT_PORT)));

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shared.edit().putString("KeyProxySettingsHost", editHost.getText().toString()).apply();
                shared.edit().putString("KeyProxySettingsPort", editPort.getText().toString()).apply();
                Snackbar.make(findViewById(R.id.content), getString(R.string.toast_saved), 2000).show();
            }
        });

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    openOtherContent(DataAnim.DURATION_ANIM);
                    switch1.setText(getString(R.string.action_ons));
                } else {
                    closeOtherContent(DataAnim.DURATION_ANIM);
                    switch1.setText(getString(R.string.action_offs));
                }

                shared.edit().putBoolean("KeyProxySettingsBool", b).apply();
            }
        });

        switch1.setChecked(shared.getBoolean("KeyProxySettingsBool", false));

        if (shared.getBoolean("KeyProxySettingsBool", false)) {
            openOtherContent(0);
        } else {
            closeOtherContent(0);
        }
    }

    private void openOtherContent(final long duration) {
        otherContent.setVisibility(View.VISIBLE);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                otherContent.animate().alpha(1.0f).setDuration(duration).start();
            }
        });
    }

    private void closeOtherContent(final long duration) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                otherContent.animate().alpha(0.0f).setDuration(duration).start();
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                otherContent.setVisibility(View.INVISIBLE);
            }
        }, duration);
    }
}

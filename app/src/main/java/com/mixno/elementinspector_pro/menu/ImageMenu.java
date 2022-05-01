package com.mixno.elementinspector_pro.menu;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.mixno.elementinspector_pro.MainActivity;
import com.mixno.elementinspector_pro.R;
import com.mixno.elementinspector_pro.app.Data;
import com.mixno.elementinspector_pro.dialog.DownloadFileDialog;
import com.squareup.picasso.Picasso;

public class ImageMenu {

    public static void alert(final Context context, final WebView.HitTestResult hit) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        final View view = inflater.inflate(R.layout.alert_long_web_image, null);

        final TextView textURL = view.findViewById(R.id.textID);
        final ImageView imagePREVIEW = view.findViewById(R.id.imagePREVIEW);

        textURL.post(new Runnable() {
            @Override
            public void run() {
                textURL.setText(hit.getExtra());
            }
        });

        textURL.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Data.clipboard(context, textURL.getText().toString(), true);
                return true;
            }
        });

        Picasso.with(context)
                .load(hit.getExtra())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imagePREVIEW);

//        imagePREVIEW.post(new Runnable() {
//            @Override
//            public void run() {
//                imagePREVIEW.setVisibility(View.GONE);
//            }
//        });

        textURL.post(new Runnable() {
            @Override
            public void run() {
                textURL.setMaxLines(3);
            }
        });

        textURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textURL.getMaxLines() > 3) {
                    textURL.setMaxLines(3);
                } else {
                    textURL.setMaxLines(60);
                }
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle(hit.getExtra());
        builder.setView(view);

        String[] menu = {context.getString(R.string.action_menu_open), context.getString(R.string.action_download), context.getString(R.string.action_menu_copy_url), context.getString(R.string.action_menu_share)};
        builder.setItems(menu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: // Open
                        MainActivity.mWeb.loadUrl(hit.getExtra());
                        break;
                    case 1: // Download
                        downloadImage(context, hit.getExtra());
                        break;
                    case 2: // Copy URL
                        Data.clipboard(context, hit.getExtra(), true);
                        break;
                    case 3: // Share
                        Data.shareText(context, hit.getExtra());
                        break;
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private static void downloadImage(final Context context, final String url) {
        new DownloadFileDialog(context, url, null, 0);
    }
}

package com.mixno.elementinspector_pro.dialog;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;


import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mixno.elementinspector_pro.R;
import com.mixno.elementinspector_pro.widget.WebEI;

public class LoadHTMLDialog {

    private BottomSheetDialog dialog;

    public LoadHTMLDialog(Context context, final WebEI web) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        final View view = inflater.inflate(R.layout.alert_load_html, null);

        final EditText editCode = view.findViewById(R.id.editCode);
        final Button btnSave = view.findViewById(R.id.btnSave);
        final ImageView ivClose = view.findViewById(R.id.ivClose);
        final RadioButton htmlReplace = view.findViewById(R.id.htmlReplace);
        final RadioButton htmlAppend = view.findViewById(R.id.htmlAppend);
        final RadioButton htmlLoad = view.findViewById(R.id.htmlLoad);

        htmlReplace.setChecked(false);
        htmlAppend.setChecked(false);
        htmlLoad.setChecked(true);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View p1){
                dialog.dismiss();
                if (htmlLoad.isChecked()) {
                    web.loadData(editCode.getText().toString(), "text/html; charset=utf-8", null);
                    return;
                } if (htmlAppend.isChecked()) {
                    web.runJS("(function(){ document.body.innerHTML += '" + Html.fromHtml(editCode.getText().toString()) + "'; })();");
                    return;
                } if (htmlReplace.isChecked()) {
                    web.runJS("(function(){ document.body.innerHTML = '" + Html.fromHtml(editCode.getText().toString()) + "'; })();");
                    return;
                }
            }
        });

        dialog = new BottomSheetDialog(context);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();
    }
}

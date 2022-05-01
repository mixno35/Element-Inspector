package com.mixno.elementinspector_pro.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.mixno.elementinspector_pro.R;
import com.mixno.elementinspector_pro.app.Data;
import com.mixno.elementinspector_pro.model.CookieManagerModel;
import com.mixno.elementinspector_pro.widget.WebEI;

public class CookieManagerAdapter extends RecyclerView.Adapter<CookieManagerAdapter.CookieManagerHolder> {

    private List<CookieManagerModel> list;
    private Context context;
    private WebEI web;

    public CookieManagerAdapter(List<CookieManagerModel> list, Context context, WebEI web) {
        this.list = list;
        this.context = context;
        this.web = web;
    }

    @Override
    public CookieManagerAdapter.CookieManagerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cookie_manager, parent, false);
        CookieManagerHolder holder = new CookieManagerHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final CookieManagerAdapter.CookieManagerHolder holder, final int position) {
        final CookieManagerModel model = list.get(position);

        holder.textName.post(new Runnable() {
            @Override
            public void run() {
                holder.textName.setText(model.getName());
            }
        });
        holder.textMessage.post(new Runnable() {
            @Override
            public void run() {
                holder.textMessage.setText(model.getValue());
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                alertMenu(model.getName(), model.getValue(), model.getUrl(), position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CookieManagerHolder extends RecyclerView.ViewHolder {
        protected TextView textName, textMessage;

        public CookieManagerHolder(View item) {
            super(item);
            textName = item.findViewById(R.id.textViewName);
            textMessage = item.findViewById(R.id.textViewMessage);
        }
    }

    private void alertMenu(final String name, final String value, final String url, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(name);

        String[] menu = {context.getString(R.string.action_menu_copy)};
        builder.setItems(menu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: // Copy
                        Data.clipboard(context, value, true);
                        dialog.dismiss();
                        break;
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}




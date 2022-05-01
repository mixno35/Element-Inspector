package com.mixno.elementinspector_pro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.mixno.elementinspector_pro.MainActivity;
import com.mixno.elementinspector_pro.R;
import com.mixno.elementinspector_pro.dialog.UserAgentDialog;
import com.mixno.elementinspector_pro.model.UserAgentModel;

public class UserAgentAdapter extends RecyclerView.Adapter<UserAgentAdapter.UserAgentHolder> {

    private List<UserAgentModel> list;
    private Context context;

    public UserAgentAdapter(List<UserAgentModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public UserAgentAdapter.UserAgentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_useragent, parent, false);
        UserAgentHolder holder = new UserAgentHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final UserAgentAdapter.UserAgentHolder holder, int position) {
        final UserAgentModel model = list.get(position);

        holder.name.post(new Runnable() {
            @Override
            public void run() {
                holder.name.setText(model.getName());
            }
        });
        holder.icon.post(new Runnable() {
            @Override
            public void run() {
                holder.icon.setImageResource(model.getDrawable());
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mWeb.setChangeUserAgent(model.getUserAgent(), model.isDesktop());
                try {
                    UserAgentDialog.dialog.dismiss();
                } catch (Exception e) {}
            }
        });

        holder.check.post(new Runnable() {
            @Override
            public void run() {
                holder.check.setVisibility(View.GONE);
            }
        });

        if (MainActivity.mWeb.getSettings().getUserAgentString().equals(model.getUserAgent())) {
            holder.check.post(new Runnable() {
                @Override
                public void run() {
                    holder.check.setVisibility(View.VISIBLE);
                }
            });
        }

//        DataAnim.setFadeAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class UserAgentHolder extends RecyclerView.ViewHolder {
        protected TextView name;
        protected ImageView icon, check;

        public UserAgentHolder(View item) {
            super(item);
            name = item.findViewById(R.id.name);
            icon = item.findViewById(R.id.image);
            check = item.findViewById(R.id.check);
        }
    }
}




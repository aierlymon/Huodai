package com.example.huodai.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.baselib.http.HttpConstant;
import com.example.baselib.utils.MyLog;
import com.example.huodai.R;
import com.example.model.bean.HomeMenuBean;

import java.util.List;

public class HomeMenuRevAdapter extends RecyclerView.Adapter<HomeMenuRevAdapter.MenuItemHolder> {

    List<HomeMenuBean> mulDataModelList;
    private Context mContext;

    public HomeMenuRevAdapter(Context mContext, List<HomeMenuBean> mulDataModelList) {
        this.mulDataModelList = mulDataModelList;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public MenuItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MenuItemHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.home_menu_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemHolder holder, int position) {
        MyLog.i("Menu的图片路径: "+mulDataModelList.get(position).getIcon());
        Glide.with(mContext).load(HttpConstant.BASE_URL+mulDataModelList.get(position).getIcon()).into(holder.image);
        holder.name.setText(mulDataModelList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mulDataModelList.size();
    }

    class MenuItemHolder extends RecyclerView.ViewHolder {
        ImageView image;

        TextView name;

        public MenuItemHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.menu_icon);
            name = itemView.findViewById(R.id.menu_text);
        }

    }
}

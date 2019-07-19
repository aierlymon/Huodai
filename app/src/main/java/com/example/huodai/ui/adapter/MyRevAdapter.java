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
import com.example.huodai.R;
import com.example.huodai.mvp.model.MyFRFunctionHolder;
import com.example.huodai.ui.adapter.base.BaseMulDataModel;
import com.example.huodai.ui.adapter.base.BaseMulViewHolder;

import java.util.List;

import butterknife.BindView;

public class MyRevAdapter extends RecyclerView.Adapter<BaseMulViewHolder> {

    private List<BaseMulDataModel> list;
    private Context mContext;

    public MyRevAdapter(List<BaseMulDataModel> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.my_func_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseMulViewHolder holder, int position) {
        holder.bindData(list.get(position),position);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends BaseMulViewHolder<MyFRFunctionHolder> {

        @BindView(R.id.img_func_icon)
        ImageView img_func_icon;

        @BindView(R.id.tx_func_name)
        TextView tx_func_name;

        public MyHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(MyFRFunctionHolder dataModel, int position) {
            Glide.with(mContext).load(dataModel.getF_icon()).into(img_func_icon);
            tx_func_name.setText(dataModel.getF_name());
        }
    }
}

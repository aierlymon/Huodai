package com.example.huodai.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.baselib.utils.MyLog;
import com.example.huodai.R;
import com.example.huodai.mvp.model.HomeFRBannerHolder;
import com.example.huodai.mvp.model.HomeFRBodyHolder;
import com.example.huodai.mvp.model.HomeFRMenuHolder;
import com.example.huodai.ui.adapter.base.BaseMulDataModel;
import com.example.huodai.ui.adapter.base.BaseMulViewHolder;
import com.example.huodai.ui.adapter.decoration.SpaceItemDecoration;

import java.util.List;

import butterknife.BindView;

public class HomeFragRevAdapyer extends RecyclerView.Adapter<BaseMulViewHolder> {

    List<BaseMulDataModel> modelList;
    private static final int BANNER = 0;
    private static final int MENU = 1;
    private static final int BODY = 2;
    private Context mContext;

    public HomeFragRevAdapyer(Context mContext, List<BaseMulDataModel> modelList) {
        this.modelList = modelList;
        this.mContext = mContext;
    }

    public List<BaseMulDataModel> getModelList() {
        return modelList;
    }

    @NonNull
    @Override
    public BaseMulViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case BANNER:
                return new BannerHolder(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.fra_home_recy_banner, viewGroup, false));
            case MENU:
                return new MenuHolder(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.fra_home_recy_menu, viewGroup, false));
            case BODY:
                return new BodyHolder(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.fra_home_recy_body, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseMulViewHolder baseMulViewHolder, int position) {
        baseMulViewHolder.bindData(modelList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (modelList.get(position) instanceof HomeFRBannerHolder) {
            MyLog.i("检测到HomeFRBannerHolder");
            return BANNER;
        } else if (modelList.get(position) instanceof HomeFRMenuHolder) {
            return MENU;
        } else {
            return BODY;
        }
    }

    class BannerHolder extends BaseMulViewHolder<HomeFRBannerHolder> {
        @BindView(R.id.banner_viewpager)
        ViewPager viewPager;

        public BannerHolder(View itemView) {
            super(itemView);

        }

        @Override
        public void bindData(HomeFRBannerHolder dataModel, int position) {
            HomeBannerVPAdapyer homeBannerVPAdapyer = new HomeBannerVPAdapyer(mContext, dataModel.getUrls());
            viewPager.setAdapter(homeBannerVPAdapyer);
        }
    }

    class MenuHolder extends BaseMulViewHolder<HomeFRMenuHolder> {
        @BindView(R.id.recv_menu)
        RecyclerView recyclerView;

        @BindView(R.id.tx_ps)
        TextView tx;

        public MenuHolder(View itemView) {
            super(itemView);
            LinearLayoutManager manager = new LinearLayoutManager(mContext);
            manager.setOrientation(RecyclerView.HORIZONTAL);
            recyclerView.setLayoutManager(manager);
        }

        @Override
        public void bindData(HomeFRMenuHolder dataModel, int position) {
            HomeMenuRevAdapter homeMenuRevAdapter = new HomeMenuRevAdapter(mContext, dataModel.getUrls());
            recyclerView.setAdapter(homeMenuRevAdapter);
        }
    }

    class BodyHolder extends BaseMulViewHolder<HomeFRBodyHolder> {

        @BindView(R.id.recv_body)
        RecyclerView recyclerView;

        public BodyHolder(View itemView) {
            super(itemView);
            LinearLayoutManager manager = new LinearLayoutManager(mContext);
            recyclerView.setLayoutManager(manager);
        }

        @Override
        public void bindData(HomeFRBodyHolder dataModel, int position) {
            HomeBodyRevAdapter homeBodyRevAdapter=new HomeBodyRevAdapter(mContext,dataModel.getHomeBodyBeanList());
            recyclerView.addItemDecoration(new SpaceItemDecoration(2));
            recyclerView.setAdapter(homeBodyRevAdapter);
        }
    }
}

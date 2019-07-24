package com.example.huodai.ui.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.baselib.utils.MyLog;
import com.example.baselib.utils.Utils;
import com.example.huodai.ApplicationPrams;
import com.example.huodai.R;
import com.example.huodai.mvp.model.HomeFRBannerHolder;
import com.example.huodai.mvp.model.HomeFRBodyHolder;
import com.example.huodai.mvp.model.HomeFRMenuHolder;
import com.example.huodai.ui.adapter.base.BaseMulDataModel;
import com.example.huodai.ui.adapter.base.BaseMulViewHolder;
import com.example.model.bean.HomeBodyBean;
import com.example.huodai.widget.jingewenku.abrahamcaijin.loopviewpagers.LoopViewPager;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;

public class HomeFragRevAdapyer extends RecyclerView.Adapter<BaseMulViewHolder> {

    List<BaseMulDataModel> modelList;
    private static final int BANNER = 0;
    private static final int MENU = 1;
    private static final int BODY = 2;
    private Activity mContext;

    public HomeFragRevAdapyer(Activity mContext, List<BaseMulDataModel> modelList) {
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
        LoopViewPager loopViewPager;

        public BannerHolder(View itemView) {
            super(itemView);
            loopViewPager.setIndicatorGravity(LoopViewPager.IndicatorGravity.RIGHT);
            loopViewPager.showIndicator(true);
            loopViewPager.startBanner();
        }

        @Override
        public void bindData(HomeFRBannerHolder dataModel, int position) {
            loopViewPager.setData(dataModel.getIcon_urls(), (view, position1, item) -> {
                view.setScaleType(ImageView.ScaleType.FIT_XY);
                view.setOnClickListener(view1 -> {
                    MyLog.i("banner 点击之后: "+dataModel.getUrls().get(position1));
                    go(view, -100,dataModel.getUrls().get(position1));
                });
                MyLog.i("banner item icon: "+item);
                //加载图片，如gide
                Glide.with(mContext).load(item).into(view);
            });
        }
    }

    class MenuHolder extends BaseMulViewHolder<HomeFRMenuHolder> {
        @BindView(R.id.recv_menu)
        RecyclerView recyclerView;

        @BindView(R.id.tx_ps)
        TextView tx;

        private HomeMenuRevAdapter homeMenuRevAdapter;

        public MenuHolder(View itemView) {
            super(itemView);
            LinearLayoutManager manager = new LinearLayoutManager(mContext);
            manager.setOrientation(RecyclerView.HORIZONTAL);
            recyclerView.setLayoutManager(manager);
            homeMenuRevAdapter = new HomeMenuRevAdapter(mContext, null);
            homeMenuRevAdapter.setOnItemClickListener((view, position1) -> {
                go(view, -1,null);
            });
            recyclerView.setAdapter(homeMenuRevAdapter);

        }

        @Override
        public void bindData(HomeFRMenuHolder dataModel, int position) {
            homeMenuRevAdapter.setMulDataModelList(dataModel.getUrls());
            homeMenuRevAdapter.notifyDataSetChanged();
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
            HomeBodyRevAdapter homeBodyRevAdapter = new HomeBodyRevAdapter(mContext, dataModel.getHomeBodyBeanList());
            homeBodyRevAdapter.setOnItemClickListener((view, position1) -> {
                go(view, position, dataModel.getHomeBodyBeanList().get(position1));
            });
          //  recyclerView.addItemDecoration(new SpaceItemDecoration(20,20,1));
            recyclerView.setAdapter(homeBodyRevAdapter);
        }
    }

    public <T> void go(View view, int position, T object) {
        if (Utils.isFastClick()) {
            if (ApplicationPrams.loginCallBackBean == null) {
                MyLog.i("点击了go");
                EventBus.getDefault().post(false);
                return;
            }
            //进行页面跳转
            if (object instanceof HomeBodyBean){
                EventBus.getDefault().post(((HomeBodyBean) object).getUrl());
            }else if(object instanceof String){
                if(!TextUtils.isEmpty(""+object))
                EventBus.getDefault().post(""+object);
            }

            if(position==-1){
                EventBus.getDefault().post(1);
            }
        }
    }
}

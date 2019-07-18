package com.example.huodai.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.baselib.http.HttpConstant;
import com.example.huodai.R;
import com.example.model.bean.HomeBodyBean;

import java.util.List;

public class HomeBodyRevAdapter extends RecyclerView.Adapter<HomeBodyRevAdapter.BodyItemHold> {

    private Context mContext;
    private List<HomeBodyBean> homeBodyBeanList;

    public HomeBodyRevAdapter(Context mContext, List<HomeBodyBean> homeBodyBeanList) {
        this.mContext = mContext;
        this.homeBodyBeanList = homeBodyBeanList;
    }

    @NonNull
    @Override
    public BodyItemHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BodyItemHold(LayoutInflater.from(mContext)
                .inflate(R.layout.home_body_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BodyItemHold holder, int position) {
        HomeBodyBean homeBodyBean = homeBodyBeanList.get(position);
        String icon_url=HttpConstant.BASE_URL + homeBodyBeanList.get(position).getIcon();

        RequestOptions options = new RequestOptions();
       int size= (int) mContext.getResources().getDimension(R.dimen.x27);
        options.override(size, size); //设置加载的图片大小
        Glide.with(mContext).load(icon_url).apply(options).into(holder.icon);

        holder.title.setText(homeBodyBean.getName());
        holder.limit.setText(""+homeBodyBean.getLimitL());
        holder.max.setText(""+homeBodyBean.getLimitH());
        holder.rate.setText(homeBodyBean.getInterest());
        holder.finaltext.setText(homeBodyBean.getProfile());
        holder.time.setText(homeBodyBean.getSpeed());
        holder.btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return homeBodyBeanList.size();
    }

    class BodyItemHold extends RecyclerView.ViewHolder {
        private ImageView icon;
        private TextView title;
        private TextView limit;
        private TextView max;
        private TextView rate;
        private TextView finaltext;
        private TextView time;
        private Button btn_request;

        public BodyItemHold(@NonNull View itemView) {
            super(itemView);
            icon = ((ImageView) itemView.findViewById(R.id.icon));
            title = ((TextView) itemView.findViewById(R.id.title));
            limit = ((TextView) itemView.findViewById(R.id.limit));
            max = ((TextView) itemView.findViewById(R.id.max));
            rate = ((TextView) itemView.findViewById(R.id.rate));
            finaltext = ((TextView) itemView.findViewById(R.id.finaltext));
            time = ((TextView) itemView.findViewById(R.id.time));
            btn_request = itemView.findViewById(R.id.btn_request);
        }
    }
}

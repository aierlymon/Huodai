package com.example.huodai.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.huodai.R;

import java.util.ArrayList;
import java.util.List;

public class LoanSpinnerRevAdapter extends RecyclerView.Adapter<LoanSpinnerRevAdapter.SpinnerHolder> implements View.OnClickListener {

    private Context mContext;
    private List<String> infoList;




    public LoanSpinnerRevAdapter(Context mContext) {
        this.mContext = mContext;
        this.infoList = new ArrayList<>();
    }

    public List<String> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<String> infoList) {
        this.infoList = infoList;
    }

    @NonNull
    @Override
    public SpinnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SpinnerHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.loan_spinner_recv_item, null, false), this);
    }

    @Override
    public void onBindViewHolder(@NonNull SpinnerHolder holder, int position) {
        holder.info.setText(infoList.get(position));
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(view, (int) view.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    class SpinnerHolder extends RecyclerView.ViewHolder {
        TextView info;

        public SpinnerHolder(@NonNull View itemView, View.OnClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(listener);
            info = itemView.findViewById(R.id.info);
        }
    }
}

package com.example.huodai.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baselib.utils.Utils;
import com.example.huodai.R;
import com.example.huodai.ui.adapter.LoanSpinnerRevAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestPopWindow extends PopupWindow {
    public static final int TYPE = 1;
    public static final int LOAN = 2;
    public static final int ORDER = 3;

    @BindView(R.id.spinner_recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.spinner_back_img)
    ImageView imageView_back;

    List<String> typeList;
    List<String> loadNums;

    private LoanSpinnerRevAdapter loanSpinnerRevAdapter;

    public TestPopWindow(Context context) {
        super(context);

        String[] typeArray = context.getResources().getStringArray(R.array.type);
        String[] loanNumArray = context.getResources().getStringArray(R.array.loan_num);

        typeList = new ArrayList<>();
        typeList.addAll(Arrays.asList(typeArray));

        loadNums = new ArrayList<>();
        loadNums.addAll(Arrays.asList(loanNumArray));



        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

        setFocusable(true);
        setOutsideTouchable(true);

        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View contentView = LayoutInflater.from(context).inflate(R.layout.fra_loan_spinner_item,
                null, false);
        setContentView(contentView);
        ButterKnife.bind(this, contentView);


        loanSpinnerRevAdapter = new LoanSpinnerRevAdapter(context);
        loanSpinnerRevAdapter.setOnItemClickListener(new LoanSpinnerRevAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(loanSpinnerRevAdapter);
    }


    @OnClick({R.id.spinner_back_img})
    public void click(){
        if(isShowing()){
            dismiss();
        }
    }

    int anchorLoc[] = new int[2];
    public void showAsDropDown(View anchor, Context context) {

        float WH[] = Utils.getScreenWH(context);
        // 获取锚点View在屏幕上的左上角坐标位置
        anchor.getLocationOnScreen(anchorLoc);
        final int anchorHeight = anchor.getHeight();
        int heigh = (int) (WH[1] - anchorHeight - anchorLoc[1]);
        setHeight(heigh);
        super.showAsDropDown(anchor);
    }

    public int[] getAnchorLoc() {
        return anchorLoc;
    }

    public void selectType(int type) {
        switch (type) {
            case TYPE:
                loanSpinnerRevAdapter.setInfoList(typeList);
                break;
            case LOAN:
                loanSpinnerRevAdapter.setInfoList(loadNums);
                break;
        }
        loanSpinnerRevAdapter.notifyDataSetChanged();
    }

}

package com.example.huodai;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.baselib.base.BaseMvpActivity;
import com.example.huodai.mvp.presenters.MainPrsenter;
import com.example.huodai.mvp.view.MainViewImpl;
import com.example.huodai.ui.adapter.MainVPAdapter;
import com.example.huodai.ui.fragments.HomeFragment;
import com.example.huodai.ui.fragments.LoanFragment;
import com.example.huodai.ui.fragments.MyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class MainActivity extends BaseMvpActivity<MainViewImpl, MainPrsenter> implements MainViewImpl {

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @BindView(R.id.group)
    RadioGroup mGroup;

    @BindView(R.id.rb_home)
    RadioButton mRb_Home;

    @BindView(R.id.rb_loan)
    RadioButton mRb_Load;

    @BindView(R.id.rb_my)
    RadioButton mRb_Mine;


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setStatusBarColor(Color.BLACK);
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        //butterknife的绑定
        ButterKnife.bind(this);
        //重新定义当前drawable的大小
        for (int i = 0; i < mGroup.getChildCount(); i++) {
            //挨着给每个RadioButton加入drawable限制边距以控制显示大小
            Drawable[] drawables = ((RadioButton) mGroup.getChildAt(i)).getCompoundDrawables();
            //获取drawables
            //给指定的radiobutton设置drawable边界
            Rect r = new Rect(0, 10, drawables[1].getMinimumWidth() * 2 / 3, drawables[1].getMinimumHeight() * 3 / 4);
            //定义一个Rect边界
            drawables[1].setBounds(r);
            //添加限制给控件
            ((RadioButton) mGroup.getChildAt(i)).setCompoundDrawables(null, drawables[1], null, null);
        }


        //初始化三页
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(HomeFragment.newInstance("home"));
        fragments.add(LoanFragment.newInstance("loan"));
        fragments.add(MyFragment.newInstance("mine"));
        MainVPAdapter mainVPagerAdapter = new MainVPAdapter(fragments, getSupportFragmentManager());
        mViewPager.setAdapter(mainVPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
    }



    @Override
    protected MainPrsenter createPresenter() {
        return new MainPrsenter();
    }


    @OnCheckedChanged({R.id.rb_home, R.id.rb_loan, R.id.rb_my})
    public void onCheckChange(RadioButton radioButton) {
        boolean checked = radioButton.isChecked();

        switch (radioButton.getId()) {
            case R.id.rb_home:
                if (checked) {
                    mViewPager.setCurrentItem(0);
                }
                break;
            case R.id.rb_loan:
                //展示标题栏
                if (checked) {
                    mViewPager.setCurrentItem(1);
                }
                break;
            case R.id.rb_my:
                if (checked) {
                    mViewPager.setCurrentItem(2);
                }
                break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg) {

    }

}

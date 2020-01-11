package com.ybbbi.player.fragment.mvpage;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ybbbi.player.BaseFragment;
import com.ybbbi.player.R;
import com.ybbbi.player.View.CustomView;
import com.ybbbi.player.adapter.MvviewPagerAdapter;
import com.ybbbi.player.bean.AreaBean;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * ybbbi
 * 2019-12-06 19:18
 */
public class MvFragment extends BaseFragment implements mvMVP.mvView {

    @Bind(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    private Presenter presenter;
    private List<AreaBean> mTitleDataList = new ArrayList<>();
    private List<Fragment> list_fragment;
    private MvviewPagerAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mv;
    }

    @Override
    protected void initView() {

        presenter = new Presenter(this);
        presenter.loadData();

        list_fragment = new ArrayList<>();
        customView.setonLoadListener(new CustomView.onLoadListener() {
            @Override
            public void Reload() {
                presenter.loadData();
                customView.showLoadingView();
            }
        });


    }

    private void initIndicator() {

        final CommonNavigator commonNavigator = new CommonNavigator(getContext());
                commonNavigator.setAdjustMode(true);

        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mTitleDataList == null ? 0 : mTitleDataList.size();
            }


            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {

                ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
                clipPagerTitleView.setBackgroundColor(Color.parseColor("#222222"));
                clipPagerTitleView.setText(mTitleDataList.get(index).name);
                clipPagerTitleView.setTextColor(getContext().getResources().getColor(R.color.colorAccent));
                clipPagerTitleView.setClipColor(Color.WHITE);

                clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });

                return clipPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {

                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);

        ViewPagerHelper.bind(magicIndicator, viewPager);

    }

    private void initViewpagerAdapter() {
        //初始化adapter
        adapter = new MvviewPagerAdapter(getFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, list_fragment) {
        };
        viewPager.setAdapter(adapter);

    }

    @Override
    public void onsuccess(List<AreaBean> list) {
        mTitleDataList = list;
        for (AreaBean areaBean:list) {
            list_fragment.add(MvChildFragment.getInstance(areaBean.code));

        }
        initViewpagerAdapter();
        initIndicator();

        adapter.notifyDataSetChanged();
        customView.showSuccessView();
    }

    @Override
    public void fail(int code, Exception e) {
        customView.showErrorView();
    }
}

package com.holy.booking.fragment.main;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.holy.booking.R;
import com.holy.common.app.Fragment;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;


public class HomeFragment extends Fragment {

    private QuickAdapter mAdapter;

    @Override
    protected int getContentLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        final RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        final RefreshLayout refreshLayout = root.findViewById(R.id.refreshLayout);

        mAdapter = new QuickAdapter();
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);
        final List<Movie> movies = new Gson().fromJson(JSON_MOVIES, new TypeToken<ArrayList<Movie>>() {}.getType());
        mAdapter.replaceData(movies);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mAdapter.getItemCount() < 2) {
                            List<Movie> movies = new Gson().fromJson(JSON_MOVIES, new TypeToken<ArrayList<Movie>>() {}.getType());
                            mAdapter.replaceData(movies);
                        }
                        refreshLayout.finishRefresh();
                    }
                },2000);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mAdapter.addData(movies);
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
        });

        //添加Header
        View header = LayoutInflater.from(getContext()).inflate(R.layout.listitem_mine_course_header, recyclerView, false);
        Banner banner = (Banner) header;
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(BANNER_ITEMS);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });
        banner.start();
        mAdapter.addHeaderView(banner);
        mAdapter.openLoadAnimation();
    }

    public class QuickAdapter extends BaseQuickAdapter<Movie, BaseViewHolder> {
        public QuickAdapter() {
            super(R.layout.listitem_course_item);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, Movie item) {
            viewHolder.setText(R.id.mci_title, item.filmName)
                    .setTextColor(R.id.mci_title, Color.parseColor(item.color))
                    .setText(R.id.mci_time, item.actors)
                    .setText(R.id.mci_grade, item.grade)
                    .setText(R.id.mci_seat, "校区："+item.shortinfo)
                    .setText(R.id.course_detail, item.course_detail)
                    .setTextColor(R.id.course_detail , Color.parseColor(item.color));
            Glide.with(mContext).load(item.picaddr).into((ImageView) viewHolder.getView(R.id.lmi_avatar));
            Glide.with(mContext).load(item.coursetag).into((ImageView) viewHolder.getView(R.id.course_tag));
        }
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            imageView.setImageResource(((BannerItem) path).pic);
        }
    }

    public static class Movie {
        public String color;
        public String actors;
        public String filmName;
        public String grade;
        public String info;
        public String picaddr;
        public String shortinfo;
        public String coursetag;
        public String course_detail;
    }

    public static class BannerItem {

        public int pic;
        public String title;

        public BannerItem() {
        }

        public BannerItem(String title, int pic) {
            this.pic = pic;
            this.title = title;
        }
    }

    public static List<BannerItem> BANNER_ITEMS = new ArrayList<BannerItem>(){{
        add(new BannerItem("PHP重磅升级", R.drawable.banner_php));
        add(new BannerItem("人工智能+Python", R.drawable.banner_python));
        add(new BannerItem("高薪得Linux运维工程师", R.drawable.banner_linux));
    }};


    public static String JSON_MOVIES = "[" +
            "{\"color\":\"#3e7cc3\",\"actors\":\"开班时间：2018-07-01\",\"filmName\":\"JavaEE\",\"grade\":\"就业班\",\"picaddr\":\"https://itacker.oss-cn-beijing.aliyuncs.com/course_icon/java_icon.jpg\",\"course_detail\":\"世界第一编程语言我们的第一学科\",\"shortinfo\":\"北京昌平校区\",\"type\":\"剧情|喜剧\",\"coursetag\":\"https://itacker.oss-cn-beijing.aliyuncs.com/course_icon/technologyad_java.jpg\"}," +
            "{\"color\":\"#77588d\",\"actors\":\"开班时间：2018-07-01\",\"filmName\":\"人工智能+Python\",\"grade\":\"就业班\",\"picaddr\":\"https://itacker.oss-cn-beijing.aliyuncs.com/course_icon/python_icon.jpg\",\"course_detail\":\"人工智能+Python人工智能主流语言\",\"shortinfo\":\"北京昌平校区\",\"type\":\"剧情|爱情|奇幻\",\"coursetag\":\"https://itacker.oss-cn-beijing.aliyuncs.com/course_icon/technologyad_python.jpg\"}," +
            "{\"color\":\"#b15159\",\"actors\":\"开班时间：2018-07-01\",\"filmName\":\"前端与移动开发\",\"grade\":\"就业班\",\"picaddr\":\"https://itacker.oss-cn-beijing.aliyuncs.com/course_icon/web_icon.jpg\",\"course_detail\":\"大前端改变世界我们改变前端\",\"shortinfo\":\"北京昌平校区\",\"type\":\"剧情|喜剧\",\"coursetag\":\"https://itacker.oss-cn-beijing.aliyuncs.com/course_icon/technologyad_web.jpg\"}," +
            "{\"color\":\"#e03b5b\",\"actors\":\"开班时间：2018-07-01\",\"filmName\":\"UI/UE设计\",\"grade\":\"就业班\",\"picaddr\":\"https://itacker.oss-cn-beijing.aliyuncs.com/course_icon/ui_icon.jpg\",\"course_detail\":\"打造会代码的全能设计师\",\"shortinfo\":\"重庆南岸校区\",\"type\":\"动作|动画|战争|冒险\",\"coursetag\":\"https://itacker.oss-cn-beijing.aliyuncs.com/course_icon/technologyad_ui.jpg\"}," +
            "{\"color\":\"#6e88ce\",\"actors\":\"开班时间：2018-07-01\",\"filmName\":\"PHP\",\"grade\":\"就业班\",\"picaddr\":\"https://itacker.oss-cn-beijing.aliyuncs.com/course_icon/php_icon.jpg\",\"course_detail\":\"打造横跨前端+后端+移动端的全能型人才！\",\"shortinfo\":\"北京昌平校区\",\"type\":\"喜剧|爱情\",\"coursetag\":\"https://itacker.oss-cn-beijing.aliyuncs.com/course_icon/technologyad_php.jpg\"}," +
            "{\"color\":\"#ebb84d\",\"actors\":\"开班时间：2018-07-01\",\"filmName\":\"C/C++与网络攻防\",\"grade\":\"就业班\",\"picaddr\":\"https://itacker.oss-cn-beijing.aliyuncs.com/course_icon/c_icon.jpg\",\"course_detail\":\"C生万物 编程之本 长远IT职业发展的选择\",\"shortinfo\":\"深圳校区\",\"type\":\"恐怖|惊悚\",\"coursetag\":\"https://itacker.oss-cn-beijing.aliyuncs.com/course_icon/technologyad_c.jpg\"}," +
            "{\"color\":\"#4ba396\",\"actors\":\"开班时间：2018-07-01\",\"filmName\":\"云计算大数据\",\"grade\":\"就业班\",\"picaddr\":\"https://itacker.oss-cn-beijing.aliyuncs.com/course_icon/cloud_icon.jpg\",\"course_detail\":\"站在云端操控万千数据\",\"shortinfo\":\"北京昌平校区\",\"type\":\"惊悚|悬疑\",\"coursetag\":\"https://itacker.oss-cn-beijing.aliyuncs.com/course_icon/technologyad_cloud.jpg\"}," +
            "{\"color\":\"#91784d\",\"actors\":\"开班时间：2018-07-01\",\"filmName\":\"H5+全栈工程师\",\"grade\":\"就业班\",\"picaddr\":\"https://itacker.oss-cn-beijing.aliyuncs.com/course_icon/stack_icon.jpg\",\"course_detail\":\"打造精通前端+后端+移动端的精英型全栈工程师\",\"shortinfo\":\"北京昌平校区\",\"type\":\"动作|战争|历史\",\"coursetag\":\"https://itacker.oss-cn-beijing.aliyuncs.com/course_icon/technologyad_stack.jpg\"}," +
            "{\"color\":\"#499bc5\",\"actors\":\"开班时间：2018-07-01\",\"filmName\":\"新媒体运营\",\"grade\":\"就业班\",\"picaddr\":\"https://itacker.oss-cn-beijing.aliyuncs.com/course_icon/xmt_icon.jpg\",\"course_detail\":\"打造会策划、懂产品运营的高端复合型人才\",\"shortinfo\":\"北京昌平校区\",\"type\":\"剧情\",\"coursetag\":\"https://itacker.oss-cn-beijing.aliyuncs.com/course_icon/technologyad_xmt.jpg\"}," +
            "{\"color\":\"#d3442a\",\"actors\":\"开班时间：2018-07-01\",\"filmName\":\"电商运营\",\"grade\":\"就业班\",\"picaddr\":\"https://itacker.oss-cn-beijing.aliyuncs.com/course_icon/ds_icon.jpg\",\"course_detail\":\"学电商运营工作创业两不误\",\"shortinfo\":\"北京昌平校区\",\"type\":\"动画|冒险|奇幻\",\"coursetag\":\"https://itacker.oss-cn-beijing.aliyuncs.com/course_icon/technologyad_ds.jpg\"}," +
            "{\"color\":\"#30babd\",\"actors\":\"开班时间：2018-07-01\",\"filmName\":\"视觉设计\",\"grade\":\"就业班\",\"picaddr\":\"https://itacker.oss-cn-beijing.aliyuncs.com/course_icon/jpui_icon.jpg\",\"course_detail\":\"专业设计 纯干货快速就业机会多\",\"shortinfo\":\"北京昌平校区\",\"type\":\"剧情|运动\",\"coursetag\":\"https://itacker.oss-cn-beijing.aliyuncs.com/course_icon/technologyad_uijp.jpg\"}," +
            "{\"color\":\"#db7b23\",\"actors\":\"开班时间：2018-07-01\",\"filmName\":\"产品经理\",\"grade\":\"就业班\",\"picaddr\":\"https://itacker.oss-cn-beijing.aliyuncs.com/course_icon/pm_icon.jpg\",\"course_detail\":\"产品之父，互联网产品规划师\",\"shortinfo\":\"重庆南岸校区\",\"type\":\"悬疑\",\"coursetag\":\"https://itacker.oss-cn-beijing.aliyuncs.com/course_icon/technologyad_pm.jpg\"}," +
            "{\"color\":\"#3a8dd8\",\"actors\":\"开班时间：2018-07-01\",\"filmName\":\"软件测试\",\"grade\":\"就业班\",\"picaddr\":\"https://itacker.oss-cn-beijing.aliyuncs.com/course_icon/test_icon.jpg\",\"course_detail\":\"软件质量的捍卫者\",\"shortinfo\":\"重庆南岸校区\",\"type\":\"上海浦东校区\",\"coursetag\":\"https://itacker.oss-cn-beijing.aliyuncs.com/course_icon/technologyad_test.jpg\"}," +
            "{\"color\":\"#448bba\",\"actors\":\"开班时间：2018-07-01\",\"filmName\":\"网络安全+运维工程师\",\"grade\":\"就业班\",\"picaddr\":\"https://itacker.oss-cn-beijing.aliyuncs.com/course_icon/linux_icon.jpg\",\"course_detail\":\"打造自动化、智能化大数据时代的IT金领\",\"shortinfo\":\"史诗纪录片 十年再相见\",\"type\":\"纪录片\",\"coursetag\":\"https://itacker.oss-cn-beijing.aliyuncs.com/course_icon/technologyad_linux.jpg\"}," +
            "{\"color\":\"#6d3e94\",\"actors\":\"开班时间：2018-07-01\",\"filmName\":\"智能物联网+区块链\",\"grade\":\"就业班\",\"picaddr\":\"https://itacker.oss-cn-beijing.aliyuncs.com/course_icon/qukuailian.jpg\",\"course_detail\":\"培养厚基础、宽口径、强能力的综合性人才\",\"shortinfo\":\"重庆南岸校区\",\"type\":\"动作|冒险\",\"coursetag\":\"https://itacker.oss-cn-beijing.aliyuncs.com/course_icon/technologyad_wlw.jpg\"}" +
            "]";

}

package com.zcj.youkumenu;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zcj.youkumenu.Util.AnimationUtil;
import com.zcj.youkumenu.Util.DensityUtil;
import com.zcj.youkumenu.adBean.ADInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView home;
    private boolean isShowLevel2 = true;
    private boolean isShowLevel3 = true;
    //是否显示整个菜单
    private boolean isShowMenu = true;
    private RelativeLayout level1, level2, level3;
    private ImageView menu;
    private ViewPager viewPager;

    private List<ADInfo> list = null;
    private String[] infoList = new String[]{
            "巩俐不低俗，我就不低俗",
            "朴树又回来了，再唱经典老歌引百万人同唱",
            "揭秘北京电影如何升级",
            "乐视网TV版大放送",
            "热血屌丝的反击"};
    private TextView imageInfo;
    private LinearLayout dot_layout;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    break;
            }
        }
    };
    private int scrollTime = 0;
    private Timer timer;
    private int scrollState = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        //图片轮播初始化数据
        initData();
        initListener();
        //开启计时器
        startTimer();
    }

    private void startTimer() {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                scrollTime++;
                if (scrollState == 1 || scrollState == 2) {
                    scrollTime = 0;
                } else if (scrollTime == 3 && scrollState == 0) {
                    Message msg = Message.obtain();
                    msg.what = 1;
                    handler.sendMessage(msg);
                    scrollTime = 0;
                }
            }
        };
        timer.schedule(task, 3000, 1000);
    }

    private void upInfo() {
        int currentPage = viewPager.getCurrentItem() % list.size();
        imageInfo.setText(infoList[currentPage]);

        for (int i = 0; i < dot_layout.getChildCount(); i++) {
            dot_layout.getChildAt(i).setEnabled(i == currentPage);
        }
    }

    private void initData() {
        list = new ArrayList<ADInfo>();
        list.add(new ADInfo(R.drawable.a, infoList[0]));
        list.add(new ADInfo(R.drawable.b, infoList[1]));
        list.add(new ADInfo(R.drawable.c, infoList[2]));
        list.add(new ADInfo(R.drawable.d, infoList[3]));
        list.add(new ADInfo(R.drawable.e, infoList[4]));
        viewPager.setAdapter(new MyPagerAdapter());
        //初始化小白点
        initDots();
        //初始化文字和白点位置
        upInfo();
        int centerValue = Integer.MAX_VALUE / 2;
        int value = centerValue % list.size();
        viewPager.setCurrentItem(centerValue - value);
    }

    private void initDots() {
        for (int i = 0; i < list.size(); i++) {
            View view = new View(MainActivity.this);
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(DensityUtil.dip2px(this,8), DensityUtil.dip2px(this,8));
            if (i != 0) {
                params.leftMargin = DensityUtil.dip2px(this,5);
            }
            view.setLayoutParams(params);
            view.setBackgroundResource(R.drawable.dot_background);
            dot_layout.addView(view);
        }
    }

    private void initListener() {
        home.setOnClickListener(this);
        menu.setOnClickListener(this);
        //viewPage添加监听
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //滑动时不断调用
            }

            @Override
            public void onPageSelected(int position) {
                //停止滑动后调用
                upInfo();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //滑动状态改变时调用
                //state==0：空闲状态，没有滑动
                //state==1：正在滑动
                //state==2：滑动完成
                scrollState = state;
            }
        });
    }

    private void initUI() {
        home = (ImageView) findViewById(R.id.iv_home);
        level1 = (RelativeLayout) findViewById(R.id.level1);
        level2 = (RelativeLayout) findViewById(R.id.level2);
        level3 = (RelativeLayout) findViewById(R.id.level3);
        menu = (ImageView) findViewById(R.id.iv_menu);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        imageInfo = (TextView) findViewById(R.id.tv_imageInfo);
        dot_layout = (LinearLayout) findViewById(R.id.dot_layout);
    }

    @Override
    public void onClick(View v) {
        if (AnimationUtil.AnimationCount != 0) {
            return;
        }
        switch (v.getId()) {
            case R.id.iv_home:
                if (isShowLevel2) {
                    //当前显示，需要隐藏
                    int startOffset = 0;
                    if (isShowLevel3) {
                        AnimationUtil.closeMenu(level3, startOffset);
                        startOffset = +200;
                        isShowLevel3 = false;
                    }
                    AnimationUtil.closeMenu(level2, startOffset);
                } else {
                    //当前隐藏，需要显示
                    AnimationUtil.openMenu(level2, 0);
                }
                isShowLevel2 = !isShowLevel2;
                break;
            case R.id.iv_menu:
                if (isShowLevel3) {
                    AnimationUtil.closeMenu(level3, 0);
                } else {
                    AnimationUtil.openMenu(level3, 0);
                }
                isShowLevel3 = !isShowLevel3;
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (AnimationUtil.AnimationCount != 0) {
                return true;
            }
            if (isShowMenu) {
                //当前显示，关闭所有菜单
                int startOffset = 0;
                if (isShowLevel3) {
                    AnimationUtil.closeMenu(level3, startOffset);
                    startOffset += 200;
                    isShowLevel3 = false;
                }
                if (isShowLevel2) {
                    AnimationUtil.closeMenu(level2, startOffset);
                    startOffset += 200;
                    isShowLevel2 = false;
                }
                AnimationUtil.closeMenu(level1, startOffset);
            } else {
                int startOffset = 0;
                AnimationUtil.openMenu(level1, startOffset);
                startOffset += 200;
                AnimationUtil.openMenu(level2, startOffset);
                isShowLevel2 = true;
                startOffset += 200;
                AnimationUtil.openMenu(level3, startOffset);
                isShowLevel3 = true;
            }
            isShowMenu = !isShowMenu;
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * viewPage适配器
     */
    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        /**
         * 返回值用于 ：判断key与view是否对应
         * 返回 false：调用instantiateItem创建新view
         * 返回 true： 复用缓存的view
         *
         * @param view   当前滑动的view
         * @param object 将要进入的view
         * @return
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        /**
         * 销毁page
         *
         * @param container 根view
         * @param position  要销毁的page的position
         * @param object    销毁的对象
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

        /**
         * 类似listView的adapter中getView
         * 返回key
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = View.inflate(MainActivity.this, R.layout.item_viewpage, null);
            ImageView image = (ImageView) itemView.findViewById(R.id.iv_image);
            image.setImageResource(list.get(position % list.size()).getIconResId());
            container.addView(itemView);
            return itemView;
        }
    }

    @Override
    protected void onDestroy() {
        timer.cancel();
        timer = null;
        super.onDestroy();
    }
}

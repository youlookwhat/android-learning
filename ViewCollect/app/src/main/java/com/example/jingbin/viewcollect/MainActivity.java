package com.example.jingbin.viewcollect;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.jingbin.viewcollect.databinding.ActivityMainBinding;
import com.example.jingbin.viewcollect.ui.CountTimeViewActivity;
import com.example.jingbin.viewcollect.ui.CustomBehaviorActivity;
import com.example.jingbin.viewcollect.ui.ExpandTextActivity;
import com.example.jingbin.viewcollect.ui.ExpandableViewActivity;
import com.example.jingbin.viewcollect.ui.FlipperActivity;
import com.example.jingbin.viewcollect.ui.LabelEditActivity;
import com.example.jingbin.viewcollect.ui.NumberAddViewActivity;
import com.example.jingbin.viewcollect.ui.ProductDetailActivity;
import com.example.jingbin.viewcollect.ui.WordWrapViewActivity;
import com.example.jingbin.viewcollect.utils.ToastUtils;
import com.kaws.lib.exoplayer.PlayerActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(v.getContext(), "点击Snackbar", Toast.LENGTH_LONG).show();
                            }
                        }).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initListener();
    }


    private void initListener() {
        binding.include.inContentMain.tvProductDetail.setOnClickListener(this);
        binding.include.inContentMain.tvAddNum.setOnClickListener(this);
        binding.include.inContentMain.tvPlayer.setOnClickListener(this);
        binding.include.inContentMain.tvTag.setOnClickListener(this);
        binding.include.inContentMain.tvExpand.setOnClickListener(this);
        binding.include.inContentMain.tvTime.setOnClickListener(this);
        binding.include.inContentMain.tvLab.setOnClickListener(this);
        binding.include.inContentMain.tvFlipper.setOnClickListener(this);
        binding.include.inContentMain.tvExpandText.setOnClickListener(this);
        binding.include.inContentMain.tvBehavior.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_product_detail:// 上拉查看商品图文详情
                ProductDetailActivity.start(MainActivity.this);
                break;
            case R.id.tv_add_num:// 加减商品数量
                NumberAddViewActivity.start(MainActivity.this);
                break;
            case R.id.tv_player:// exoPlayer播放视频
                String videoUrl = "http://7xvh0u.com2.z0.glb.qiniucdn.com/pe-3gptest-2_30.3gp";
                PlayerActivity.play(view.getContext(), "测试标题", videoUrl);
                break;
            case R.id.tv_tag:// 自动换行标签
                WordWrapViewActivity.start(MainActivity.this);
                break;
            case R.id.tv_expand: // 点击展开收起布局
                ExpandableViewActivity.start(MainActivity.this);
                break;
            case R.id.tv_time: // 倒计时控件
                CountTimeViewActivity.start(MainActivity.this);
                break;
            case R.id.tv_lab: // 可拖动的RecyclerView
                LabelEditActivity.start(MainActivity.this);
                break;
            case R.id.tv_flipper: // Flipper滚动条
                FlipperActivity.start(MainActivity.this);
                break;
            case R.id.tv_expand_text: // 文字展开收起(列表适用)
                ExpandTextActivity.start(MainActivity.this);
                break;
            case R.id.tv_behavior:// 自定义behavior
                CustomBehaviorActivity.start(MainActivity.this);
                break;
            default:
                break;
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

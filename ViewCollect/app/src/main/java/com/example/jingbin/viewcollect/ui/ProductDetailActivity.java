package com.example.jingbin.viewcollect.ui;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.jingbin.viewcollect.R;
import com.example.jingbin.viewcollect.databinding.ActivityProductDetailBinding;
import com.example.jingbin.viewcollect.slideview.SlidingMenu;

public class ProductDetailActivity extends AppCompatActivity {


    private ActivityProductDetailBinding binding;
    private SlidingMenu.CallBack_toggleRefreshUi callBackToggleRefreshUi;
    // 是否加载了详情页
    private boolean isLoadWebView = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail);

        setTitle("商品详情");
        initStatus();
    }


    private void loadWebView() {
//        String url = BuildConfig.BASE_V4_HTTPS_URL + "/mall/products/" + productId + "/" + "description" + ".html";
//        DebugUtil.error("------url:"+url);
//        Map<String, String> extraHeaders = new HashMap<String, String>();
//        extraHeaders.put("a", HttpUtils.getInstance().getHeaderStr("GET"));
//        UserUtils userUtils = new UserUtils(activity, new SQuser(activity).selectKey());
//        String token = userUtils.getToken();
//        extraHeaders.put("token", token);
        String url = "https://github.com/youlookwhat";
        binding.ysnowswebview.loadUrl(url);
    }

    /**
     * 如此控件在fragment里，则在对应activity调用此函数可以在对应的页面做相关的处理
     */
    public void fun(final SlidingMenu.CallBack_toggleRefreshUi callBackToggleRefreshUi) {
        this.callBackToggleRefreshUi = callBackToggleRefreshUi;
    }

    /**
     * 加载详情页及设置状态
     */
    private void initStatus() {
        initWebView();
//        loadWebView();//上拉才加载
        binding.ivProductArrow.setImageResource(R.drawable.ic_arrow_up);
        binding.tvProductExplain.setText("上拉查看图文详情");

        binding.expandedMenu.setCallBack_addRefreshUi(new SlidingMenu.CallBack_toggleRefreshUi() {
            @Override
            public void secondPager() {
                binding.tvProductExplain.setText("下拉收起图文详情");
                setTitle("图文详情");
                binding.ivProductArrow.setImageResource(R.drawable.ic_arrow_down);
                if (callBackToggleRefreshUi != null) {
                    callBackToggleRefreshUi.secondPager();
                }
                if (!isLoadWebView) {
                    loadWebView();
                    isLoadWebView = true;
                }
            }

            @Override
            public void firstPager() {
                binding.tvProductExplain.setText("上拉查看图文详情");
                setTitle("商品详情");
                binding.ivProductArrow.setImageResource(R.drawable.ic_arrow_up);
                if (callBackToggleRefreshUi != null) {
                    callBackToggleRefreshUi.firstPager();
                }
            }
        });
    }

    /**
     * 回到顶端所要做的处理
     */
    public void backToTop() {
        // 三块布局都回到顶端
        binding.expandedMenu.moveToTop();
        // 上拉提示文字图标等状态改变
        binding.tvProductExplain.setText("上拉查看图文详情");
        binding.ivProductArrow.setImageResource(R.drawable.ic_arrow_up);
    }


    private void initWebView() {
        WebSettings ws = binding.ysnowswebview.getSettings();
        ws.setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
        ws.setSavePassword(true);
        ws.setSaveFormData(true);// 保存表单数据
//        ws.setBuiltInZoomControls(false);// 隐藏缩放按钮
        ws.setSupportZoom(true);
        // 双击缩放
        ws.setBuiltInZoomControls(true);
        ws.setDisplayZoomControls(false);

        //设置缓存模式
        ws.setAppCacheEnabled(true);
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);//设置缓存模式
        // 设置此属性，可任意比例缩放。
        ws.setUseWideViewPort(true);
        //缩放比例 1
        binding.ysnowswebview.setInitialScale(1);
//        ws.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        //---------------------
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);// 排版适应屏幕

        ws.setSupportMultipleWindows(true);// 新加
//        MyWebChromeClient xwebchromeclient = new MyWebChromeClient();
//        webview_list.setWebChromeClient(xwebchromeclient);
//        webview_list.addJavascriptInterface(new VideoAndImageClickInterface(mContext), "injectedObject");
        binding.ysnowswebview.setWebViewClient(new MyWebViewClient());
    }


    public class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("http://v.youku.com/")) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.addCategory("android.intent.category.BROWSABLE");
                Uri content_url = Uri.parse(url);
                intent.setData(content_url);
                startActivity(intent);
                return true;
            } else {
                view.loadUrl(url);
            }
            return false;
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            // html加载完成之后，添加监听图片的点击js函数
            //  stopProgressDialog();
            // mProgressBar.setVisibility(View.GONE);
//            addImageClickListener();
//            view.loadUrl("javascript:window.android.test();");
            super.onPageFinished(view, url);
        }

        //        webView默认是不处理https请求的，页面显示空白，需要进行如下设置：
//         onReceivedSslError为webView处理ssl证书设置
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
            super.onReceivedSslError(view, handler, error);
        }
    }


    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, ProductDetailActivity.class);
        mContext.startActivity(intent);
    }
}

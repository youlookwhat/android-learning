package com.alibaba.sdk.android.demo.mac;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.sdk.android.mac.MACService;
import com.alibaba.sdk.android.mac.MACServiceProvider;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class BasicTask extends AsyncTask<Void, Long, String> {

    private Button btnMAC, btnNative;
    private TextView textView;
    private ProgressBar bar;
    private boolean isMAC;
    private int success = 0, failure = 0;
    private MACService macService;

    private static final String UNSET_RT = "- ms";
    private static final String[] MAC_TEST_URL = {"http://macimg0bm.ams.aliyuncs.com/static_file/20k", "http://macimg1bm.ams.aliyuncs.com/static_file/20k"};
    private static final String[] TEST_URL = {"http://img0bm.ams.aliyuncs.com/static_file/20k", "http://img1bm.ams.aliyuncs.com/static_file/20k"};
    private static final String MAC_TEST_API_URL = "http://macapibm.ams.aliyuncs.com/api_request";
    private static final String TEST_API_URL = "http://apibm.ams.aliyuncs.com/api_request";
    private static final String TAG = "MAC_BENCHMARK";
    private static final int BENCHMARK_TOTAL_REQUESTS = 200;
    private static final int BENCHMARK_CONCURRENT_REQUESTS = 8;
    private static final int BENCHMARK_SLEEP_INTERVAL_MS = 10 * 1000;
    private static final Random random = new Random();

    private static Context context;


    // 示例代码Section II
    // 本Section给出了一个基本的TestBench, 对比MAC与原生网络库之间的性能表现
    // TestBench总共包含200次请求, 这200次请求将会被打散为若干“堆”, 每一“堆”请求包含一次API请求以及若干次20K图片获取请求(请求次数为1-8的随机值)
    // 每一“堆”的请求相互间隔若干秒(间隔值为1-10的随机值), 以模拟用户常规的点击行为
    // 分别点击“Use Native”和“Use MAC”, 以测试使用原生网络和加速网络访问相同资源的延迟情况
    // 为了体现弱网环境下的加速效果, 测试URL为短连接接口, 并配置了固定的200ms延迟模拟弱网环境下的访问
    // 如果您需要替换测试URL进行测试, 我们建议您在2G或3G等弱网环境下进行测试, 强网环境下加速效果可能并不明显
    // 由于线下测试环境比较固定, 我们强烈建议您通过线上大样本的灰度测试数据科学评估整体加速效果
    //////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void setContext(Context context) {
        BasicTask.context = context;
    }

    public BasicTask(Button btnMAC, Button btnNative, TextView textView, ProgressBar bar, boolean isMAC) {
        super();
        this.btnMAC = btnMAC;
        this.btnNative = btnNative;
        this.textView = textView;
        this.bar = bar;
        this.isMAC = isMAC;
        this.macService = MACServiceProvider.getService(context);
        // 由于native网络库不打印log, 为保持公平, 关闭移动加速的log
        macService.setLogEnabled(false);
    }

    private String GetViewText(String rt, int success, int failure) {
        return "Rt: " + rt
                + "\nSuccess: " + success
                + "\nFailure: " + failure
                + "\nTotal: " + BENCHMARK_TOTAL_REQUESTS;
    }

    @Override
    protected String doInBackground(Void... params) {
        long res;
        try {
            if (isMAC) {
                res = randomGetRequestWithMAC();
            } else {
                res = randomGetRequestWithNative();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return UNSET_RT;
        }
        return res + " ms";
    }

    @Override
    protected void onPreExecute() {
        textView.setText(GetViewText(UNSET_RT, 0, 0));
        btnMAC.setEnabled(false);
        btnNative.setEnabled(false);
        bar.setMax(BENCHMARK_TOTAL_REQUESTS);
        bar.setProgress(0);
        bar.setEnabled(true);
    }

    @Override
    protected void onPostExecute(String result) {
        textView.setText(GetViewText(result, this.success, this.failure));
        btnMAC.setEnabled(true);
        btnNative.setEnabled(true);
        bar.setProgress(this.success + this.failure);
    }

    @Override
    protected void onProgressUpdate(Long... values) {
        long rt = values[0];
        textView.setText(GetViewText(rt + " ms", this.success, this.failure));
        bar.setProgress(this.success + this.failure);
    }

    public long randomGetRequestWithMAC() throws Exception {

        int requestCount = BENCHMARK_TOTAL_REQUESTS;
        final AtomicLong rtAverage = new AtomicLong(0);
        final AtomicLong rtSum = new AtomicLong(0);
        final AtomicInteger successCount = new AtomicInteger(0);
        final AtomicInteger failureCount = new AtomicInteger(0);
        final CountDownLatch latch = new CountDownLatch(requestCount);

        for (int i = 0; i < requestCount; /* void */) {
            int concurrentRequest = random.nextInt(BENCHMARK_CONCURRENT_REQUESTS) + 1;
            if (concurrentRequest > requestCount - i) {
                concurrentRequest = requestCount - i;
            }
            i += concurrentRequest;

            for (int j = 0; j < concurrentRequest; j++) {
                final boolean isApiUrl = (j == concurrentRequest - 1);
                Log.i(TAG, "starting thread " + (i - j));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        byte[] buff = new byte[4096];
                        String url = (isApiUrl) ? MAC_TEST_API_URL : MAC_TEST_URL[random.nextInt(2)];

                        long startTime = System.currentTimeMillis();
                        long endTime;

                        try {
                            HttpURLConnection conn = macService.open(new URL(url));
                            int responseCode = conn.getResponseCode();
                            if (responseCode == 200) {
                                InputStream in = conn.getInputStream();
                                DataInputStream dis = new DataInputStream(in);
                                int len = 0;
                                StringBuilder sb = new StringBuilder();
                                while ((len = dis.read(buff)) != -1) {
                                    sb.append(new String(buff, 0, len));
                                }
                                endTime = System.currentTimeMillis();
                                Log.d(TAG, "[get] - " + sb.length() + " bytes. Comsumed " + (endTime - startTime) + "ms");
                                rtSum.addAndGet(endTime - startTime);
                                successCount.incrementAndGet();
                                rtAverage.set(rtSum.get() / successCount.get());
                            } else {
                                failureCount.incrementAndGet();
                            }
                        } catch (IOException e) {
                            failureCount.incrementAndGet();
                            e.printStackTrace();
                        }
                        latch.countDown();
                    }
                }).start();
            }
            Thread.sleep((random.nextInt(BENCHMARK_SLEEP_INTERVAL_MS) + 1));
            this.success = successCount.get();
            this.failure = failureCount.get();
            publishProgress(rtAverage.get());
        }
        latch.await();
        this.success = successCount.get();
        this.failure = failureCount.get();
        publishProgress(rtAverage.get());
        Log.i(TAG, "MAC average rt " + rtAverage + " ms");
        Log.i(TAG, "MAC " + this.success + " success, " + this.failure + " failure");
        return rtAverage.get();
    }

    public long randomGetRequestWithNative() throws Exception {

        int requestCount = BENCHMARK_TOTAL_REQUESTS;
        final AtomicLong rtAverage = new AtomicLong(0);
        final AtomicLong rtSum = new AtomicLong(0);
        final AtomicInteger successCount = new AtomicInteger(0);
        final AtomicInteger failureCount = new AtomicInteger(0);
        final CountDownLatch latch = new CountDownLatch(requestCount);

        for (int i = 0; i < requestCount; /* void */) {
            int concurrentRequest = random.nextInt(BENCHMARK_CONCURRENT_REQUESTS) + 1;
            if (concurrentRequest > requestCount - i) {
                concurrentRequest = requestCount - i;
            }
            i += concurrentRequest;
            for (int j = 0; j < concurrentRequest; j++) {
                final boolean isApiUrl = (j == concurrentRequest - 1);
                Log.i(TAG, "starting thread " + (i - j));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        byte[] buff = new byte[4096];
                        String url = (isApiUrl) ? TEST_API_URL : TEST_URL[random.nextInt(2)];

                        long startTime = System.currentTimeMillis();
                        long endTime;

                        try {
                            HttpURLConnection conn = (HttpURLConnection) (new URL(url)).openConnection();
                            int responseCode = conn.getResponseCode();
                            if (responseCode == 200) {
                                InputStream in = conn.getInputStream();
                                DataInputStream dis = new DataInputStream(in);
                                int len = 0;
                                StringBuilder sb = new StringBuilder();
                                while ((len = dis.read(buff)) != -1) {
                                    sb.append(new String(buff, 0, len));
                                }
                                endTime = System.currentTimeMillis();
                                Log.d(TAG, "[get] - " + sb.length() + " bytes. Comsumed " + (endTime - startTime) + "ms");
                                rtSum.addAndGet(endTime - startTime);
                                successCount.incrementAndGet();
                                rtAverage.set(rtSum.get() / successCount.get());
                            } else {
                                failureCount.incrementAndGet();
                            }
                        } catch (IOException e) {
                            failureCount.incrementAndGet();
                            e.printStackTrace();
                        }
                        latch.countDown();
                    }
                }).start();
            }
            Thread.sleep((random.nextInt(BENCHMARK_SLEEP_INTERVAL_MS) + 1));
            this.success = successCount.get();
            this.failure = failureCount.get();
            publishProgress(rtAverage.get());
        }
        latch.await();
        this.success = successCount.get();
        this.failure = failureCount.get();
        publishProgress(rtAverage.get());
        Log.i(TAG, "Native average rt " + rtAverage + " ms");
        Log.i(TAG, "Native " + this.success + " success, " + this.failure + " failure");
        return rtAverage.get();
    }
}

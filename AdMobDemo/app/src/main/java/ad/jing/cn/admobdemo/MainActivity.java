package ad.jing.cn.admobdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the Mobile Ads SDK.
//        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

        // 我们应用的
        MobileAds.initialize(this, "ca-app-pub-9895194124287488~1553211240");

        // Gets the ad view defined in layout/ad_fragment.xml with ad unit ID set in
        // values/strings.xml.
        adView = findViewById(R.id.ad_view);

        // Create an ad request. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        // Start loading the ad in the background.
        adView.loadAd(adRequest);

//        AdLoader adLoader = new AdLoader.Builder(this, "/6499/example/native")
//                .forCustomTemplateAd("ca-app-pub-3940256099942544~3347511713",
//                        new NativeCustomTemplateAd.OnCustomTemplateAdLoadedListener() {
//                            @Override
//                            public void onCustomTemplateAdLoaded(NativeCustomTemplateAd nativeCustomTemplateAd) {
//                                nativeCustomTemplateAd.recordImpression();
//                            }
//                            // Display the ad.
//                        },
//                        new NativeCustomTemplateAd.OnCustomClickListener() {
//                            @Override
//                            public void onCustomClick(NativeCustomTemplateAd ad, String assetName) {
//                                Log.i("MyApp", "A custom click just happened for " + assetName + "!");
//                            }
//                        }).build();
//
//        adLoader.loadAd(adRequest);
    }


    /**
     * Called when leaving the activity
     */
    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    /**
     * Called when returning to the activity
     */
    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    /**
     * Called before the activity is destroyed
     */
    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}

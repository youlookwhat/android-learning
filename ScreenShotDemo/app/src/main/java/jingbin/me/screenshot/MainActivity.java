package jingbin.me.screenshot;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

/**
 * 注意：
 * 1、加权限，不然不能成功
 * 2、权限同意后退出应用再次进入截取才有效
 * 参考：
 * 直接截图不加水印：https://github.com/langjun/Screenshot
 * 点击截图带水印：https://blog.csdn.net/qq_25193681/article/details/52218175
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String PATH = Environment.getExternalStorageDirectory() + "/DCIM/Screenshots/";

    private FileChangedObserver mFileChangedObserver;
    private FileChangedObserver.IOnFileChangedListener mFileChangedListener;

    private ImageView mImageView;
    private Handler mHandler;
    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        utils = new Utils(this);
        mImageView = findViewById(R.id.imageView);

        mFileChangedObserver.startWatching();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFileChangedObserver.stopWatching();
    }

    private void init() {
        mHandler = new Handler();

        mFileChangedObserver = new FileChangedObserver(PATH, FileObserver.CLOSE_WRITE);
        mFileChangedListener = new FileChangedObserver.IOnFileChangedListener() {
            @Override
            public void onFileChanged(final Uri uri) {
                //  /storage/emulated/0/DCIM/Screenshots/Screenshot_20180831-115048_Screenshot.jpg
                Log.e("TAG", "onFileChanged Uri = " + uri.getPath());

                Bitmap bitmap = FilePathUtil.decodeUri(MainActivity.this, uri, 1080, 1920);
                bitmap = utils.addTagUtil(bitmap);//返回有图片水印

//				bitmap=utils.drawTextToBitmap(bitmap, "http://blog.csdn.net/qq_25193681");//返回有文字的水印

                utils.saveCroppedImage(MainActivity.this, bitmap);

//				String path = uri.getPath();
//				File file = new File(PATH, path);
//				file.delete();
//				// 通知图库更新
//				Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file));
//				MainActivity.this.sendBroadcast(scannerIntent);

                final Bitmap finalBitmap = bitmap;
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
//						mImageView.setImageURI(uri);
                        mImageView.setImageBitmap(finalBitmap);
                    }
                });
            }
        };
        mFileChangedObserver.setFileChangedListener(mFileChangedListener);
    }
}

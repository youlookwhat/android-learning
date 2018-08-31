package jingbin.me.screenshot;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Utils {
    private Context mContext;

    Utils(Context mContext) {
        this.mContext = mContext;
    }

    private static String newFilePath = "";


    /**
     * 定义动画
     *
     * @param mBg    父级容器
     * @param mImage 将要显示的图片控件
     * @param bitmap 图片
     */
    public void startAnim(final LinearLayout mBg, final ImageView mImage, final Bitmap bitmap) {
        //设置为半透明
        mBg.setBackgroundColor(Color.parseColor("#e0000000"));

        PropertyValuesHolder values1 = PropertyValuesHolder.ofFloat("alpha", 0f, 1f, 0.5f);
        PropertyValuesHolder values2 = PropertyValuesHolder.ofFloat("scaleX", 1, 3 / 4f);
        PropertyValuesHolder values3 = PropertyValuesHolder.ofFloat("scaleY", 1, 3 / 4f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(mImage, values1, values2, values3);
        animator.setDuration(1000);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();

        animator.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator arg0) {

            }

            @Override
            public void onAnimationRepeat(Animator arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator arg0) {
                mImage.setVisibility(View.INVISIBLE);
                //重置背景
                mBg.setBackgroundColor(Color.parseColor("#00000000"));

            }

            @Override
            public void onAnimationCancel(Animator arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    /**
     * 把布局转换成bitmap
     *
     * @param scrollView
     * @return bitmap
     */
    public Bitmap getBitmapByView(ScrollView scrollView) {
        Bitmap bitmap = null;

        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), scrollView.getHeight(),
                Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        // 测试输出
        FileOutputStream out = null;
        try {

            out = new FileOutputStream("/sdcard/screen_test.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (null != out) {
                bitmap.compress(CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 保存图片至/sdcard/myFolder文件夹下
     *
     * @param bmp
     */
    void saveCroppedImage(Context context, Bitmap bmp) {
        File file = new File("/sdcard/myFolder");
        if (!file.exists()) {
            file.mkdir();
        }
        long time = System.currentTimeMillis();

        file = new File("/sdcard/" + time + ".jpg".trim());
        String fileName = file.getName();
        String mName = fileName.substring(0, fileName.lastIndexOf("."));
        String sName = fileName.substring(fileName.lastIndexOf("."));

        newFilePath = "/sdcard/myFolder" + "/" + mName + sName;
        file = new File(newFilePath);
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(CompressFormat.JPEG, 50, fos);
            fos.flush();
            fos.close();

            Uri uri = Uri.fromFile(file);
            // 通知图库更新
            Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
            context.sendBroadcast(scannerIntent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 有水印的保存
     *
     * @param photo 当前截图的bitmap
     * @param mark  水印的图片
     * @param image 控件
     * @return
     */
    Bitmap addTagUtil(Bitmap photo) {
        Bitmap photoMark = Bitmap.createBitmap(photo.getWidth(), photo.getHeight(), Config.ARGB_8888);
        Bitmap mark = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
        Canvas canvas = new Canvas(photoMark);
        canvas.drawBitmap(photo, 0, 0, null);
        Bitmap bitmapMark = mark.copy(Config.ARGB_8888, true);
        canvas.drawBitmap(bitmapMark, photo.getWidth() - bitmapMark.getWidth() - 10, photo.getHeight() - bitmapMark.getHeight(), null);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return photoMark;
    }

    /**
     * 添加文字到图片，类似水印文字。
     *
     * @param gContext
     * @param gResId
     * @param gText
     * @return
     */
    public Bitmap drawTextToBitmap(Bitmap bitmap, String gText) {
        Resources resources = mContext.getResources();
        float scale = resources.getDisplayMetrics().density;

        Config bitmapConfig = bitmap.getConfig();
        if (bitmapConfig == null) {
            bitmapConfig = Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#458EFD"));
        paint.setTextSize((int) (3 * scale * 5));
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);
        Rect bounds = new Rect();
        paint.getTextBounds(gText, 0, gText.length(), bounds);
        int x = (bitmap.getWidth() - bounds.width()) / 10 * 9;
        int y = (bitmap.getHeight() + bounds.height()) / 10 * 9;
        canvas.drawText(gText, x, y, paint);

        return bitmap;
    }

}

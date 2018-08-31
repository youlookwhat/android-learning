package jingbin.me.screenshot;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ClickShotActivity extends Activity {
	
	private TextView button;
	
	private ScrollView mScrollView;
	
	private ImageView mImage;
	
	private LinearLayout mBg;

	private Utils utils;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_click_shot);
		utils=new Utils(ClickShotActivity.this);
		initView();
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				
				Bitmap bitmap = utils.getBitmapByView(mScrollView);//这里返回的是没有水印的图片
				mImage.setVisibility(View.VISIBLE);
			
				utils.startAnim(mBg,mImage,bitmap);
				
				bitmap=	utils.addTagUtil(bitmap);//返回有图片水印
				
//				bitmap=utils.drawTextToBitmap(bitmap, "http://blog.csdn.net/qq_25193681");//返回有文字的水印
				
				utils.saveCroppedImage(ClickShotActivity.this,bitmap);
				
				mImage.setImageBitmap(bitmap);
				
				mImage.setVisibility(View.VISIBLE);


				
			}
		});
		
	}


	private void initView() {
		mScrollView=(ScrollView) findViewById(R.id.mScrollview);
		button=(TextView) findViewById(R.id.save);
		mBg=(LinearLayout) findViewById(R.id.image_bg);
		mImage=(ImageView) findViewById(R.id.jietu_image);
	}

}


package com.hzqianxun.my_thread_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;

import com.hzqianxun.my_thread_test.ui.MainActivity;

//import net.oschina.gitapp.common.UIHelper;

/**
 * app的欢迎界面
 * @created 2014-07-22
 * @author deyi（http://my.oschina.net/LittleDY）
 *
 */
public class WelcomePage extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout view = (LinearLayout) getLayoutInflater().inflate(R.layout.app_welcome_page, null);
		setContentView(view);
        
		//渐变展示启动屏
		AlphaAnimation aa = new AlphaAnimation(0.3f,1.0f);
		aa.setDuration(3000);
		view.startAnimation(aa);
		aa.setAnimationListener(new AnimationListener()
		{
			@Override
			public void onAnimationEnd(Animation arg0) {
				finish();
				Log.d("finish", "finish");

				Intent intent = new Intent(AppContext.getInstance(), MainActivity.class);
				startActivity(intent);
//				UIHelper.goMainActivity(WelcomePage.this);
			}
			@Override
			public void onAnimationRepeat(Animation animation) {}
			@Override
			public void onAnimationStart(Animation animation) {}
			
		});
	}
}

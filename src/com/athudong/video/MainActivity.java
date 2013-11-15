package com.athudong.video;

import com.athudong.video.component.SelectAnim;
import com.athudong.video.task.BaseTask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class MainActivity extends BaseActivity {

	private View oneHead;
	private View twoHead;
	private View threeHead;
	private View fourHead;

	private SelectAnim anim;

	@Override
	protected void initView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);
		oneHead = findViewById(R.id.oneHead);
		twoHead = findViewById(R.id.twoHead);
		threeHead = findViewById(R.id.threeHead);
		fourHead = findViewById(R.id.fourHead);

		findViewById(R.id.main_select_btn_03).setOnClickListener(this);

		findViewById(R.id.thumbBtn).setOnClickListener(this);
		findViewById(R.id.headLayout).setOnTouchListener(new HeadTouchListener());

		anim = new SelectAnim(oneHead, twoHead, threeHead, fourHead);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.main_select_btn_03) {
			anim.next();
		} else if (id == R.id.thumbBtn) {
			toast("你好");
		}
	}

	@Override
	protected void beforeEveryVisable() {

	}

	@Override
	protected void afterEveryInVisable() {

	}

	@Override
	protected void beforeDestory() {

	}

	@Override
	public void executeTaskResult(BaseTask task, Object data) {

	}

	// 重写Activity中onKeyDown（）方法
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {// 当keyCode等于退出事件值时
			ToQuitTheApp();
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	private boolean isExit = false;

	// 封装ToQuitTheApp方法
	private void ToQuitTheApp() {
		if (isExit) {
			// ACTION_MAIN with category CATEGORY_HOME 启动主屏幕
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			startActivity(intent);

			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					MainActivity.this.finish();
					System.exit(0);// 使虚拟机停止运行并退出程序
				}
			}, 1500);
		} else {
			isExit = true;
			toast("再按一次退出");
			mHandler.sendEmptyMessageDelayed(0, 3000);// 3秒后发送消息
		}
	}

	// 创建Handler对象，用来处理消息
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {// 处理消息
			super.handleMessage(msg);
			isExit = false;
		}
	};

	private class HeadTouchListener implements OnTouchListener {
		float x_temp01 = 0.0f;
		float y_temp01 = 0.0f;
		float x_temp02 = 0.0f;
		float y_temp02 = 0.0f;

		@Override
		public boolean onTouch(View view, MotionEvent event) {
			// 获得当前坐标
			float x = event.getX();
			float y = event.getY();

			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				x_temp01 = x;
				y_temp01 = y;
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				x_temp02 = x;
				y_temp02 = y;
				if (x_temp01 != 0 && y_temp01 != 0) {
					// 比较x_temp01和x_temp02
					// x_temp01>x_temp02 //向左
					// x_temp01==x_temp02 //竖直方向或者没动
					// x_temp01<x_temp02 //向右
					// 向左
					if (x_temp01 > x_temp02) {
						// 移动了x_temp01-x_temp02
						anim.next();
					}

					// 向右
					if (x_temp01 < x_temp02) {
						// 移动了x_temp02-x_temp01
						anim.prev();
					}
				}
			} else if (event.getAction() == MotionEvent.ACTION_MOVE) {

			}
			return true;
		}

	}
}

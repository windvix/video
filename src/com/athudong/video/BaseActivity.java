package com.athudong.video;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.EncodingUtils;

import com.athudong.video.task.BaseTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * 公共基类Activity，封装各activity常用到的方法
 */
public abstract class BaseActivity extends Activity implements OnClickListener{
	/**
	 * 当前界面的任务列表
	 */
	private List<BaseTask> taskList = new ArrayList<BaseTask>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		initView(savedInstanceState);
		super.onCreate(savedInstanceState);
	}
	
	public Handler getHandler(){
		return new Handler();
	}

	/**
	 * 初始化视图
	 */
	protected abstract void initView(Bundle savedInstanceState);

	/**
	 * 增加任务
	 */
	public void addTask(BaseTask task) {
		if (task != null) {
			taskList.add(task);
		}
	}

	/**
	 * 清除所有任务
	 */
	public void cleanAllTask() {
		if (taskList != null && taskList.size() > 0) {
			for (BaseTask task : taskList) {
				if (task != null) {
					task.stopTask();
				}
			}
			taskList.clear();
		}
	}

	/**
	 * 用于标识当前界面的可见状态
	 */
	private boolean isVisiable = false;

	private boolean isFinished = false;

	/**
	 * 判断当前界面是否销毁了
	 */
	public boolean isFinished() {
		return isFinished;
	}

	@Override
	protected void onResume() {
		super.onResume();
		isVisiable = true;
	}

	@Override
	protected void onStart() {
		beforeEveryVisable();
		super.onStart();
	}

	/**
	 * 视图在每次可见之前调用
	 */
	protected abstract void beforeEveryVisable();

	/**
	 * 视图在每次不可见之后调用 ，如果有不需要的资源，最好在此方法中将资源释放
	 */
	protected abstract void afterEveryInVisable();

	@Override
	protected void onStop() {
		super.onStop();
		isVisiable = false;
		afterEveryInVisable();
	}

	/**
	 * 判断当前界面是否可见
	 */
	public boolean isVisiable() {
		return isVisiable;
	}


	/**
	 * toast显示text字符串
	 */
	public void toast(String text) {
		if (text == null) {
			text = "";
		}
		Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 输出日志信息
	 */
	public void log(String text) {
		if (text == null) {
			text = "";
		}
		Log.d(this.getClass().getName(), text);
	}

	
	
	/**
	 * 返回动画
	 */
	protected void backAnim() {
		overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
	}

	/**
	 * 获取软件当前的版本号
	 */
	public int getVersionCode() {
		int version = 0;
		PackageManager pm = getPackageManager();
		PackageInfo pi;
		try {
			pi = pm.getPackageInfo(getPackageName(), 0);
			version = pi.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return version;
	}

	/**
	 * 启动activity的动画效果
	 */
	public void startAnim() {
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	}

	/**
	 * 根据资源ID，生成对应的view
	 */
	public View createView(int resourceId) {
		View view = getInflater().inflate(resourceId, null);
		return view;
	}

	/**
	 * 获取inflater
	 */
	private LayoutInflater getInflater() {
		return getLayoutInflater();
	}
	
	/**
	 * 获取标题栏高度
	 */
	public int getTitleBarHeight() {
		return getResources().getDimensionPixelSize(R.dimen.actionbar_height);
	}
	

	/**
	 * 获取屏幕高度
	 */
	@SuppressWarnings("deprecation")
	public int getScreenHeight() {
		
		return getWindowManager().getDefaultDisplay().getHeight();
	}

	/**
	 * 获取屏幕宽度
	 */
	@SuppressWarnings("deprecation")
	public int getScreenWidth() {
		return getWindowManager().getDefaultDisplay().getWidth();
	}

	/**
	 * 获取资源中的字符串
	 */
	public String getResString(int resourceId) {
		return getResources().getString(resourceId);
	}

	// 从assets 文件夹中获取文件并读取数据
	protected String getFromAssets(String fileName) {
		String result = "";
		try {
			InputStream in = getResources().getAssets().open(fileName);
			// 获取文件的字节数
			int lenght = in.available();
			// 创建byte数组
			byte[] buffer = new byte[lenght];
			// 将文件中的数据读到byte数组中
			in.read(buffer);
			result = EncodingUtils.getString(buffer, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		startAnim();
	}

	@Override
	public void finish() {
		cleanAllTask();
		isVisiable = false;
		super.finish();
		backAnim();
		isFinished = true;
	}

	/**
	 * 保存字符串数据
	 */
	public void saveDataString(String key, String data) {
		SharedPreferences pre = getPreference();
		Editor editor = pre.edit();
		editor.putString(key, data);
		editor.commit();
	}

	/**
	 * 保存数字数据
	 */
	public void saveDataInt(String key, int val) {
		SharedPreferences pre = getPreference();
		Editor editor = pre.edit();
		editor.putInt(key, val);
		editor.commit();
	}

	/**
	 * 获取保存的数字数据
	 */
	public int getDataInt(String key) {
		SharedPreferences pre = getPreference();
		return pre.getInt(key, -1);
	}

	/**
	 * 获取字符串数据
	 */
	public String getDataString(String key) {
		SharedPreferences pre = getPreference();
		return pre.getString(key, "");
	}

	/**
	 * 清除保存的数据
	 */
	protected void cleanPreData() {
		SharedPreferences pre = getPreference();
		Editor editor = pre.edit();
		editor.clear();
		editor.commit();
	}

	/**
	 * ROOT
	 */
	private static final String PRE_ROOT_KEY = "fxcewsdf41s2x1fs";
	
	/**
	 * 获取Preference对象
	 */
	protected SharedPreferences getPreference() {
		return getSharedPreferences(PRE_ROOT_KEY, 0);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// 触摸主界面时，使键盘隐藏起来
			if (this.getCurrentFocus() != null) {
				if (this.getCurrentFocus().getWindowToken() != null) {
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
				}
			}
		}
		return super.onTouchEvent(event);
	}

	@Override
	protected void onDestroy() {
		System.gc();
		cleanAllTask();
		beforeDestory();
		super.onDestroy();
		this.isFinished = true;
	}

	/**
	 * 获取服务器地址
	 */
	public String getServerAddr() {
		return getString(R.string.server_addr);
	}

	/**
	 * 获取应用的缓存目录
	 */
	public String getAppCacheDir() {
		String path = "";
		try {
			path = getCacheDir().getAbsolutePath() + "/";
		} catch (Exception e) {

		}
		return path;
	}

	/**
	 * 界面销毁之前的做的事情
	 */
	protected abstract void beforeDestory();
	
	/**
	 * 获取asset目录下的图片文件，转成 drawable
	 */
	public Drawable getDrawableFromAsset(String filePath){
		Drawable drawable = null;
		AssetManager asm = getAssets();
		try{
			InputStream is = asm.open(filePath);
			drawable = Drawable.createFromStream(is, null);
		}catch(Exception e){
		}
		return drawable;
	}
	
	
	/**
	 * 获取asset目录下的图片文件，转成 Bitmap
	 */
	public Bitmap getBitmapFromAsset(String filePath){
		Bitmap bitmap = null;
		AssetManager asm = getAssets();
		try{
			InputStream is = asm.open(filePath);
			bitmap = BitmapFactory.decodeStream(is);
		}catch(Exception e){
		}
		return bitmap;
	}

	/**
	 * 执行一个任务后返回的结果
	 */
	public abstract void executeTaskResult(BaseTask task, Object data);

	/**
	 * 将绝对路径的图片,以指定的高度和宽度生成bitmap对象
	 */
	public Bitmap readBitmapAutoSize(String filePath, int outWidth, int outHeight) {
		// outWidth和outHeight是目标图片的最大宽度和高度，用作限制
		FileInputStream fs = null;
		BufferedInputStream bs = null;
		try {
			fs = new FileInputStream(filePath);
			bs = new BufferedInputStream(fs);
			BitmapFactory.Options options = setBitmapOption(filePath, outWidth, outHeight);
			return BitmapFactory.decodeStream(bs, null, options);
		} catch (Exception e) {
		} finally {
			try {
				bs.close();
				fs.close();
			} catch (Exception e) {
			}
		}
		return null;
	}

	private BitmapFactory.Options setBitmapOption(String file, int width, int height) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inJustDecodeBounds = true;
		// 设置只是解码图片的边距，此操作目的是度量图片的实际宽度和高度
		BitmapFactory.decodeFile(file, opt);

		int outWidth = opt.outWidth; // 获得图片的实际高和宽
		int outHeight = opt.outHeight;

		opt.inDither = false;
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		// 设置加载图片的颜色数为16bit，默认是RGB_8888，表示24bit颜色和透明通道，但一般用不上
		opt.inSampleSize = 1;
		// 设置缩放比,1表示原比例，2表示原来的四分之一....
		// 计算缩放比
		if (outWidth != 0 && outHeight != 0 && width != 0 && height != 0) {
			int sampleSize = (outWidth / width + outHeight / height) / 2;
			opt.inSampleSize = sampleSize;
		}
		opt.inJustDecodeBounds = false;// 最后把标志复原
		return opt;
	}
}

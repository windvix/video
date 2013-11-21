package com.athudong.video;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.athudong.video.component.ImageViewTouchListener;
import com.athudong.video.task.BaseTask;
import com.nineoldandroids.animation.ObjectAnimator;


/**
 * 个人资料界面（主要展示个人图片）
 */
public class IntroActivity extends BaseActivity{

	private ImageView intrImg;
	
	private ImageViewTouchListener touch;
	
	private TextView sayingTv;
	
	private ViewPager viewpager;
	
	
	private List<View> imgViews;
	
	@Override
	protected void initView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_intro);
		
		
		sayingTv = (TextView)findViewById(R.id.introSayingTv);
		viewpager = (ViewPager)findViewById(R.id.viewpager);
		findViewById(R.id.backBtn).setOnClickListener(this);
		intrImg = (ImageView)findViewById(R.id.introImg);
		
		
		
		imgViews = new ArrayList<View>();
		View h1 = createView(R.layout.intro_nav_imgveiw);
		View h2 = createView(R.layout.intro_nav_imgveiw);
		View h3 = createView(R.layout.intro_nav_imgveiw);
		View h4 = createView(R.layout.intro_nav_imgveiw);
		View h5 = createView(R.layout.intro_nav_imgveiw);
		View h6 = createView(R.layout.intro_nav_imgveiw);
		View h7 = createView(R.layout.intro_nav_imgveiw);
		View h8 = createView(R.layout.intro_nav_imgveiw);
		
		((ImageView)h1.findViewById(R.id.intro_headimg)).setImageResource(R.drawable.intro_test_head_05);
		((ImageView)h2.findViewById(R.id.intro_headimg)).setImageResource(R.drawable.intro_test_head_02);
		((ImageView)h3.findViewById(R.id.intro_headimg)).setImageResource(R.drawable.intro_test_head_03);
		((ImageView)h4.findViewById(R.id.intro_headimg)).setImageResource(R.drawable.intro_test_head_04);
		((ImageView)h5.findViewById(R.id.intro_headimg)).setImageResource(R.drawable.intro_test_head_01);
		((ImageView)h6.findViewById(R.id.intro_headimg)).setImageResource(R.drawable.intro_test_head_06);
		((ImageView)h7.findViewById(R.id.intro_headimg)).setImageResource(R.drawable.intro_test_head_07);
		((ImageView)h8.findViewById(R.id.intro_headimg)).setImageResource(R.drawable.intro_test_head_08);
		
		h1.setTag("intro_test_img_05.jpg");
		h2.setTag("intro_test_img_02.jpg");
		h3.setTag("intro_test_img_03.jpg");
		h4.setTag("intro_test_img_04.jpg");
		h5.setTag("intro_test_img_01.jpg");
		h6.setTag("intro_test_img_06.jpg");
		h7.setTag("intro_test_img_07.jpg");
		h8.setTag("intro_test_img_08.jpg");
		
		
		
		
		imgViews.add(h1);
		imgViews.add(h2);
		imgViews.add(h3);
		imgViews.add(h4);
		imgViews.add(h5);
		imgViews.add(h6);
		imgViews.add(h7);
		imgViews.add(h8);
		
		
		viewpager.setAdapter(new ViewPagerAdapter(imgViews));
		viewpager.setOnPageChangeListener(new ViewPagerPageChangeListener());
		viewpager.setOffscreenPageLimit(5);
		viewpager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.intro_img_nav_margin));
		viewpager.setCurrentItem(imgViews.size()/2);
		
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				String path = "intro_test_img_01.jpg";
				touch = new ImageViewTouchListener(IntroActivity.this) {
					@Override
					public void touchUp() {
						
					}
				};
				Bitmap bitmap  = getBitmapFromAsset(path);
				touch.initMatrix(intrImg, bitmap);
				viewpager.setCurrentItem(imgViews.size()/2);
			}
		}, 600);
		
		
		initImgViewsClick();
	}
	
	
	private void initImgViewsClick(){
		for(int i=0;i<imgViews.size();i++){
			View view  = imgViews.get(i);
			final int index = i;
			view.findViewById(R.id.intro_headimg).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					int cur = viewpager.getCurrentItem();
					if(cur!=index){
						viewpager.setCurrentItem(index);
					}
				}
			});
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
	public void onClick(View view) {
		int id = view.getId();
		if(id==R.id.backBtn){
			finish();
		}
	}

	
	@Override
	public void executeTaskResult(BaseTask task, Object data) {
		
	}
	class ViewPagerAdapter extends PagerAdapter {
		private List<View> list = null;

		public ViewPagerAdapter(List<View> list) {
			this.list = list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(list.get(position));
			return list.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(list.get(position));
		}

		
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}

	class ViewPagerPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int state) {
			
		}

		@Override
		public void onPageScrolled(int page, float positionOffset, int positionOffsetPixels) {
		}

		@Override
		public void onPageSelected(int page) {
			for(int i=0;i<imgViews.size();i++){
				View view = imgViews.get(i);
				
				
				if(page==i){
					
					String path = view.getTag().toString();
					
					touch = new ImageViewTouchListener(IntroActivity.this) {
						@Override
						public void touchUp() {
							
						}
					};
					intrImg.setImageBitmap(null);
					Bitmap bitmap  = getBitmapFromAsset(path);
					touch.initMatrix(intrImg, bitmap);
					
					ObjectAnimator.ofFloat(view, "scaleX", 1 ,1.3f).start();
					ObjectAnimator.ofFloat(view, "scaleY", 1 ,1.3f).start();
				}else{
					ObjectAnimator.ofFloat(view, "scaleX", 1 ,1f).start();
					ObjectAnimator.ofFloat(view, "scaleY", 1 ,1f).start();
				}
			}
		}
	}
}

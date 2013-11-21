package com.athudong.video;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;

import com.athudong.video.component.ImageViewTouchListener;
import com.athudong.video.task.BaseTask;
import com.nineoldandroids.animation.ObjectAnimator;


/**
 * 参赛者的视频列表界面（主要是参赛者的视频）
 */
public class VideosActivity extends BaseActivity{

	private ViewPager viewpager;
	
	private List<View> imgViews;
	
	@Override
	protected void initView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_videos);
		findViewById(R.id.backBtn).setOnClickListener(this);
		
		viewpager = (ViewPager)findViewById(R.id.viewpager);
		
		findViewById(R.id.defaultPlayBtn).setOnClickListener(this);
		
		imgViews = new ArrayList<View>();
		
		
		View h01 = createView(R.layout.video_nav_imgveiw);
		View h02 = createView(R.layout.video_nav_imgveiw);
		View h03 = createView(R.layout.video_nav_imgveiw);
		View h04 = createView(R.layout.video_nav_imgveiw);
		View h05 = createView(R.layout.video_nav_imgveiw);
		View h06 = createView(R.layout.video_nav_imgveiw);
		View h07 = createView(R.layout.video_nav_imgveiw);
		
		imgViews.add(h01);
		imgViews.add(h02);
		imgViews.add(h03);
		imgViews.add(h04);
		imgViews.add(h05);
		imgViews.add(h06);
		imgViews.add(h07);

		viewpager.setAdapter(new ViewPagerAdapter(imgViews));
		viewpager.setOnPageChangeListener(new ViewPagerPageChangeListener());
		
		viewpager.setCurrentItem(3);
		
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

	@Override
	public void onClick(View view) {
		int id = view.getId();
		if(id==R.id.backBtn){
			finish();
		}
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

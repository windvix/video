package com.athudong.video;

import android.os.Bundle;
import android.view.View;

import com.athudong.video.task.BaseTask;


/**
 * 个人资料界面（主要展示个人图片）
 */
public class IntroActivity extends BaseActivity{

	@Override
	protected void initView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_intro);
		
		findViewById(R.id.backBtn).setOnClickListener(this);
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

}

package com.creaminjector.presenter;


import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Activity业务类
 * 主要负责:
 * 1.启动一个新的Activity
 * 2.按照Activity类声明的注解绑定布局,实例化初始的Fragment,并切换到相应的视图
 * 3.
 * @author xinjun
 *
 */
public interface IFragmentPresenter{

	
	/**
	 * 切换fragment是需要的信息
	 * @author xinjun
	 *
	 */
	public static class FragmentInfo{
		public int viewID;
		public Class<? extends Fragment> clazz;
		public FragmentInfo(int viewID, Class<? extends Fragment> clazz) {
			super();
			this.viewID = viewID;
			this.clazz = clazz;
		}
		
		
	}

	
	void changeFragment(Context context,FragmentInfo... fragments);

}

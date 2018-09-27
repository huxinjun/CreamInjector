package com.creaminjector.templete;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.creaminjector.presenter.ILayoutPresenter;
import com.creaminjector.presenter.impl.layout.LayoutCreater;
import com.creaminjector.utils.CreamUtils;

public class SmartAbsListAdapter extends BaseAdapter {

	/**
	 * 这个Adapter被附加的适配器视图
	 */
	private AdapterView<BaseAdapter> mAdapterView;
	
	/**
	 * 适配器包含的所有数据(数据真实类型是代理的)
	 */
	private List<Object> mAllDatas;
	
	/**
	 * 使用的视图创建器模板类型
	 */
	private Class<? extends LayoutCreater> mItemCreaterType;

	private Context context;
	
	@SuppressWarnings("unchecked")
	public SmartAbsListAdapter(Context context,AdapterView<BaseAdapter> parent) {
		this.context=context;
		this.mAdapterView=parent;
		if(mAdapterView!=null){
			mAllDatas=(List<Object>) mAdapterView.getTag(LayoutCreater.TAG_ITEMS_DATA);
			mItemCreaterType=(Class<? extends LayoutCreater>) mAdapterView.getTag(LayoutCreater.TAG_LAYOUT_CRETAER_ITEM_CLASS);
		}
	}
	
	@Override
	public int getCount() {
		Object dataCount = mAdapterView.getTag(LayoutCreater.TAG_MULTI_DATA_COUNT);
		if(dataCount==null)
			return mAllDatas==null?0:mAllDatas.size();
		else{
			int count= (int) dataCount;
			return mAllDatas.size()/count;
		}
	}

	@Override
	public Object getItem(int position) {
		return mAllDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	private LayoutCreater tempCreater;
	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position,  final View convertView, ViewGroup parent) {
		if(convertView==null){
			CreamUtils.inflate(context,mItemCreaterType, new ILayoutPresenter.InflateCallBack() {
				
				@Override
				public void onCompleted(LayoutCreater instance) {
					tempCreater=instance;
					tempCreater.getContentView().setTag(tempCreater);
				}
			});
		}else
			tempCreater = (LayoutCreater) convertView.getTag();

		tempCreater.setInParentIndex(position);
		Object dataCount = mAdapterView.getTag(LayoutCreater.TAG_MULTI_DATA_COUNT);
		if(dataCount==null){
			Object o = mAllDatas.get(position);
			tempCreater.setContentData(o);
		}else{
			//一个布局绑定多个对象
			int count= (int) dataCount;
			List<Object> datas=new ArrayList<>();
			for(int i=position*count;i<position*count+count;i++)
				datas.add(mAllDatas.get(i));
			tempCreater.setContentData(datas);
		}
		return tempCreater.getContentView();
	}

}

package com.demo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.creaminjector.annotation.BindFieldName;
import com.creaminjector.annotation.creater.BindLayoutCreater;
import com.creaminjector.annotation.creater.BindView;
import com.creaminjector.presenter.impl.layout.LayoutCreater;
import com.creaminjector.utils.CreamUtils;
import com.demo.R;

@BindLayoutCreater(creater=LoadingDialog.LoadingDialogCreater.class)
public class LoadingDialog extends Dialog {

	
	
	public LoadingDialog(Context context) {
		super(context);
		CreamUtils.bind(this);
	}

	@BindView(R.layout.dialog_loading)
	public static class LoadingDialogCreater extends LayoutCreater {
		

		@BindFieldName("abc")
		@BindView(R.id.tv_content)
		public TextView tv_content;
		
		

		@Override
		public void onDataPrepared() {

		}
	}

	
}

package com.example.animemania;

import android.app.Activity;
import android.app.Dialog;
import android.view.WindowManager;

public class AboutDialog {
	public AboutDialog() {
	} // AboutDialog

	public void onAboutClick(Activity activity) {
		Dialog dialog = new Dialog(activity, R.style.Theme_CustomDialog);
		dialog.setContentView(activity.getLayoutInflater().inflate(R.layout.dialog_about,
				null));
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		dialog.show();
	} // onAboutClick
} // class AboutDialog

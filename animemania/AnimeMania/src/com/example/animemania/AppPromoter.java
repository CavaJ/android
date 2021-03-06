package com.example.animemania;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class AppPromoter {
	
    private final static String PROMOTION_APP_PNAME = "com.example.promotionapp";
    
    private final static int DAYS_UNTIL_PROMPT = 3; // default 3
    private final static int LAUNCHES_UNTIL_PROMPT = 7; // default 7
    
    public static void app_launched(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("apppromoter", 0);
        if (prefs.getBoolean("dontshowagain", false)) { return ; }
        
        SharedPreferences.Editor editor = prefs.edit();
        
        // Increment launch counter
        long launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);

        // Get date of first launch
        Long date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong("date_firstlaunch", date_firstLaunch);
        } // if
        
        // Wait at least n days before opening
        if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
            if (System.currentTimeMillis() >= date_firstLaunch + 
                    (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
                showPromotionDialog(mContext, editor);
            } //if
        } // if
        
        editor.commit();
    } // app_launched  
    
    public static void showPromotionDialog(final Context mContext, final SharedPreferences.Editor editor) {
        AlertDialog.Builder promotionDialog = new AlertDialog.Builder(mContext);
        
        View v = ((ActionBarActivity) mContext).getLayoutInflater()
                .inflate(R.layout.dialog_promotion, null);
        
        promotionDialog.setTitle(R.string.promotion_dialog_title);
        promotionDialog.setView(v);
        promotionDialog.setIcon(R.drawable.ic_flashlight_logo);
        promotionDialog.setNegativeButton(R.string.promotion_dialog_later_button_title, 
        		new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				
				//reset counters
				if (editor != null) {
					editor.putLong("launch_count", 0);
					editor.putLong("date_firstlaunch", 0);
					editor.commit();
				} // if
				
				
				dialog.dismiss();
			} // onClick
		});
        
        promotionDialog.setPositiveButton(R.string.promotion_dialog_install_button_title, 
        		new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mContext.startActivity(new Intent(Intent.ACTION_VIEW, 
						Uri.parse("market://details?id=" + PROMOTION_APP_PNAME)));
				
				if (editor != null) {
                    editor.putBoolean("dontshowagain", true);
                    editor.commit();
                } // if
				
				dialog.dismiss();
			} // onClick
		});
    	
        AlertDialog dialog = promotionDialog.create();
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
    } // showPromotionDialog
} // class AppPromoter

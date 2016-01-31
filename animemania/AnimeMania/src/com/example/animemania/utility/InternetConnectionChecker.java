package com.example.animemania.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetConnectionChecker {

	private Context context;

	public InternetConnectionChecker (Context context) {
		this.context = context;
	}

	/**
	 * Checking for all possible internet providers
	 * **/
	
	public boolean isConnectedToInternet() {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm != null) {
			NetworkInfo[] info = cm.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}

		}
		return false;
	} // isConnectedToInternet
	
} // InternetConnectionChecker

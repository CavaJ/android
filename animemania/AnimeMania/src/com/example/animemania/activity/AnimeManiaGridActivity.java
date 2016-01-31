package com.example.animemania.activity;

import com.example.animemania.R;
import com.example.animemania.fragment.AnimeManiaGridFragment;

import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class AnimeManiaGridActivity extends ActionBarActivity 
{
	private static final String TAG = "AnimeManiaGridActivity";
	
    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate() called");
		setContentView(R.layout.activity_animemania_grid);
		
		// changing the way threads work for newer SDKS
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		// customize the action bar.
		
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);

		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.title_actionbar_main, null);

		TextView actionBarTitleTextView = (TextView) v.findViewById(R.id.main_actionbar_title_textview);
		actionBarTitleTextView.setText(R.string.app_name);
		actionBarTitleTextView.setSelected(true);
		actionBar.setCustomView(v);
		
		//end - customize action bar
		
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm
				.findFragmentById(R.id.fragment_cartoon_container);

		if (fragment == null) {
			fragment = new AnimeManiaGridFragment();
			fm.beginTransaction()
					.add(R.id.fragment_cartoon_container, fragment).commit();
		} // if
		
    } // onCreate
    
	@Override
	protected void onResume() {		
		super.onResume();
	} // onResume

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	} // onBackPressed
    
} // class AnimeManiaGridActivity
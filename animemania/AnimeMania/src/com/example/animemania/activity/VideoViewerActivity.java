package com.example.animemania.activity;


import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnBufferingUpdateListener;
import io.vov.vitamio.MediaPlayer.OnCompletionListener;
import io.vov.vitamio.MediaPlayer.OnErrorListener;
import io.vov.vitamio.MediaPlayer.OnInfoListener;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;











import com.example.animemania.R;
import com.example.animemania.utility.InternetConnectionChecker;


public class VideoViewerActivity extends FragmentActivity implements OnInfoListener, OnBufferingUpdateListener
{

	public static final String KEY_MAIN_VIDEO_URL = "com.example.animemania.main_video_url";
	
	private String path = "";
	private Uri uri;
	private VideoView mVideoView;
	private boolean isStart;
	private ProgressBar pb;
	private TextView downloadRateView, loadRateView;
	private RelativeLayout adLayout;
	private boolean isFirstTimeAdShown = true;
	private long mPosition = 0;
	private int mVideoLayout = 0;
	private InternetConnectionChecker mInternetConnectionChecker;
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		if (!LibsChecker.checkVitamioLibs(this))
			return;
		
		// hide the window title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// hide the status bar and other OS-level chrome
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		setContentView(R.layout.activity_video_viewer);
		
	    mInternetConnectionChecker = new InternetConnectionChecker(getApplicationContext());
	    
		mVideoView = (VideoView) findViewById(R.id.buffer);
		pb = (ProgressBar) findViewById(R.id.probar);

		downloadRateView = (TextView) findViewById(R.id.download_rate);
		loadRateView = (TextView) findViewById(R.id.load_rate);
		
		path = ((Uri) getIntent().getExtras().getParcelable(KEY_MAIN_VIDEO_URL)).toString();
		uri = Uri.parse(path);
		
		adLayout = (RelativeLayout) findViewById(R.id.adlayout);
		adLayout.setVisibility(View.GONE);
		
		if (path == "") {
			// Tell the user to provide a media file URL/path.
			Toast.makeText(VideoViewerActivity.this, "Please edit VideoViewer Activity, and set path" + " variable to your media file URL/path", Toast.LENGTH_LONG).show();
			return;
		} else {
			/*
			 * Alternatively,for streaming media you can use
			 * mVideoView.setVideoURI(Uri.parse(URLstring));
			 */
			
			mVideoView.setOnCompletionListener(new OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer mp) {
					
					mVideoView.stopPlayback();
					finish(); 
				} // onCompletion
			});
			
			mVideoView.setOnErrorListener(new OnErrorListener() {
				
				@Override
				public boolean onError(MediaPlayer mp, int what, int extra) {
					pb.setVisibility(View.GONE);
					
					if (mInternetConnectionChecker != null && ! mInternetConnectionChecker.isConnectedToInternet()) {
						Toast.makeText(getApplicationContext(),
								getString(R.string.episodelist_activity_error),
								Toast.LENGTH_LONG).show();
					} // if
					else {
						Toast.makeText(getApplicationContext(),
								getString(R.string.video_activity_error),
								Toast.LENGTH_LONG).show();
					} // else
					
					//mVideoView.stopPlayback();
					
					VideoViewerActivity.this.finish();
					
					return true;
				} // onError
			});
			
			mVideoView.setVideoURI(uri);
			mVideoView.setMediaController(new MediaController(this));
			mVideoView.requestFocus();
			mVideoView.setOnInfoListener(this);
			mVideoView.setOnBufferingUpdateListener(this);
			mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
				@Override
				public void onPrepared(MediaPlayer mediaPlayer) {
						// optional need Vitamio 4.0
						//mediaPlayer.setPlaybackSpeed(1.0f);
				} // onPrepared
			});
		
		} // else
	} // onCreate

	@Override
	public boolean onInfo(MediaPlayer mp, int what, int extra) {
		switch (what) {
		case MediaPlayer.MEDIA_INFO_BUFFERING_START:
			if (mVideoView.isPlaying()) {
				mVideoView.pause();
				isStart = true;
				pb.setVisibility(View.VISIBLE);
				downloadRateView.setVisibility(View.VISIBLE);
				loadRateView.setVisibility(View.VISIBLE);

			}
			break;
		case MediaPlayer.MEDIA_INFO_BUFFERING_END:
			if (isFirstTimeAdShown)
			{
				if (adLayout.getVisibility() == View.VISIBLE)
					mVideoView.pause();
				else
					mVideoView.start();
				
				pb.setVisibility(View.GONE);
				downloadRateView.setVisibility(View.GONE);
				loadRateView.setVisibility(View.GONE);
				isFirstTimeAdShown = false;
			} // if
			else {
				if (isStart) {
					mVideoView.start();
					pb.setVisibility(View.GONE);
					downloadRateView.setVisibility(View.GONE);
					loadRateView.setVisibility(View.GONE);
				}
			} // else
			
			break;
		case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
			downloadRateView.setText("" + extra + "kb/s" + "  ");
			break;
		}
		return true;
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		loadRateView.setText(percent + "%");
	}
	
	@Override
	protected void onPause() {
		mPosition = mVideoView.getCurrentPosition();
		mVideoView.pause();
		
		super.onPause();
	} // onPause

	@Override
	protected void onResume() {
		super.onResume();
		
		pb.setVisibility(View.VISIBLE);
		
		if (mPosition > 0) {
			mVideoView.seekTo(mPosition);
			mPosition = 0;
			
			mVideoView.resume();
		} // if
		else
		{
			mVideoView.start();
		} // else
		
	} // onResume
	
	public void changeLayout(View view) {
		mVideoLayout++;
		if (mVideoLayout == 4) {
			mVideoLayout = 0;
		}
		switch (mVideoLayout) {
		case 0:
			mVideoLayout = VideoView.VIDEO_LAYOUT_ORIGIN;
			view.setBackgroundResource(R.drawable.mediacontroller_sreen_size_100);
			break;
		case 1:
			mVideoLayout = VideoView.VIDEO_LAYOUT_SCALE;
			view.setBackgroundResource(R.drawable.mediacontroller_screen_fit);
			break;
		case 2:
			mVideoLayout = VideoView.VIDEO_LAYOUT_STRETCH;
			view.setBackgroundResource(R.drawable.mediacontroller_screen_size);
			break;
		case 3:
			mVideoLayout = VideoView.VIDEO_LAYOUT_ZOOM;
			view.setBackgroundResource(R.drawable.mediacontroller_sreen_size_crop);

			break;
		}
		mVideoView.setVideoLayout(mVideoLayout, 0);
	}
	
	
	private static long back_pressed;
	
	@Override
	public void onBackPressed() {
		
		if (back_pressed + 2000 > System.currentTimeMillis()) {
	        super.onBackPressed();
	    }
	    else{
	        Toast.makeText(getBaseContext(), R.string.back_clicked, Toast.LENGTH_SHORT).show();
	        back_pressed = System.currentTimeMillis();
	    } // else
		

		//super.onBackPressed();
	} // onBackPressed

	@Override
	protected void onDestroy() {
		
		super.onDestroy();
	} // onDestroy
	
} // VideoViewerActivity

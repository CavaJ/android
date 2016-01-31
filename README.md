# Sample Android App

This is just a simple android app with recycling gridview for loading big images from the memory. On the other hand,
second Activity in the project provides you with custom listview to model episode list of some free to use cartoons. It has a
powerful customized video player based on Vitamio library. You can start development by adding new images and strings to 
AnimeManiaGridFragment.java class. The episode lists are loaded using xml files. Sample bunny.xml file has been
provided.


**public class AnimeManiaGridFragment extends Fragment {**

	private Integer[] customImages = { R.drawable.bunny, R.drawable.bunny,
			R.drawable.bunny };

	private Integer[] customNames = { R.string.bunny, R.string.bunny,
			R.string.bunny };
			
			// Other stuff
**}** 

Do not forget to add your own xml file urls to Constants.java file:


**public class Constants {**

	  //URLs can be encrypted by app signature, look at the methods in Utils class
	  public static final String URLBunny = "your_direct_link_to_xml_file";
	  
	  //different urls could be added here.
**}**

In AnimeListActivity.java class you can load different xml files based on click position of grid_item's from the
previous activity.


**public class AnimeListActivity extends ActionBarActivity {**

    	//Some initializations
		
		switch (position) {
		case 0:
			mURL = Constants.URLBunny;
			actionBarTitleTextView.setText(R.string.bunny);
			actionBar.setIcon(R.drawable.bunny);
			mAnimeSeries.setSeriesName(getString(R.string.bunny_series_name));
			mAnimeSeries.setSeasonName(getString(R.string.bunny));
			break;
		case 1:
			mURL = Constants.URLBunny;
			actionBarTitleTextView.setText(R.string.bunny);
			actionBar.setIcon(R.drawable.bunny);
			mAnimeSeries.setSeriesName(getString(R.string.bunny_series_name));
			mAnimeSeries.setSeasonName(getString(R.string.bunny));
			break;
		case 2:
			mURL = Constants.URLBunny;
			actionBarTitleTextView.setText(R.string.bunny);
			actionBar.setIcon(R.drawable.bunny);
			mAnimeSeries.setSeriesName(getString(R.string.bunny_series_name));
			mAnimeSeries.setSeasonName(getString(R.string.bunny));
			break;
		default: // do nothing
		} // switch
		
		//Some other stuff
**}** 

Play store url of my applications: https://play.google.com/store/apps/developer?id=AnimeSoft

More sample projects will be added. Stay tuned!

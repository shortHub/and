package solarEclipse.gui;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
//import com.google.android.maps.MyLocationOverlay;

public class SolarMapActivity extends MapActivity {

	private MapController mapController;
	private MapView mMapView;
	//private MyLocationOverlay mLocationOverlay;
	private static final String TAG = SolarMapActivity.class.getSimpleName();
	private boolean isSatellite = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.solareclipse_mapview);

		initMapView();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	private void initMapView() {
		mMapView = (MapView) findViewById(R.id.solar_map);
		mapController = mMapView.getController();

		final int maxZoomlevel = mMapView.getMaxZoomLevel();
		mapController.setZoom(maxZoomlevel - 4);

		mMapView.setBuiltInZoomControls(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.mapview_menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.me_deactivate_graphics:
			return true;
		case R.id.me_show_satelite:
			if (!this.isSatellite) {
				this.mMapView.setSatellite(true);
				this.isSatellite = true;
			} else {
				this.mMapView.setSatellite(false);
				this.isSatellite = false;
			}
			return true;
		case R.id.me_show_own_pos:
			return true;
		default:
			Log.w(TAG, "unbekannte Option gewaehlt: " + item);
			return super.onOptionsItemSelected(item);
		}
	}

}

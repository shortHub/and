package solarEclipse.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
/** hm */
public class SolarEclipseStartup extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.solareclipse_startup);
        
        Button buMapsView = (Button) findViewById(R.id.bu_googleMaps);
        buMapsView.setOnClickListener(tempOnClickShowMapsView);
    }
    
    private OnClickListener tempOnClickShowMapsView = new OnClickListener () {
    	
    	public void onClick(View v) {
    		onClickShowMapsView(v);
    	}
    };
    
    public void onClickShowSClipse (final View sfnormal) {
    	final Intent intent = new Intent(this, SolarTableView.class);
    	startActivity(intent);
    }
    
    public void onClickShowSolarDict(final View sfnormal) {
    	final Intent intent = new Intent(this, AstronomicDict.class);
    	startActivity(intent);
    }
    
    public void onClickShowMapsView (final View sfnormal) {
    	final Intent intent = new Intent(this, SolarMapActivity.class);
    	startActivity(intent);
    }
}
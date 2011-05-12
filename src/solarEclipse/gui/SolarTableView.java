package solarEclipse.gui;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class SolarTableView extends Activity implements OnClickListener {
	
	private Button btn;
	private TableLayout table;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.solareclipse_tableview);
		
		this.table = (TableLayout) findViewById(R.id.lo_tableview);
		addExpandButton();

	}
	
	public void onClick (View v) {
		addRows();
	}
	
	private void addRows() {
		this.table.removeView(this.btn);
		TableRow tr = new TableRow(this);
		
		TextView tv1 = new TextView(this);
		tv1.setText("21.12.2012");
		
		TextView tv2 = new TextView(this);
		tv2.setText("Berlin");
		
		TextView tv3 = new TextView(this);
		tv3.setText("Voll");
		
		tr.addView(tv1);
		tr.addView(tv2);
		tr.addView(tv3);
		
		this.table.addView(tr, getLayoutParams());
		addExpandButton();
	}
	
	private final LayoutParams getLayoutParams() {
		return new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	}
	
	private void addExpandButton () {
		if(this.btn == null)
			this.btn = new Button(this);
		this.btn.isClickable();
		this.btn.setText("Weitere");
		this.btn.setOnClickListener(this);
		this.table.addView(this.btn, getLayoutParams());
	}

}

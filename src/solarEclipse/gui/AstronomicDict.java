package solarEclipse.gui;

import android.util.Log;
import solarEclipse.helper.GlossarParser;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ScrollView;
import android.widget.TextView;

public class AstronomicDict extends Activity implements OnClickListener,
		OnCancelListener, OnTouchListener {

	private final static String DEBUG_TAG = AstronomicDict.class.getSimpleName();

	private final int SELECTIONDIALOG = 0;
	private final int ERRORDIALOG = 1;

	private boolean dialogIsShown = false;
	private float xPosition;
	private TextView headLine;
	private TextView contentField;
	private int currentSelected;
	private GlossarParser glossar;
	
	//***************** OWN METHODS ***************** 
	
	private void insertTextContent(String[] content) {
		this.headLine.setText(content[0]);
		this.contentField.setText(content[1]);
	}	

	//************** OVERRIDEN METHODS ************** 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.solareclipse_dict);
		try {
			this.glossar = new GlossarParser(this);
			showDialog(SELECTIONDIALOG);
			this.headLine = (TextView) findViewById(R.id.tv_astrodictHead);
			this.contentField = (TextView) findViewById(R.id.tv_astrodictContent);
			ScrollView scrollView = (ScrollView) findViewById(R.id.test);
			scrollView.setOnTouchListener(this);
		} catch (Exception e) {
			Log.e(DEBUG_TAG, e.toString());
			showDialog(ERRORDIALOG);
			return;
		}
	}

	@Override
	protected Dialog onCreateDialog(int id, Bundle icBundle) {
		
		dialogIsShown = true;
		
		switch (id) {
		case SELECTIONDIALOG:
			return new AlertDialog.Builder(AstronomicDict.this)
					.setTitle(R.string.di_dict_titel)
					.setItems(this.glossar.getKeyNames(), this)
					.setIcon(android.R.drawable.ic_menu_agenda)
					.setOnCancelListener(this).setCancelable(true).create();
		case ERRORDIALOG:
			return new AlertDialog.Builder(this)
					.setTitle(R.string.di_dict_errtitel)
					.setMessage(R.string.err_noFileErr)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setOnCancelListener(this).setCancelable(true).create();
		}
		
		return null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.dictview_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			//speichere X-Position bei Down-Action
			this.xPosition = event.getX();
			break;
		case MotionEvent.ACTION_UP:
			if (Math.abs((float) this.xPosition - event.getX()) > 20) {
				//wenn distanz zwischen DOWN-X > 20 "Wischen", sonst OptionsMenu einblenden
				if (this.xPosition > event.getX()) {
					if (this.glossar.idHasNext(this.currentSelected)) {
						this.currentSelected++;
						this.insertTextContent(this.glossar.getContentById(this, this.currentSelected));
					}
				} else {
					if (this.glossar.idHasPrev(this.currentSelected)) {
						this.currentSelected--;
						this.insertTextContent(this.glossar.getContentById(this, this.currentSelected));
					}
				}
			} else {
				if ((event.getEventTime() - event.getDownTime() ) > 1000) {
					super.showDialog(SELECTIONDIALOG);
				} else {
					super.openOptionsMenu();
				}
			}
			break;
		}
		return true;
	}

	public void onClick(DialogInterface dialog, int which) {
		this.dialogIsShown = false;
		this.currentSelected = which + 1;
		this.insertTextContent(this.glossar.getContentById(this,
				this.currentSelected));
	}

	public void onCancel(DialogInterface dialog) {
		dialog.dismiss();
		super.finish();
		
		return;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		/*
		 * Blendet nicht nutzbare Menuelemente aus, z.B. wenn kein Wikilink 
		 * im XML enthalten oder kein vorheriges / naechstes Element vorhanden. 
		 */
		menu.findItem(R.id.me_dictPrev).setEnabled(this.glossar.idHasPrev(this.currentSelected));
		menu.findItem(R.id.me_dictNext).setEnabled(this.glossar.idHasNext(this.currentSelected));
		menu.findItem(R.id.me_dictWikipedia).setEnabled(this.glossar.idHasWikiLink(this.currentSelected));
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.me_dict_showList:
			showDialog(SELECTIONDIALOG);
			return true;
		case R.id.me_dictNext:
			this.currentSelected += 1;
			this.insertTextContent(this.glossar.getContentById(this, this.currentSelected));
			break;
		case R.id.me_dictPrev:
			this.currentSelected -= 1;
			this.insertTextContent(this.glossar.getContentById(this, this.currentSelected));
			break;
		case R.id.me_dictWikipedia:
			/*
			 * Sende impliziten Intent an Browser mit, im Dokument enthaltenem,
			 * Wikilink (sofern ein solcher existiert), sonst Menupunkt
			 * ausgeblendet
			 */
			final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(this.glossar.getWikiLink()));
			startActivity(intent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		
		if(this.dialogIsShown) {
			super.onBackPressed();
		} else {
			super.showDialog(SELECTIONDIALOG);
		}
	}
}

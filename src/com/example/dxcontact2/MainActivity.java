package com.example.dxcontact2;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
//@suppressWarnings("deprecation")
;
public class MainActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab);
		
		TabHost mTab=getTabHost();
		TabHost.TabSpec spec;
		Intent intent;
		
		// 첫번째 탭
		intent = new Intent(this, Tab0ne.class);
		spec = mTab.newTabSpec("친밀도").setIndicator("친밀도").setContent(intent);
		mTab.addTab(spec);
		// 두번째 탭
		intent = new Intent(this, TabTwo.class);
		spec = mTab.newTabSpec("연락처").setIndicator("연락처").setContent(intent);
		mTab.addTab(spec);
	}

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}


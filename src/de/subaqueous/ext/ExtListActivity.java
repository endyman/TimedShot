package de.subaqueous.ext;

import de.subaqueous.utils.Log;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

/**
 * extended Activity Class with loggging
 * @author Nils Domrose
 *
 */
public class ExtListActivity extends ListActivity {
	
	protected static String LCLASS;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		LCLASS = getClass().getName();
		Log.lifecycle(LCLASS, "onCreate Called");
	}
	
	@Override
	public void onStart(){
		super.onStart();
		Log.lifecycle(LCLASS, "onStart Called");	
	}
	
	@Override
	public void onResume(){
		super.onResume();
		Log.lifecycle(LCLASS, "onResume Called");	
	}
	
	@Override
	public void onPause(){
		super.onPause();
		Log.lifecycle(LCLASS, "onPause Called");	
	}
	
	@Override
	public void onStop(){
		super.onStop();
		Log.lifecycle(LCLASS, "onStop Called");			
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		Log.lifecycle(LCLASS, "onDestroy Called");			
	}
	
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
		Log.lifecycle(LCLASS, "onListItemClick Called - position:" + position );			
    }
}

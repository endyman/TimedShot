package de.subaqueous.ext;

import de.subaqueous.utils.Log;
import android.app.Activity;
import android.os.Bundle;

/**
 * extended Activity Class with loggging
 * @author Nils Domrose
 *
 */
public class ExtActivity extends Activity {
	
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
}

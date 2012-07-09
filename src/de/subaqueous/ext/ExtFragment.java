package de.subaqueous.ext;

import de.subaqueous.utils.Log;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * extended Fragment Class with loggging
 * @author Nils Domrose
 *
 */
public class ExtFragment extends Fragment {
	
	protected static String LCLASS;
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		LCLASS = getClass().getName();
		Log.lifecycle(LCLASS, "onAttach Called");	
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		LCLASS = getClass().getName();
		Log.lifecycle(LCLASS, "onCreate Called");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		Log.lifecycle(LCLASS, "onCreateView Called");	
		return null;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		Log.lifecycle(LCLASS, "onActivityCreated Called");			
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
	public void onDestroyView(){
		super.onDestroyView();
		Log.lifecycle(LCLASS, "onDestroyView Called");	
	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		Log.lifecycle(LCLASS, "onDestroy Called");			
	}
	
	@Override
	public void onDetach(){
		super.onDetach();
		LCLASS = getClass().getName();
		Log.lifecycle(LCLASS, "onDetach Called");	
	}
	
}

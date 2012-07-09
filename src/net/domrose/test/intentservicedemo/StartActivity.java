package net.domrose.test.intentservicedemo;

import java.util.Locale;

import net.domrose.timedshot.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.widget.Button;
import android.widget.ImageView;

public class StartActivity extends Activity implements CustomResultReceiver.Receiver{
	
	private CustomResultReceiver mReceiver;
	private IntentServiceHelper mServiceHelper;
	private Boolean vis = false;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.d("test<<<<<<<<<<", "vendor: " + Build.MANUFACTURER);
        try {
        	Log.d("test<<<<<<<<<<", "version: " + this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName);
        	Log.d("test<<<<<<<<<<", "packageName: " + this.getPackageManager().getPackageInfo(this.getPackageName(), 0).packageName);
        	Log.d("test<<<<<<<<<<", "label: " + this.getPackageManager().getApplicationInfo(this.getPackageName(), 0).loadLabel(this.getPackageManager()).toString());
        	Log.d("test<<<<<<<<<<", "locale: " + Locale.getDefault().getLanguage() + "_" + Locale.getDefault().getCountry());
        } catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        Log.d("test<<<<<<<<<<", "screen: " + width + "x" + height);
        
        Log.d("test<<<<<<<<<<", "scale: " + displaymetrics.densityDpi);
        mReceiver = new CustomResultReceiver(new Handler());
        mServiceHelper = new IntentServiceHelper(this);
        
        final ImageView img = (ImageView) findViewById(R.id.imageView1);
        Button btn = (Button) findViewById(R.id.button1); 
        
        btn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if (vis){
					img.setVisibility(View.VISIBLE);								
				}else{
					img.setVisibility(View.GONE);			
				}
				vis = !vis;
			}
		});
        
        
        
    }
    
    public void startSleep(View v){
    	//mServiceHelper.startService(IntentServiceHelper.SLEEP_ACTION, mReceiver);
    	
//    	try {
//			Thread.sleep(12000);
//			Log.d("SleepIntentService", "Sleep done");
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    	
    	Intent sIntent = new Intent(this, CustomIntentService.class);
    	startService(sIntent);
    }

	public void onReceiveResult(int resultCode, Bundle resultData) {
		// TODO Auto-generated method stub
		
	}
}
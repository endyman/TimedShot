package net.domrose.test.intentservicedemo;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {
	
	private static Context instance;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		setInstance(this);
	}

	public static Context getInstance() {
		return instance;
	}
	

	private void setInstance(Context instance) {
		MyApp.instance = instance;
	}

}

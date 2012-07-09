package net.domrose.test.intentservicedemo;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class CustomIntentService extends IntentService {
	

	public CustomIntentService() {
		super("MyService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		//intent.getExtras().get
		try {
			Thread.sleep(5000);
			Log.d("SleepIntentService", "Sleep done");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

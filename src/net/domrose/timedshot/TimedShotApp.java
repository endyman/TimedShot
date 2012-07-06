package net.domrose.timedshot;

import android.app.Application;
import android.content.Context;

public class TimedShotApp extends Application {
	
	private static Context context;
	private static byte [] mCaptureImage = null;

    public void onCreate(){
        super.onCreate();
        TimedShotApp.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return TimedShotApp.context;
    }

	public static byte [] getCaptureImage() {
		return mCaptureImage;
	}

	public static void setCaptureImage(byte [] mCaptureImage) {
		TimedShotApp.mCaptureImage = mCaptureImage;
	}
    
    

}

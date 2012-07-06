package net.domrose.timedshot;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.PowerManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.util.Log;
import android.widget.FrameLayout;

public class CaptureActivity extends Activity {
		
	private final static Integer INITAL_CAPTURE_DELAY = 3000;
	private static final boolean SCALE_IMAGE = true;
	private final static Integer IMAGE_WIDTH = 1280;
	private final static Integer IMAGE_HEIGHT = 960;
	
	protected static final String TAG = "CaptureActivity";
	
	private Camera mCamera;
	private CameraPreview mPreview;
	private Timer mTimer;
	private Integer mInterval;
	private PowerManager.WakeLock mWakeLock;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        if (checkCameraHardware()){ 
        	mCamera = getCameraInstance();
        	mPreview = new CameraPreview(this, mCamera);
			FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
			preview.addView(mPreview);
        }
        mInterval = getIntent().getExtras().getInt(MainActivity.EXTRA_INTERVAL);
        mWakeLock = getWakeLock();
        setTimer(INITAL_CAPTURE_DELAY);
    }
	 
    @Override
    protected void onResume() {
    	mWakeLock.acquire();
    	super.onResume();
    }
    @Override
    protected void onPause() {
	    releaseCamera();
	    if (mTimer != null){
	    	mTimer.cancel();
	    	mTimer = null;
	    }
	    mWakeLock.release();
	    TimedShotApp.setCaptureImage(null);
    	super.onPause();
    }
    
    private PowerManager.WakeLock getWakeLock(){
    	PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
    	return pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "CaptureActivity");    	
    }
    private boolean checkCameraHardware() {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            return true;
        } else {
            return false;
        }
    }
    
	public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open();
        }
        catch (Exception e){
        }
        
        if (SCALE_IMAGE == true){
        	// TODO implement UI Feature to set size
        	Parameters params = c.getParameters();
            params.setPictureSize(IMAGE_WIDTH, IMAGE_HEIGHT);
            c.setParameters(params);
        }
        
        return c;
    }
    
    private void releaseCamera(){
        if (mCamera != null){
            mCamera.release();
            mCamera = null;
        }
    }
    
    private void takePicture(){
    	Log.d(TAG, "takePicture ");        
        mCamera.takePicture(null, null, mPicture);
    }
    
    private void setTimer(Integer interval){
    	if (interval > 0){
    		mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
    			
    			@Override
    			public void run() {
    		        takePicture();				
    			}
    		}, interval);
    	}
    }
    
    private void startFtpIntent(){
    	Intent ftpIntent = new Intent(this, FtpIntentService.class);
    	startService(ftpIntent);
    }
    
    private PictureCallback mPicture = new PictureCallback() {

        public void onPictureTaken(byte[] data, Camera camera) {       
        	Log.d(TAG, "onPictureTaken data size: " + data.length );
        	TimedShotApp.setCaptureImage(data);
        	startFtpIntent();
        	setTimer(mInterval * 60 * 1000);
        	camera.startPreview();
        }
    };
}
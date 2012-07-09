package net.domrose.timedshot;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Message;
import android.os.PowerManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.PreviewCallback;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.FrameLayout;

/**
 * Activity to capture images at a given interval and trigger uploads
 * 
 * @author Nils Domrose
 *
 */
public class CaptureActivity extends Activity {
		
	private final static Integer CAPTURE_DELAY = 3000;
	// TODO provide access from the ui to set dynamic values
	private static final boolean SCALE_IMAGE = true;
	private final static Integer IMAGE_WIDTH = 1280;
	private final static Integer IMAGE_HEIGHT = 960;
	
	protected static final String TAG = "CaptureActivity";
	
	private Camera mCamera;
	private CameraPreview mPreview;
	private Timer mTimer;
	private Integer mInterval;
	private Boolean mUseOverlay;
	private Boolean mUseTimestamp;
	private PowerManager.WakeLock mWakeLock;
	private FrameLayout mPreviewHolder;
	
	
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        mPreviewHolder = (FrameLayout) findViewById(R.id.camera_preview);
        mInterval = getIntent().getExtras().getInt(MainActivity.EXTRA_INTERVAL);
        mUseOverlay = getIntent().getExtras().getBoolean(MainActivity.EXTRA_USE_OVERLAY);
        mUseTimestamp = getIntent().getExtras().getBoolean(MainActivity.EXTRA_USE_TIMESTAMP);
        mWakeLock = getWakeLock();
    }
	 
    /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume() {
       	initCamera();
        setPictureDelayTimer(CAPTURE_DELAY);
    	mWakeLock.acquire();
    	super.onResume();
    }
    /* (non-Javadoc)
     * @see android.app.Activity#onPause()
     */
    @Override
    protected void onPause() {
	    if (mTimer != null){
	    	mTimer.cancel();
	    	mTimer = null;
	    }
	    releaseCamera();
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		preview.removeView(mPreview);
		mPreview = null;
	    mWakeLock.release();
	    TimedShotApp.setCaptureImage(null);
    	super.onPause();
    }
    
    /**
     * prepare cam, preview and viewholder
     */
    private void initCamera(){
    	if (checkCameraHardware()){ 
        	mCamera = getCameraInstance();
        	mPreview = new CameraPreview(this, mCamera);
        	mPreviewHolder.addView(mPreview);
        }
    }
    
    /**
     * Acquire a wake lock instance used to prevent screen locks
     * @return <li> Instance of {@link PowerManager.WakeLock}
     */
    private PowerManager.WakeLock getWakeLock(){
    	PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
    	return pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "CaptureActivity");    	
    }
    
    /**
     * check if we have a camera
     * @return <li> true if device has a camera
     */
    private boolean checkCameraHardware() {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            return true;
        } else {
            return false;
        }
    }
    
	/**
	 * create a new Camera instance and set some default parameters
	 * @return Instance of {@link Camera}
	 */
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
    
    /**
     * release Camera if any.
     */
    private void releaseCamera(){
        if (mCamera != null){
            mCamera.release();
            mCamera = null;
        }
    }
    
    /**
     * start the Camera preview and set the Picture Delay until first shot is taken
     */
    private void prepareCamera(){
    	mCamera.startPreview();
    	setPictureDelayTimer(CAPTURE_DELAY);
    }
    
    /**
     * take the picture
     */
    private void takePicture(){
        mCamera.takePicture(null, null, mPicture);
    }
    
    /**
     * sets a Timer for the next execution of prepareCamera
     * @param interval
     */
    private void setNextPictureTimer(Integer interval){
    	if (interval > 0){
    		mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
    			
    			@Override
    			public void run() {
    		        prepareCamera();
    			}
    		}, interval);
    	}
    }
    
    /**
     * sets a Timer for the next execution of takePicture
     * @param interval
     */
    private void setPictureDelayTimer(Integer interval){
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
    
    /**
     * start an ftp upload intent
     */
    private void startFtpIntent(){
    	Intent ftpIntent = new Intent(this, FtpIntentService.class);
    	startService(ftpIntent);
    }
    
    /**
     * process the image, add overlays an trigger upload
     * @param data
     */
    private void processImageData(byte[] data){
    	
    	if (mUseOverlay){
        	Bitmap cameraBitmap = BitmapFactory.decodeByteArray
                    (data, 0, data.length);
        	int wid = cameraBitmap.getWidth();
            int hgt = cameraBitmap.getHeight();
            
            // create new image
            Bitmap newImage = Bitmap.createBitmap
                    (wid, hgt, Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(newImage);
            canvas.drawBitmap(cameraBitmap, 0f, 0f, null);
            Paint paint = new Paint(); 
            paint.setColor(Color.TRANSPARENT); 
            paint.setStyle(Style.FILL); 
            canvas.drawPaint(paint); 

            paint.setColor(Color.WHITE); 
            paint.setTextSize(14);
            
            // create timestamp
            String timestamp = DateFormat.format("dd/MM/yyyy hh:mm:ss", new Date()).toString();
            
            // get battery status
            Intent batteryIntent = registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            double temp = batteryIntent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1) / 10;
            
            // draw overlay
            canvas.drawText(timestamp , wid - 150, hgt - 100, paint);
            canvas.drawText("Battery: " + level , wid - 150, hgt - 79, paint);
            canvas.drawText("Temp:. " + temp , wid - 150, hgt - 58, paint);
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            newImage.compress(Bitmap.CompressFormat.JPEG, 100, output);
        	TimedShotApp.setCaptureImage(output.toByteArray());
        	
        	// clean up
        	newImage.recycle();
        	newImage = null;
        	cameraBitmap.recycle();
        	cameraBitmap = null;
        	canvas = null;
        	paint = null;
    		
    	}else{
        	TimedShotApp.setCaptureImage(data);
    	}

    	// upload the image
    	startFtpIntent();
    }
    
    private PictureCallback mPicture = new PictureCallback() {

        /* (non-Javadoc)
         * @see android.hardware.Camera.PictureCallback#onPictureTaken(byte[], android.hardware.Camera)
         */
        public void onPictureTaken(byte[] data, Camera camera) {       
        	processImageData(data);
        	setNextPictureTimer(mInterval * 60 * 1000 - CAPTURE_DELAY );
        }
    };
}
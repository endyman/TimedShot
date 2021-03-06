package net.domrose.timedshot;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	protected final static String PREFS_SERVER = "server";
	protected final static String PREFS_USER = "user";
	protected final static String PREFS_PASSWORD = "password";
	protected final static String PREFS_INTERVAL = "interval";
	protected final static String PREFS_FILE_PREFIX = "file_prefix";
	protected final static String PREFS_FILE_DIR = "file_dir";
	protected final static String PREFS_USE_OVERLAY = "overlay";
	protected final static String PREFS_USE_TIMESTAMP = "timestamp";
	
	
	protected final static String EXTRA_INTERVAL = "interval";
	protected final static String EXTRA_USE_OVERLAY = "overlay";
	protected final static String EXTRA_USE_TIMESTAMP = "timestamp";
	
	private SeekBar mIntervalSeekBar;
	private TextView mIntervalLabelCurrent;
	private TextView mIntervalUnit;
	private EditText mServerField;
	private EditText mUserField;
	private EditText mPasswordField;
	private EditText mFilePrefixField;
	private EditText mFileDir;
	private Button mCaptureButton;
	private SharedPreferences mainSettings;
	private ToggleButton mOverlayToggle;
	private Boolean isOverlayToggleChecked = false;
	private ToggleButton mTimestampToggle;
	private Boolean isTimestampToggleChecked = false;
	private Editor editor;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mIntervalSeekBar = (SeekBar) findViewById(R.id.interval_bar);
        
        mIntervalUnit = (TextView) findViewById(R.id.interval_label_unit);
        mIntervalLabelCurrent = (TextView) findViewById(R.id.interval_label_current);
        mIntervalLabelCurrent.setText(""+mIntervalSeekBar.getProgress());
        
        mServerField = (EditText) findViewById(R.id.server);
        
        mUserField = (EditText) findViewById(R.id.user);
        
        mPasswordField = (EditText) findViewById(R.id.password);
        
        mFilePrefixField = (EditText) findViewById(R.id.file_prefix);

        mFileDir = (EditText) findViewById(R.id.file_dir);
        
        mCaptureButton = (Button) findViewById(R.id.capture);
        
        mOverlayToggle = (ToggleButton) findViewById(R.id.toggle_overlay);
        mOverlayToggle.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				isOverlayToggleChecked = (!isOverlayToggleChecked);
				mOverlayToggle.setChecked(isOverlayToggleChecked);
			}
		});
        
        mTimestampToggle = (ToggleButton) findViewById(R.id.toggle_timestamp);
        mTimestampToggle.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				isTimestampToggleChecked = (!isTimestampToggleChecked);
				mTimestampToggle.setChecked(isTimestampToggleChecked);
			}
		});
        
        mIntervalSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if (progress > 0){
					mIntervalUnit.setText(R.string.interval_unit);
					mIntervalLabelCurrent.setText(progress+" ");						
				}else{
					mIntervalLabelCurrent.setText(R.string.text_off);
					mIntervalUnit.setText("");
				}
			}
		});
        
        mCaptureButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				onCaptureClick();
				
			}
		});
        
    	mainSettings = PreferenceManager.getDefaultSharedPreferences(TimedShotApp.getAppContext());
    	editor = mainSettings.edit();
        
    }
    
    private void onCaptureClick(){
        Intent intent = new Intent(this, CaptureActivity.class);
        intent.putExtra(EXTRA_INTERVAL, mIntervalSeekBar.getProgress());
        intent.putExtra(EXTRA_USE_OVERLAY, isOverlayToggleChecked);
        intent.putExtra(EXTRA_USE_TIMESTAMP, isTimestampToggleChecked);
        startActivity(intent);    	
    }
    
    private void persistData(){
    	editor.putString(PREFS_SERVER, mServerField.getText().toString());
    	editor.putString(PREFS_USER, mUserField.getText().toString());
    	editor.putString(PREFS_PASSWORD, mPasswordField.getText().toString());
    	editor.putInt(PREFS_INTERVAL, mIntervalSeekBar.getProgress());
    	editor.putString(PREFS_FILE_PREFIX, mFilePrefixField.getText().toString());
    	editor.putString(PREFS_FILE_DIR, mFileDir.getText().toString());
    	editor.putBoolean(PREFS_USE_OVERLAY, isOverlayToggleChecked);
    	editor.putBoolean(PREFS_USE_TIMESTAMP, isTimestampToggleChecked);
    	editor.commit();

    }
    
    private void restoreData(){
    	mServerField.setText(mainSettings.getString(PREFS_SERVER, ""));
    	mUserField.setText(mainSettings.getString(PREFS_USER, ""));
    	mPasswordField.setText(mainSettings.getString(PREFS_PASSWORD, ""));
    	mIntervalSeekBar.setProgress(mainSettings.getInt(PREFS_INTERVAL, 5));
    	mFilePrefixField.setText(mainSettings.getString(PREFS_FILE_PREFIX, ""));
    	mFileDir.setText(mainSettings.getString(PREFS_FILE_DIR, ""));
    	isOverlayToggleChecked = mainSettings.getBoolean(PREFS_USE_OVERLAY, false);
    	mOverlayToggle.setChecked(isOverlayToggleChecked);
    	isTimestampToggleChecked = mainSettings.getBoolean(PREFS_USE_TIMESTAMP, false);
    	mTimestampToggle.setChecked(isTimestampToggleChecked);
    }
    
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	persistData();
    	super.onPause();
    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	restoreData();
    	if (mServerField.getText().toString().equals("")){
    		mServerField.requestFocus();
    	}
    	super.onResume();
    }
}

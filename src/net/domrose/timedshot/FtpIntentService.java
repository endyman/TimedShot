package net.domrose.timedshot;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.util.Log;

public class FtpIntentService extends IntentService {

	private static final String TAG = "FtpIntentService";
	private static final String REMOTE_DIRECTORY = "timedshot";
	private static final String REMOTE_FILE_PREFIX = "ts_";

	public FtpIntentService() {
		super("FtpInentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		byte[] data = TimedShotApp.getCaptureImage();
		Log.d(TAG, "onHandleIntent");

		if (data != null){
			SharedPreferences mainsettings = PreferenceManager.getDefaultSharedPreferences(TimedShotApp.getAppContext());
			String server = mainsettings.getString(MainActivity.PREFS_SERVER, "");
			String user = mainsettings.getString(MainActivity.PREFS_USER, "");
			String password = mainsettings.getString(MainActivity.PREFS_PASSWORD, "");
			String dir_name = mainsettings.getString(MainActivity.PREFS_FILE_DIR, REMOTE_DIRECTORY);
			String file_name = mainsettings.getString(MainActivity.PREFS_FILE_PREFIX, REMOTE_FILE_PREFIX);
			Boolean useTimestamp = mainsettings.getBoolean(MainActivity.PREFS_USE_TIMESTAMP, false);
			Log.d(TAG, "Server: " + server + " user: " + user + " pass: ***" );
			
			// append timestamp to filename
			if (useTimestamp == true){
	            String timestamp = DateFormat.format("yyyyMMddhhmmss", new Date()).toString();
	            file_name = file_name + "_" + timestamp;
			}
			
			// use anonymous ftp user if no user provided
			if (user.equals("") || password.equals("")){
				user = "ftp";
				password = "ftp";
			}
			
			FTPClient mFtp = new FTPClient();
			try {
				mFtp.connect(server);
				if (mFtp.login(user, password)){
					mFtp.setFileType(FTP.BINARY_FILE_TYPE);
					mFtp.enterLocalPassiveMode();
					mFtp.changeWorkingDirectory(dir_name);
					InputStream is = new ByteArrayInputStream(data);
					Boolean success = mFtp.storeFile(file_name + ".jpg", is);
					is.close();
					mFtp.disconnect();
					if (success == true){
						// TODO display success
					}else{
						// TODO display error
					}
				}else{
					// TODO display error
				}
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}
}

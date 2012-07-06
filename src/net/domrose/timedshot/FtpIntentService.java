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
			Log.d(TAG, "Server: " + server + " user: " + user + " pass: ***" );
			
			if (user.equals("")){
				user = "ftp";
			}
			if (password.equals("")){
				password = "ftp";
			}
			FTPClient mFtp = new FTPClient();
			try {
				mFtp.connect(server);
				if (mFtp.login(user, password)){
					mFtp.setFileType(FTP.BINARY_FILE_TYPE);
					mFtp.enterLocalPassiveMode();
					mFtp.changeWorkingDirectory(REMOTE_DIRECTORY);
					InputStream is = new ByteArrayInputStream(data);
					Boolean success = mFtp.storeFile(REMOTE_FILE_PREFIX + getDateTimeStamp() + ".jpg", is);
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
	
	private String getDateTimeStamp(){
		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		Date date = cal.getTime();
		return "" + date.getYear() +""+  date.getMonth() +""+ date.getDate() + "" + date.getHours() + "" + date.getMinutes();
	}

}

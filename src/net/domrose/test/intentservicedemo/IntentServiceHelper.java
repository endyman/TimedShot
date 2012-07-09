package net.domrose.test.intentservicedemo;

import android.content.Context;
import android.content.Intent;

public class IntentServiceHelper {
	
	public static final int SLEEP_ACTION = 0x01;

	private static final String LOG = IntentServiceHelper.class.getName();
	private Context context;
	private static IntentServiceHelper serviceHelper;

	public static IntentServiceHelper getInstance(Context context) {
		if (serviceHelper == null)
			serviceHelper = new IntentServiceHelper(context);
		
		// to update the context each time a service helper is called
		serviceHelper.setContext(context);
		return serviceHelper;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public IntentServiceHelper(Context context) {
		this.context = context;
	}

	public boolean startService(int action,
			CustomResultReceiver resultReceiver) {
		if (resultReceiver != null) {
			Intent intent = null;
			intent = new Intent(Intent.ACTION_SYNC, null, context,
					CustomIntentService.class);
			intent.putExtra("action", action);
			intent.putExtra("receiver", resultReceiver);

			context.startService(intent);
			return true;
		} else {

			return false;
		}
	}
}

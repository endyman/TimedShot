package net.domrose.test.intentservicedemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class CustomResultReceiver extends ResultReceiver {

	public static final int STATUS_RUNNING = 0;
	public static final int STATUS_FINISHED = 1;
	public static final int STATUS_ERROR = 2;

	private Receiver mReceiver;

	public CustomResultReceiver(Handler handler) {
		super(handler);
	}

	public void setReceiver(Receiver receiver) {
		mReceiver = receiver;
	}

	public interface Receiver {
		public void onReceiveResult(int resultCode, Bundle resultData);
	}

	@Override
	protected void onReceiveResult(int resultCode, Bundle resultData) {
		if (mReceiver != null) {
			mReceiver.onReceiveResult(resultCode, resultData);
		}
	}

}

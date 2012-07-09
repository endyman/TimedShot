package de.subaqueous.ext;

import android.app.Application;
import android.content.res.Resources;

/**
 * extended Application Class including convenience methods and logging
 * @author Nils Domrose
 *
 */
public class ExtApp extends Application {
	
	private static ExtApp instance;
	protected static String LCLASS;
	public ExtApp(){
		LCLASS = getClass().getName();		
	}
	
	@Override
	public void onCreate(){
		super.onCreate();
		instance = this;
	}
	
	/**
	 * get the instance of this application
	 * @return {@link ExtApp} - instance of this application
	 */
	public static ExtApp getInstance(){
		return instance;
	}
	
	/**
	 * get the resources of this Application
	 * @return {@link Resources} - Resources of this Application
	 */
	public static Resources getResource(){
		return instance.getResources();
	}

}

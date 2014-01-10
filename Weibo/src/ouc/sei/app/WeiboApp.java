/**
 * 
 */
package ouc.sei.app;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * @author wangqi
 *
 */
public class WeiboApp extends Application{
	public static Context mContext;
	public static WeiboApp instance;
	public static SharedPreferences mPref;
	private static List<Activity> activities = new ArrayList<Activity>();	
	public static void addActivity(Activity activity) {
		activities.add(activity);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mContext = this.getApplicationContext();
		mPref = PreferenceManager.getDefaultSharedPreferences(this);
		System.out.println("_____________________________________________");

	}
	public static WeiboApp getInstance() { 
		if (null == instance) { 
		instance = new WeiboApp(); 
		} 
		return instance; 
		} 
	
	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();

		for (Activity activity : activities) {
			activity.finish();
		}
		android.os.Process.killProcess(android.os.Process.myPid());
	}

}


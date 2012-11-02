package com.example.ambientlightsensor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;
import android.view.WindowManager;

/*
 <activity android:name=".RefresherActivity"
          android:excludeFromRecents="true"
          android:theme="@android:style/Theme.Translucent.NoTitleBar"
          android:taskAffinity="com.antonc.phone_schedule.Dummy">
</activity>
 
 */
public class RefresherActivity extends Activity
{
	private static int brightness;
	private static PowerManager.WakeLock wake_lock;
	
	public void afterCreate()
	{
    	//try
    	{
            WindowManager.LayoutParams lp = getWindow().getAttributes(); 
    		lp.flags |= WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;// | WindowManager.LayoutParams.FLAG_DIM_BEHIND; 
    		lp.screenBrightness = //Settings.System.getInt(getContentResolver(),Settings.System.SCREEN_BRIGHTNESS)
    				brightness/255.0f; 
        	Log.e("testing","window br applied "+Float.toString(lp.screenBrightness));
    		getWindow().setAttributes(lp);		
    	}
    	//catch (SettingNotFoundException ex)    	{    	}
		
	}
	// for each action we need slight delay -- tested, the direct calls do not work 
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        afterCreate();
        super.onCreate(savedInstanceState);            
        
    	new Handler().postDelayed(new Runnable() { 
	         public void run() {
	        	 if (wake_lock!=null)
	        	 {
	        		 wake_lock.release();
	        		 wake_lock = null;
	        	 }
	        	 RefresherActivity.this.finish();
	        	 } },0);
    }
    
    public static void run(final Context context)
    {
       	new Handler().postDelayed(new Runnable() { 
  	         public void run() {
	        	Intent intent = new Intent(context, RefresherActivity.class);
   	  			intent.addFlags(Intent.FLAG_FROM_BACKGROUND | Intent.FLAG_ACTIVITY_NEW_TASK);
   	  			context.startActivity(intent);
 	         } },0); 
  	}
    
    public static void setSystemBrightness(Context context,int _brightness,PowerManager.WakeLock wl) // range: 1-255
    {
    	wake_lock = wl;
    	brightness = _brightness;
        //Settings.System.putInt(context.getContentResolver(),Settings.System.SCREEN_BRIGHTNESS,256-brightness);    	
        Settings.System.putInt(context.getContentResolver(),Settings.System.SCREEN_BRIGHTNESS,brightness);    	

       	RefresherActivity.run(context);
    }
    public static void setSystemBrightness(Context context,int _brightness) // range: 1-255
    {
    	setSystemBrightness(context,_brightness,null);
    }
    	
}

package com.example.ambientlightsensor;

import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;

// TODO: the project died, because I cannot find a reliable method to APPLY system-wide changes
// not just set them
// for example: user locks the screen (power off button shortly), then turn screen on (power off shortly again)
// this app should APPLY high brightness, but if time between those two presses was very short
// then I cannot simply know any method which would APPLY brightness at all -- because Android (thanks Google)
// will ignore it

public class SensorReceiver extends BroadcastReceiver
{
	private static PrefsData prefs_data;
	private static Integer brightness = null;
	
	public static PrefsData getPrefsData(Context context)
	{
		if (prefs_data==null)
			prefs_data = Preferences.loadPreferences(context);
		return prefs_data;
	}

	private static int fallbackBrightness(Context context)
	{
   		return brightness==null?getPrefsData(context).outdoorBrightness:Math.min(255, Math.max(1, brightness));
	}

    @Override public void onReceive(final Context context, Intent intent) 
	{
    	//Log.e("testing","rec. "+intent.getAction());

    	if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF))
        {
       		Log.e("testing","OFF applying "+Integer.toString(getPrefsData(context).outdoorBrightness));
			RefresherActivity.setSystemBrightness(context, getPrefsData(context).outdoorBrightness);
        }
        else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON))
        {
    		KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        	if (!km.inKeyguardRestrictedInputMode()) // unlocked
        	{
           		//Log.e("testing","RESTORING "+(brightness==null?"fallback!":"")+" applying "+Integer.toString(fallbackBrightness(context)));
           		//RefresherActivity.setSystemBrightness(context, fallbackBrightness(context));
        	}
        	else // locked
        	{
       		Log.e("testing","ON applying "+Integer.toString(getPrefsData(context).outdoorBrightness));
            Settings.System.putInt(context.getContentResolver(),Settings.System.SCREEN_BRIGHTNESS,getPrefsData(context).outdoorBrightness);

/*            KeyguardLock kl = (KeyguardLock) km.newKeyguardLock("User");
            kl.disableKeyguard();
            kl.reenableKeyguard();
*/
            
/*       		DevicePolicyManager dpm = (DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
            dpm.lockNow();
*/       		
       		/*PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
       	 PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "My Tag");
       	 wl.acquire();
			RefresherActivity.setSystemBrightness(context, getPrefsData(context).outdoorBrightness,wl);
       	 */
			RefresherActivity.setSystemBrightness(context, getPrefsData(context).outdoorBrightness);
	       	// pm.userActivity(0, false);

        		brightness = null;
        		
    			LightSensor.averageLuma(new Action1<Float>(){

        			@Override
        			public void apply(Float value) 
        			{
                		PrefsData d = getPrefsData(context);
            			float alpha = (d.outdoorBrightness-d.indoorBrightness)/(d.outdoorLightning-d.indoorLightning);
            			float beta = d.indoorBrightness-alpha*d.indoorLightning;

            			brightness = Float.valueOf(alpha*value+beta).intValue();
        	        	Log.e("testing","SCREEN ON out.br="+Integer.toString(d.outdoorBrightness)+
        	        					", int.br="+Integer.toString(d.indoorBrightness)+
        	        					", out.lum="+Float.toString(d.outdoorLightning)+
        	        					", in.lum="+Float.toString(d.indoorLightning)+
        	        					", a="+Float.toString(alpha)+
        	        					", b="+Float.toString(beta)+
        	        					", val="+Float.toString(value)+
        	        					", comp="+Integer.toString(brightness));
        			}});

        	}
        }
	    // screen is ON and unlocked
        /*else if (intent.getAction().equals(Intent.ACTION_USER_PRESENT))
        {
        	Log.e("testing","UNLOCKED "+(brightness==null?"fallback!":"")+" applying "+Integer.toString(fallbackBrightness(context)));
       		RefresherActivity.setSystemBrightness(context, fallbackBrightness(context));
        }*/
	}

	

}

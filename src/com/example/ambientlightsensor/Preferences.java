package com.example.ambientlightsensor;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences 
{
	private class Keys
	{
		static final String light_sensor = "light_sensor";
		static final String indoor_lightning = "indoor_lightning";
		static final String indoor_brightness = "indoor_brightness";
		static final String outdoor_lightning = "outdoor_lightning";
		static final String outdoor_brightness = "outdoor_brightness";;
	}
	
	public static PrefsData loadPreferences(Context context)
	{
	    SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(context);    	
	    
	    PrefsData data = PrefsData.create()
	    		.enableLightSensor(prefs.getBoolean(Keys.light_sensor, false))
	    		.setIndoorLightning(prefs.getFloat(Keys.indoor_lightning, 30f))
	    		.setIndoorBrightness(prefs.getInt(Keys.indoor_brightness, 100))
	    		.setOutdoorLightning(prefs.getFloat(Keys.outdoor_lightning, 70f))
	    		.setOutdoorBrightness(prefs.getInt(Keys.outdoor_brightness, 255));		
        
        return data;
	}
	
	public static PrefsData savePreferences(Context context,PrefsData data)
	{
	    SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(context);    	
        SharedPreferences.Editor editor = prefs.edit();

        if (data.lightSensorEnabled!=null)
        	editor.putBoolean(Keys.light_sensor, data.lightSensorEnabled);
        if (data.indoorLightning!=null)
        	editor.putFloat(Keys.indoor_lightning, data.indoorLightning);
        if (data.indoorBrightness!=null)
        	editor.putInt(Keys.indoor_brightness, data.indoorBrightness);
        if (data.outdoorLightning!=null)
        	editor.putFloat(Keys.outdoor_lightning, data.outdoorLightning);
        if (data.outdoorBrightness!=null)
        	editor.putInt(Keys.outdoor_brightness, data.outdoorBrightness);

        editor.commit();
        
        return data;
	}

}

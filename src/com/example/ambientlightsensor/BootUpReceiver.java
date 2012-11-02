package com.example.ambientlightsensor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootUpReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		if (SensorReceiver.getPrefsData(context).lightSensorEnabled)
		{
//			context.startService(new Intent(context, SensorService.class));
		}
	}
}
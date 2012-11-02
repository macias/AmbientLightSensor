package com.example.ambientlightsensor;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class SensorService extends Service
{
    SensorReceiver receiver = null;

    private boolean isReceiverOn()
    {
    	return receiver!=null;
    }
    
    @Override
    public void onCreate() {
		super.onCreate();

        if (!isReceiverOn())
        {
       		receiver = new SensorReceiver();
        	
       		IntentFilter filter = new IntentFilter();
       		filter.addAction(Intent.ACTION_USER_PRESENT);
       		filter.addAction(Intent.ACTION_SCREEN_ON);
       		filter.addAction(Intent.ACTION_SCREEN_OFF);
       		registerReceiver(receiver, filter);
        }
    }
    
    @Override 
    public void onDestroy()
    {
    	if (isReceiverOn())
    		unregisterReceiver(receiver);
    	
    	receiver = null;
    	
    	super.onDestroy();
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent,flags,startId);
        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}

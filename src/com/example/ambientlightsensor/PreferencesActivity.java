package com.example.ambientlightsensor;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.TextView;

public class PreferencesActivity extends Activity 
{
	Intent sensor_service;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        
    	sensor_service = new Intent(this, SensorService.class);
        
        TextView tv1 = (TextView)findViewById(R.id.categoryText1);
        TextView tv2 = (TextView)findViewById(R.id.categoryText2);
        tv1.setBackgroundColor(enabledCheckedText().getCurrentTextColor());
        tv2.setBackgroundColor(enabledCheckedText().getCurrentTextColor());

    	PrefsData d = updateWidgets();
        
        if (d.lightSensorEnabled)
        	startSensorService();
    }
    
    void startSensorService()
    {
    	Log.e("testing","start service");    	
    	startService(sensor_service);
    }
    
    private PrefsData getPreferences()
    {
    	return SensorReceiver.getPrefsData(this);
    }
    private PrefsData updateWidgets()
    {
    	PrefsData d = getPreferences();

    	enabledCheckedText().setChecked(d.lightSensorEnabled);
    	((TextView)findViewById(R.id.indoorLightningText)).setText(Integer.toString(Float.valueOf((d.indoorLightning-1f)*100f/255f).intValue())+"%");
    	((TextView)findViewById(R.id.indoorBrightnessText)).setText(Integer.toString(Float.valueOf(d.indoorBrightness*100f/255f).intValue())+"%");
    	((TextView)findViewById(R.id.outdoorLightningText)).setText(Integer.toString(Float.valueOf((d.outdoorLightning-1f)*100f/255f).intValue())+"%");
    	((TextView)findViewById(R.id.outdoorBrightnessText)).setText(Integer.toString(Float.valueOf(d.outdoorBrightness*100f/255f).intValue())+"%");
    	
    	return d; 
    }
	private CheckedTextView enabledCheckedText()
	{
		return (CheckedTextView)findViewById(R.id.enabledCheckedText);
	}
    
    private PrefsData savePreferences(PrefsData data)
    {
    	return Preferences.savePreferences(this, data);
    }
    public void enabledClicked(View view)
    {
    	CheckedTextView chk = enabledCheckedText();
    	chk.toggle();
    	
    	if (savePreferences(getPreferences().enableLightSensor(chk.isChecked())).lightSensorEnabled)
    		startSensorService();
    	else
	    	stopService(sensor_service);
    }
    
    void measureLuma(final PrefsData.TypeEnum type)
    {
    	final TextView tv = getLightningText(type);
    	tv.setText(getString(R.string.measuring));
    	
    	if (!LightSensor.averageLuma(new Action1<Float>(){

			@Override
			public void apply(Float value) {
		    	savePreferences(getPreferences().setLightning(type,value));
		    	updateWidgets();
			}}))
    	{
    		AlertDialog dialog = new AlertDialog.Builder(this).create();
    		dialog.setMessage(getString(R.string.photo_error));
    		dialog.setButton(AlertDialog.BUTTON_NEUTRAL,getString(R.string.ok),new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dlg,int id) {
					dlg.dismiss();
				}
			  });
    		
    		dialog.show();
    	}
    }

    TextView getLightningText(PrefsData.TypeEnum type)
    {
    	if (type==PrefsData.TypeEnum.Indoor)
    		return (TextView)findViewById(R.id.indoorLightningText);
    	else
    		return (TextView)findViewById(R.id.outdoorLightningText);
    }
    
    public void indoorPhotoClicked(View view)
    {
    	measureLuma(PrefsData.TypeEnum.Indoor);
    }
    
    public void outdoorPhotoClicked(View view)
    {
    	measureLuma(PrefsData.TypeEnum.Outdoor);
    }

    static private PrefsData.TypeEnum brightness_mode;
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
    	switch(requestCode) 
    	{
    		case BRIGHTNESS:
    			if (resultCode == RESULT_OK) 
    			{
    				savePreferences(getPreferences().setBrightness(brightness_mode,BrightnessActivity.value));
    				updateWidgets();
    				break;
    			}
    	}
    }
    
    private static final int BRIGHTNESS = 1;
    
    public void runBrightness()
    {
    	BrightnessActivity.value = getPreferences().getBrightness(brightness_mode);
    	Intent intent = new Intent(this, BrightnessActivity.class);
    	startActivityForResult(intent,BRIGHTNESS);
    }
    
    public void indoorBrightnessClicked(View view)
    {
    	brightness_mode = PrefsData.TypeEnum.Indoor;
    	runBrightness();
    }
    
    public void outdoorBrightnessClicked(View view)
    {
    	brightness_mode = PrefsData.TypeEnum.Outdoor;
    	runBrightness();
    }
    
    
/*    public void testClicked(View view)
    {
    	Handler handler = new Handler();
    	handler.postDelayed(new Runnable() {
    	    @Override
    	    public void run() {
    	    	KeyguardManager km = (KeyguardManager)getSystemService(KEYGUARD_SERVICE);
    	    	KeyguardManager.KeyguardLock kl = km.newKeyguardLock("My_App");
    	    	kl.disableKeyguard();
    	        kl.reenableKeyguard();
    	        Log.e("LOCK","key guard back on");
    	    }
    	}, 300);
    	
    }*/
}


package com.example.ambientlightsensor;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class BrightnessActivity extends Activity 
{
	// I gave up with elegant coding on Android
	static int value;
	
	private void setup()
	{
    	getValueText().setText(Integer.toString((value*100)/255)+"%");

    	WindowManager.LayoutParams lp = getWindow().getAttributes(); 
		lp.screenBrightness = value/255.0f; 
		getWindow().setAttributes(lp);		
	}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brightness);

        setup();
        
    	SeekBar seekbar = (SeekBar)findViewById(R.id.seekBar1);
    	seekbar.setProgress(value);
    	
    	seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				value = progress+1; // we do not allow to set 0 as brightness
		    	
				setup();
			}

			@Override public void onStartTrackingTouch(SeekBar seekBar) { }
			@Override public void onStopTrackingTouch(SeekBar seekBar) { }
			
    	});
    	
    }
    

    private TextView getValueText()
    {
    	return (TextView)findViewById(R.id.valueText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_brightness, menu);
        return true;
    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
    	if (keyCode == KeyEvent.KEYCODE_BACK ) 
    	{
    		  Intent returnIntent = new Intent();
    	      setResult(RESULT_OK,returnIntent);     		
    	}

    	return super.onKeyDown(keyCode, event);    
    }
    
    
}

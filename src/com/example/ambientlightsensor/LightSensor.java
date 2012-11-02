package com.example.ambientlightsensor;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.util.Log;

public class LightSensor 
{
    private static int getFrontCamera()
    {
    	int cameras = Camera.getNumberOfCameras();
    	
    	for (int i=0;i<cameras;++i)
    	{
    		Camera.CameraInfo info = new Camera.CameraInfo();
    		Camera.getCameraInfo(i, info);
    		if (info.facing==Camera.CameraInfo.CAMERA_FACING_FRONT)
    			return i;
    	}
    	
    	return -1;
    }
    
    private static boolean preview = false;
    
    private static final int width = 320;
    private static final int height = 240;

	public static boolean averageLuma(final Action1<Float> callback)
    {
    	int front = getFrontCamera();
    	if (front==-1)
    		return false;
    	
    	if (preview)
    		return true;
    	
    	preview = true;
    	
    	final Camera camera = Camera.open(front);

    	Camera.Parameters params = camera.getParameters();
    	params.setPreviewSize(width,height);
    	params.setPreviewFormat(ImageFormat.NV21);
        camera.setParameters(params);

           
   	    camera.setPreviewCallback(new Camera.PreviewCallback(){
    	    	
				@Override
				public void onPreviewFrame(byte[] data, Camera camera) {
					Log.e("testing","end preview");
					
    	        	camera.stopPreview();
    	        	camera.release();

    	            float avg = 0f;
    	        	for (int i=width*height-1;i>=0;--i)
    	        	{
    	        		if (data[i]<0)
    	        			avg += 256.0+data[i];
    	        		else
    	        			avg += data[i];
    	        	}
    	        	
    	        	avg /= width*height;
    	        			
    	        	callback.apply(avg);
    	        	
    	        	preview = false;
					
				}});

		Log.e("testing","preview");
    	    camera.startPreview();
    	    
    	    return true;
    }
}

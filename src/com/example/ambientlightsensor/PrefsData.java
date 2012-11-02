package com.example.ambientlightsensor;

public class PrefsData 
{
	public Boolean lightSensorEnabled = null;
	public Float indoorLightning = null; // range: 0-255
	public Integer indoorBrightness = null; // range: 0-255
	public Float outdoorLightning = null;
	public Integer outdoorBrightness = null;
	
	public enum TypeEnum { Indoor,Outdoor };
	
	public static PrefsData create()
	{
		return new PrefsData();
	}
	public PrefsData setLightning(TypeEnum type,Float val)
	{
		if (type==TypeEnum.Indoor)
			return setIndoorLightning(val);
		else
			return setOutdoorLightning(val);
	}
	public PrefsData setBrightness(TypeEnum type,Integer val)
	{
		if (type==TypeEnum.Indoor)
			return setIndoorBrightness(val);
		else
			return setOutdoorBrightness(val);
	}
	public Integer getBrightness(TypeEnum type)
	{
		if (type==TypeEnum.Indoor)
			return indoorBrightness;
		else
			return outdoorBrightness;
	}
	public PrefsData enableLightSensor(boolean val)
	{
		lightSensorEnabled = val;
		return this;
	}
	public PrefsData setIndoorLightning(Float val)
	{
		indoorLightning = val;
		return this;
	}
	public PrefsData setIndoorBrightness(Integer val)
	{
		indoorBrightness = val;
		return this;
	}
	public PrefsData setOutdoorLightning(Float val)
	{
		outdoorLightning = val;
		return this;
	}
	public PrefsData setOutdoorBrightness(Integer val)
	{
		outdoorBrightness = val;
		return this;
	}
}

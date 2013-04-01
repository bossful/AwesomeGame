package com.niejun.androidgame.framework.handler;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class CompassHandler implements SensorEventListener {

	float yaw;//Æ«º½
	float pitch;//ÇãÐ±
	float roll;//·­¹ö
	
	@SuppressWarnings("deprecation")
	public CompassHandler(Context context) {
		SensorManager manager = (SensorManager) context
				.getSystemService(Context.SENSOR_SERVICE);
		if(manager.getSensorList(Sensor.TYPE_ORIENTATION).size() != 0){
			Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
			manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		//
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		this.yaw = event.values[0];
		this.pitch = event.values[1];
		this.roll = event.values[2];
	}

	/**
	 * @return the yaw
	 */
	public float getYaw() {
		return yaw;
	}

	/**
	 * @return the pitch
	 */
	public float getPitch() {
		return pitch;
	}

	/**
	 * @return the roll
	 */
	public float getRoll() {
		return roll;
	}

}

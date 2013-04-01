package com.niejun.androidgame.framework.handler;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class AccelerometerHandler implements SensorEventListener {

	float accelX;
	float accelY;
	float accelZ;

	public AccelerometerHandler(Context context) {
		SensorManager manager = (SensorManager) context
				.getSystemService(Context.SENSOR_SERVICE);
		if (manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0) {
			Sensor sensor = manager.getSensorList(Sensor.TYPE_ACCELEROMETER)
					.get(0);
			manager.registerListener(this, sensor,
					SensorManager.SENSOR_DELAY_GAME);
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		//
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		this.accelX = event.values[0];
		this.accelY = event.values[1];
		this.accelZ = event.values[2];
	}

	/**
	 * @return the accelX
	 */
	public float getAccelX() {
		return accelX;
	}

	/**
	 * @return the accelY
	 */
	public float getAccelY() {
		return accelY;
	}

	/**
	 * @return the accelZ
	 */
	public float getAccelZ() {
		return accelZ;
	}

}

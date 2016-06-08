package com.bitman.btgpstest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

public class MockLocationService extends Service {
	public static final String TAG = MockLocationService.class.getName();

	public void setChange(double change) {
		this.change = change;
	}

	private int UPDATE_TIME = 1000;

	private MyBinder myBinder = new MyBinder();
	private Handler handler = new Handler();

	private LocationManager mLocationManager;
	// private static double unit = 0;
	private static double latitude = 30.5525326188;
	private static double longitude = 104.0329972433;
	private static double altitude = 104.0329972433;
	private double change = 0.00004;
	private int i = 0;

	private static List<Double> latSendList = new ArrayList<Double>();
	private static List<Double> lonSendList = new ArrayList<Double>();

	public void setLatSendList(List<Double> latList) {
		latSendList = latList;
	}

	public void setLonSendList(List<Double> lonList) {
		lonSendList = lonList;
	}

	public MockLocationService() {
		Log.i(TAG, "服务初始化");
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		Log.i(TAG, "BindService-->onBind()");
		mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		mLocationManager.addTestProvider(LocationManager.GPS_PROVIDER, false, false, false, false, true, true, true, 0,
				/* magic */5);
		mLocationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true);

		return myBinder;
	}

	public void setUPDATE_TIME(int uPDATE_TIME) {
		UPDATE_TIME = uPDATE_TIME;
	}

	public class MyBinder extends Binder {
		public MockLocationService getService1() {
			return MockLocationService.this;
		}
	}

	public void startMockLocation() {
		Log.i(TAG, "start mock");
		handler.post(update_thread);
	}

	public void puaseMockLocation() {
		Log.i(TAG, "puase mock");
		handler.removeCallbacks(update_thread);
	}

	public void stopMockLocation() {
		Log.i(TAG, "stop mock");
		handler.removeCallbacks(update_thread);
		mLocationManager.removeTestProvider(LocationManager.GPS_PROVIDER);
		mLocationManager.removeTestProvider(LocationManager.NETWORK_PROVIDER);
		latitude = 30.5525326188;
		longitude = 104.0329972433;
	}

	public void buildMockLocation() {
		Log.i(TAG, "start mock");
			handler.post(update_thread2);
	}

	Runnable update_thread2 = new Runnable() {
		public void run() {
				latitude = latSendList.get(i);
				longitude = lonSendList.get(i);
				Random rand = new Random();
				altitude = rand.nextDouble() * 50 + 500;
				setMockLocation2(LocationManager.GPS_PROVIDER);
				setMockLocation2(LocationManager.NETWORK_PROVIDER);
				handler.postDelayed(update_thread2, 2500);
				i++;
			}
	};

	Runnable update_thread = new Runnable() {
		public void run() {
			// unit = unit+0.00002;
			// if(unit > 1)unit = 0;
			latitude += change;
			longitude += change;
			Random rand = new Random();
			altitude = rand.nextDouble() * 50 + 500;
			setMockLocation(LocationManager.GPS_PROVIDER);
			setMockLocation(LocationManager.NETWORK_PROVIDER);
			handler.postDelayed(update_thread, UPDATE_TIME);
		}
	};

	private void setMockLocation2(String PROVIDER) {
		// mLocationManager.removeTestProvider(LocationManager.GPS_PROVIDER);
		mLocationManager.addTestProvider(
				// LocationManager.GPS_PROVIDER,
				PROVIDER, "requiresNetwork" == "", "requiresSatellite" == "", "requiresCell" == "",
				"hasMonetaryCost" == "", "supportsAltitude" == "", "supportsSpeed" == "", "supportsBearing" == "",

				android.location.Criteria.POWER_LOW, android.location.Criteria.ACCURACY_FINE);

		// Location newLocation = new Location(LocationManager.GPS_PROVIDER);
		Location newLocation = new Location(PROVIDER);
		// newLocation.setLatitude(30.5525326188 + unit);
		// newLocation.setLongitude(104.0329972433 + unit);
		newLocation.setLatitude(latitude);
		newLocation.setLongitude(longitude);
		newLocation.setAltitude(altitude);
		newLocation.setAccuracy(50.f);
		newLocation.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
		newLocation.setTime(System.currentTimeMillis());
		// mLocationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER,
		// true);
		mLocationManager.setTestProviderEnabled(PROVIDER, true);
		// mLocationManager.setTestProviderStatus(LocationManager.GPS_PROVIDER,
		mLocationManager.setTestProviderStatus(PROVIDER, LocationProvider.AVAILABLE, null, System.currentTimeMillis());
		// mLocationManager.setTestProviderLocation(LocationManager.GPS_PROVIDER,
		// newLocation);
		mLocationManager.setTestProviderLocation(PROVIDER, newLocation);
		Log.i(TAG, "Altitude:" + altitude);
		Log.i(TAG, "la:" + newLocation.getLatitude());
		Log.i(TAG, "lo:" + newLocation.getLongitude());
	}

	private void setMockLocation(String PROVIDER) {
		// mLocationManager.removeTestProvider(LocationManager.GPS_PROVIDER);
		mLocationManager.addTestProvider(
				// LocationManager.GPS_PROVIDER,
				PROVIDER, "requiresNetwork" == "", "requiresSatellite" == "", "requiresCell" == "",
				"hasMonetaryCost" == "", "supportsAltitude" == "", "supportsSpeed" == "", "supportsBearing" == "",

				android.location.Criteria.POWER_LOW, android.location.Criteria.ACCURACY_FINE);

		// Location newLocation = new Location(LocationManager.GPS_PROVIDER);
		Location newLocation = new Location(PROVIDER);
		// newLocation.setLatitude(30.5525326188 + unit);
		// newLocation.setLongitude(104.0329972433 + unit);
		newLocation.setLatitude(latitude);
		newLocation.setLongitude(longitude);
		newLocation.setAltitude(altitude);
		newLocation.setAccuracy(50.f);
		newLocation.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
		newLocation.setTime(System.currentTimeMillis());
		// mLocationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER,
		// true);
		mLocationManager.setTestProviderEnabled(PROVIDER, true);
		// mLocationManager.setTestProviderStatus(LocationManager.GPS_PROVIDER,
		mLocationManager.setTestProviderStatus(PROVIDER, LocationProvider.AVAILABLE, null, System.currentTimeMillis());
		// mLocationManager.setTestProviderLocation(LocationManager.GPS_PROVIDER,
		// newLocation);
		mLocationManager.setTestProviderLocation(PROVIDER, newLocation);
		Log.i(TAG, "Altitude:" + altitude);
		Log.i(TAG, "Time:" + UPDATE_TIME);
		Log.i(TAG, "change:" + change);
		Log.i(TAG, "la:" + newLocation.getLatitude());
		Log.i(TAG, "lo:" + newLocation.getLongitude());
	}
}

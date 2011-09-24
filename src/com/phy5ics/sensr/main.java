package com.phy5ics.sensr;

import com.phy5ics.sensr.R;
import java.util.List;
import java.util.Iterator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class main extends Activity implements SensorEventListener {
    private final String TAG = "sensr main";
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mOrientation;
    private Sensor mLight;
    private Sensor mMagnetic;
    private Sensor mProximity;
    private Sensor mTemperature;

    private List<Sensor> lSensorList;
    
    private TextView tvAccelX;
    private TextView tvAccelY;
    private TextView tvAccelZ;
    private TextView tvOrientX;
    private TextView tvOrientY;
    private TextView tvOrientZ;
    private TextView tvLight;
    private TextView tvProximity;
    private TextView tvGravity;
    private TextView tvMagnetic;
    private TextView tvTemperature;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mOrientation = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mMagnetic = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mTemperature = mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
        
        lSensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        /*
     	02-05 20:30:03.882: DEBUG/sensr main(12883): 1: KR3DM 3-axis Accelerometer
		02-05 20:30:03.882: DEBUG/sensr main(12883): 2: AK8973 3-axis Magnetic field sensor
		02-05 20:30:03.882: DEBUG/sensr main(12883): 3: AK8973 Orientation sensor
		02-05 20:30:03.886: DEBUG/sensr main(12883): 4: K3G Gyroscope sensor
		02-05 20:30:03.882: DEBUG/sensr main(12883): 5: GP2A Light sensor
		02-05 20:30:03.886: DEBUG/sensr main(12883): 8: GP2A Proximity sensor
		02-05 20:30:03.886: DEBUG/sensr main(12883): 9: Gravity Sensor
		02-05 20:30:03.886: DEBUG/sensr main(12883): 10: Linear Acceleration Sensor
		02-05 20:30:03.890: DEBUG/sensr main(12883): 11: Rotation Vector Sensor
        */
        
        int j=0; 
  	  	while (j < lSensorList.size()) {
  	  		Sensor s = lSensorList.get(j);
  			Log.d(TAG, s.getType() + ": " + s.getName());
  			j++;
  	  	}
        
        tvAccelX = (TextView) findViewById(R.id.accelX);
        tvAccelY = (TextView) findViewById(R.id.accelY);
        tvAccelZ = (TextView) findViewById(R.id.accelZ);
        
        tvOrientX = (TextView) findViewById(R.id.orientX);
        tvOrientY = (TextView) findViewById(R.id.orientY);
        tvOrientZ = (TextView) findViewById(R.id.orientZ);
        
        //Treated as 3d vector
        tvGravity = (TextView) findViewById(R.id.gravity);
        
        tvLight = (TextView) findViewById(R.id.light);
        tvProximity = (TextView) findViewById(R.id.proximity);
        tvMagnetic = (TextView) findViewById(R.id.magnetic);
        tvTemperature = (TextView) findViewById(R.id.temperature);
        
    }
    
    public void onSensorChanged(SensorEvent event) {
        synchronized (this) {
            //Log.d(TAG, "onSensorChanged: " + sensor + ", x: " + values[0] + ", y: " + values[1] + ", z: " + values[2]);
            if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
                tvOrientX.setText("Orientation X: " + event.values[0]);
                tvOrientY.setText("Orientation Y: " + event.values[1]);
                tvOrientZ.setText("Orientation Z: " + event.values[2]);
            }
            
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                tvAccelX.setText("Accel X: " + event.values[0]);
                tvAccelY.setText("Accel Y: " + event.values[1]);
                tvAccelZ.setText("Accel Z: " + event.values[2]);
            }
            
            if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            	tvLight.setText("Light: " + event.values[0]);
            	Log.d(TAG, "onSensorChanged: " + event.sensor.getType() + ", ???: " + event);
            }
            
            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            	tvMagnetic.setText("Magnetic: " + event.values[0]);
            	//Log.d(TAG, "onSensorChanged: " + event.sensor.getType() + ", Magnetic: " + event.values);
            }
            
            if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            	tvProximity.setText("Proximity: " + event.values[0]);
            	Log.d(TAG, "onSensorChanged: " + event.sensor.getType() + ", Proximity: " + event.values);
            }
            
            if (event.sensor.getType() == Sensor.TYPE_TEMPERATURE) {
            	tvTemperature.setText("Temperature: " + event.values[0]);
            	Log.d(TAG, "onSensorChanged: " + event.sensor.getType() + ", Temperature: " + event.values);
            }
        }
    }
    
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    	//Log.d(TAG,"onAccuracyChanged: " + sensor + ", accuracy: " + accuracy);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // register this class as a listener for the orientation and accelerometer sensors
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mOrientation, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mMagnetic, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mTemperature, SensorManager.SENSOR_DELAY_NORMAL);
    }
    
    @Override
    protected void onStop() {
    	mSensorManager.unregisterListener(this);
    	super.onStop();
    }    
}
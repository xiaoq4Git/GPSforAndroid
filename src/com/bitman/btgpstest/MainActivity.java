package com.bitman.btgpstest;

import java.util.Random;
import java.util.Timer;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Button bStart,bPause,bStop,bRestart;
	private RadioGroup group;
	private java.util.Timer timer;
	
    public static final String TAG = MainActivity.class.getName();
    MockLocationService bindService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bStart = (Button)this.findViewById(R.id.start);
        bPause = (Button)this.findViewById(R.id.pause);
        bStop = (Button)this.findViewById(R.id.stop);
        bRestart = (Button)this.findViewById(R.id.goon);
        group = (RadioGroup)this.findViewById(R.id.radioGroup1);
        final Intent intent = new Intent(MainActivity.this, MockLocationService.class);
        group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int radioButtonId = group.getCheckedRadioButtonId();
				RadioButton rb = (RadioButton)MainActivity.this.findViewById(radioButtonId);
				if(checkedId==R.id.fast){
					bindService.setUPDATE_TIME(1000);
					bindService.setChange(0.00004);
				}
				else if(checkedId==R.id.normal){
					bindService.setUPDATE_TIME(900);
					bindService.setChange(0.00003);
				}
				else if(checkedId==R.id.slow){
					bindService.setUPDATE_TIME(800);
					bindService.setChange(0.00002);
				}
				else if(checkedId==R.id.change){
					timer = new Timer(true);
					timer.schedule(new java.util.TimerTask(){
						@Override
						public void run() {
								Random rand = new Random();
								int update = rand.nextInt(300)+700;
								double unit = ((double)4 - rand.nextInt(3))/100000;
								bindService.setUPDATE_TIME(update);
								bindService.setChange(unit);
						}}, 0,5*30*1000);
				}
			}
		});
        
        Log.i(TAG, "bindService()");
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    public void onStartClickListener(View view){
        Toast.makeText(this, "��ʼ", Toast.LENGTH_SHORT).show();
        bStart.setVisibility(android.view.View.INVISIBLE);
        bRestart.setVisibility(android.view.View.INVISIBLE);
        bPause.setVisibility(android.view.View.VISIBLE);
        bStop.setVisibility(android.view.View.VISIBLE);
        bindService.startMockLocation();
        
//        Intent home = new Intent(Intent.ACTION_MAIN);
//        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        home.addCategory(Intent.CATEGORY_HOME);
//        startActivity(home);
    }
    
    public void onPauseClickListener(View view){
        bStart.setVisibility(android.view.View.INVISIBLE);
        bRestart.setVisibility(android.view.View.VISIBLE);
        bPause.setVisibility(android.view.View.INVISIBLE);
        bStop.setVisibility(android.view.View.VISIBLE);
    	Toast.makeText(this, "��ͣ��", Toast.LENGTH_SHORT).show();
    	bindService.puaseMockLocation();
    }

    public void onStopClickListener(View view){
        bStart.setVisibility(android.view.View.VISIBLE);
        bRestart.setVisibility(android.view.View.INVISIBLE);
        bPause.setVisibility(android.view.View.INVISIBLE);
        bStop.setVisibility(android.view.View.INVISIBLE);

        bindService.stopMockLocation();
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub
            Log.i(TAG, "onServiceDisconnected()");
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // TODO Auto-generated method stub
            Log.i(TAG, "onServiceConnected()");
            MockLocationService.MyBinder binder = (MockLocationService.MyBinder)service;
            bindService = binder.getService1();
        }
    };

}

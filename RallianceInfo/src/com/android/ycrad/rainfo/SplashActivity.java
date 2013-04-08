package com.android.ycrad.rainfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author YcraD
 * @function Setup a splash screen on start
 *
 */
public class SplashActivity extends Activity {

	private final int SPLASH_DISPLAY_LENGHT = 5000; //Set the splash time

    @Override 
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);           //remove the title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);  //fullscreen
        setContentView(R.layout.activity_splash); 
        new Handler().postDelayed(new Runnable(){ 

        @Override 
        public void run() { 
        	Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class); 
            SplashActivity.this.startActivity(mainIntent); 
            SplashActivity.this.finish(); 
        } 
            
        }, SPLASH_DISPLAY_LENGHT); 
    }
}

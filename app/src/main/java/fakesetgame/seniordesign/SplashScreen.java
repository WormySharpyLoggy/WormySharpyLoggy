package fakesetgame.seniordesign;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


/**
 * This is the Activity class for the splash screen,
 * which displays a brief graphic on app startup.
 */
public class SplashScreen extends Activity {

    private static int TIMEOUT = 1500;
    private static boolean active = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        //PreferenceManager.getDefaultSharedPreferences(this).edit().clear().commit();
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if(active) {
                    Intent i = new Intent(SplashScreen.this, HomeScreen.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
                // close this activity
                finish();
            }
        }, TIMEOUT);
    }

    @Override
    protected void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        active= false;
    }
}

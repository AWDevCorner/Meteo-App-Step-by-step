package it.tiwiz.tdb;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import it.tiwiz.tdb.helpers.BackOps;
import it.tiwiz.tdb.helpers.C;

public class WeatherActivity extends Activity implements View.OnClickListener{
    protected final ServiceReceiver mServiceReceiver = new ServiceReceiver();
    protected final IntentFilter mLocationFilter = new IntentFilter(C.LOCATION_BROADCAST_ACTION);
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        findViewById(R.id.button).setOnClickListener(this);
        mTextView = (TextView) findViewById(R.id.textView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void requestLocation() {
        Intent locationIntent = new Intent(this, BackOps.class);
        locationIntent.setAction(BackOps.GET_LOCATION);
        startService(locationIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(mServiceReceiver, mLocationFilter);
        requestLocation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mServiceReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mServiceReceiver);
        }
    }

    @Override
    public void onClick(View view) {
        requestLocation();
    }

    private class ServiceReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String location = intent.getStringExtra(C.LOCATION_BROADCAST_EXTRA);
            mTextView.setText("Sei a " + location);
        }
    }
}

package it.tiwiz.tdb;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import it.tiwiz.tdb.dialogs.GenericDialog;
import it.tiwiz.tdb.dialogs.LocationConfirmationDialog;
import it.tiwiz.tdb.helpers.BackOps;
import it.tiwiz.tdb.helpers.C;
import it.tiwiz.tdb.helpers.WeatherData;

public class WeatherActivity extends Activity implements View.OnClickListener, GenericDialog.Callbacks{
    protected final LocationServiceReceiver mLocationServiceReceiver = new LocationServiceReceiver();
    protected final WeatherUpdatesReceiver mWeatherUpdatesReceiver = new WeatherUpdatesReceiver();
    protected final IntentFilter mLocationFilter = new IntentFilter(C.LOCATION_BROADCAST_ACTION);
    protected final IntentFilter mWeatherFilter = new IntentFilter(C.WEATHER_BROADCAST_ACTION);
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

    private void requestWeatherForLocation(String city) {
        Intent weatherIntent = new Intent(this, BackOps.class);
        weatherIntent.setAction(BackOps.GET_WEATHER);
        weatherIntent.putExtra(BackOps.GET_WEATHER_EXTRA, city);
        startService(weatherIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerLocationReceiver();
        registerWeatherReceiver();
        requestLocation();
    }

    private void registerLocationReceiver() {
        LocalBroadcastManager.getInstance(this).registerReceiver(mLocationServiceReceiver, mLocationFilter);
    }

    private void registerWeatherReceiver() {
        LocalBroadcastManager.getInstance(this).registerReceiver(mWeatherUpdatesReceiver, mWeatherFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterLocationReceiver();
        unregisterWeatherReceiver();
    }

    private void unregisterLocationReceiver() {
        if (mLocationServiceReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mLocationServiceReceiver);
            Intent locationIntent = new Intent(this, BackOps.class);
            stopService(locationIntent);
        }
    }

    private void unregisterWeatherReceiver() {
        if (mWeatherUpdatesReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mWeatherUpdatesReceiver);
        }
    }

    @Override
    public void onClick(View view) {
        requestLocation();
    }

    @Override
    public void onPositiveButtonClick(DialogInterface dialog, String dialogTag) {
        dialog.dismiss();
    }

    @Override
    public void onNegativeButtonClick(DialogInterface dialog, String dialogTag) {
        dialog.dismiss();
    }

    private class LocationServiceReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String location = intent.getStringExtra(C.LOCATION_BROADCAST_EXTRA);
            showLocationConfirmationDialog(location);
        }
    }

    private class WeatherUpdatesReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            WeatherData weatherData = intent.getParcelableExtra(C.WEATHER_BROADCAST_RESULT);
            mTextView.setText(weatherData.toString());
        }
    }

    private void showLocationConfirmationDialog(String location) {
        LocationConfirmationDialog dialog = new LocationConfirmationDialog();
        dialog.setLocation(location).show(getFragmentManager(), C.LOCATION_CONFIRMATION_DIALOG_TAG);
        requestWeatherForLocation("Berlin");
    }
}

package it.tiwiz.tdb.helpers;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import it.tiwiz.tdb.interfaces.WeatherUpdates;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 */
public class BackOps extends IntentService implements WeatherUpdates{
    private static final String PREFIX = BackOps.class.getPackage().getName();
    public static final String GET_LOCATION = PREFIX + ".GET_LOCATION";
    public static final String GET_WEATHER = PREFIX + ".GET_WEATHER";

    public BackOps() {
        super("BackOps");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final String action = intent.getAction();
        if (action != null) {
            if (action.equals(GET_LOCATION)) {
                handleLocationRequest();
            } else if (action.equals(GET_WEATHER)) {
                handleWeatherRequest();
            }
        }
    }

    protected void handleLocationRequest() {
        String cityName = "";
        //gets Location Service
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //gets last known locations
        final Location lastKnownLocationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        final Location lastKnownLocationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        //and from them extracts the most recent
        final Location mostRecentLocation = LocationUtils.getMostRecentLocation(new Location[]{lastKnownLocationGPS, lastKnownLocationNetwork});
        //from the most recent location, extracts the city and
        if(null != mostRecentLocation)
            cityName = LocationUtils.getCityFromLocation(this, mostRecentLocation);

        Intent locationIntent = new Intent(C.LOCATION_BROADCAST_ACTION)
                .putExtra(C.LOCATION_BROADCAST_EXTRA, cityName);
        LocalBroadcastManager.getInstance(this).sendBroadcast(locationIntent);

    }

    protected void handleWeatherRequest() {
        if (NetworkUtils.isOnline(this)) {
            //we make the request to the server here
        } else {
            onWeatherFailure("");
        }
    }

    @Override
    public void onWeatherSuccess(String success) {

    }

    @Override
    public void onWeatherFailure(String failure) {
        Intent weatherResponseIntent = new Intent(C.WEATHER_BROADCAST_ACTION)
                .putExtra(C.WEATHER_BROADCAST_RESULT, false);
        LocalBroadcastManager.getInstance(this).sendBroadcast(weatherResponseIntent);
    }

    protected static class LocationUtils {

        public static String getCityFromLocation(Context context, Location location){

            String cityName = "NoIdeaLand"; //too funny
            final Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses;

            try {
                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                if(addresses.size() > 0) {
                    cityName = addresses.get(0).getLocality();
                }
            } catch (IOException e) {
                cityName = "NoIdeaLand (And IOException)";
            }

            return cityName;
        }

        public static Location getMostRecentLocation(Location[] locations){

            Location mostRecentLocation = null;
            final int numberOfAvailableLocations = locations.length;

            if(numberOfAvailableLocations > 0){
                mostRecentLocation = locations[0];

                for(int i = 1; i < numberOfAvailableLocations; i++){
                    if((null == mostRecentLocation) && (null != locations[i]))
                        mostRecentLocation = locations[i];
                    else if((null == locations[i])) break;
                    else if(mostRecentLocation.getTime() > locations[i].getTime())
                        mostRecentLocation = locations[i];

                }
            }

            return mostRecentLocation;
        }
    }

    protected static class NetworkUtils {

        public static boolean isOnline(Context context) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null) {
                return info.isConnected();
            } else {
                return false;
            }
        }
    }

}

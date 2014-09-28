package it.tiwiz.tdb.helpers;

/**
 * Created by tiwiz on 30/08/14.
 */
public class C {
    private static final String PREFIX = C.class.getPackage().getName();
    private static final String LOCATION_PREFIX = ".location.broadcast";
    private static final String WEATHER_PREFIX = ".weather.broadcast";
    public static final String LOCATION_BROADCAST_ACTION = PREFIX + LOCATION_PREFIX + ".ACTION";
    public static final String LOCATION_BROADCAST_EXTRA = PREFIX + LOCATION_PREFIX + ".EXTRA";
    public static final String WEATHER_BROADCAST_ACTION = PREFIX + WEATHER_PREFIX + ".ACTION";
    public static final String WEATHER_BROADCAST_RESULT = PREFIX + WEATHER_PREFIX + ".RESULT";
    public static final String WEATHER_BROADCAST_DETAILS = PREFIX + WEATHER_PREFIX + ".DETAILS";

    public static final String LOCATION_CONFIRMATION_DIALOG_TAG = "LocationConfirmationDialog";
}

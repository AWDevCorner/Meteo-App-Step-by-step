package it.tiwiz.tdb;

/**
 * This class will contain all the constants we will use for the project
 */
public final class C {
    private static final String PREFIX_KEY = "it.tiwiz.tbd.";
    /**
     * Home location
     * This is the key (and default value) we use to store user's home location
     * (so that we can change it when needed).
     * */
    public static final String USER_HOME_LOCATION_KEY = PREFIX_KEY + "HOME_LOCATION";
    public static final String USER_HOME_LOCATION_DEFAULT_VALUE = "";
    public static final String USER_HOME_COORD_LAT_KEY = PREFIX_KEY + "HOME_COORD_LAT";
    public static final String USER_HOME_COORD_LONG_KEY = PREFIX_KEY + "HOME_COORD_LONG";
}

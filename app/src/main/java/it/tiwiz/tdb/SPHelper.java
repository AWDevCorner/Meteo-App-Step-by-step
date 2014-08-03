package it.tiwiz.tdb;

/**
 * This class will help us manage the shared preferences loading and saving data.
 */
public class SPHelper {
    private static SPHelper instance = null;
    private static String homeCity;

    /**
     * Private constructor to avoid initialization
     * */
    private SPHelper() {}

    public static SPHelper getInstance() {
        if (instance == null) {
            instance = new SPHelper();
        }
        return instance;
    }



}

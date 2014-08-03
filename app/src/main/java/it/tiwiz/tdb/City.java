package it.tiwiz.tdb;

/***
 * This class contains the data relative to the City, like name and coordinates.
 * Using this data type, it will be easier to save and load data from SharedPreferences
 */
public class City {
    private String name;
    private String latitude;
    private String longitude;

    public City (String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = String.format("%.5f", latitude);
        this.longitude = String.format("%.5f", longitude);
    }

    
}

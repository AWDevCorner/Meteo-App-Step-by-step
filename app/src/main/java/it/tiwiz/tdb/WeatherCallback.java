package it.tiwiz.tdb;

/**
 * Created by tiwiz on 01/08/14.
 */
public interface WeatherCallback {
    public void run(String weatherCondition, String temperature, String umidity);
}

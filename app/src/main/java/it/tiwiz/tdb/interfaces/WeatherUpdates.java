package it.tiwiz.tdb.interfaces;

/**
 * Created by tiwiz on 28/09/14.
 */
public interface WeatherUpdates {

    public void onWeatherSuccess(String success);
    public void onWeatherFailure(String failure);

}

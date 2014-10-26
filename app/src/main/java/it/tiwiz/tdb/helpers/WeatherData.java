package it.tiwiz.tdb.helpers;

import android.os.Parcel;
import android.os.Parcelable;

public class WeatherData implements Parcelable {

    private final String mCondition;
    private final String mDescription;
    private final int mIconRes;
    private final double mTemperature;
    private final double mHumidity;

    private WeatherData(String condition, String description, int iconRes,
                        double temperature, double humidity) {

        mCondition = condition;
        mDescription = description;
        mIconRes = iconRes;
        mTemperature = temperature;
        mHumidity = humidity;
    }

    private WeatherData(Parcel in) {
        mCondition = in.readString();
        mDescription = in.readString();
        mIconRes = in.readInt();
        mTemperature = in.readDouble();
        mHumidity = in.readDouble();
    }

    public String getCondition() {
        return mCondition;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getIconRes() {
        return mIconRes;
    }

    public double getTemperature() {
        return mTemperature;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public static double kelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }

    public static double kelvinToFahrenheit(double kelvin) {
        double fahrenheit = kelvinToCelsius(kelvin) * 1.8f;
        return fahrenheit + 32;
    }

    @Override
    public int hashCode() {
        int hashCode = mCondition.hashCode() +
                mDescription.hashCode() +
                mIconRes;

        int roundTemp = (int) Math.round(kelvinToCelsius(mTemperature));
        int k = (int) Math.sqrt(Math.pow(mHumidity, roundTemp));
        return hashCode + k;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof WeatherData)) {
            return false;
        } else {
            final WeatherData secondWeatherData = (WeatherData) o;
            boolean sameCondition = mCondition.equals(secondWeatherData.getCondition());
            boolean sameDescription = mDescription.equals(secondWeatherData.getDescription());
            boolean sameIcon = mIconRes == secondWeatherData.getIconRes();
            boolean sameTemperature = mTemperature == secondWeatherData.getTemperature();
            boolean sameHumidity = mTemperature == secondWeatherData.getHumidity();

            return sameCondition && sameDescription && sameIcon && sameTemperature && sameHumidity;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(mCondition);
        builder.append(" - ").append(mDescription).append(" - Temp: ")
                .append(String.valueOf(mTemperature)).append(" - H: ")
                .append(String.valueOf(mHumidity)).append("%");

        return builder.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mCondition);
        parcel.writeString(mDescription);
        parcel.writeInt(mIconRes);
        parcel.writeDouble(mTemperature);
        parcel.writeDouble(mHumidity);
    }

    public static final Creator CREATOR = new Creator<WeatherData>() {
        @Override
        public WeatherData createFromParcel(Parcel parcel) {
            return new WeatherData(parcel);
        }

        @Override
        public WeatherData[] newArray(int size) {
            return new WeatherData[size];
        }
    };

    public static class Builder {

        private String mCondition;
        private String mDescription;
        private int mIconRes = -1;
        private double mTemperature;
        private double mHumidity;

        public Builder setCondition(String condition) {
            mCondition = condition;
            return this;
        }

        public Builder setDescription(String description) {
            mDescription = description;
            return this;
        }

        public Builder setIconRes(int iconRes) {
            mIconRes = iconRes;
            return this;
        }

        public Builder setTemperature(double temperature) {
            mTemperature = temperature;
            return this;
        }

        public Builder setHumidity(double humidity) {
            mHumidity = humidity;
            return this;
        }

        public WeatherData build() {
            if (mIconRes != -1) {
                return new WeatherData(mCondition, mDescription, mIconRes, mTemperature, mHumidity);
            } else {
                return null;
            }
        }
    }
}

package br.com.rf.purpledeckschallenge.model;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import br.com.rf.purpledeckschallenge.util.PrefKeys;
import br.com.rf.purpledeckschallenge.util.PreferencesUtil;

/**
 * Created by rodrigoferreira on 3/5/16.
 */
public class Weather {

    public static final int SUPPORTED_TYPE_SUN = 0;
    public static final int SUPPORTED_TYPE_RAIN = 1;
    public static final int SUPPORTED_TYPE_CLOUDY = 2;

    private static final String UNSUPPORTED_TYPE_THUNDERSTORM = "Thunderstorm";
    private static final String UNSUPPORTED_TYPE_DRIZZLE = "Drizzle";
    private static final String UNSUPPORTED_TYPE_RAIN = "Rain";
    private static final String UNSUPPORTED_TYPE_SNOW = "Snow";
    private static final String UNSUPPORTED_TYPE_ATMOSPHERE = "Atmosphere";
    private static final String UNSUPPORTED_TYPE_CLOUDS = "Clouds";
    private static final String UNSUPPORTED_TYPE_EXTREME = "Extreme";
    private static final String UNSUPPORTED_TYPE_ADDITIONAL = "Additional";
    private static final String UNSUPPORTED_TYPE_HAZE = "Haze";

    public String city;
    public String time;
    public String temperature;
    public int weatherType;
    public String photoUrl;

    public Weather() {

    }

    public Weather(String city, String time, String temperature, int weatherType) {
        this.city = city;
        this.time = time;
        this.temperature = temperature;
        this.weatherType = weatherType;
    }

    public Weather(WeatherApiWrapper apiWrapper) {
        this.city = apiWrapper.getCity();
        this.time = apiWrapper.getDt();
        this.temperature = apiWrapper.getTempture();
        this.weatherType = apiWrapper.getWeatherType();
    }

    public static List<String> getDefaultCities() {
        return Arrays.asList(new String[]{"Dublin", "London", "Beijing", "Sydney"});
    }

    public static void saveMyCitiesByWeatherList(Context context, List<Weather> weatherList) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < weatherList.size(); i++) {
            sb.append(weatherList.get(i).city);
            if (i < weatherList.size() - 1) {
                sb.append(";");
            }
        }
        Log.d("saveCities", sb.toString());
        PreferencesUtil.savePreference(context, PrefKeys.PREF_KEY_MY_CITIES, sb.toString());
    }

    public static void saveMyCitiesByStringList(Context context, List<String> citiesList) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < citiesList.size(); i++) {
            sb.append(citiesList.get(i));
            if (i < citiesList.size() - 1) {
                sb.append(";");
            }
        }
        Log.d("saveCities", sb.toString());
        PreferencesUtil.savePreference(context, PrefKeys.PREF_KEY_MY_CITIES, sb.toString());
    }

    public static List<String> getMySavedCities(Context context) {
        String mySavedCities = PreferencesUtil.getStringPreference(context, PrefKeys.PREF_KEY_MY_CITIES, null);
        if (mySavedCities == null || mySavedCities.length() == 0) {
            return new ArrayList<String>();
        }
        return Arrays.asList(mySavedCities.split(";"));

    }

    public static boolean cityAlreadyExists(Context context, String city) {
        List<String> cities = Weather.getMySavedCities(context);

        for (String savedCity : cities) {
            if (city.toLowerCase().equals(savedCity.toLowerCase())) {
                return true;
            }
        }
        return false;
    }


    public static int getSupportedWeatherType(String weatherType) {
        weatherType = weatherType.toLowerCase();
        if (UNSUPPORTED_TYPE_THUNDERSTORM.toLowerCase().equals(weatherType) || UNSUPPORTED_TYPE_DRIZZLE.toLowerCase().equals(weatherType)
                || UNSUPPORTED_TYPE_RAIN.toLowerCase().equals(weatherType) || UNSUPPORTED_TYPE_SNOW.toLowerCase().equals(weatherType)) {
            return SUPPORTED_TYPE_RAIN;
        } else if (UNSUPPORTED_TYPE_ATMOSPHERE.toLowerCase().equals(weatherType) || UNSUPPORTED_TYPE_CLOUDS.toLowerCase().equals(weatherType)
                || UNSUPPORTED_TYPE_EXTREME.toLowerCase().equals(weatherType) || UNSUPPORTED_TYPE_ADDITIONAL.toLowerCase().equals(weatherType)
                || UNSUPPORTED_TYPE_HAZE.toLowerCase().equals(weatherType)) {
            return SUPPORTED_TYPE_CLOUDY;
        } else {
            return SUPPORTED_TYPE_SUN;
        }
    }

    //method for mock data
    public static Weather getRandomWeather(String city) {
        Random random = new Random();
        String time = String.valueOf(random.nextInt(24) + 1);
        String tempture = String.valueOf(random.nextInt(30));
        int weatherType = random.nextInt(3);
        return new Weather(city, time.length() == 1 ? "0" + time + ":00" : time + ":00", tempture + "˚", weatherType);
    }

    public static List<Weather> getRandomWeatherListByCities(List<String> citiesList) {
        List<Weather> weatherList = new ArrayList<Weather>();
        for (String city : citiesList) {
            weatherList.add(Weather.getRandomWeather(city));
        }
        return weatherList;
    }
}

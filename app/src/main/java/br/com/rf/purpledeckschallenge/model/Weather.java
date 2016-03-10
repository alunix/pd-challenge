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

    public static final int TYPE_SUN = 0;
    public static final int TYPE_RAIN = 1;
    public static final int TYPE_CLOUDY = 2;

    public String city;
    public String time;
    public String tempture;
    public int weatherType;
    public String photoUrl;

    public Weather() {

    }

    public Weather(String city, String time, String tempture, int weatherType) {
        this.city = city;
        this.time = time;
        this.tempture = tempture;
        this.weatherType = weatherType;
    }

    public Weather(WeatherApiWrapper apiWrapper) {
        this.city = apiWrapper.getCity();
        this.time = apiWrapper.getDt();
        this.tempture = apiWrapper.getTempture();
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



    public static int getSupportedWeatherType(String weatherType) {
        weatherType = weatherType.toLowerCase();
        if ("Thunderstorm".toLowerCase().equals(weatherType) || "Drizzle".toLowerCase().equals(weatherType)
                || "Rain".toLowerCase().equals(weatherType) || "Snow".toLowerCase().equals(weatherType)) {
            return TYPE_RAIN;
        } else if ("Atmosphere".toLowerCase().equals(weatherType) || "Clouds".toLowerCase().equals(weatherType)
                || "Extreme".toLowerCase().equals(weatherType) || "Additional".toLowerCase().equals(weatherType)
                || "Haze".toLowerCase().equals(weatherType)) {
            return TYPE_CLOUDY;
        } else {
            return TYPE_SUN;
        }
    }
}

package br.com.rf.purpledeckschallenge.helper;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.rf.purpledeckschallenge.model.Weather;
import br.com.rf.purpledeckschallenge.util.PrefKeys;
import br.com.rf.purpledeckschallenge.util.PreferencesUtil;

/**
 * Created by rodrigoferreira on 3/10/16.
 */
public class WeatherHelper {

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
        List<String> cities = WeatherHelper.getMySavedCities(context);

        for (String savedCity : cities) {
            if (city.toLowerCase().equals(savedCity.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}

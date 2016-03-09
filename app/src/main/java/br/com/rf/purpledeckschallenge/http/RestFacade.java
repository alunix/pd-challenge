package br.com.rf.purpledeckschallenge.http;

import br.com.rf.purpledeckschallenge.model.FlickrPhoto;
import br.com.rf.purpledeckschallenge.model.WeatherApiWrapper;
import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Created by rodrigoferreira on 3/7/16.
 */
public class RestFacade {

    public static void searchPhotoByTag(String tag, Callback<FlickrPhoto> callback) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(RestApi.PHOTO_SEARCH_API_END_POINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        restAdapter.create(RestApi.class).searchPhotoByTag(tag, callback);
    }

    public static WeatherApiWrapper getWeatherByCity(String city) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(RestApi.WEATHER_API_END_POINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        return restAdapter.create(RestApi.class).getWeatherByCity(city);
    }
}

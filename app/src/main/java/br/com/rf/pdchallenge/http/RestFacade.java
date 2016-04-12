package br.com.rf.pdchallenge.http;

import br.com.rf.pdchallenge.model.FlickrPhoto;
import br.com.rf.pdchallenge.model.TimeZoneApi;
import br.com.rf.pdchallenge.model.WeatherApiWrapper;
import retrofit.RestAdapter;

/**
 * Created by rodrigoferreira on 3/7/16.
 */
public class RestFacade {

    public static FlickrPhoto searchPhotoByTag(String tag) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(RestApi.PHOTO_SEARCH_API_END_POINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        return restAdapter.create(RestApi.class).searchPhotoByTag(tag);
    }

    public static WeatherApiWrapper getWeatherByCity(String city) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(RestApi.WEATHER_API_END_POINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        return restAdapter.create(RestApi.class).getWeatherByCity(city);
    }

    public static TimeZoneApi getTimeZoneByLatLong(String location) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(RestApi.TIME_ZONE_API_END_POINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        return restAdapter.create(RestApi.class).getTimeZoneByLatLong(location);
    }
}

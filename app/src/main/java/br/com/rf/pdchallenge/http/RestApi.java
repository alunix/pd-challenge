package br.com.rf.pdchallenge.http;

import br.com.rf.pdchallenge.model.FlickrPhoto;
import br.com.rf.pdchallenge.model.TimeZoneApi;
import br.com.rf.pdchallenge.model.WeatherApiWrapper;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by rodrigoferreira on 3/7/16.
 */
public interface RestApi {

    String WEATHER_API_END_POINT = "http://api.openweathermap.org/data";
    String PHOTO_SEARCH_API_END_POINT = "https://api.flickr.com/services";
    String TIME_ZONE_API_END_POINT = "https://maps.googleapis.com/maps/api";

    @GET("/rest/?method=flickr.photos.search&api_key=34fc09eb49b0653e3bf5c3f7d94f44fa&format=json&nojsoncallback=1&accuracy=6-11&tags=landmark")
    FlickrPhoto searchPhotoByTag(@Query("text") String tag);

    @GET("/2.5/weather?units=metric&appid=2042aca6e63e2d0b6c9dead41a19e999")
    WeatherApiWrapper getWeatherByCity(@Query("q") String city);

    @GET("/timezone/json?timestamp=0&key=AIzaSyC-XqUCO7YhFJ7TIrG2wLDFTS3g7958e7s")
    TimeZoneApi getTimeZoneByLatLong(@Query("location") String location);
}

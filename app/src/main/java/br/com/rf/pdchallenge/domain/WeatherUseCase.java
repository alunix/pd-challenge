package br.com.rf.pdchallenge.domain;

import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import br.com.rf.pdchallenge.R;
import br.com.rf.pdchallenge.event.WeatherEvent;
import br.com.rf.pdchallenge.helper.WeatherHelper;
import br.com.rf.pdchallenge.http.RestFacade;
import br.com.rf.pdchallenge.model.FlickrPhoto;
import br.com.rf.pdchallenge.model.Weather;
import br.com.rf.pdchallenge.model.WeatherApiWrapper;

/**
 * Created by rodrigoferreira on 3/6/16.
 */
public class WeatherUseCase extends BaseUseCase {
    public WeatherUseCase(Context context) {
        super(context);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void retrieveWeatherList(final WeatherEvent.RetrieveList event) {
        List<Weather> weatherList = new ArrayList<>();
        Exception exception = null;
        for (String city : WeatherHelper.getMySavedCities(mContext)) {
            try {
                WeatherApiWrapper apiWrapper = RestFacade.getWeatherByCity(city.toUpperCase());
                apiWrapper.timeZoneId = RestFacade.getTimeZoneByLatLong(apiWrapper.getLocation()).timeZoneId;
                Weather weather = new Weather(apiWrapper);
                FlickrPhoto flickrPhoto = RestFacade.searchPhotoByTag(weather.city);
                if ("ok".equals(flickrPhoto.stat) && flickrPhoto.photos.photo.size() > 0) {
                    FlickrPhoto.Photo photo = flickrPhoto.photos.photo.get(0);
                    String photoUrl = mContext.getString(R.string.url_flikr_photo, photo.farm, photo.server, photo.id, photo.secret);
                    Log.d("photoUrl", photoUrl);
                    weather.photoUrl = photoUrl;
                }
                weatherList.add(weather);
            } catch (Exception e) {
                exception = e;
            }
        }

        if (exception != null) {
            weatherList = new ArrayList<>();
        }
        EventBus.getDefault().post(new WeatherEvent().new SuccessRetrieveList(weatherList));
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void AddCityToList(final WeatherEvent.AddCity event) {
        try {
            WeatherApiWrapper apiWrapper = RestFacade.getWeatherByCity(event.city.toUpperCase());
            EventBus.getDefault().post(new WeatherEvent().new ShowLoading());
            if (apiWrapper != null && event.city.toLowerCase().equals(apiWrapper.getCity().toLowerCase())) {
                apiWrapper.timeZoneId = RestFacade.getTimeZoneByLatLong(apiWrapper.getLocation()).timeZoneId;
                Weather weather = new Weather(apiWrapper);
                FlickrPhoto flickrPhoto = RestFacade.searchPhotoByTag(weather.city);
                if ("ok".equals(flickrPhoto.stat) && flickrPhoto.photos.photo.size() > 0) {
                    FlickrPhoto.Photo photo = flickrPhoto.photos.photo.get(0);
                    String photoUrl = mContext.getString(R.string.url_flikr_photo, photo.farm, photo.server, photo.id, photo.secret);
                    Log.d("photoUrl", photoUrl);
                    weather.photoUrl = photoUrl;
                }
                EventBus.getDefault().post(new WeatherEvent().new SuccessCityAdded(weather));
            } else {
                EventBus.getDefault().post(new WeatherEvent().new ErrorCityAdded(mContext.getString(R.string.msg_error_search_city)));
            }
        } catch (Exception e) {
            EventBus.getDefault().post(new WeatherEvent().new ErrorCityAdded(mContext.getString(R.string.msg_error_generic)));
        }

    }

}

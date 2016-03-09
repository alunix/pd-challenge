package br.com.rf.purpledeckschallenge.domain;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import br.com.rf.purpledeckschallenge.event.WeatherEvent;
import br.com.rf.purpledeckschallenge.http.RestFacade;
import br.com.rf.purpledeckschallenge.model.Weather;
import br.com.rf.purpledeckschallenge.model.WeatherApiWrapper;

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
        for (String city : Weather.getMySavedCities(mContext)) {
            try {
                weatherList.add(new Weather(RestFacade.getWeatherByCity(city)));
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

        WeatherApiWrapper apiWrapper = RestFacade.getWeatherByCity(event.city);
        EventBus.getDefault().post(new WeatherEvent().new ShowLoading());
        if (apiWrapper != null && event.city.toLowerCase().equals(apiWrapper.getCity().toLowerCase())) {
            EventBus.getDefault().post(new WeatherEvent().new SuccessCityAdded(new Weather(apiWrapper)));
        } else {
            EventBus.getDefault().post(new WeatherEvent().new ErrorCityAdded());
        }

    }

}

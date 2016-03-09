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

/**
 * Created by rodrigoferreira on 3/6/16.
 */
public class WeatherUseCase extends BaseUseCase {
    public WeatherUseCase(Context context) {
        super(context);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void retrieveList(final WeatherEvent.RetrieveList event) {
        List<Weather> weatherList = new ArrayList<>();

        for (String city : Weather.getMySavedCities(mContext)) {
            weatherList.add(new Weather(RestFacade.getWeatherByCity(city)));
        }

        EventBus.getDefault().post(new WeatherEvent().new RetrieveListSuccess(weatherList));
    }

}

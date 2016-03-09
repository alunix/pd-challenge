package br.com.rf.purpledeckschallenge.event;

import java.util.List;

import br.com.rf.purpledeckschallenge.model.Weather;

public class WeatherEvent {

    public class RetrieveList {
    }

    public class RetrieveListSuccess {

        public RetrieveListSuccess(List<Weather> weatherList) {
            this.weatherList = weatherList;
        }

        public List<Weather> weatherList;

    }

    public class UpdateList {

    }


}

package br.com.rf.purpledeckschallenge.event;

import java.util.List;

import br.com.rf.purpledeckschallenge.model.Weather;

public class WeatherEvent {

    public class RetrieveList {
    }

    public class SuccessRetrieveList {

        public SuccessRetrieveList(List<Weather> weatherList) {
            this.weatherList = weatherList;
        }

        public List<Weather> weatherList;

    }

    public class AddCity {
        public String city;

        public AddCity(String city) {
            this.city = city;
        }
    }

    public class SuccessCityAdded {
        public Weather weather;

        public SuccessCityAdded(Weather weather) {
            this.weather = weather;
        }
    }

    public class ErrorCityAdded {
        public String msg;

        public ErrorCityAdded(String msg) {
            this.msg = msg;
        }
    }

    public class CityAlreadyExists {
        public String city;

        public CityAlreadyExists(String city) {
            this.city = city;
        }
    }

    public class ShowLoading {

    }

}

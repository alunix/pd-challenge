package br.com.rf.purpledeckschallenge.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by rodrigoferreira on 3/8/16.
 */
public class WeatherApiWrapper {

    public Coord coord;
    public List<Weather> weather;
    public Main main;
    public long dt;
    public String name;

    public String getDt() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis(dt * 1000);
        return calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE);
    }

    public String getCity() {
        return this.name;
    }

    public String getTempture() {
        return String.valueOf(Math.round(main.temp)) + "˚";
    }

    public int getWeatherType() {
        return br.com.rf.purpledeckschallenge.model.Weather.getSupportedWeatherType(this.weather.get(0).main);
    }

    public class Coord {
        public double lon;
        public double lat;
    }

    private class Weather {
        public String main;
    }

    private class Main {
        public double temp;
    }

}

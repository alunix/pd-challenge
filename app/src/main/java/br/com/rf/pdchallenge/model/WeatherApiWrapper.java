package br.com.rf.pdchallenge.model;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    public String timeZoneId;

    public String getDt() {

        if (timeZoneId == null) {
            return "#error";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone(timeZoneId));

        return sdf.format(new Date());

    }

    public String getCity() {
        return this.name;
    }

    public String getTempture() {
        return String.valueOf(Math.round(main.temp)) + "˚";
    }

    public int getWeatherType() {
        return br.com.rf.pdchallenge.model.Weather.getSupportedWeatherType(this.weather.get(0).main);
    }

    public String getLocation() {
        return coord.lat + "," + coord.lon;
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

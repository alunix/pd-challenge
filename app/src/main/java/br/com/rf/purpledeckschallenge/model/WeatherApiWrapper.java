package br.com.rf.purpledeckschallenge.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    public String getDt() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis(dt * 1000);

        Date date = new Date(dt * 1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm"); // the format of your date
        sdf.setTimeZone(TimeZone.getDefault()); // give a timezone reference for formating (see comment at the bottom

        return sdf.format(date);
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

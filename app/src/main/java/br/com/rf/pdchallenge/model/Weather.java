package br.com.rf.pdchallenge.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by rodrigoferreira on 3/5/16.
 */
public class Weather {

    public static final int SUPPORTED_TYPE_SUN = 0;
    public static final int SUPPORTED_TYPE_RAIN = 1;
    public static final int SUPPORTED_TYPE_CLOUDY = 2;

    private static final String UNSUPPORTED_TYPE_THUNDERSTORM = "Thunderstorm";
    private static final String UNSUPPORTED_TYPE_DRIZZLE = "Drizzle";
    private static final String UNSUPPORTED_TYPE_RAIN = "Rain";
    private static final String UNSUPPORTED_TYPE_SNOW = "Snow";
    private static final String UNSUPPORTED_TYPE_ATMOSPHERE = "Atmosphere";
    private static final String UNSUPPORTED_TYPE_CLOUDS = "Clouds";
    private static final String UNSUPPORTED_TYPE_EXTREME = "Extreme";
    private static final String UNSUPPORTED_TYPE_ADDITIONAL = "Additional";
    private static final String UNSUPPORTED_TYPE_HAZE = "Haze";

    public String city;
    public String time;
    public String temperature;
    public int weatherType;
    public String photoUrl;

    public Weather() {

    }

    public Weather(String city, String time, String temperature, int weatherType) {
        this.city = city;
        this.time = time;
        this.temperature = temperature;
        this.weatherType = weatherType;
    }

    public Weather(WeatherApiWrapper apiWrapper) {
        this.city = apiWrapper.getCity();
        this.time = apiWrapper.getDt();
        this.temperature = apiWrapper.getTempture();
        this.weatherType = apiWrapper.getWeatherType();
    }

    public static int getSupportedWeatherType(String weatherType) {
        weatherType = weatherType.toLowerCase();
        if (UNSUPPORTED_TYPE_THUNDERSTORM.toLowerCase().equals(weatherType) || UNSUPPORTED_TYPE_DRIZZLE.toLowerCase().equals(weatherType)
                || UNSUPPORTED_TYPE_RAIN.toLowerCase().equals(weatherType) || UNSUPPORTED_TYPE_SNOW.toLowerCase().equals(weatherType)) {
            return SUPPORTED_TYPE_RAIN;
        } else if (UNSUPPORTED_TYPE_ATMOSPHERE.toLowerCase().equals(weatherType) || UNSUPPORTED_TYPE_CLOUDS.toLowerCase().equals(weatherType)
                || UNSUPPORTED_TYPE_EXTREME.toLowerCase().equals(weatherType) || UNSUPPORTED_TYPE_ADDITIONAL.toLowerCase().equals(weatherType)
                || UNSUPPORTED_TYPE_HAZE.toLowerCase().equals(weatherType)) {
            return SUPPORTED_TYPE_CLOUDY;
        } else {
            return SUPPORTED_TYPE_SUN;
        }
    }

    //methods for mock data
    public static Weather getRandomWeather(String city) {
        Random random = new Random();
        String time = String.valueOf(random.nextInt(24) + 1);
        String tempture = String.valueOf(random.nextInt(30));
        int weatherType = random.nextInt(3);
        return new Weather(city, time.length() == 1 ? "0" + time + ":00" : time + ":00", tempture + "˚", weatherType);
    }

    public static List<Weather> getRandomWeatherListByCities(List<String> citiesList) {
        List<Weather> weatherList = new ArrayList<Weather>();
        for (String city : citiesList) {
            weatherList.add(Weather.getRandomWeather(city));
        }
        return weatherList;
    }
}

package com.moringaschool.weatherapp;

public class CurrentWeatherService {

        private static final String TAG = CurrentWeatherService.class.getSimpleName();

        private static final String URL = "https://api.openweathermap.org/data/2.5/weather";
        private static final String CURRENT_WEATHER_TAG = "CURRENT_WEATHER";
        private static final String API_KEY = "YOUR API KEY HERE"; <--- INSERT API KEY HERE

        private RequestQueue queue;
    }

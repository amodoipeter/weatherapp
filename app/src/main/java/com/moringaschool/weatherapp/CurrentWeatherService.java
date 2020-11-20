package com.moringaschool.weatherapp;

import android.app.Activity;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Request;
import okhttp3.Response;


public class CurrentWeatherService {

        private static final String TAG = CurrentWeatherService.class.getSimpleName();

        private static final String URL = "https://api.openweathermap.org/data/2.5/weather";
        private static final String CURRENT_WEATHER_TAG = "CURRENT_WEATHER";
        private static final String API_KEY = "";

        private RequestQueue queue;

        public CurrentWeatherService(@NonNull final Activity activity) {
                queue = Volley.newRequestQueue(activity.getApplicationContext());
        }

        public interface CurrentWeatherCallback {
                @MainThread
                void onCurrentWeather(@NonNull final CurrentWeather currentWeather);

                @MainThread
                void onError(@Nullable Exception exception);
        }

        public void getCurrentWeather(@NonNull final String locationName, @NonNull final CurrentWeatherCallback callback) {
                final String url = String.format("%s?q=%s&appId=%s", URL, locationName, API_KEY);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                        try {
                                                final JSONObject currentWeatherJSONObject = new JSONObject(response);
                                                final JSONArray weather = currentWeatherJSONObject.getJSONArray("weather");
                                                final JSONObject weatherCondition = ((JSONArray) weather).getJSONObject(0);
                                                final String locationName = currentWeatherJSONObject.getString("name");
                                                final int conditionId = weatherCondition.getInt("id");
                                                final String conditionName = weatherCondition.getString("main");
                                                final double tempKelvin = currentWeatherJSONObject.getJSONObject("main").getDouble("temp");
                                                final CurrentWeather currentWeather = new CurrentWeather(locationName, conditionId, conditionName, tempKelvin);
                                                callback.onCurrentWeather(currentWeather);
                                        } catch (JSONException e) {
                                                callback.onError(e);
                                        }
                                }
                        }, new Response(). {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                                callback.onError(error);
                        }
                });
                stringRequest.setTag(CURRENT_WEATHER_TAG);
                queue.add(stringRequest);
        }

        public void cancel() {
                queue.cancelAll(CURRENT_WEATHER_TAG);
        }
}

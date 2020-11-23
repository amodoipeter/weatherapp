package com.moringaschool.weatherapp;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class CurrentWeatherService {

        private static final String TAG = CurrentWeatherService.class.getSimpleName();

        private static final String URL = "https://api.openweathermap.org/data/2.5/weather";
        private static final String CURRENT_WEATHER_TAG = "CURRENT_WEATHER";
        private static final String API_KEY = "7e2b318ebd705d6a26737aba46a66355\n";

        private final RequestQueue queue;

        public CurrentWeatherService(@NonNull final AppCompatActivity activity) {
                queue = Volley.newRequestQueue(activity.getApplicationContext());
        }

        public interface CurrentWeatherCallback {
                @MainThread
                void onCurrentWeather(@NonNull final CurrentWeather currentWeather);

                @MainThread
                void onError(@Nullable Exception exception);
        }

        public void getCurrentWeather(@androidx.annotation.NonNull final String locationName, @androidx.annotation.NonNull final CurrentWeatherCallback callback) {
                final String url = String.format("%s?q=%s&appId=%s", URL, locationName, API_KEY);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                        try {
                                                final JSONObject currentWeatherJSONObject = new JSONObject(response);
                                                final JSONArray weather = currentWeatherJSONObject.getJSONArray("weather");
                                                final JSONObject weatherCondition = weather.getJSONObject(0);
                                                final String locationName1 = currentWeatherJSONObject.getString("name");
                                                final int conditionId = weatherCondition.getInt("id");
                                                final String conditionName = weatherCondition.getString("main");
                                                final double tempKelvin = currentWeatherJSONObject.getJSONObject("main").getDouble("temp");
                                                final CurrentWeather currentWeather = new CurrentWeather(locationName1, conditionId, conditionName, tempKelvin);
                                                callback.onCurrentWeather(currentWeather);
                                        } catch (JSONException e) {
                                                callback.onError(e);
                                        }
                                }
                        }, callback::onError);
                stringRequest.setTag(CURRENT_WEATHER_TAG);
                queue.add(stringRequest);
        }

        public void cancel() {
                queue.cancelAll(CURRENT_WEATHER_TAG);
        }
}

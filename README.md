# weatherapp

##Author Amodoi Peter

### Overview
This is an Android weather application built with open weather map API.

### Setup Steps
1. Download or clone this project
2. Import into Android Studio
3. Create a Open Weather Map API account [here](https://home.openweathermap.org/users/sign_up) if you don't have one
4. Create an Open Weather Map API key and add it to the project inside of the file CurrentWeatherService.java replace the API_KEY static variable value with your API key

```
public class CurrentWeatherService {

    private static final String TAG = CurrentWeatherService.class.getSimpleName();

    private static final String URL = "https://api.openweathermap.org/data/2.5/weather";
    private static final String CURRENT_WEATHER_TAG = "CURRENT_WEATHER";
    private static final String API_KEY = "YOUR API KEY HERE"; <--- INSERT API KEY HERE

    private RequestQueue queue;
...    
```
5. Run the app on an emulator or device (ensure you have a network connection)
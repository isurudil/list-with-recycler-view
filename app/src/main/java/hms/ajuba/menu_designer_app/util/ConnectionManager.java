package hms.ajuba.menu_designer_app.util;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import hms.ajuba.menu_designer_app.exception.ConnectionFailureException;

public class ConnectionManager {

    public static String get(String url, Activity activity) throws ConnectionFailureException {

        if (isConnected(activity)) {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            return restTemplate.getForObject(url, String.class);
        } else {
            throw new ConnectionFailureException();
        }
    }

    public static boolean isConnected(Activity activity) {
        Log.d("MD", "Checking internet connection of the device");

        ConnectivityManager manager = (ConnectivityManager) activity.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            Log.d("MD", "Device is connected to internet");
            return true;
        } else {
            Log.d("MD", "Device is not connected to internet");
            return false;
        }
    }
}

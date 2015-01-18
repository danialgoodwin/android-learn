package net.simplyadvanced.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

/** Static helper methods related to accessing Internet. */
public class HttpUtils {

    /** No need to instantiate this class. */
    private HttpUtils() {}

    /** Returns true if device is connected to Internet, otherwise false. */
    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    /** Downloads and returns the information from a given URI in the same thread
     * this is called in. */
    public static String get(String uri) throws IOException, URISyntaxException {
        return get(new URI(uri));
    }

    /** Downloads and returns the information from a given URI in the same
     * thread this is called in. The returned String may contain XML, JSON,
     * or another format. You should check for network connectivity before
     * calling this.
     * Note: This is just a simple implementation, it should be made more
     * robust, like by using a nice library. */
    public static String get(URI uri) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet();
        httpGet.setURI(uri);
        HttpResponse httpResponse = httpClient.execute(httpGet);

        InputStream inputStream;
        BufferedReader reader = null;
        String value = null;
        try {
            inputStream = httpResponse.getEntity().getContent();
            reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder(inputStream.available());
            while ((value = reader.readLine()) != null) {
                sb.append(value);
            }
            value = sb.toString();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // Close quietly.
                }
            }
        }
        return value;
    }

}
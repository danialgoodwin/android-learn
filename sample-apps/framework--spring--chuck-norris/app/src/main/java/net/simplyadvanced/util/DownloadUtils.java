/**
 * Created by Danial on 11/27/2014.
 */
package net.simplyadvanced.util;

import android.os.AsyncTask;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/** This class currently requires Spring for Android Framework. */
public class DownloadUtils {

    // The listener will be notified when done.
    public static void get(String url, OnFinishedListener listener) {
        if (url != null && !url.isEmpty() && listener != null) {
            new HttpGetStringTask(listener).execute(url);
        }
    }

    public interface OnFinishedListener {
        void onFinished(String result);
    }


    private static class HttpGetStringTask extends AsyncTask<String, Void, String> {

        private OnFinishedListener mListener;

        public HttpGetStringTask(OnFinishedListener listener) {
            mListener = listener;
        }

        @Override
        protected String doInBackground(String... params) {
            // The connection URL
            String url = params[0];
//            String url = "https://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=spring";
            // String url = "https://ajax.googleapis.com/ajax/services/search/web?v=1.0&q={query}";

            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();

            // Add the String message converter
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            // Make the HTTP GET request, marshaling the response to a String
            String result = restTemplate.getForObject(url, String.class, "Android");
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            mListener.onFinished(result);
        }
    }

}

package net.simplyadvanced.testspringforandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.simplyadvanced.testspringforandroid.sample.chucknorris.ChuckNorrisMainActivity;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/** Sample app using Spring: http://projects.spring.io/spring-android/. */
public class MainActivity extends Activity {

    private Button mGoToXmlTestButton;
    private TextView mDebugText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDebugText = (TextView) findViewById(R.id.debugText);
        mGoToXmlTestButton = (Button) findViewById(R.id.goToXmlTestButton);
        mGoToXmlTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ChuckNorrisMainActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateInBackground();
    }

    private void updateInBackground() {
        new HttpRequestTask().execute();
        new HttpGetStringTask().execute();
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Greeting> {
        @Override
        protected Greeting doInBackground(Void... params) {
            try {
                final String url = "http://rest-service.guides.spring.io/greeting";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Greeting greeting = restTemplate.getForObject(url, Greeting.class);
                return greeting;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Greeting greeting) {
//            mDebugText.setText(greeting);
            TextView greetingIdText = (TextView) findViewById(R.id.id_value);
            TextView greetingContentText = (TextView) findViewById(R.id.content_value);
            greetingIdText.setText(greeting.getId());
            greetingContentText.setText(greeting.getContent());
        }
    }

    private class HttpGetStringTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            // The connection URL
            String url = "https://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=spring";
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
            mDebugText.setText(result);
        }
    }

}

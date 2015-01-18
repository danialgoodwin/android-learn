package net.simplyadvanced.testspringforandroid.RestWithXml;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import net.simplyadvanced.testspringforandroid.Greeting;
import net.simplyadvanced.testspringforandroid.R;
import net.simplyadvanced.testspringforandroid.RestWithXml.model.Greeting2;

import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;


public class RestWithXmlActivity extends Activity {

    private TextView mDebugText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_with_xml);
        mDebugText = (TextView) findViewById(R.id.debugText);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final String url = "http://10.0.2.2:8080/hello-world";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new SimpleXmlHttpMessageConverter());

        Greeting2 greeting = restTemplate.getForObject(url, Greeting2.class);

        mDebugText.setText(greeting.getContent());
    }

}

package net.simplyadvanced.testspringforandroid.sample.chucknorris;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import net.simplyadvanced.testspringforandroid.R;
import net.simplyadvanced.util.DownloadUtils;

import org.json.JSONException;
import org.json.JSONObject;

// Sample usage of API from http://www.icndb.com/api/.
public class ChuckNorrisMainActivity extends Activity {

    private TextView mDebugText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuck_norris_main);
        mDebugText = (TextView) findViewById(R.id.debugText);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // http://api.icndb.com/jokes/random?firstName=John&amp;lastName=Doe
        DownloadUtils.get("http://api.icndb.com/jokes/random", new DownloadUtils.OnFinishedListener() {
            @Override
            public void onFinished(String result) {
                try {
                    JSONObject jsonResult = new JSONObject(result);
                    String joke = jsonResult.getJSONObject("value").getString("joke");
                    mDebugText.setText(joke);
                } catch (JSONException e) {
                    mDebugText.setText("Error: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

}

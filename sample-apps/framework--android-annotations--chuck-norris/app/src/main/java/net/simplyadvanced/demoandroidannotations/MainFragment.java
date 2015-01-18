package net.simplyadvanced.demoandroidannotations;

import android.support.v4.app.Fragment;
import android.text.Html;
import android.widget.TextView;

import net.simplyadvanced.util.HttpUtils;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;

// By passing in layout XML here, we don't need to manually call `onCreateView()`.
@EFragment(R.layout.fragment_main)
public class MainFragment extends Fragment {

    private static final String CHUCK_NORRIS_API_URL = "http://api.icndb.com/jokes/random";
    private static final String TYPE = "type";
    private static final String SUCCESS = "success";
    private static final String VALUE = "value";
    private static final String JOKE = "joke";

    // This is the minimum needed to bind a variable to widget in XML.
    // AndroidAnnotations will look for an id `R.id.jokeTextView`.
    // Or, you could manually specify the id by passing it in as an
    // argument to the annotation.
    @ViewById TextView jokeTextView;

    // Required empty constructor.
    public MainFragment() {}

    // This annotation will auto-generate code to find a View with id of
    // `R.id.jokeButton` and set the onClickListener to run this method.
    @Click
    void jokeButtonClicked() {
        if (HttpUtils.isInternetAvailable(getActivity())) {
            loadNewJoke();
        } else {
            setJokeText(getActivity().getString(R.string.internet_not_available));
        }
    }

    // This annotation makes sure this method is ran in a background thread.
    @Background
    void loadNewJoke() {
        String joke;
        try {
            String jokeJsonString = HttpUtils.get(CHUCK_NORRIS_API_URL);
            JSONObject jokeJson = new JSONObject(jokeJsonString);
            if (jokeJson.getString(TYPE).equals(SUCCESS)) {
                joke = jokeJson.getJSONObject(VALUE).getString(JOKE);
            } else {
                joke = getActivity().getString(R.string.api_error);
            }
        } catch (IOException | JSONException | URISyntaxException e) {
            joke = getActivity().getString(R.string.joke_error);
            e.printStackTrace();
        }
        setJokeText(joke);
    }

    // This annotation makes sure this method is ran on the main/UI thread.
    @UiThread
    void setJokeText(String joke) {
        jokeTextView.setText(Html.fromHtml(joke));
    }

}
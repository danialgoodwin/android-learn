package net.simplyadvanced.blogreader;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import net.simplyadvanced.blogreader.ui.BlogWebViewActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

// Another URL to try: https://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=JSON
public class MainListActivity extends ListActivity {

    private static final String LOGCAT_TAG = "DEBUG: MainListActivity";

    private void logException(Exception e) {
        logException(e);
    }
    
//    protected String[] mBlogPostTitles;
    public static final int NUMBER_OF_POSTS = 20;
    protected JSONObject mBlogData;

    protected ProgressBar mProgressBar;

    private static final String KEY_TITLE = "title";
    private static final String KEY_AUTHOR = "author";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        if (isNetworkAvailable()) {
            mProgressBar.setVisibility(View.VISIBLE);
            GetBlogPostsTask getBlogPostsTask = new GetBlogPostsTask();
            getBlogPostsTask.execute();
        } else {
            Toast.makeText(this, "Network is unavailable", Toast.LENGTH_LONG).show();
        }

//        mBlogPostTitles = getResources().getStringArray(R.array.android_names);

//        ArrayAdapter<String> adapter =
//                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mBlogPostTitles);
//        setListAdapter(adapter);

//        String message = getString(R.string.no_items);
//        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        try {
            JSONArray jsonPosts = mBlogData.getJSONArray("posts");
            JSONObject jsonPost = jsonPosts.getJSONObject(position);
            String blogUrl = jsonPost.getString("url");
            Intent intent = new Intent(this, BlogWebViewActivity.class);
//            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(blogUrl));
            startActivity(intent);
        } catch (JSONException e) {
            logException(e);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        Fragment frag;

        return isAvailable;
    }

    private void handleBlogResponse() {
        mProgressBar.setVisibility(View.INVISIBLE);

        if (mBlogData == null) {
            updateDisplayForError();
        } else {
            try {
//                Log.d(LOGCAT_TAG, "mBlogData: " + mBlogData.toString(2));
                JSONArray jsonPosts = mBlogData.getJSONArray("posts");
                ArrayList<HashMap<String, String>> blogPosts =  new ArrayList<HashMap<String, String>>();
//                mBlogPostTitles = new String[jsonPosts.length()];

                for (int i = 0; i < jsonPosts.length(); i++) {
                    JSONObject post = jsonPosts.getJSONObject(i);
                    String title = post.getString(KEY_TITLE);
                    title = Html.fromHtml(title).toString();
                    String author = post.getString(KEY_AUTHOR);
                    author = Html.fromHtml(author).toString();
                    HashMap<String, String> blogPost = new HashMap<String, String>();
                    blogPost.put(KEY_TITLE, title);
                    blogPost.put(KEY_AUTHOR, author);
                    blogPosts.add(blogPost);
//                    mBlogPostTitles[i] = title;
                }

                String[] keys = {KEY_TITLE, KEY_AUTHOR};
                int[] ids = {android.R.id.text1, android.R.id.text2};
                SimpleAdapter adapter = new SimpleAdapter(this, blogPosts,
                        android.R.layout.simple_list_item_2, keys, ids);
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                        android.R.layout.simple_list_item_1, mBlogPostTitles);
                setListAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateDisplayForError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.error_title));
        builder.setMessage(getString(R.string.error_message));
        builder.setPositiveButton(android.R.string.ok, null);
//            builder.show();
        AlertDialog dialog = builder.create();
        dialog.show();

        TextView emptyTextView = (TextView) getListView().getEmptyView();
        emptyTextView.setText(getString(R.string.no_items));
    }


    public class GetBlogPostsTask extends AsyncTask<Object, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(Object[] params) {
            int responseCode = -1;
            JSONObject jsonResponse = null;

            try {
                URL blogFeedUrl = new URL("http://blog.teamtreehouse.com/api/get_recent_summary/?count=" + NUMBER_OF_POSTS);
                HttpURLConnection connection = (HttpURLConnection) blogFeedUrl.openConnection();
                connection.connect();
                responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    Reader reader = new InputStreamReader(inputStream);

                    int nextCharacter; // read() returns an int, we cast it to char later
                    String responseData = "";
                    while (true){ // Infinite loop, can only be stopped by a "break" statement
                        nextCharacter = reader.read(); // read() without parameters returns one character
                        if (nextCharacter == -1) { // A return value of -1 means that we reached the end
                            break;
                        }
                        responseData += (char) nextCharacter; // The += operator appends the character to the end of the string
                    }

                    // Old, doesn't work as well.
//                    int contentLength = connection.getContentLength(); // Possible bug if no content length is set by server.
//                    char[] charArray = new char[contentLength];
//                    reader.read(charArray);
//                    String responseData = new String(charArray);

                    Log.v(LOGCAT_TAG, responseData);

                    jsonResponse = new JSONObject(responseData);
//                    String status = jsonResponse.getString("status");
//                    Log.v(LOGCAT_TAG, "status: " + status);
//
//                    JSONArray jsonPosts = jsonResponse.getJSONArray("posts");
//                    for (int i = 0; i < jsonPosts.length(); i++) {
//                        JSONObject jsonPost = jsonPosts.getJSONObject(i);
//                        String title = jsonPost.getString("title");
//                        Log.v(LOGCAT_TAG, "Post " + i + ": title: " + title);
//                    }
                } else {
                    Log.i(LOGCAT_TAG, "Unsuccessful HTTP Response Code: " + responseCode);
                }
                Log.i(LOGCAT_TAG, "responseCode: " + responseCode);
            } catch (MalformedURLException e) {
                logException(e);
            } catch (IOException e) {
                logException(e);
            } catch (Exception e) {
                logException(e);
            }

            return jsonResponse;
//            return "Code: " + responseCode;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            mBlogData = result;
            handleBlogResponse();
        }

    }

}

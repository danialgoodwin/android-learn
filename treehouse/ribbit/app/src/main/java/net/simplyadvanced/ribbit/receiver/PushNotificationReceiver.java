package net.simplyadvanced.ribbit.receiver;

import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.parse.ParseAnalytics;
import com.parse.ParsePushBroadcastReceiver;

import net.simplyadvanced.ribbit.ui.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Danial on 12/13/2014.
 */
public class PushNotificationReceiver extends ParsePushBroadcastReceiver {

    private static final String LOGCAT_TAG = "DEBUG: PushNotificationReceiver";

    // TODOv2: When Parse API fixes there push notification, then this method shouldn't be needed
    // anymore. There is basically just one bit added: "&& !uriString.isEmpty()".
    // This was added because of the error:
    // android.content.ActivityNotFoundException: No Activity found to handle Intent { act=android.intent.action.VIEW dat= flg=0x1000c000 (has extras)
    // More info: http://stackoverflow.com/questions/26154855/exception-when-opening-parse-push-notification/26252760#26252760
    @Override
    protected void onPushOpen(Context context, Intent intent) {
        ParseAnalytics.trackAppOpened(intent);

        String uriString = null;
        try {
            JSONObject pushData = new JSONObject(intent.getStringExtra("com.parse.Data"));
            uriString = pushData.optString("uri");
        } catch (JSONException e) {
            Log.v("com.parse.ParsePushReceiver", "Unexpected JSONException when receiving push data: ", e);
        }
        Class<? extends Activity> cls = getActivity(context, intent);
        Intent activityIntent;
        if (uriString != null && !uriString.isEmpty()) {
            activityIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uriString));
        } else {
            activityIntent = new Intent(context, MainActivity.class);
        }
        activityIntent.putExtras(intent.getExtras());
        if (Build.VERSION.SDK_INT >= 16) {
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(cls);
            stackBuilder.addNextIntent(activityIntent);
            stackBuilder.startActivities();
        } else {
            activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(activityIntent);
        }
    }

    @Override
    protected Class<? extends Activity> getActivity(Context context, Intent intent) {
        Class<? extends Activity> activityClass = super.getActivity(context, intent);
        Log.d(LOGCAT_TAG, "activityClass: " + activityClass.getName());
        Class<? extends Activity> defaultActivityClass = MainActivity.class;
        return defaultActivityClass;
    }

}

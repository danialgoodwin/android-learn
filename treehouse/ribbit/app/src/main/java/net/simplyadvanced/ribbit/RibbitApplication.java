/**
 * Created by Danial on 12/8/2014.
 */
package net.simplyadvanced.ribbit;

import android.app.Activity;
import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.PushService;

import net.simplyadvanced.ribbit.ui.MainActivity;
import net.simplyadvanced.ribbit.util.ParseConstants;

public class RibbitApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "kda0WtjEf4mL5NxL27BS9IYX3PTkmY05HgPfwsNo", "YvMUQlwUwn7qUVsUaqWzvpDdbVWEd9cijqmffzDd");

        // Enable push notification and specify a default Activity to handle push notifications.
//        PushService.setDefaultPushCallback(this, MainActivity.class);
        // May or may not need. The quickstart didn't have it on 2014-12-13, but
        ParseInstallation.getCurrentInstallation().saveInBackground();


        // Test Parse.
//        ParseObject testObject = new ParseObject("TestObject");
//        testObject.put("foo", "bar");
//        testObject.saveInBackground();
    }

    public static void updateParseInstallation(ParseUser user) {
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put(ParseConstants.KEY_USER_ID, user.getObjectId());
        installation.saveInBackground();
    }

}

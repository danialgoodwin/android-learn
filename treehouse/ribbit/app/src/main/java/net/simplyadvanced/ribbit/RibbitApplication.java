/**
 * Created by Danial on 12/8/2014.
 */
package net.simplyadvanced.ribbit;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class RibbitApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "kda0WtjEf4mL5NxL27BS9IYX3PTkmY05HgPfwsNo", "YvMUQlwUwn7qUVsUaqWzvpDdbVWEd9cijqmffzDd");

//        ParseObject testObject = new ParseObject("TestObject");
//        testObject.put("foo", "bar");
//        testObject.saveInBackground();
    }
}

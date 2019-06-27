package com.kowbot.superuser;

import android.util.Log;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

import eu.chainfire.libsuperuser.Shell;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableNativeArray;

public class RNSuperUserModule extends ReactContextBaseJavaModule {
    public static final String TAG = "RNSuperUser";

    public RNSuperUserModule(ReactApplicationContext reactContext) {
        super(reactContext);

        Log.i(TAG, TAG + " init...");
    }

    @Override
    public String getName() {
        return TAG;
    }

    @ReactMethod
    public void isAvailable(Promise p) {
        p.resolve(Shell.SU.available());
    }

    @ReactMethod
    public void execute(String Command, Promise p) {
        if(Command == null || Command.equals("")) {
            p.reject("Invalid Command..");
        }

        final List<String> result = Shell.SU.run(Command);
        if(result == null) {
            p.reject("Command did not execute successfully or root access denied");
        } else {
            try {
                final JSONArray Data = new JSONArray(result);
                WritableArray array = new WritableNativeArray();

                for (int i = 0; i < Data.length(); i++) {
                    Object value = Data.get(i);
                    if (value instanceof String)  {
                        array.pushString((String) value);
                    } else {
                        array.pushString(value.toString());
                    }
                }
                p.resolve(array);
            } catch (JSONException e) {
                p.reject("Result Parse Error..");
            }
        }
    }
}
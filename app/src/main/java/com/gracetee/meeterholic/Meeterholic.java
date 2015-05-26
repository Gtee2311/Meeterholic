package com.gracetee.meeterholic;

import android.app.Application;
import com.parse.Parse;

/**
 * Created by Grace on 24/5/2015.
 */
public class Meeterholic extends Application {

    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "zSK1a4B3BvTM25OX53fv0dL4k2VyVh9qQY1gfiHF", "70ILY4Tja4EKFBkasOEDZQjtwxd94LxxPKXV0r5H");
    }

}

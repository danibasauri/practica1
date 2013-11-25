package com.ecoparque.objects;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by dcandelas on 25/11/13.
 */
public class NetInfo {
    private Activity mActivity;

    public NetInfo(Activity activity) {
        mActivity = activity;
    }


    public Boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager)
                mActivity.getSystemService(mActivity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}

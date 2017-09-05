package android.fastrack.udacity.easybaking;

import android.app.Application;
import android.fastrack.udacity.easybaking.utils.ConnectivityReceiver;

/**
 * Created by winzaldi on 9/5/17.
 */

public class BakingApplication extends Application  {
    private static BakingApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized BakingApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}

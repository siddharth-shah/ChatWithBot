package co.chatbot.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {

    private Context context;
    private static NetworkUtils instance;

    public NetworkUtils(Context context) {
        this.context = context.getApplicationContext();
        instance = this;
    }

    public synchronized static NetworkUtils getInstance(Context context) {
        if (instance == null) {
            new NetworkUtils(context);
        }

        return instance;
    }

    public boolean isConnected() {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }

        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

}
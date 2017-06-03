package br.com.nglauber.aula06_movies.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Util {

    public static boolean hasConnection(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager)
                ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }
}

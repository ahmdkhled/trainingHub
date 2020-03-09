package com.example.traininghub.helpers;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class SnackBarHelper {

    public static Snackbar showSnackBar(View v, int message, int duration) {
        Snackbar snackbar = Snackbar.make(v, message, duration);
        snackbar.show();
        return snackbar;
    }
    public static Snackbar showSnackBar(View v, String message, int duration) {
        Snackbar snackbar = Snackbar.make(v, message, duration);
        snackbar.show();
        return snackbar;
    }

    public static Snackbar showSnackBar(View v, int message, int duration
            , int action, View.OnClickListener onClickListener) {
        Snackbar snackbar = showSnackBar(v, message, duration);
        snackbar.setAction(action, onClickListener);
        return snackbar;

    }

}

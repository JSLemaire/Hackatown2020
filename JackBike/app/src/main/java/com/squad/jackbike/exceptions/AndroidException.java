package com.squad.jackbike.exceptions;

import android.app.Activity;
import android.widget.Toast;

public class AndroidException extends Exception {

    public AndroidException (Activity context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
